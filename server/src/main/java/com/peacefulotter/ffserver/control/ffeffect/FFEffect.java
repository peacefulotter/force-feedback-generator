package com.peacefulotter.ffserver.control.ffeffect;

import at.wisch.joystick.ffeffect.ConstantEffect;
import at.wisch.joystick.ffeffect.Effect;
import at.wisch.joystick.ffeffect.SineEffect;
import com.peacefulotter.ffserver.control.attributes.FFDirection;
import com.peacefulotter.ffserver.control.attributes.FFLevel;

import static at.wisch.joystick.ffeffect.Effect.NO_BUTTON;

public class FFEffect
{
    protected static final int effectDelay = 0;
    protected static final int buttonIndex = NO_BUTTON;
    protected static final int buttonInterval = 0;
    protected static final int attackLength = 0;
    protected static final int fadeLength = 300;

    private final Effect effect;

    public FFEffect(
            FFDirection direction, int effectLength, FFLevel previousLevel, FFLevel nextLevel,
            FFLevel level )
    {
        this.effect = new ConstantEffect(direction.getDirection(), effectLength,
                effectDelay, buttonIndex, buttonInterval, attackLength,fadeLength,
                previousLevel.getLevel(), nextLevel.getLevel(), level.getLevel() );
    }

    public FFEffect(
            FFDirection direction, int effectLength, FFLevel previousLevel, FFLevel nextLevel,
            int period, FFLevel magnitude, FFLevel offset, int phase)
    {
        this.effect = new SineEffect(direction.getDirection(), effectLength,
                effectDelay, buttonIndex, buttonInterval, attackLength, fadeLength,
                previousLevel.getLevel(), nextLevel.getLevel(), period, magnitude.getLevel(), offset.getLevel(), phase);
    }

    public Effect getEffect()
    {
        return effect;
    }
}
