package org.wahyaw.sudokusolver.entity;

import org.apache.commons.collections.list.FixedSizeList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by wahyaw on 11/20/2017.
 *
 * Square is a collection of 3x3 cells
 *
 * Cell number and position:
 * 1|2|3
 * 4|5|6
 * 7|8|9
 */
public class Square {
    private List<Cell> cells;

    public List<Cell> getCells() {
        return cells;
    }

    public void setCells(List<Cell> cells) {
        this.cells = cells;
    }


    //DEFAULT CONSTRUCTOR//
    public Square() {
        Cell[] cellArray = new Cell[9];
        Arrays.fill(cellArray, new Cell());
        this.cells = FixedSizeList.decorate(Arrays.asList(cellArray));
    }

    public Square(List<Cell> cells) {
        this.cells = FixedSizeList.decorate(cells);
    }

    public Square(Square square) {
        List<Cell> cells = new ArrayList<>();

        for (Cell cell : square.cells){
            cells.add(new Cell(cell));
        }

        this.cells = cells;
    }
}
