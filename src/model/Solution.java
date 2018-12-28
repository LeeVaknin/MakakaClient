package model;

import javafx.fxml.Initializable;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;


// P is the position type of the step
public abstract class Solution<P> implements Serializable {

    // For the class serialization and deserialization!
    private static final long serialVersionUID = 30L;
    // Variables
    private ArrayList<P> steps;

    public Solution(Solution<P> solution) {
        this.setSteps(solution.getSteps());
    }

    public Solution() {
        super();
        this.setSteps(null);
    }

    // Methods

    public ArrayList<P> getSteps() {
        return steps;
    }

    private void setSteps(ArrayList<P> steps) {
        this.steps = new ArrayList<>();
        if(steps != null) {
            this.steps.addAll(steps);
        }
    }

    protected void addStep(P step) {
        if (step != null && this.steps != null) {
           this.steps.add(step);
        }
    }

    public abstract void fromString(String strSolution);

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (P step : this.steps) {
            result.append(step.toString());
            if (!step.equals(this.steps.get(this.steps.size() - 1))) {
                 result.append("\n");
            }
        }
        return result.toString();
    }
}
