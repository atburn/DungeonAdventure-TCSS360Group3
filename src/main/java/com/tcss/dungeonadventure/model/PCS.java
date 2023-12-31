package com.tcss.dungeonadventure.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * This class contains constants for the Property Chance Listener.
 *
 * @author Aaron Burnham
 * @author Sunny Ali
 * @author Hieu Doan
 * @version TCSS 360 - Fall 2023
 */
public enum PCS {

    /**
     * The property to log a message in the GUI's logger.
     * Should be paired with a {@link String} as the message
     * to display.
     */
    LOG,

    /**
     * The property when the players inventory changes.
     * Should be paired with a Map<Item, Integer>, representing
     * the players current inventory.
     */
    ITEMS_CHANGED,


    /**
     * The property to log a message in the CombatGUI's logger.
     * Should be paired with a {@link String} as the message
     * to display.
     */
    COMBAT_LOG,



    /**
     * The property of loading a new room.
     * Should be paired with {@link Room}
     */
    LOAD_ROOM,

    /**
     * The property of player location change.
     * Not to be confused with MOVE_PLAYER.
     * <p>
     * Should be paired with nothing, as player location is
     * stored within the room.
     */
    UPDATED_PLAYER_LOCATION,

    /**
     * The property of discovering new room(s)
     * by going into them or using the vision potion.
     */
    ROOMS_DISCOVERED,


    /**
     * The property of when actions are performed by either the
     * player or the monster, which synchronizes the health in the GUI.
     * Should be paired with the monster.
     */
    SYNC_COMBAT,

    /**
     * The property of loading a combat sequence between
     * the player and a monster.
     * Should be paired with a Monster that the player is fighting.
     */
    BEGIN_COMBAT,


    /**
     * The property of when a combat sequence is over.
     * Should be paired with null.
     */
    END_COMBAT,


    /**
     * The property of when the game ends.
     * Should be paired with a Boolean representing if
     * the player was victorious (true), or failed (false).
     */
    GAME_END,

    /**
     * The property of the entire view of the dungeon.
     */
    CHEAT_CODE,
    /**
     * The property to toggle when the user can do an action during combat.
     * Should be paired with a boolean, true if the user can attack.
     */
    TOGGLE_COMBAT_LOCK;

    /**
     * The Property Change Listener for DungeonAdventure.
     */
    private static final PropertyChangeSupport PCS = new PropertyChangeSupport(PCS.class);


    /**
     * Fires a change of property to the listeners
     * with the current and old value.
     *
     * @param theProperty the changed property
     * @param theOldValue the old value of that property
     * @param theNewValue the new value of that property
     */
    public static void firePropertyChanged(final PCS theProperty,
                                           final Object theOldValue,
                                           final Object theNewValue) {

        PCS.firePropertyChange(theProperty.name(), theOldValue, theNewValue);
    }

    /**
     * Fires a change of property to the listeners with the new value.
     *
     * @param theProperty the changed property
     * @param theNewValue the new value of that property
     */
    public static void firePropertyChanged(final PCS theProperty,
                                           final Object theNewValue) {

        firePropertyChanged(theProperty, null, theNewValue);
    }

    /**
     * Adds a listener to the list of listeners
     * such that it has only one occurrence in the list.
     *
     * @param theListener the listener to be added
     */
    public static void addPropertyListener(final PropertyChangeListener theListener) {
        PCS.removePropertyChangeListener(theListener);
        PCS.addPropertyChangeListener(theListener);
    }
}
