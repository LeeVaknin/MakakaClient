package Model;
import java.io.Serializable;
import java.time.Duration;
import java.util.Observable;


public class PipeBoardModel extends Observable implements Serializable {

    private char[][] board;
    private int StepsCounter;
    private Duration gameDuration;

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
            System.out.println("MatrixBoard.toBoard(): Error details: " + ex.getMessage());
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
}
