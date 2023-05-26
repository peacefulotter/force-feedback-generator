package controllers

import models.ControllerInfo
import net.java.games.input.{Component, Controller, ControllerEnvironment, Event}


object USBControllerPolling {
	
	private var controller: Option[Controller] = None
	
	def fetchController(): Unit = {
		val environment = ControllerEnvironment.getDefaultEnvironment
		val controllers = environment.getControllers
		controller = controllers.find( _.getType == Controller.Type.GAMEPAD )
	}
	
	def pollController(): Option[ControllerInfo] = controller match {
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
			Some(ControllerInfo(identifierName, component.getPollData, value))
		case None =>
			println("Controller None")
			None;
	}
}
