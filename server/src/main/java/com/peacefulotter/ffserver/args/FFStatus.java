package com.peacefulotter.ffserver.args;

public class FFStatus
{
    private boolean active;
    private String name, description, ffDescription;
    private int gain;

    public FFStatus( boolean active, String name, String description, String ffDescription, int gain )
    {
        this.active = active;
        this.name = name;
        this.description = description;
        this.ffDescription = ffDescription;
        this.gain = gain;
    }

    public boolean isActive()
    {
        return active;
    }

    public void setActive( boolean active )
    {
        this.active = active;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription( String description )
    {
        this.description = description;
    }

    public String getFfDescription()
    {
        return ffDescription;
    }

    public void setFFDescription( String ffDescription )
    {
        this.ffDescription = ffDescription;
    }

    public int getGain()
    {
        return gain;
    }

    public void setGain( int gain )
    {
        this.gain = gain;
    }

    @Override
    public String toString()
    {
        return active + " " + name;
    }
}
