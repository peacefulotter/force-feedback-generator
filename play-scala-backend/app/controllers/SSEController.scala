package controllers

import akka.Done

import javax.inject.{Inject, Singleton}
import akka.actor.{ActorRef, ActorSystem, Cancellable}
import akka.stream.{CompletionStrategy, OverflowStrategy}
import akka.stream.scaladsl.Source
import manager.ActorRefManager
import manager.ActorRefManager._
import models.Message
import play.api.http.ContentTypes
import play.api.libs.EventSource
//import play.api.libs.functional.syntax.{toFunctionalBuilderOps, unlift}
//import play.api.libs.json.{JsPath, Json, Writes}
//import play.filters.csrf.{CSRF, CSRFAddToken}
import play.api.mvc._

import java.awt.MouseInfo
import scala.concurrent.ExecutionContext
import scala.concurrent.duration._
import scala.language.postfixOps

@Singleton
class SSEController @Inject()(
	                             system: ActorSystem,
	                             cc: ControllerComponents,
	                             /*addToken: CSRFAddToken*/)
                             (implicit executionContext: ExecutionContext)
	extends AbstractController(cc) {
	
	private[this] val manager = system.actorOf(ActorRefManager.props())
	
//	implicit val CSRFTokenWrites: Writes[CSRF.Token] = (
//		(JsPath \ "name").write[String] and
//		(JsPath \ "value").write[String]
//	)(unlift(CSRF.Token.unapply))
//
//	def token(): Action[AnyContent] = addToken( Action { implicit request =>
//		val token = CSRF.getToken.get
//		val json = Json.toJson(token)
//		Ok(json)
//	} )
	
	private def sendMouseLocation: Runnable = new Runnable() {
		def run(): Unit = {
			// You can replace the Message object by anything you want to send to the frontend through SSE
			val loc = MouseInfo.getPointerInfo.getLocation
			val msg = Message(loc.x, loc.y, System.currentTimeMillis()).toString
			manager ! SendMessage(msg)
		}
	}
	
	private var scheduled: Option[Cancellable] = None
	
	private def startScheduler(): Cancellable = system.scheduler.scheduleAtFixedRate(
		initialDelay = 0 microseconds,
		interval = 10 millisecond,
	)(runnable = sendMouseLocation)
	
	def receiveMessage(): Action[AnyContent] = Action.apply { request =>
		println(f"received message, scheduled: $scheduled")
		scheduled match {
			case Some(s) => s.cancel(); scheduled = None;
			case _ => scheduled = Some(startScheduler());
		}
		Ok
	}
	
	def sse(): Action[AnyContent] = Action {
		val source: Source[String, ActorRef] = Source
			.actorRef(
				completionMatcher = {
					case Done => CompletionStrategy.immediately
				},
				failureMatcher = PartialFunction.empty,
				bufferSize = 32,
				overflowStrategy = OverflowStrategy.dropHead
			)
			.watchTermination() { case (actorRef, terminate) =>
				manager ! Register(actorRef)
				terminate.onComplete(_ => manager ! UnRegister(actorRef))
				actorRef
			}
		Ok.chunked(source via EventSource.flow).as(ContentTypes.EVENT_STREAM)
	}
}

