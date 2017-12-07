package org.wahyaw.sudokusolver.entity;

import edu.emory.mathcs.backport.java.util.Collections;
import org.apache.commons.collections.list.FixedSizeList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wahyaw on 11/20/2017.
 *
 * Board is a collection of boards
 *
 * Board number position:
 * 1|2|3
 * 4|5|6
 * 7|8|9
 */
public class Board {
    private List<Square> squares;

    public List<Square> getSquares() {
        return squares;
    }

    public void setSquares(List<Square> squares) {
        this.squares = squares;
    }

    public boolean isSolved(){
        for (Square square : getSquares()){
            for (Cell cell : square.getCells()){
                if (cell.getValue() == null){
                    return false;
                }
            }
        }

        return true;
    }


    //DEFAULT CONSTRUCTOR//
    public Board() {
        Square[] squareArray = new Square[9];
        Arrays.fill(squareArray, new Square());
        this.squares = FixedSizeList.decorate(Arrays.asList(squareArray));
    }

    public Board(List<Square> squares) {
        this.squares = FixedSizeList.decorate(squares);
    }

    public Board(Board board) {
        List<Square> squares = new ArrayList<>();
        for (Square square : board.squares){
            squares.add(new Square(square));
        }
        this.squares = squares;
    }


}
