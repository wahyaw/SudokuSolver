package org.wahyaw.sudokusolver.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wahyaw on 11/20/2017.
 *
 * This class is representation of a Cell.
 *
 * If its value is not null, means the cell already solved.
 * If its value is still null, means the cell hasn't been solved.
 *
 * xPosition is horizontal coordinate of the cell
 * yPosition is vertical coordinate of the cell
 *
 * 1|2|3    4|5|6   7|8|9
 * 2
 * 3
 *
 * 4
 * 5
 * 6
 *
 * 7
 * 8
 * 9
 */
public class Cell {
    private Integer value;
    private Coordinate coordinate;
    private Candidates candidates;

    public Cell() {
        this.candidates = new Candidates();
    }

    public Cell(Integer value) {
        this.value = value;
        this.candidates = new Candidates(false);
    }

    public Cell(Integer xPosition, Integer yPosition) {
        this.coordinate = new Coordinate(xPosition, yPosition);
        this.candidates = new Candidates();
    }

    public Cell(Integer value, Integer xPosition, Integer yPosition) {
        this.value = value;
        this.coordinate = new Coordinate(xPosition, yPosition);
        this.candidates = new Candidates(false);
    }

    public Cell(Cell cell){
        if(cell.value != null){
            this.value = new Integer(cell.value);
        }

        Coordinate coordinate = new Coordinate(cell.getxPosition(), cell.getyPosition());
        this.coordinate = coordinate;

        List<Boolean> candidates = new ArrayList<>();
        for (Boolean candidate : cell.candidates.getCandidateList()){
            candidates.add(new Boolean(candidate));
        }
        this.candidates = new Candidates(candidates);
    }



    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
        this.candidates = new Candidates(false);
    }

    public Cell(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public Integer getxPosition() {
        return coordinate.getxPosition();
    }

    public void setxPosition(Integer xPosition) {
        this.coordinate.setxPosition(xPosition);
    }

    public Integer getyPosition() {
        return coordinate.getyPosition();
    }

    public void setyPosition(Integer yPosition) {
        this.coordinate.setyPosition(yPosition);
    }

    public Candidates getCandidates() {
        return candidates;
    }

    public void setCandidates(Candidates candidates) {
        this.candidates = candidates;
    }


}
