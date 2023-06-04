import at.wisch.joystick.exception.FFJoystickException
import at.wisch.joystick.ffeffect.direction.{Direction, PolarDirection}
import at.wisch.joystick.ffeffect.{ConstantEffect, Effect, SineEffect}
import at.wisch.joystick.{FFJoystick, Joystick, JoystickManager}

import java.util
import scala.annotation.tailrec
import scala.collection.mutable
import scala.language.postfixOps

object FFMain {

	// install instructions: http://boat.lachsfeld.at/ffjoystick4java/#install_windows|region
	def getJoystick: Option[FFJoystick] = try {
		val dir = System.getProperty("user.dir")
		System.load(dir + "\\lib\\SDL.dll")
		System.load(dir + "\\lib\\ffjoystick.dll")
		JoystickManager.init()
		JoystickManager.getAllJoysticks.forEach(println)
		val joystick = JoystickManager.getFFJoystick
		joystick.getSupportedEffects.forEach(println)
		Some(joystick)
	} catch {
		case e: FFJoystickException =>
			e.printStackTrace()
			None
	}
	
	def info(joystick: FFJoystick): Unit = {
		val name = joystick.getName
		val effects = joystick.getSupportedEffects
		val desc = joystick.getFFDescription
		println(f"name=$name, desc=$desc, effects=$effects")
	}
	
	def ff(joystick: FFJoystick, eff: Effect): Unit = {
		 // joystick.getSimpleEffect
		//		eff.setEffectLength(3000)
		//		eff.setStrength(32767)
		if ( !joystick.setAutoCenter(0) )
			println("autocenter not working")

		println(f" uploading effect..., effect=$eff")
		if ( !joystick.newEffect(eff) )
		{
			println("effect not working")
			return
		}
		println(" playing effect...")
		if (!joystick.playEffect(eff, 1))
			println("not playing")
	}

	@tailrec
	def poll(joystick: FFJoystick, effects: Array[Effect]): Unit = {
		joystick.poll()
		
		val throttle = joystick.getAxisValue(0)
		val brake = joystick.getAxisValue(1)
		val clutch = joystick.getAxisValue(2)
		val wheel = joystick.getAxisValue(3)
		
		println(f"axis=${joystick.getAxisCount}, wheel=$wheel%1.1f, throttle=$throttle%1.1f, brake=$brake%1.1f, clutch=$clutch%1.1f")
		// POV = arrow btns
		// println(f"====, lx=${joystick.getPov1X}, ly=${joystick.getPov1Y}")
		ff(joystick, effects.head)
		Thread.sleep(1000)
		poll(joystick, effects.tail)
	}
	
	def getEffects(joystick: FFJoystick): Array[Effect] = {
		joystick.getSupportedEffects.toArray.map( eff => {
			val clazz = eff.asInstanceOf[Class[_ <: Effect]]
			clazz.getDeclaredConstructor().newInstance()
		} )
	}

	def main(args: Array[String]): Unit = {
		getJoystick match {
			case Some(j) =>
				info(j)
				poll(j, getEffects(j))
			case None => ;
		}
	}
}