package org.wahyaw.sudokusolver.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wahyaw on 11/20/2017.
 *
 * This class is representation of a Cell - Only for check Horizontal/Vertical only Candidate in Square.
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
public class NakedCell {
    protected Integer value;
    protected Coordinate coordinate;

    public NakedCell(){
        //default
    }

    public NakedCell(Integer value) {
        this.value = value;
    }

    public NakedCell(Integer xPosition, Integer yPosition) {
        this.coordinate = new Coordinate(xPosition, yPosition);
    }

    public NakedCell(Integer value, Integer xPosition, Integer yPosition) {
        this.value = value;
        this.coordinate = new Coordinate(xPosition, yPosition);
    }

    public NakedCell(NakedCell cell){
        if(cell.value != null){
            this.value = new Integer(cell.value);
        }

        Coordinate coordinate = new Coordinate(cell.getxPosition(), cell.getyPosition());
        this.coordinate = coordinate;
    }



    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public NakedCell(Coordinate coordinate) {
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
}
