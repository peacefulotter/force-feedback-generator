package com.peacefulotter.ffserver.websocket;

public class FFPoll
{
    private float axisAngle;

    public FFPoll( float axisAngle )
    {
        this.axisAngle = axisAngle;
    }

    public float getAxisAngle()
    {
        return axisAngle;
    }

    public void setAxisAngle( float axisAngle )
    {
        this.axisAngle = axisAngle;
    }
}
