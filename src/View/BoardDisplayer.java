package View;

import Model.PipeBoardModel;
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

    private PipeBoardModel board;
    private ThemeModel themeModel;

//    Mappings

    private final HashMap<Character, Character> rotationMapping;
    private HashMap<Character, String> imageMap;

    private double cellWidth;
    private double cellHeight;
    private GraphicsContext gc;

    private Point boardStartPosition;

    // Setters

    private void setCellsWidth() {
        double width = getWidth();
        double height = getHeight();

        char[][] boardData = board.getBoard();
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

    public void setThemeModel(ThemeModel themeModel) {
        this.themeModel = themeModel;
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
        this.redraw();
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
        this.gc = getGraphicsContext2D();
        // Redraw canvas when size changes.
        widthProperty().addListener(evt -> redraw());
        heightProperty().addListener(evt -> redraw());

        addMouseClickHandler();
    }

    public PipeBoardModel getBoard() {
        return board;
    }

    public void setBoard(PipeBoardModel board) {
        this.board = board;
        if (themeModel != null) {
            redraw();
        }
    }

    // Draws the whole board
    private void redraw() {
        if (board != null) {
            setCellsWidth();
            if (cellWidth == 0 || cellHeight == 0) { return;}
            this.gc.clearRect(0, 0, getWidth(), getHeight());

            char[][] boardData = board.getBoard();
            for (int i = 0; i < boardData.length; i++)
                for (int j = 0; j < boardData[0].length; j++) {
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
            double xLocation = mouseEvent.getX() - this.boardStartPosition.x;
            double yLocation = mouseEvent.getY() - this.boardStartPosition.y;

            if (xLocation < 0 || yLocation < 0) {return;}
            int colLocation = (int) ((xLocation) / cellWidth);
            int rowLocation = (int) ((yLocation)/ cellHeight);

            if (board.getBoard().length > rowLocation && board.getBoard()[0].length > colLocation) {
                Character pipe = board.getBoard()[rowLocation][colLocation];
                // Validate we clicked on actual pipe
                if (rotationMapping.containsKey(pipe)){
                    board.getBoard()[rowLocation][colLocation] = rotationMapping.get(pipe);
                    board.notifyObservers(board);
                    drawSomething(board.getBoard()[rowLocation][colLocation], new Point(colLocation, rowLocation));
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


