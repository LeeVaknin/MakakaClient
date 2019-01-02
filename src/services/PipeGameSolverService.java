package services;

import model.PipeGameSolution;
import model.Settings;
import utils.ServerCommunicator;

import java.awt.*;

/// This class is responsible for managing the board game solutions
// In addition it talks to the server and asks for solutions
public class PipeGameSolverService {

    public static PipeGameSolution solveBoard(String board) {
        PipeGameSolution solution = new PipeGameSolution();
        Settings settings = CommonService.loadSettings();
        String strSolution = ServerCommunicator.getSolutionFromServer(settings.getIp(), settings.getPort(), board);
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
