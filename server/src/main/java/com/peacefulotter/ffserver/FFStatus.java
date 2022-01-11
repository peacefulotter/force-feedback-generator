package com.peacefulotter.ffserver;

public class FFStatus
{
    private boolean active;
    private String name;
    private float axisAngle;

    public FFStatus( boolean active, String name, float axisAngle )
    {
        this.active = active;
        this.name = name;
        this.axisAngle = axisAngle;
    }

    public boolean isActive() { return active; }
    public String getName() { return name; }
    public float getAxisAngle() { return axisAngle; }

    public void setActive(boolean active) { this.active = active; }
    public void setName(String name) { this.name = name; }
    public void setAxisAngle(float axisAngle) { this.axisAngle = axisAngle; }

    @Override
    public String toString()
    {
        return active + " " + name + " " + axisAngle;
    }
}
