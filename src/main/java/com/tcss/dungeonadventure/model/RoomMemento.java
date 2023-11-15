package com.tcss.dungeonadventure.model;

import com.tcss.dungeonadventure.objects.items.Item;
import com.tcss.dungeonadventure.objects.tiles.Tile;
import java.awt.Point;

/**
 * Represents a snapshot of the state of a room in the dungeon.
 * It includes a copy of the room data, player position,
 * and the state of a specific item (pillar).
 * @author Sunny, Aaron, Hieu
 * @version Fall 2023
 */


public class RoomMemento {
    /**
     * The saved room data, representing the layout of tiles in the room.
     */
    private final Tile[][] mySavedRoomData;

    /**
     * The saved player position in the room.
     */
    private final Point mySavedPlayerPosition;

    /**
     * The saved state of a specific item (pillar) in the room.
     */
    private final Item mySavedPillar;

    /**
     * Constructs a RoomMemento with the specified room data, player position,
     * and pillar state.
     *
     * @param theRoomData      The original room data to be saved.
     * @param thePlayerPosition The original player position to be saved.
     * @param thePillar         The original state of the pillar to be saved.
     */
    public RoomMemento(final Tile[][] theRoomData,
                       final Point thePlayerPosition, final Item thePillar) {
        mySavedRoomData = deepCopyRoomData(theRoomData);
        mySavedPlayerPosition = new Point(thePlayerPosition);
        mySavedPillar = (thePillar != null) ? thePillar.copy() : null;
    }

    /**
     * Gets a deep copy of the saved room data.
     *
     * @return A deep copy of the saved room data.
     */
    public Tile[][] getSavedRoomData() {
        return deepCopyRoomData(mySavedRoomData);
    }

    /**
     * Gets a copy of the saved player position.
     *
     * @return A copy of the saved player position.
     */
    public Point getSavedPlayerPosition() {
        return new Point(mySavedPlayerPosition);
    }

    /**
     * Gets a copy of the saved pillar state.
     *
     * @return A copy of the saved pillar state, or null if there is no pillar.
     */
    public Item getSavedPillar() {
        if (mySavedPillar != null) {
            return mySavedPillar.copy();
        } else {
            return null;
        }
    }


    /**
     * Deep copies the original room data.
     *
     * @param theOriginalRoomData The original room data to be copied.
     * @return A deep copy of the original room data.
     */
    private Tile[][] deepCopyRoomData(final Tile[][] theOriginalRoomData) {
        final Tile[][] copy = new Tile[theOriginalRoomData.length][];
        for (int i = 0; i < theOriginalRoomData.length; i++) {
            copy[i] = new Tile[theOriginalRoomData[i].length];
            for (int j = 0; j < theOriginalRoomData[i].length; j++) {
                if (theOriginalRoomData[i][j] != null) {
                    copy[i][j] = theOriginalRoomData[i][j].copy();
                }
            }
        }
        return copy;
    }
}
