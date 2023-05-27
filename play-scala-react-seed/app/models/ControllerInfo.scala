package models

import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._

case class ControllerInfo(
	name: String,
	controllerType: String,
	portType: String
) {
	override def toString: String = Json.toJson(this).toString()
}

object ControllerInfo {
	implicit val ControllerInfoFormat: Format[ControllerInfo] = (
		(JsPath \ "name").format[String] and
		(JsPath \ "controllerType").format[String] and
		(JsPath \ "portType").format[String]
	)(ControllerInfo.apply, unlift(ControllerInfo.unapply))
	
	implicit val ControllerInfoWrite: Writes[ControllerInfo] = (
		(JsPath \ "name").format[String] and
		(JsPath \ "controllerType").format[String] and
		(JsPath \ "portType").format[String]
	)(ControllerInfo.apply, unlift(ControllerInfo.unapply))
}
