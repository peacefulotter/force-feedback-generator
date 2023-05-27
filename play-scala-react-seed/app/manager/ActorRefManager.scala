package manager

import akka.actor.{Actor, ActorRef, Props}

class ActorRefManager extends Actor {
	import manager.ActorRefManager.{Register, SendMessage, UnRegister}
	
	private[this] val actor: Option[ActorRef] = None
	
	def receive: PartialFunction[Any, Unit] = onMessage(actor)
	
	private def onMessage(actor: Option[ActorRef]): Receive = {
		case Register(actorRef) => context.become(onMessage(Some(actorRef)))
		case UnRegister(_) => context.become(onMessage(None))
		case SendMessage(message) => actor match {
			case Some(ref) => ref ! message
			case None => ;
		}
	}
}

object ActorRefManager {
	def props(): Props = Props[ActorRefManager]()
	case class SendMessage(message: String)
	case class Register(actorRef: ActorRef)
	case class UnRegister(actorRef: ActorRef)
}
