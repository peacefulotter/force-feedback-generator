package models

import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._

case class ControllerData(
	name: String,
	data: Float,
	value: Float,
) {
	override def toString: String = Json.toJson(this).toString()
}

object ControllerData {
	implicit val ControllerDataFormat: Format[ControllerData] = (
		(JsPath \ "name").format[String] and
		(JsPath \ "data").format[Float] and
		(JsPath \ "value").format[Float]
	)(ControllerData.apply, unlift(ControllerData.unapply))
}
