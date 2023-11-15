package com.tcss.dungeonadventure.model;


import com.tcss.dungeonadventure.objects.Directions;
import com.tcss.dungeonadventure.objects.heroes.Hero;
import com.tcss.dungeonadventure.objects.tiles.EntranceTile;
import com.tcss.dungeonadventure.objects.tiles.Tile;
import com.tcss.dungeonadventure.view.GUIHandler;
import javafx.application.Application;

import java.awt.Point;


public class DungeonAdventure {

    /**
     * Singleton instance for DungeonAdventure.
     */
    private static DungeonAdventure INSTANCE;


    /**
     * The name of the player.
     */
    private String myPlayerName;

    /**
     * The class of the hero.
     */
    private Hero myHero;

    /**
     * The current Dungeon.
     */
    private Dungeon myDungeon;


    private DungeonAdventure() {
    }

    /**
     * Lazy singleton accessor.
     *
     * @return The instance of the DungeonAdventure.
     */
    public static DungeonAdventure getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DungeonAdventure();
        }
        return INSTANCE;
    }

    public void initialize() {
        /*
        * I have absolutely no clue why I couldn't throw this in the
        * constructor.
        * */
        Application.launch(GUIHandler.class);
    }



    /**
     * Starts a NEW game with the specified hero name and hero class.
     *
     * @param thePlayerName The name of the player.
     * @param theHero       The hero class.
     */
    public void startNewGame(final String thePlayerName, final Hero theHero) {
        this.myPlayerName = thePlayerName;
        this.myHero = theHero;
        this.myDungeon = new Dungeon();

        final Room startingRoom = myDungeon.getStartingRoom();
        final Tile[][] roomTiles = startingRoom.getRoomTiles();


        // This locates the entrance tile in the entrance room.
        Point entranceTileLocation = null;
        loop:
        for (int i = 0; i < roomTiles.length; i++) {
            for (int j = 0; j < roomTiles[i].length; j++) {
                if (roomTiles[i][j].getClass() == EntranceTile.class) {
                    entranceTileLocation = new Point(i, j);
                    break loop;
                }
            }
        }
        if (entranceTileLocation == null) {
            throw new IllegalStateException("Could not find EntranceTile in starting room.");
        }

        this.myDungeon.loadPlayerTo(
                startingRoom.getDungeonLocation(),
                entranceTileLocation);

        PCS.firePropertyChanged(PCS.LOAD_ROOM, myDungeon.getStartingRoom());
    }

    public void movePlayer(final Directions.Cardinal theDirection) {
        this.myDungeon.getCurrentRoom().movePlayer(theDirection);
        PCS.firePropertyChanged(PCS.UPDATED_PLAYER_LOCATION, null);
    }

    public Room getCurrentRoom() {
        return this.myDungeon.getCurrentRoom();
    }

    public DungeonAdventureMemento createMemento() {
        final String playerName = this.myPlayerName;
        final Hero hero = this.myHero;
        final Dungeon dungeon = this.myDungeon;

        final DungeonAdventureMemento memento;
        memento = new DungeonAdventureMemento(playerName, hero, dungeon);
        memento.addRoomMemento(myDungeon.getCurrentRoom().saveToMemento());

        return memento;
    }
    public void saveGameState() {
        // Create and save a memento
        final DungeonAdventureMemento memento = createMemento();
        GameStateManager.getInstance().setMemento(memento);
    }

    public void loadGameState() {
        // Load and restore the saved memento
        final DungeonAdventureMemento memento = GameStateManager.getInstance().getMemento();
        if (memento != null) {
            restoreFromMemento(memento);
        }
    }
    // Restore the state from a Memento
    public void restoreFromMemento(final DungeonAdventureMemento theMemento) {
        this.myPlayerName = theMemento.getSavedPlayerName();
        this.myHero = theMemento.getSavedHero();
        this.myDungeon = theMemento.getSavedDungeon();

        // Restore the current room
        RoomMemento roomMemento = theMemento.getRoomMementos().get(0);
        myDungeon.getCurrentRoom().restoreFromMemento(roomMemento);

        PCS.firePropertyChanged(PCS.LOAD_ROOM, myDungeon.getCurrentRoom());
    }
}



