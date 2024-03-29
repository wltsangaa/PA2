package viewmodel.panes;

import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import model.Exceptions.InvalidMapException;
import model.LevelManager;
import model.Map.Cell;
import viewmodel.Config;
import viewmodel.MapRenderer;
import viewmodel.SceneManager;


import java.io.File;

/**
 * Represents the main menu in the game
 */
public class LevelSelectPane extends BorderPane {
    private VBox leftContainer;
    private Button returnButton;
    private Button playButton;
    private Button chooseMapDirButton;
    private ListView<String> levelsListView;
    private VBox centerContainer;
    private Canvas levelPreview;

    /**
     * Instantiate the member components and connect and style them. Also set the callbacks.
     * Use 20 for VBox spacing
     */
    public LevelSelectPane() {
        //TODO
        leftContainer=new VBox(20);
        returnButton=new Button("Return");
        playButton=new Button("Play");
        chooseMapDirButton=new Button("Choose map directory");
        levelsListView=new ListView<>();
        centerContainer=new VBox(20);
        levelPreview=new Canvas();
        connectComponents();
        styleComponents();
        setCallbacks();
    }

    /**
     * Connects the components together (think adding them into another, setting their positions, etc). Reference
     * the other classes in the {@link javafx.scene.layout.Pane} package.
     */
    private void connectComponents() {
        //TODO
        leftContainer.getChildren().addAll(
                returnButton,
                chooseMapDirButton,
                levelsListView,
                playButton
        );
        this.setLeft(leftContainer);

        centerContainer.getChildren().add(levelPreview);
        this.setCenter(centerContainer);
    }

    /**
     * Apply CSS styling to components. Also sets the {@link LevelSelectPane#playButton} to be disabled.
     */
    private void styleComponents() {
        //TODO
        returnButton.getStyleClass().add("big-button");
        chooseMapDirButton.getStyleClass().add("big-button");
        centerContainer.getStyleClass().add("big-vbox");
    }

    /**
     * Set the event handlers for the 3 buttons and listview.
     * <p>
     * Hints:
     * The return button should show the main menu scene
     * The chooseMapDir button should prompt the user to choose the map directory, and load the levels
     * The play button should set the current level based on the current level name (see LevelManager), show
     * the gameplay scene, and start the level timer.
     * The listview, based on which item was clicked, should set the current level (see LevelManager), render the
     * preview (see {@link MapRenderer#render(Canvas, Cell[][])}}, and set the play button to enabled.
     */
    private void setCallbacks() {
        //TODO
        returnButton.setOnAction(e -> SceneManager.getInstance().showMainMenuScene());
        chooseMapDirButton.setOnAction(e -> {
            promptUserForMapDirectory();
        });
        playButton.setOnAction(e -> {
            var manager = LevelManager.getInstance();
            try {
                manager.setLevel(levelsListView.getSelectionModel().getSelectedItem());
            } catch (InvalidMapException ie) {
                ie.printStackTrace();
                return;
            }
            SceneManager.getInstance().showGamePlayScene();
            manager.startLevelTimer();
        });


        levelsListView.setOnMouseClicked(e -> {
            var selected = levelsListView.getSelectionModel().getSelectedItem();
            if (selected == null) return;
            try {
                LevelManager.getInstance().setLevel(levelsListView.getSelectionModel().getSelectedItem());
            } catch (InvalidMapException ie) {
                ie.printStackTrace();
                return;
            }
            MapRenderer.render(levelPreview, LevelManager.getInstance().getGameLevel().getMap().getCells());
            playButton.setDisable(false);
        });

    }

    /**
     * Popup a DirectoryChooser window to ask the user where the map folder is stored.
     * Update the LevelManager's map directory afterwards, and potentially
     * load the levels from disk using LevelManager (if the user didn't cancel out the window)
     */
    private void promptUserForMapDirectory() {
        //TODO

        DirectoryChooser chooser = new DirectoryChooser();

        chooser.setTitle("Load map directory");
        chooser.setInitialDirectory(new File("."));


        File directory = chooser.showDialog(SceneManager.getInstance().getStage());
        if (directory != null) {
            LevelManager.getInstance().setMapDirectory(directory.getPath());
            LevelManager.getInstance().loadLevelNamesFromDisk();

            var list = LevelManager.getInstance().getLevelNames();

            levelsListView.setItems(list);
            playButton.setDisable(true);
        }
    }
}
