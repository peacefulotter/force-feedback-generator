package com.peacefulotter.ffserver.control.attributes;

import at.wisch.joystick.ffeffect.direction.CartesianDirection;

public enum FFDirection
{
    LEFT( CartesianDirection.EAST ),
    NORTH( CartesianDirection.NORTH ),
    RIGHT( CartesianDirection.WEST );

    private final CartesianDirection direction;

    FFDirection(int[] direction)
    {
        this.direction = new CartesianDirection(direction);
    }

    public CartesianDirection getDirection()
    {
        return direction;
    }
}
