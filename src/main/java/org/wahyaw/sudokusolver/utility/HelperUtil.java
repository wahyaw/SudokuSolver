package org.wahyaw.sudokusolver.utility;

import org.wahyaw.sudokusolver.entity.Board;
import org.wahyaw.sudokusolver.entity.Candidates;
import org.wahyaw.sudokusolver.entity.Cell;
import org.wahyaw.sudokusolver.entity.Square;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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

    /**
     * Printing board in console.
     * Using sudoku format.
     *
     * @param board
     */
    public static void printBoard(Board board){
        Integer[][] result = ConverterUtil.convertBoardIntoBoardArray(board);
        for(int i = 0; i<result.length; i++){
            System.out.printf(
                    "  %s%s%s  |  %s%s%s  |  %s%s%s  %n",
                    result[i][0] == null ? "0" : result[i][0],
                    result[i][1] == null ? "0" : result[i][1],
                    result[i][2] == null ? "0" : result[i][2],
                    result[i][3] == null ? "0" : result[i][3],
                    result[i][4] == null ? "0" : result[i][4],
                    result[i][5] == null ? "0" : result[i][5],
                    result[i][6] == null ? "0" : result[i][6],
                    result[i][7] == null ? "0" : result[i][7],
                    result[i][8] == null ? "0" : result[i][8]
            );
            if(i==2 || i==5){
                System.out.println("-------|-------|-------");
            }
        }
        System.out.println(
                board.isSolved() ? "=========SOLVED=========" : "not solved"
        );
    }


    public static boolean isBoardDeepEqual(Board board1, Board board2){
        for(int x = 0; x < board1.getSquares().size(); x++){
            if (!isSquareDeepEqual(board1.getSquares().get(x), board2.getSquares().get(x))){
                return false;
            }
        }

        return true;
    }


    public static boolean isSquareDeepEqual(Square square1, Square square2){
        for(int x = 0; x < square1.getCells().size(); x++){
            if (!isCellDeepEqual(square1.getCells().get(x), square2.getCells().get(x))){
                return false;
            }
        }

        return true;
    }


    public static boolean isCellDeepEqual(Cell cell1, Cell cell2){
        //comparing value
        if (!Objects.equals(cell1.getValue(),cell2.getValue())){
            return false;
        }

        //comparing candidates
        return isCandidatesDeepEqual(cell1.getCandidates(), cell2.getCandidates());
    }

    public static boolean isCandidatesDeepEqual(Candidates candidates1, Candidates candidates2){
        for (int x = 0; x<candidates1.getCandidateList().size(); x++){
            if(candidates1.getCandidateList().get(x) ^ candidates2.getCandidateList().get(x)){
                return false;
            }
        }

        return true;
    }
}
