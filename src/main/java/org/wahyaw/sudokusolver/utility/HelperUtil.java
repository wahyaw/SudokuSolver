package org.wahyaw.sudokusolver.utility;

import org.wahyaw.sudokusolver.entity.Cell;
import org.wahyaw.sudokusolver.entity.Square;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wahyaw on 11/20/2017.
 */
public class HelperUtil {
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
     * @param square
     * @return
     */
    public static List<Integer> generateSolvedCellList(Square square){
        List<Integer> result = new ArrayList<>();

        for(Cell cell : square.getCells()){
            if(cell.getValue() != null){
                result.add(cell.getValue());
            }
        }

        return result;
    }
}
