package model;

import java.awt.*;

public class PipeGameStep extends Step<Point> {

    private Integer rotations;

    public PipeGameStep(Point position, Integer rotations) {
        this.setRotations(rotations);
        this.setPosition(position);
    }

    public Integer getRotations() {
        return rotations;
    }

    public void setRotations(Integer rotations) {
        this.rotations = rotations;
    }

    @Override
    public void setPosition(Point position) {
        if (position != null) {
            this.position = new Point(position);
        }
    }
    @Override
    public String toString() {
        return String.join(",", this.getPosition().toString(), this.getRotations().toString());
    }

    // For the class serialization and deserialization!
    private static final long serialVersionUID = 31L;
}
