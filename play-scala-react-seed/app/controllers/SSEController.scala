package controllers

import akka.Done
import akka.actor.{ActorRef, ActorSystem}
import akka.stream.scaladsl.Source
import akka.stream.{CompletionStrategy, OverflowStrategy}
import handler.{ControllerHandler, ScheduleHandler}
import manager.ActorRefManager
import manager.ActorRefManager.{Register, UnRegister}
import play.api.http.ContentTypes
import play.api.libs.EventSource
import play.api.libs.json.Json
import play.api.mvc._

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext
import scala.language.postfixOps


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

@Singleton
class SSEController @Inject()(
	                             system: ActorSystem,
	                             cc: ControllerComponents,
	                             /*addToken: CSRFAddToken*/)
                             (implicit executionContext: ExecutionContext)
	extends AbstractController(cc) {
	
	private[this] val manager = system.actorOf(ActorRefManager.props())
	private[this] val scheduler = new ScheduleHandler(system, manager)

	def controller(): Action[AnyContent] = Action {
		ControllerHandler.fetchController() match {
			case Some(info) => Ok(Json.toJson(info))
			case None => NotFound
		}
	}
	
	def toggleActive(): Action[AnyContent] = Action {
		println(f"received message, scheduled: ${scheduler}")
		scheduler.toggleActive()
		Ok(Json.toJson("ok"))
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

