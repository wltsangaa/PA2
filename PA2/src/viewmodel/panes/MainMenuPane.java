package viewmodel.panes;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import viewmodel.SceneManager;

/**
 * Represents the main menu in the game
 */
public class MainMenuPane extends BorderPane {

    private VBox container;
    private Label title;
    private Button playButton;
    private Button levelEditorButton;
    private Button settingsButton;
    private Button quitButton;


    /**
     * Instantiate the member components and connect and style them. Also set the callbacks.
     */
    public MainMenuPane() {
        //TODO
        container=new VBox(20);
        title=new Label("Sokoban");
        playButton=new Button("Play");
        levelEditorButton=new Button("Level Editor");
        settingsButton=new Button("About / Settings");
        quitButton=new Button("Quit");
        connectComponents();
        styleComponents();
        setCallbacks();

    }

    /**
     * Connects the components together (think adding them into another, setting their positions, etc).
     * Use this for reference.
     */
    private void connectComponents() {
        container.getChildren().addAll(
                title,
                playButton,
                levelEditorButton,
                settingsButton,
                quitButton
        );
        this.setCenter(container);
    }

    /**
     * Apply CSS styling to components.
     */
    private void styleComponents() {
        title.getStyleClass().add("Label");
        container.getStyleClass().add("big-vbox");
        playButton.getStyleClass().add("big-button");
        levelEditorButton.getStyleClass().add("big-button");
        settingsButton.getStyleClass().add("big-button");
        quitButton.getStyleClass().add("big-button");
    }

    /**
     * Set the event handlers for the 4 buttons, 3 of which switch to different scene, and 1 of which exits the program.
     */
    private void setCallbacks() {
        playButton.setOnAction(e -> SceneManager.getInstance().showLevelSelectMenuScene());
        levelEditorButton.setOnAction(e -> SceneManager.getInstance().showLevelEditorScene());

        settingsButton.setOnAction(e -> SceneManager.getInstance().showSettingsMenuScene());
        quitButton.setOnAction(e -> SceneManager.getInstance().getStage().close());

    }
}
