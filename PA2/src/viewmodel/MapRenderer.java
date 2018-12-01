package viewmodel;

import javafx.scene.canvas.Canvas;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import model.Map.Cell;
import model.Map.Occupant.Player;
import model.Map.Occupiable.DestTile;
import model.Map.Occupiable.Occupiable;

import java.net.URISyntaxException;

import static viewmodel.Config.LEVEL_EDITOR_TILE_SIZE;

/**
 * Renders maps onto canvases
 */
public class MapRenderer {
    private static Image wall = null;
    private static Image crateOnTile = null;
    private static Image crateOnDest = null;

    private static Image playerOnTile = null;
    private static Image playerOnDest = null;

    private static Image dest = null;
    private static Image tile = null;

    static {
        try {
            wall = new Image(MapRenderer.class.getResource("/assets/images/wall.png").toURI().toString());
            crateOnTile = new Image(MapRenderer.class.getResource("/assets/images/crateOnTile.png").toURI().toString());
            crateOnDest = new Image(MapRenderer.class.getResource("/assets/images/crateOnDest.png").toURI().toString());
            playerOnTile = new Image(MapRenderer.class.getResource("/assets/images/playerOnTile.png").toURI().toString());
            playerOnDest = new Image(MapRenderer.class.getResource("/assets/images/playerOnDest.png").toURI().toString());
            dest = new Image(MapRenderer.class.getResource("/assets/images/dest.png").toURI().toString());
            tile = new Image(MapRenderer.class.getResource("/assets/images/tile.png").toURI().toString());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * Render the map onto the canvas. This method can be used in Level Editor
     * <p>
     * Hint: set the canvas height and width as a multiple of the rows and cols
     *
     * @param canvas The canvas to be rendered onto
     * @param map    The map holding the current state of the game
     */
    static void render(Canvas canvas, LevelEditorCanvas.Brush[][] map) {
        //TODO

        final int SIZE = LEVEL_EDITOR_TILE_SIZE;
        int cols = map[0].length;
        int rows = map.length;
        canvas.setWidth(cols * SIZE);
        canvas.setHeight(rows * SIZE);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Image img = null;
                switch (map[i][j]) {

                    case CRATE_ON_TILE: img =  crateOnTile;  break;
                    case CRATE_ON_DEST: img =  crateOnDest;  break;
                    case PLAYER_ON_TILE: img =  playerOnTile;  break;
                    case PLAYER_ON_DEST: img =  playerOnDest;  break;
                    case DEST: img = dest;  break;
                    case TILE: img = tile;  break;
                    case WALL: img = wall;  break;
                }

                canvas.getGraphicsContext2D().drawImage(img,j * SIZE, i * SIZE);   // check
            }
        }
    }

    /**
     * Render the map onto the canvas. This method can be used in GamePlayPane and LevelSelectPane
     * <p>
     * Hint: set the canvas height and width as a multiple of the rows and cols
     *
     * @param canvas The canvas to be rendered onto
     * @param map    The map holding the current state of the game
     */
    public static void render(Canvas canvas, Cell[][] map) {
        //TODO
        final int SIZE = LEVEL_EDITOR_TILE_SIZE;

        int rows = map.length;

        int cols = map[0].length;

        canvas.setHeight(rows * SIZE);

        canvas.setWidth(cols * SIZE);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Image img = null;

                if (map[i][j] instanceof Occupiable) {
                    Occupiable o = (Occupiable) map[i][j];

                    if (o.getOccupant().isPresent()) {
                        if (o.getOccupant().get() instanceof Player) {
                            if (map[i][j] instanceof DestTile) img = playerOnDest;
                            else img = playerOnTile;
                        } else {
                            if (map[i][j] instanceof DestTile) img = crateOnDest;
                            else img = crateOnTile;

                        }
                    } else {

                        if (map[i][j] instanceof DestTile) img = dest;
                        else img = tile;
                    }
                } else {
                    img = wall;
                }

                canvas.getGraphicsContext2D().drawImage(img,j * SIZE, i * SIZE);
            }
        }
    }
}
