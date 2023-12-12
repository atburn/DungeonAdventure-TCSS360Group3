package com.tcss.dungeonadventure.view;

import com.tcss.dungeonadventure.model.DungeonAdventure;
import javafx.scene.Node;
import javafx.scene.control.Button;


/**
 * Represents the pause menu GUI of the program.
 *
 * @author Aaron, Sunny, Hieu
 * @version TCSS 360: Fall 2023
 */
public class PauseGUI extends GUILayout {


    /**
     * The resume button.
     */
    private Button myResumeButton;

    /**
     * The save game button.
     */
    private Button mySaveGameButton;

    /**
     * The help button.
     */
    private Button myHelpButton;

    /**
     * Initializes a basic pause menu screen with 3 menu options.
     */
    public PauseGUI(final GUIHandler theGUI) {
        super(theGUI);
        locateNodes();
        attachEvents();
    }


    /**
     * Helper method to attach mouse events to certain nodes.
     */
    private void locateNodes() {
        this.myResumeButton = (Button) lookup("menuResumeButton");
        this.mySaveGameButton = (Button) lookup("menuSaveButton");
        this.myHelpButton = (Button) lookup("menuHelpButton");
    }


    /**
     * Helper method to organize the binding of nodes to variables.
     */
    private void attachEvents() {
        this.myResumeButton.setOnAction(e -> getGui().resumeGame());

        this.mySaveGameButton.setOnAction(e -> DungeonAdventure.getInstance().saveGameState());

        // Load the game when the "Load Game" button is clicked
        this.myHelpButton.setOnAction(e -> {
            new HelpGUI(getGui());
            GUIHandler.Layouts.swapLayout(GUIHandler.Layouts.HELP);
        });
    }
}
