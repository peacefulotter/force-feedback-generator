package com.peacefulotter.ffserver.control;

import at.wisch.joystick.FFJoystick;
import at.wisch.joystick.JoystickManager;
import at.wisch.joystick.exception.FFJoystickException;
import com.peacefulotter.ffserver.control.attributes.FFDirection;
import com.peacefulotter.ffserver.control.attributes.FFLevel;
import com.peacefulotter.ffserver.control.ffeffect.FFEffect;

// -Djava.library.path="D:\Java\projects\ForceFeedback\ff\"
// D:\Java\projects\ForceFeedback
public class FFControl
{
    public static void launchFF()
    {
        FFJoystick joystick;
        try
        {
            JoystickManager.init();
            joystick = JoystickManager.getFFJoystick();
        } catch ( FFJoystickException e )
        {
            e.printStackTrace();
            return;
        }

        System.out.println(joystick.getFFDescription());
        System.out.println(joystick.getGain());
        System.out.println(joystick.getSupportedEffects());
        System.out.println(joystick.getSimpleEffect());

        FFEffect effect1 = createConstantEffect( FFDirection.LEFT, new FFLevel( 0 ), new FFLevel( -0.5 ));
        FFEffect effect2 = createConstantEffect(FFDirection.RIGHT, new FFLevel( -0.5 ), new FFLevel( 0 ));
        FFEffect effect3 = createConstantEffect(FFDirection.RIGHT, new FFLevel( 0 ), new FFLevel( 0.5 ));

        boolean x = joystick.newEffect( effect1.getEffect() );
        boolean y = joystick.newEffect( effect2.getEffect() );
        boolean z = joystick.newEffect( effect3.getEffect() );
        System.out.println(x + " " + y + " " + z);

        boolean a = joystick.playEffect( effect1.getEffect(), 1 );
        boolean b = joystick.playEffect( effect2.getEffect(), 1 );
        boolean c = joystick.playEffect( effect3.getEffect(), 1 );
        System.out.println(a + " " + b + " " + c);
    }

    private static FFEffect createConstantEffect(FFDirection direction, FFLevel previousLevel, FFLevel nextLevel )
    {
        int effectLength = 300;        // the effect length in ms (or INFINITE_LENGTH)
        FFLevel level = new FFLevel( 0.2 );
        return new FFEffect(direction, effectLength, previousLevel, nextLevel, level );
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
