package model;

import java.awt.*;

public class PipeGameSolution  extends Solution<PipeGameStep>{

    @Override
    public void fromString(String strSolution) {
        if (strSolution == null || strSolution.equals("")) {
            return;
        }
        String[] splitSolution = strSolution.split("\n");
        for (String strStep: splitSolution) {
            if(strStep.equals("done")) continue;
            String[] splitStep = strStep.split(",");
            Point position = new Point(Integer.parseInt(splitStep[0]), Integer.parseInt(splitStep[1])) ;
            int rotations = Integer.parseInt(splitStep[2]);
            PipeGameStep newStep = new PipeGameStep( position, rotations);
            this.addStep(newStep);
        }
    }

}
