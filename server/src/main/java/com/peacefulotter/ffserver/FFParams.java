package com.peacefulotter.ffserver;

import com.peacefulotter.ffserver.control.attributes.FFDirection;
import com.peacefulotter.ffserver.control.attributes.FFLevel;

public class FFParams
{
    private String type;
    private int direction;
    private int effectLength;
    private double previousLevel, nextLevel, level;

    public FFParams( String type, int direction, int effectLength, double previousLevel, double nextLevel, double level )
    {
        this.type = type;
        this.direction = direction;
        this.effectLength = effectLength;
        this.previousLevel =  previousLevel;
        this.nextLevel = nextLevel;
        this.level = level;
    }

    public String getType() { return type; }
    public int getDirection() { return direction; }
    public double getPreviousLevel() { return previousLevel; }
    public double getNextLevel() { return nextLevel; }
    public double getLevel() { return level; }
    public int getEffectLength() { return effectLength; }

    public void setType( String type )
    {
        this.type = type;
    }
    public void setDirection( int direction )
    {
        this.direction = direction;
    }
    public void setEffectLength( int effectLength ) { this.effectLength = effectLength; }
    public void setPreviousLevel( double previousLevel )
    {
        this.previousLevel = previousLevel;
    }
    public void setNextLevel( double nextLevel )
    {
        this.nextLevel = nextLevel;
    }
    public void setLevel( double level ) { this.level = level; }

    @Override
    public String toString()
    {
        return  "[" + type + "] " +
                direction + " " +
                effectLength + "ms " +
                previousLevel + " " +
                nextLevel + " " +
                level;
    }
}
