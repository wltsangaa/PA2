package viewmodel.panes;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import viewmodel.Config;
import viewmodel.LevelEditorCanvas;
import viewmodel.SceneManager;
import viewmodel.customNodes.NumberTextField;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import static viewmodel.LevelEditorCanvas.Brush;

/**
 * Represents the level editor in the game
 */
public class LevelEditorPane extends BorderPane {
    private final LevelEditorCanvas levelEditor;
    private VBox leftContainer;
    private HBox RowsStore;
    private HBox ColStore;
    private Button returnButton;
    private Label rowText;
    private NumberTextField rowField;
    private Label colText;
    private NumberTextField colField;
    private BorderPane rowBox; //holds the rowText and rowField side by side
    private BorderPane colBox; //holds the colText and colField side by side
    private Button newGridButton;
    private ObservableList<Brush> brushList;
    private ListView<Brush> selectedBrush = new ListView<>();
    private Button saveButton;
    private VBox centerContainer;

    /**
     * Instantiate the member components and connect and style them. Also set the callbacks.
     * <p>
     * Hints: {@link LevelEditorPane#rowField} and {@link LevelEditorPane#colField} should be initialized to "5".
     * {@link LevelEditorPane#levelEditor} should be initialized to 5 rows and 5 columns.
     * {@link LevelEditorPane#brushList} should be initialized with all values of the {@link Brush} enum.
     * Use 20 for VBox spacing
     */
    public LevelEditorPane() {
        //TODO
        RowsStore=new HBox();
        ColStore=new HBox();
        levelEditor=new LevelEditorCanvas(5,5);
        leftContainer= new VBox(20);
        returnButton=new Button("Return");
        rowText=new Label("Rows");
        rowField=new NumberTextField("5");
        colText=new Label("Columns");
        colField=new NumberTextField("5");
        rowBox=new BorderPane(); //holds the rowText and rowField side by side
        colBox=new BorderPane(); //holds the colText and colField side by side
        newGridButton=new Button("New Grid");
        brushList=FXCollections.observableArrayList(Brush.TILE,Brush.PLAYER_ON_TILE,Brush.PLAYER_ON_DEST,Brush.CRATE_ON_TILE,Brush.CRATE_ON_DEST,Brush.WALL,Brush.DEST );
        saveButton=new Button("Save");
        centerContainer=new VBox(20);
        setCallbacks();
        connectComponents();
        styleComponents();
    }

    /**
     * Connects the components together (think adding them into another, setting their positions, etc). Reference
     * the other classes in the {@link javafx.scene.layout.Pane} package.
     * <p>
     * Also sets {@link LevelEditorPane#selectedBrush}'s items, and selects the first.
     */
    private void connectComponents() {
        //TODO
        leftContainer.getChildren().addAll(returnButton, rowBox, colBox, newGridButton, selectedBrush, saveButton);
        centerContainer.getChildren().add(levelEditor);
        selectedBrush.setItems(brushList);
        selectedBrush.getSelectionModel().select(0);
        selectedBrush.setPrefHeight(Config.LIST_CELL_HEIGHT * brushList.size());

        rowBox.setLeft(rowText);
        rowBox.setRight(rowField);
        colBox.setLeft(colText);
        colBox.setRight(colField);
        //may have problem

        this.setCenter(centerContainer);
        this.setLeft(leftContainer);
    }

    /**
     * Apply CSS styling to components.
     */
    private void styleComponents() {
        //TODO
        returnButton.getStyleClass().add("big-button");
        newGridButton.getStyleClass().add("big-button");
        saveButton.getStyleClass().add("big-button");
        rowText.getStyleClass().add("Label");
        colBox.getStyleClass().add("Label");
        leftContainer.getStyleClass().add("big-vbox");
    }

    /**
     * Sets the event handlers for the 3 buttons and 1 canvas.
     * <p>
     * Hints:
     * The save button should save the current LevelEditorCanvas to file.
     * The new grid button should change the LevelEditorCanvas size based on the entered values
     * The return button should switch back to the main menu scene
     * The LevelEditorCanvas, upon mouse click, should call {@link LevelEditorCanvas#setTile(Brush, double, double)},
     * passing in the currently selected brush and mouse click coordinates
     */
    private void setCallbacks() {
        //TODO
        saveButton.setOnAction(e -> levelEditor.saveToFile());

        newGridButton.setOnAction(e -> {
            int col = colField.getValue();
            int row = rowField.getValue();
            levelEditor.changeSize(row, col);
        });

        returnButton.setOnAction(e -> SceneManager.getInstance().showMainMenuScene());

        levelEditor.setOnMouseClicked(e -> {
            Brush selected = selectedBrush.getSelectionModel().getSelectedItem();
            levelEditor.setTile(selected, e.getX(), e.getY());
        });


    }
}
