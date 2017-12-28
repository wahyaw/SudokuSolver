package org.wahyaw.sudokusolver.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wahyaw on 12/27/2017.
 *
 * Class to save Hidden Pair position.
 */
public class HiddenPair {
    private Integer firstValue;
    private Integer secondValue;
    private Cell leftCell;
    private Cell rightCell;

    public HiddenPair(){
        //default
    }

    public HiddenPair(Integer firstValue, Integer secondValue, Cell leftCell) {
        this.firstValue = firstValue;
        this.secondValue = secondValue;
        this.leftCell = leftCell;
    }

    public Integer getFirstValue() {
        return firstValue;
    }

    public void setFirstValue(Integer firstValue) {
        this.firstValue = firstValue;
    }

    public Integer getSecondValue() {
        return secondValue;
    }

    public void setSecondValue(Integer secondValue) {
        this.secondValue = secondValue;
    }

    public Cell getLeftCell() {
        return leftCell;
    }

    public void setLeftCell(Cell leftCell) {
        this.leftCell = leftCell;
    }

    public Cell getRightCell() {
        return rightCell;
    }

    public void setRightCell(Cell rightCell) {
        this.rightCell = rightCell;
    }

    /**
     * Checking if all property is filled, nothing should be null
     *
     * @return
     */
    public boolean isAllPropertyNotNull(){
        return firstValue != null
                && secondValue != null
                && leftCell != null
                && rightCell != null;
    }

    /**
     * To determine whether this is a Horizontally aligned or Vertically alligned
     *
     * @return
     */
    public boolean isHorizontalAligned(){
        return leftCell.getyPosition() == rightCell.getyPosition();
    }

    /**
     * Returning leftCell and rightCell as a List<Cell>
     * @return
     */
    public List<Cell> getCellsAsList(){
        List<Cell> result = new ArrayList<>();
        result.add(leftCell);
        result.add(rightCell);

        return result;
    }
}
