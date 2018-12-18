package ViewModels;
import Model.PipeBoardModel;
import Model.ThemeModel;
import Model.User;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;

import java.util.Observable;
import java.util.Observer;

public class PipeGameViewModel extends Observable implements Observer {

    // GameBoard
    public PipeBoardModel currentBoard;

    // C-TOR
    public PipeGameViewModel() {
        currentBoard = new PipeBoardModel();
        char[][] boardData = {
                {'s','7','-', 'L'},
                {'|','|','F', '|'},
                {'L','7',' ', '-'},
                {'-','|','-', '|'},
                {'F','J',' ', 'g'},
        };
        currentBoard.setBoard(boardData);
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}

//
//public class PipeGameViewModel extends Observable implements BoardModel<MatrixBoard> {
//
//    @Override
//    public MatrixBoard getBoard() {
//        return null;
//    }
//}
