package org.wahyaw.sudokusolver.utility;

import org.wahyaw.sudokusolver.entity.Board;
import org.wahyaw.sudokusolver.entity.Cell;
import org.wahyaw.sudokusolver.entity.Square;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by wahyaw on 11/20/2017.
 */
public class HelperUtil {
    public static List<Integer> possibleValues = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

    /**
     * Helper method to create list from ANYTHING
     *
     * @param items
     * @param <T>
     * @return
     */
    public static <T> List<T> asList(T ... items) {
        List<T> list = new ArrayList<>();
        for (T item : items) {
            list.add(item);
        }

        return list;
    }

    /**
     * Gather all solved value in a cell, and return a List of the values.
     * Content of the value is between number 1-9.
     *
     * @param square
     * @return
     */
    public static List<Integer> generateSolvedValueCellList(Square square){
        List<Integer> result = new ArrayList<>();

        for(Cell cell : square.getCells()){
            if(cell.getValue() != null){
                result.add(cell.getValue());
            }
        }

        return result;
    }


    /**
     * Gather all unsolved value in a cell, and return a List of the values.
     * Content of the value is between number 1-9.
     *
     * @param square
     * @return
     */
    public static List<Integer> generateUnsolvedCellValueList(Square square){
        List<Integer> result = new ArrayList<>(possibleValues);
        List<Integer> solvedCellList = generateSolvedValueCellList(square);

        result = result.stream()
                .filter(p -> !solvedCellList.contains(p))
                .collect(Collectors.toList());

        return result;
    }

    /**
     * Substitute Cell in a Board with a Cell from parameter.
     * By comparing the Cells coordinates.
     *
     * @param board
     * @param cellToBeInserted
     * @return
     */
    public static Board substituteCellOfBoard(Board board, Cell cellToBeInserted){
        for(Square square : board.getSquares()){
            for (int i = 0; i< square.getCells().size(); i++){
                Cell cell = square.getCells().get(i);
                if(cell.getxPosition() == cellToBeInserted.getxPosition()
                        && cell.getyPosition() == cellToBeInserted.getyPosition()){
                    square.getCells().set(i, cellToBeInserted);
                    return board;
                }
            }
        }

        return board;
    }
}
