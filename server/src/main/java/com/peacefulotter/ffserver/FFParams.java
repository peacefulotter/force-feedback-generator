package com.peacefulotter.ffserver;

public class FFParams
{
    private String type;
    private int direction;
    private int effectLength;
    private double level;

    public FFParams( String type, int direction, int effectLength, double level )
    {
        this.type = type;
        this.direction = direction;
        this.effectLength = effectLength;
        this.level = level;
    }

    public String getType() { return type; }
    public int getDirection() { return direction; }
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
    public void setLevel( double level ) { this.level = level; }

    @Override
    public String toString()
    {
        return  "[" + type + "] direction: " +
                direction + "; effectLength: " +
                effectLength + "ms; level: " +
                level;
    }
}
