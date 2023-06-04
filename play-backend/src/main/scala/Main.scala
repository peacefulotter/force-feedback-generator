import at.wisch.joystick.{FFJoystick, Joystick, JoystickManager}
import at.wisch.joystick.exception.FFJoystickException

import scala.annotation.tailrec

object Main {

	// install instructions: http://boat.lachsfeld.at/ffjoystick4java/#install_windows|region
	def getJoystick: Option[Joystick] = try {
		val dir = System.getProperty("user.dir")
		System.load(dir + "\\lib\\SDL.dll")
		System.load(dir + "\\lib\\ffjoystick.dll")
		JoystickManager.init()
		JoystickManager.getAllJoysticks.forEach(println)
		// JoystickManager.getAllFFJoystick
		Some(JoystickManager.getJoystick)
	} catch {
		case e: FFJoystickException =>
			e.printStackTrace()
			None
	}
	
	@tailrec
	def poll(joystick: Joystick): Unit = {
		joystick.poll()
		val lt_rt = joystick.getAxisValue(0)
		val rx = joystick.getAxisValue(1)
		val ry = joystick.getAxisValue(2)
		val lx = joystick.getAxisValue(3)
		val ly = joystick.getAxisValue(4)
		printf(f"\raxis=${joystick.getAxisCount}, lx=$lx%1.2f, ly=$ly%1.2f, rx=$rx%1.2f, ry=$ry%1.2f")
		// POV = arrow btns
		// println(f"====, lx=${joystick.getPov1X}, ly=${joystick.getPov1Y}")
		Thread.sleep(100)
		poll(joystick)
	}

	def main(args: Array[String]): Unit = {
		getJoystick match {
			case Some(j) => poll(j)
			case None => ;
		}
	}
}