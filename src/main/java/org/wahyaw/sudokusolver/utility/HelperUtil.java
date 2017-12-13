package org.wahyaw.sudokusolver.utility;

import org.wahyaw.sudokusolver.entity.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.wahyaw.sudokusolver.utility.RemoverUtil.removeDuplicateNakedCells;

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


    /**
     * Method to compare every Values and Candidates of two Boards.
     *
     * @param board1
     * @param board2
     * @return boolean
     */
    public static boolean isBoardDeepEqual(Board board1, Board board2){
        for(int x = 0; x < board1.getSquares().size(); x++){
            if (!isSquareDeepEqual(board1.getSquares().get(x), board2.getSquares().get(x))){
                return false;
            }
        }

        return true;
    }

    /**
     * Method to compare every Values and Candidates of two Squares.
     *
     * @param square1
     * @param square2
     * @return
     */
    public static boolean isSquareDeepEqual(Square square1, Square square2){
        for(int x = 0; x < square1.getCells().size(); x++){
            if (!isCellDeepEqual(square1.getCells().get(x), square2.getCells().get(x))){
                return false;
            }
        }

        return true;
    }

    /**
     * Method to compare every Values and Candidates of two Cells.
     *
     * @param cell1
     * @param cell2
     * @return
     */
    public static boolean isCellDeepEqual(Cell cell1, Cell cell2){
        //comparing value
        if (!Objects.equals(cell1.getValue(),cell2.getValue())){
            return false;
        }

        //comparing candidates
        return isCandidatesDeepEqual(cell1.getCandidates(), cell2.getCandidates());
    }

    /**
     * Method to compare every Candidates of two Candidates.
     *
     * @param candidates1
     * @param candidates2
     * @return
     */
    public static boolean isCandidatesDeepEqual(Candidates candidates1, Candidates candidates2){
        for (int x = 0; x<candidates1.getCandidateList().size(); x++){
            if(candidates1.getCandidateList().get(x) ^ candidates2.getCandidateList().get(x)){
                return false;
            }
        }

        return true;
    }

    /**
     * Checking whether a square has a horizontally-lined only candidate.
     * Return list of those candidate as a NakedCell which value = the candidate,
     * and the y coordinate.
     *
     * @param square
     * @return
     */
    public static List<NakedCell> checkHorizontalLineCandidateInSquare(Square square){
        return checkLineCandidateInSquare(square, true);
    }

    /**
     * Checking whether a square has a vertically-lined only candidate.
     * Return list of those candidate as a NakedCell which value = the candidate,
     * and the x coordinate.
     *
     * @param square
     * @return
     */
    public static List<NakedCell> checkVerticalLineCandidateInSquare(Square square){
        return checkLineCandidateInSquare(square, false);
    }

    /**
     * Checking whether a square has a horizontally-lined only candidate.
     * Return list of those candidate as a NakedCell which value = the candidate,
     * and the y coordinate.
     *
     * @param squareList
     * @return
     */
    public static List<NakedCell> checkHorizontalLineCandidateInSquare(List<Square> squareList){
        List<NakedCell> result = new ArrayList<>();

        for(Square square : squareList){
            result.addAll(checkLineCandidateInSquare(square, true));
        }

        return result;
    }

    /**
     * Checking whether a square has a vertically-lined only candidate.
     * Return list of those candidate as a NakedCell which value = the candidate,
     * and the x coordinate.
     *
     * @param squareList
     * @return
     */
    public static List<NakedCell> checkVerticalLineCandidateInSquare(List<Square> squareList){
        List<NakedCell> result = new ArrayList<>();

        for(Square square : squareList){
            result.addAll(checkLineCandidateInSquare(square, false));
        }

        return result;
    }

    /**
     * Checking whether a square has a horizontally-lined / vertically-lined only candidate.
     * Return list of those candidate as a NakedCell which value = the candidate,
     * and the y / x coordinate.
     *
     * @param square
     * @return
     */
    public static List<NakedCell> checkLineCandidateInSquare(Square square, boolean isHorizontal){
        List<NakedCell> resultList = new ArrayList<>();
        int candidateValue = 0;

        //Loop cell in square
        for(int i = 0; i < square.getCells().size(); i++){
            Cell cell = square.getCells().get(i);

            for(int j = 0; j < cell.getCandidates().getCandidateList().size(); j++){
                if(cell.getValue() != null){
                    break;
                }

                int divMod = isHorizontal ? i / 3 : i % 3;
                candidateValue = cell.getCandidates().getCandidateList().get(j) ? j : candidateValue;

                if(cell.getCandidates().getCandidateList().get(candidateValue)){ //get cell's candidate
                    boolean isFound = false;

                    //loop through cells
                    for(int k = 0; k < square.getCells().size(); k++){
                        Cell cell2 = square.getCells().get(k);

                        if(cell2.getValue() == null
                                && cell2.getCandidates().getCandidateList().get(candidateValue)
                                && k != i){
                            //if TRUE, then it mean they're same candidate

                            int divModCurrent = isHorizontal ? k / 3 : k % 3;
                            if(divModCurrent == divMod){ //for Horizontal, same DIV. for Vertical, same MOD
                                isFound = true;
                            } else {
                                isFound = false;
                                candidateValue = 0;
                                break;
                            }
                        }
                    }

                    if(isFound){
                        resultList.add(new NakedCell(candidateValue+1, cell.getxPosition(), cell.getyPosition()));
                        candidateValue = 0;
                    }
                }

            }
        }

        return removeDuplicateNakedCells(resultList);
    }

    /**
     * Determine Cell's Square index based on its candidate.
     * 0 1 2
     * 3 4 5
     * 6 7 8
     *
     * @param cell
     * @return
     */
    public static int determineSquareIndexInBoardByCell(Cell cell){
        return determineSquareIndexInBoardByCell((NakedCell) cell);
    }

    /**
     * Determine Cell's Square index based on its candidate.
     * 0 1 2
     * 3 4 5
     * 6 7 8
     *
     * @param nakedCell
     * @return
     */
    public static int determineSquareIndexInBoardByCell(NakedCell nakedCell){
        int xCoordinate = nakedCell.getxPosition() - 1;
        int yCoordinate = nakedCell.getyPosition() - 1;

        //(3*(Y DIV 3)) + (X DIV 3)
        return (3 * (yCoordinate / 3)) + (xCoordinate / 3);
    }

}
