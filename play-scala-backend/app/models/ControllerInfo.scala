package models

import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._

case class ControllerInfo(
	name: String,
	data: Float,
	value: Float,
) {
	override def toString: String = Json.toJson(this).toString()
}

object ControllerInfo {
	implicit val ControllerInfoFormat: Format[ControllerInfo] = (
		(JsPath \ "name").format[String] and
		(JsPath \ "data").format[Float] and
		(JsPath \ "value").format[Float]
	)(ControllerInfo.apply, unlift(ControllerInfo.unapply))
}
