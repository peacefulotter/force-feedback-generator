package com.peacefulotter.ffserver.control;

import at.wisch.joystick.FFJoystick;
import at.wisch.joystick.JoystickManager;
import at.wisch.joystick.exception.FFJoystickException;
import at.wisch.joystick.exception.JoystickNotFoundException;
import com.peacefulotter.ffserver.args.FFAngle;
import com.peacefulotter.ffserver.args.FFParams;
import com.peacefulotter.ffserver.websocket.FFPoll;
import com.peacefulotter.ffserver.args.FFStatus;
import com.peacefulotter.ffserver.control.attributes.FFDirection;
import com.peacefulotter.ffserver.control.attributes.FFLevel;
import com.peacefulotter.ffserver.control.ffeffect.FFEffect;

// -Djava.library.path="D:\Java\projects\ForceFeedback\ff\"
// D:\Java\projects\ForceFeedback
public class FFControl
{
    private static boolean initialized;
    private static FFJoystick joystick;

    public static boolean initControls()
    {
        try
        {
            JoystickManager.init();
            FFControl.joystick = JoystickManager.getFFJoystick();
        } catch ( Exception e )
        {
            e.printStackTrace();
            return false;
        }

        initialized = true;
        return true;
    }

    public static FFStatus getStatus()
    {
        if ( !initialized )
            return new FFStatus( false, "","", "", 0 );

        String name = joystick.getName();
        String description = joystick.getDescription();
        String ffDescription = joystick.getFFDescription();
        int gain = joystick.getGain();

        return new FFStatus( true, name, description, ffDescription, gain );
    }

    public static FFPoll getPoll()
    {
        if ( !initialized )
            return new FFPoll( 0 );

        joystick.poll();
        float axisAngle = joystick.getRXAxisValue();
        return new FFPoll( axisAngle );
    }

    public static boolean launchFF( FFParams params )
    {
        if ( !initialized )
            return false;

        FFEffect effect = createConstantEffect( params );
        boolean created = joystick.newEffect( effect.getEffect() );
        if ( !created ) return false;
        boolean played = joystick.playEffect( effect.getEffect(), 1 );
        System.out.println(played);
        return played;
    }

    public static boolean moveAngle( FFAngle angle )
    {
        if ( !initialized )
            return false;

        joystick.poll();

        System.out.println(joystick.getAutoCenter());
        System.out.println(joystick.isAutocenterSupported());
        int maxNumEffects = 10;

        float axisAngle = joystick.getRXAxisValue();
        float delta = angle.getAngle() - axisAngle;
        int time = (int) (Math.abs( delta ) * 200);
        System.out.println("Angle: " + angle.getAngle() + " Axis: " + axisAngle + " Delta: " + delta + " Time: " + time );
        FFParams params = new FFParams( "constant", 0, 100, delta );
        return launchFF( params );
    }

    private static FFEffect createConstantEffect( FFParams params )
    {
        FFDirection direction = FFDirection.fromIndex( params.getDirection() );
        int effectLength = params.getEffectLength(); // the effect length in ms (or INFINITE_LENGTH)
        FFLevel level = new FFLevel( params.getLevel() );
        return new FFEffect( direction, effectLength, level );
    }

    /*private static FFEffect createSineEffect()
    {
        FFDirection direction = FFDirection.NORTH;
        int effectLength = 10000;        // the effect length in ms (or INFINITE_LENGTH)
        FFLevel previousLevel = new FFLevel( 0 );            // the level from where to start the fade-in
        FFLevel nextLevel = new FFLevel( 0 );              // the level where the fade-out fades to
        int period = 400; //  the period of the wave in ms
        FFLevel magnitude = new FFLevel( 0.25 ); // the magnitude (peak value of the wave)
        FFLevel offset = new FFLevel( 0 ); // the offset (mean value of the wave)
        int phase = 10000; // the phase (horizontal cycle shift in hundredth of a degree)
        return new FFEffect(direction, effectLength, previousLevel, nextLevel, period, magnitude, offset, phase);
    }*/
}
