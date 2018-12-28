package services;

import model.PipeGameSolution;
import utils.ServerCommunicator;

import java.awt.*;

/// This class is responsible for managing the board game solutions
// In addition it talks to the server and asks for solutions
public class PipeGameSolverService {

    public static PipeGameSolution solveBoard(String board) {
        PipeGameSolution solution = new PipeGameSolution();
        // TODO: replace those with saved config
        String strSolution = ServerCommunicator.getSolutionFromServer("127.0.0.1", 2222, board);
        solution.fromString(strSolution);
        return solution;
    }

    public static boolean submitBoard(String board, Point goalPosition) {
        PipeGameSolution solution = solveBoard(board);
        if (solution == null ) return false;
        Point resultPosition = solution.getSteps().get(0).getPosition();
        return solution.getSteps().size() == 1 &&
                resultPosition.x == goalPosition.x && resultPosition.y == goalPosition.y;
    }
}
