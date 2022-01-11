package com.peacefulotter.ffserver.control.attributes;

public class FFLevel
{
    private static final int LEVEL_RANGE = 32767;

    private final int ffLevel;

    /**
     * Converts a force in the range [-1, 1] to a force feedback level range [-LEVEL_RANGE, LEVEL_RANGE]
     * @param level: force to apply to the steering wheel in the range [-1, 1]
     * @return force feedback level in the range [-LEVEL_RANGE, LEVEL_RANGE]
     */
    public FFLevel( double level )
    {
        ffLevel = (int) Math.round( Math.min(Math.max( level, -1 ), 1) * LEVEL_RANGE );
    }

    public int getLevel()
    {
        return ffLevel;
    }

    @Override
    public String toString()
    {
        return "[FFLevel] " + ffLevel;
    }
}
