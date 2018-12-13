package View.Controls;

import Model.ImageType;
import Model.ThemeModel;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

import static Model.ImageType.*;

public class BoardDisplayerControl extends Canvas {

    char[][] boardData;
    private ThemeModel themeModel;

    private double cellWidth = getWidth() / boardData[0].length;
    private double cellHeight = getHeight() / boardData.length;

    public BoardDisplayerControl() {
    }

    private final HashMap<Character, Integer> rotationMapping = new HashMap<Character, Integer>() {{
        put('F', 90);
        put('7', 180);
        put('J', 270);
        put('L', 0);
        put('-', 0);
        put('|', 90);
        put('g', 0);
        put('s', 0);
    }};

    private final HashMap<Character, String> imageMap = new HashMap<Character, String>() {{

        put('F', themeModel.getImagePath(CORNER));
        put('7', themeModel.getImagePath(CORNER));
        put('J', themeModel.getImagePath(CORNER));
        put('L', themeModel.getImagePath(CORNER));
        put('-', themeModel.getImagePath(VERTICAL));
        put('|', themeModel.getImagePath(VERTICAL));
        put('g', themeModel.getImagePath(GOAL));
        put('s', themeModel.getImagePath(START));
    }};

    public char[][] getBoardData() {
        return boardData;
    }


    public void setBoardData(char[][] boardData) {
        this.boardData = boardData;
        redraw();
    }

    private void redraw() {
        if (boardData != null) {
            double width = getWidth();
            double height = getHeight();

            GraphicsContext gc = getGraphicsContext2D();

            gc.clearRect(0, 0, width, height);

            for (int i = 0; i < boardData.length; i++)
                for (int j = 0; j < boardData[i].length; j++) {
                    Character pipe = boardData[i][j];
                    if (pipe != ' ') {
                        drawSomething(boardData[i][j], new Point(j, i), gc);
                    }
                }
        }
    }

    private void rotate(GraphicsContext gc, double angle, double px, double py) {
        Rotate r = new Rotate(angle, px, py);
        gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
    }

    private void drawRotatedImage(GraphicsContext gc, Image image, double angle, Point position) {
        gc.save(); // saves the current state on stack, including the current transform
        rotate(gc, angle, position.x + cellWidth / 2, position.y + cellHeight / 2);
        gc.drawImage(image, position.x * cellWidth, position.y * cellHeight, cellHeight, cellWidth);
        gc.restore(); // back to original state (before rotation)
    }

    private void drawSomething(Character pipeChar, Point position, GraphicsContext gc) {
        try {
            int rotationAngle = this.rotationMapping.get(pipeChar);
            String imagePath = this.imageMap.get(pipeChar);
            Image childImage = new Image(new FileInputStream(imagePath));
            drawRotatedImage(gc, childImage, rotationAngle, position);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}


