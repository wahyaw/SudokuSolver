package org.wahyaw.sudokusolver.entity;

/**
 * Created by wahyaw on 11/21/2017.
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
public class Coordinate {
    private Integer xPosition;
    private Integer yPosition;

    public Coordinate() {
        //default
    }

    public Coordinate(Integer xPosition, Integer yPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    public Integer getxPosition() {
        return xPosition;
    }

    public void setxPosition(Integer xPosition) {
        this.xPosition = xPosition;
    }

    public Integer getyPosition() {
        return yPosition;
    }

    public void setyPosition(Integer yPosition) {
        this.yPosition = yPosition;
    }
}
