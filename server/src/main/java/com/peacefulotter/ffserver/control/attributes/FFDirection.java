package com.peacefulotter.ffserver.control.attributes;

import at.wisch.joystick.ffeffect.direction.CartesianDirection;

public enum FFDirection
{
    NORTH( CartesianDirection.NORTH ),
    NORTHEAST( CartesianDirection.NORTHEAST ),
    EAST( CartesianDirection.EAST ),
    SOUTHEAST( CartesianDirection.SOUTHEAST ),
    SOUTH( CartesianDirection.SOUTH ),
    SOUTHWEST( CartesianDirection.SOUTHWEST ),
    WEST( CartesianDirection.WEST ),
    NORTHWEST( CartesianDirection.NORTHWEST );

    private final CartesianDirection direction;

    FFDirection(int[] direction)
    {
        this.direction = new CartesianDirection(direction);
    }

    public CartesianDirection getDirection()
    {
        return direction;
    }

    public static FFDirection fromIndex(int index)
    {
        if ( index <= 0 || index >= values().length )
            return values()[0]; // should throw exception
        return values()[index];
    }

    @Override
    public String toString()
    {
        return "[Direction] " + direction.getName() + "; " +
                direction.getCartesianXCoordinate() + " " +
                direction.getCartesianYCoordinate() + " " +
                direction.getCartesianZCoordinate();
    }
}
