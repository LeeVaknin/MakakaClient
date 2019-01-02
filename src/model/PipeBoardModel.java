package model;
import javafx.animation.Timeline;

import javax.swing.text.Position;
import java.awt.*;
import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.util.Observable;
import java.util.Timer;


public class PipeBoardModel extends Observable implements Serializable {

    private char[][] board;
    private int StepsCounter;
    public int mins = 0, secs = 0, millis = 0;

    // Stopwatch
    public PipeBoardModel() {
    }

    public void setBoard(String newBoard) {
        board = convertFromStringToBoard(newBoard);
        setChanged();
        notifyObservers(board);
    }

    public void setBoard(char[][] newBoard) {
        board = newBoard;
        setChanged();
        notifyObservers(board);
    }

    public char[][] getBoard() {
        return board;
    }

    @Override
    public String toString() {
        String result = null;
        try {
            result = "";
            for (char[] pipes : this.board) {
                for (char pipe : pipes) {
                    result = result.concat(((Character)pipe).toString());
                }
                result = result.concat(System.lineSeparator());
            }
        } catch (Exception ex) {
            System.out.println("BoardModel.toBoard(): Error details: " + ex.getMessage());
        }
        return result;
    }

    public char[][] convertFromStringToBoard(String strBoard) {
        char[][] tmpBoard = null;
        try {
            if (strBoard == null)
                throw new Exception("Bord string is empty");

            // Get values for col and row according to the given string
            String[] splitterBord = strBoard.split(System.lineSeparator());
            Integer row = splitterBord.length;
            Integer col = splitterBord[0].length();

            // Initial bord with all strBoard values
            tmpBoard = new char[row][col];
            for (Integer i = 0; i < row; i++) {
                String tmpLine = splitterBord[i];
                if (tmpLine == null)
                    throw new Exception(String.join(": ", "The following line is null at the matrix board", i.toString()));
                for (Integer j = 0; j < col; j++) {
                    tmpBoard[i][j] = tmpLine.charAt(j);
                }
            }
        } catch (Exception ex) {
            System.out.println("MatrixBoard.toBoard(): Error details: " + ex.getMessage());
        }
        return tmpBoard;
    }

    public Point findGoalPosition() {
        try {
            if (this.board == null)
                return null;
            for (int row=0; row< this.board.length; row++){
                for (int col=0; col < this.board[0].length; col++ ) {
                    Character pipe = this.board[row][col];
                    if (pipe.equals('g') || pipe.equals('G'))
                        return new Point(row, col);
                }
            }
        } catch (Exception ex) {
            System.out.println(String.join(": ", "Position.findEndPosition(): Error details", ex.getMessage()));
        }
        return null;
    }

}
