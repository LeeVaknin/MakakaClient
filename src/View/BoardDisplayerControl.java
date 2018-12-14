package View;

import Model.PipeGameTheme;
import Model.ThemeModel;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

import static Model.ImageType.*;

public class BoardDisplayerControl extends Canvas {

    private char[][] boardData;
    private ThemeModel themeModel;

//    Mappings

    private final HashMap<Character, Character> rotationMapping;
    private final HashMap<Character, String> imageMap;

    private double cellWidth;
    private double cellHeight;

    private void setCellsWidth() {
        double width = getWidth();
        double height = getHeight();
        cellWidth = width / boardData[0].length;
        cellHeight = height / boardData.length;

        // In case the width and the height are not symmetric, make it so
        if (cellWidth > cellHeight) {
            cellWidth = cellHeight;
        } else {
            cellHeight = cellWidth;
        }
    }

    public BoardDisplayerControl() {
        themeModel = new PipeGameTheme();
        rotationMapping = new HashMap<Character, Character>() {{
            put('F', '7');
            put('7', 'J');
            put('J', 'L');
            put('L', 'F');
            put('-', '|');
            put('|', '-');
        }};

        imageMap =  new HashMap<Character, String>() {{

            put('F', themeModel.getImagePath(CORNERF));
            put('7', themeModel.getImagePath(CORNER7));
            put('J', themeModel.getImagePath(CORNERJ));
            put('L', themeModel.getImagePath(CORNERL));
            put('-', themeModel.getImagePath(HORIZONTAL));
            put('|', themeModel.getImagePath(VERTICAL));
            put('g', themeModel.getImagePath(GOAL));
            put('s', themeModel.getImagePath(START));
        }};

        // Redraw canvas when size changes.
        widthProperty().addListener(evt -> redraw());
        heightProperty().addListener(evt -> redraw());
    }

    public char[][] getBoardData() {
        return boardData;
    }

    public void setBoardData(char[][] boardData) {
        this.boardData = boardData;
        redraw();
    }

    private void redraw() {
        if (boardData != null) {
            setCellsWidth();
            GraphicsContext gc = getGraphicsContext2D();

            gc.clearRect(0, 0, getWidth(), getHeight());

            for (int i = 0; i < boardData.length; i++)
                for (int j = 0; j < boardData[i].length; j++) {
                    Character pipe = boardData[i][j];
                    if (pipe != ' ') {
                        drawSomething(boardData[i][j], new Point(j, i), gc);
                    }
                }
        }
    }

    private void drawSomething(Character pipeChar, Point position, GraphicsContext gc) {
        try {
            String imagePath = this.imageMap.get(pipeChar);
            Image childImage = new Image(new FileInputStream(imagePath),cellWidth, cellHeight, true, true);
            gc.drawImage(childImage, position.x * cellWidth, position.y * cellHeight, cellWidth, cellHeight);
//            drawRotatedImage(gc, childImage, rotationAngle, position);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isResizable() {
        return true;
    }

    @Override
    public double prefWidth(double height) {
        return getWidth();
    }

    @Override
    public double prefHeight(double width) {
        return getHeight();
    }

}

