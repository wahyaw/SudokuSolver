package org.wahyaw.sudokusolver.entity;

import static org.wahyaw.sudokusolver.utility.HelperUtil.determineSquareIndexInBoardByCell;

/**
 * Created by wahyaw on 11/20/2017.
 *
 * Class to save XWing position.
 */
public class XWing {
    private Integer value;
    private Cell upperLeft;
    private Cell upperRight;
    private Cell lowerLeft;
    private Cell lowerRight;

    public XWing(){
        //default
    }

    public XWing(Cell upperLeft){
        this.upperLeft = upperLeft;
    }

    public XWing(Integer value, Cell upperLeft){
        this.value = value;
        this.upperLeft = upperLeft;
    }

    public XWing(Integer value, Cell upperLeft, Cell upperRight, Cell lowerLeft, Cell lowerRight){
        this.value = value;
        this.upperLeft = upperLeft;
        this.upperRight = upperRight;
        this.lowerLeft = lowerLeft;
        this.lowerRight = lowerRight;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Cell getUpperLeft() {
        return upperLeft;
    }

    public void setUpperLeft(Cell upperLeft) {
        this.upperLeft = upperLeft;
    }

    public Cell getUpperRight() {
        return upperRight;
    }

    public void setUpperRight(Cell upperRight) {
        this.upperRight = upperRight;
    }

    public Cell getLowerLeft() {
        return lowerLeft;
    }

    public void setLowerLeft(Cell lowerLeft) {
        this.lowerLeft = lowerLeft;
    }

    public Cell getLowerRight() {
        return lowerRight;
    }

    public void setLowerRight(Cell lowerRight) {
        this.lowerRight = lowerRight;
    }

    public boolean isAllCellInOneSquare(){
        //validate all cell is not on one square
        int squareLowerLeft = determineSquareIndexInBoardByCell(this.lowerLeft);
        int squareLowerRight = determineSquareIndexInBoardByCell(this.lowerRight);
        int squareUpperLeft = determineSquareIndexInBoardByCell(this.upperLeft);
        int squareUpperRight = determineSquareIndexInBoardByCell(this.upperRight);

        return (squareLowerLeft == squareLowerRight
                && squareLowerRight == squareUpperLeft
                && squareUpperLeft == squareUpperRight);
    }
}
