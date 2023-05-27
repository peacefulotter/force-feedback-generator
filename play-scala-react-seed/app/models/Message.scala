package models

import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._

case class Message(
	x: Int,
	y: Int,
	timestamp: Long
) {
	override def toString: String = Json.toJson(this).toString()
}

object Message {
	implicit val messageFormat: Format[Message] = (
		(JsPath \ "x").format[Int] and
		(JsPath \ "y").format[Int] and
		(JsPath \ "timestamp").format[Long]
	)(Message.apply, unlift(Message.unapply))
}
