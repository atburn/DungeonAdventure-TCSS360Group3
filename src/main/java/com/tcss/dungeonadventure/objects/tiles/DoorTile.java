package com.tcss.dungeonadventure.objects.tiles;


import com.tcss.dungeonadventure.objects.Directions;
import com.tcss.dungeonadventure.objects.TileChars;

public class DoorTile extends Tile {

    public DoorTile(final Directions.Axis axis) {
        super(axis == (Directions.Axis.VERTICAL)
                        ? TileChars.Room.VERTICAL_DOOR
                        : TileChars.Room.HORIZONTAL_DOOR,
                true);
    }


}