package com.peacefulotter.ffserver.control;

import at.wisch.joystick.FFJoystick;
import at.wisch.joystick.JoystickManager;
import at.wisch.joystick.exception.FFJoystickException;
import com.peacefulotter.ffserver.FFParams;
import com.peacefulotter.ffserver.FFStatus;
import com.peacefulotter.ffserver.control.attributes.FFDirection;
import com.peacefulotter.ffserver.control.attributes.FFLevel;
import com.peacefulotter.ffserver.control.ffeffect.FFEffect;

// -Djava.library.path="D:\Java\projects\ForceFeedback\ff\"
// D:\Java\projects\ForceFeedback
public class FFControl
{
    private static FFJoystick joystick;

    public static void initControls()
    {
        try
        {
            JoystickManager.init();
            FFControl.joystick = JoystickManager.getFFJoystick();
        } catch ( FFJoystickException e )
        {
            e.printStackTrace();
            return;
        }

        System.out.println(joystick.getFFDescription());
        System.out.println(joystick.getGain());
        System.out.println(joystick.getSupportedEffects());
        System.out.println(joystick.getSimpleEffect());
    }

    public static FFStatus getStatus()
    {
        boolean active = true;
        String name = joystick.getName();
        float axisAngle = joystick.getXAxisValue();
        return new FFStatus( active, name, axisAngle );
    }

    public static boolean launchFF( FFParams params )
    {
        FFEffect effect = createConstantEffect( params );
        boolean created = joystick.newEffect( effect.getEffect() );
        if ( !created ) return false;
        boolean played = joystick.playEffect( effect.getEffect(), 1 );
        System.out.println(played);
        return played;
    }

    private static FFEffect createConstantEffect( FFParams params )
    {
        FFDirection direction = FFDirection.fromIndex( params.getDirection() );
        int effectLength = params.getEffectLength(); // the effect length in ms (or INFINITE_LENGTH)
        FFLevel previousLevel = new FFLevel( params.getPreviousLevel() );
        FFLevel nextLevel = new FFLevel( params.getNextLevel() );
        FFLevel level = new FFLevel( params.getLevel() );
        return new FFEffect( direction, effectLength, previousLevel, nextLevel, level );
    }

    private static FFEffect createSineEffect()
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
    }
}
