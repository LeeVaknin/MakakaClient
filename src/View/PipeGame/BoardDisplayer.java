package View.PipeGame;

import ViewModels.ThemeManagerViewModel;
import Model.ThemeModel;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import static Model.ImageType.*;


public class BoardDisplayer extends Canvas {

    private char[][] boardData;

    private ThemeModel themeModel;
//    Mappings

    private final HashMap<Character, Character> rotationMapping;
    private final HashMap<Character, String> imageMap;

    private double cellWidth;
    private double cellHeight;
    private GraphicsContext gc;

    private Point boardStartPosition;

    private void setCellsWidth() {
        double width = getWidth();
        double height = getHeight();
        cellWidth = width / boardData[0].length;
        cellHeight = height / boardData.length;
        boardStartPosition = new Point();
        // In case the width and the height are not symmetric, make it so
        if (cellWidth > cellHeight) {
            double diff = cellWidth - cellHeight;
            // Shift the board to the right according to the size of the board and the window
            boardStartPosition.x = (int)(diff * boardData[0].length) / 2;
            cellWidth = cellHeight;
        } else {
            double diff = cellHeight - cellWidth;
            // Shift the board to the bottom according to the size of the board and the window
            boardStartPosition.y = (int)(diff * boardData.length) / 2;
            cellHeight = cellWidth;
        }
    }

    public BoardDisplayer() {

        this.rotationMapping = new HashMap<Character, Character>() {{
            put('F', '7');
            put('7', 'J');
            put('J', 'L');
            put('L', 'F');
            put('-', '|');
            put('|', '-');
        }};
        this.imageMap =  new HashMap<Character, String>() {{

            put('F', themeModel.getImagePath(CORNERF));
            put('7', themeModel.getImagePath(CORNER7));
            put('J', themeModel.getImagePath(CORNERJ));
            put('L', themeModel.getImagePath(CORNERL));
            put('-', themeModel.getImagePath(HORIZONTAL));
            put('|', themeModel.getImagePath(VERTICAL));
            put('g', themeModel.getImagePath(GOAL));
            put('s', themeModel.getImagePath(START));
        }};
        this.gc = getGraphicsContext2D();

        // Redraw canvas when size changes.
        widthProperty().addListener(evt -> redraw());
        heightProperty().addListener(evt -> redraw());

        addMouseClickHandler();
    }

    public char[][] getBoardData() {
        return boardData;
    }

    public void setBoardData(char[][] boardData) {
        this.boardData = boardData;
        redraw();
    }

    // Draws the whole board
    private void redraw() {
        if (boardData != null) {
            setCellsWidth();
            this.gc.clearRect(0, 0, getWidth(), getHeight());

            for (int i = 0; i < boardData.length; i++)
                for (int j = 0; j < boardData[i].length; j++) {
                    Character pipe = boardData[i][j];
                    if (pipe != ' ') {
                        drawSomething(boardData[i][j], new Point(j, i));
                    }
                }
        }
    }

    // Draws one of the cells in our board
    private void drawSomething(Character pipeChar, Point position) {
        try {
            String imagePath = this.imageMap.get(pipeChar);
            Image childImage = new Image(new FileInputStream(imagePath),cellWidth, cellHeight, true, true);
            this.gc.clearRect(position.x * cellWidth + boardStartPosition.x,
                    position.y * cellHeight + boardStartPosition.y, cellWidth, cellHeight);
            this.gc.drawImage(childImage, position.x * cellWidth + boardStartPosition.x,
                    boardStartPosition.y +  position.y * cellHeight, cellWidth, cellHeight);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // handles the rotations of the pipes
    private void addMouseClickHandler(){
        addEventFilter(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
            double xLocation = mouseEvent.getX();
            double yLocation = mouseEvent.getY();

            int colLocation = (int) (xLocation / cellWidth);
            int rowLocation = (int) (yLocation / cellHeight);

            if (boardData.length > rowLocation && boardData[0].length > colLocation) {
                Character pipe = boardData[rowLocation][colLocation];
                // Validate we clicked on actual pipe
                if (rotationMapping.containsKey(pipe)){
                    boardData[rowLocation][colLocation] = rotationMapping.get(pipe);
                    drawSomething(boardData[rowLocation][colLocation], new Point(colLocation, rowLocation));
                }
            }

        });
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


