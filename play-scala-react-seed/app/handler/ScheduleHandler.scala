package handler

import akka.actor.{ActorRef, ActorSystem, Cancellable}
import manager.ActorRefManager.SendMessage

import scala.concurrent.ExecutionContext
import scala.concurrent.duration.DurationInt
import scala.language.postfixOps

class ScheduleHandler(system: ActorSystem, manager: ActorRef)(implicit context: ExecutionContext) {
	private var scheduled: Option[Cancellable] = None
	
	private def sendControllerData: Runnable = new Runnable() {
		def run(): Unit = {
			ControllerHandler.pollController() match {
				case Some(data) => manager ! SendMessage(data.toString)
				case None => ;
			}
		}
	}
	
	private def startScheduler(): Cancellable = system.scheduler.scheduleAtFixedRate(
		initialDelay = 0 microseconds,
		interval = 25 millisecond,
	)(runnable = sendControllerData)
	
	def toggleActive(): Boolean = scheduled match {
		case Some(s) =>
			if (s.cancel()) {
				scheduled = None
				true
			}
			else false
		case _ =>
			scheduled = Some(startScheduler())
			true
	}
	
	override def toString: String = scheduled match {
		case Some(s) => s.toString
		case None => "Not scheduled"
	}
}
