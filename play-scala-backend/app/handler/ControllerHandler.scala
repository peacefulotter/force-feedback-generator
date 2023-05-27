package handler

import models.{ControllerInfo, ControllerData}
import net.java.games.input.{Controller, ControllerEnvironment, Event}


object ControllerHandler {
	
	private var controller: Option[Controller] = None
	
	def fetchController(): Option[ControllerInfo] = {
		val environment = ControllerEnvironment.getDefaultEnvironment
		val controllers = environment.getControllers
		controller = controllers.find( _.getType == Controller.Type.GAMEPAD )
		controller match {
			case Some(c) => Some(ControllerInfo(c.getName, c.getType.toString, c.getPortType.toString))
			case _ => None
		}
	}
	
	def pollController(): Option[ControllerData] = controller match {
		case Some(c) =>
			if (!c.poll()) {
				println("Controller no longer valid")
				return None
			}
			
			val eventQueue = c.getEventQueue
			val event: Event = new Event()
			if (!eventQueue.getNextEvent(event)) return None
			val component = event.getComponent
			if (component == null) return None;
			val identifierName = component.getIdentifier.getName
			val value = event.getValue
			Some(ControllerData(identifierName, component.getPollData, value))
		case None =>
			println("Controller None")
			None;
	}
}
