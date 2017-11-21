package org.wahyaw.sudokusolver.utility;

import org.wahyaw.sudokusolver.Main;
import org.wahyaw.sudokusolver.entity.Board;
import org.wahyaw.sudokusolver.entity.Cell;
import org.wahyaw.sudokusolver.entity.Square;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by wahyaw on 11/20/2017.
 */
public class RemoverUtil {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    /**
     * MUTATION!
     * Removing candidate from a cell based on the index.
     *
     * @param cell
     * @param candidateIndex
     * @return
     */
    public static void removeCandidateValueFromCell(Cell cell, int candidateIndex){
        if(cell.getValue() == null){
            cell.getCandidates().getCandidateList().set(candidateIndex, false);
        }
    }

    /**
     * Set FALSE into candidate of certain index.
     *
     * @param square
     * @param candidateToBeRemoved
     * @return
     */
    public static Square removeAllCandidateValueFromAllCell(Square square, int candidateToBeRemoved){
        Square result = new Square(square);
        int candidateIndex = candidateToBeRemoved - 1;

        for(Cell cell : result.getCells()){
            removeCandidateValueFromCell(cell, candidateIndex);
        }

        return result;
    }


    /**
     * Removing all solved cell value from candidates.
     * So candidate only have value of unsolved number.
     *
     * @param square
     * @return
     */
    public static Square cleanNakedValueFromCandidates(Square square){
        Square result = new Square(square);
        List<Integer> listNakedValue = new ArrayList<>();

        for(Cell cell : result.getCells()){
            if(cell.getValue() != null){
                listNakedValue.add(cell.getValue());
            }
        }

        for(Cell cell : result.getCells()){
            if(cell.getValue() != null){
                for(int index:listNakedValue){
                    removeAllCandidateValueFromAllCell(result,index);
                }
            }
        }

        return result;
    }


    /**
     * Removing all solved cell value from candidates.
     * So candidate only have value of unsolved number.
     *
     * @param board
     * @return
     */
    public static Board cleanNakedValueFromBoardCandidates(Board board){
        Board result = new Board(board.getSquares());

        for(Square square : result.getSquares()){
            for(Cell cell : square.getCells()){
                if(cell.getValue() != null){
                    result = cleanBoardBasedOnCell(board, cell);
                }
            }
        }

        return result;
    }


    /**
     * Remove all candidate based on Cell's value from Cell's horizontal, vertical, and Square.
     * Cell Value must not be empty
     *
     * @param board
     * @param cell
     * @return
     */
    public static Board cleanBoardBasedOnCell(Board board, Cell cell){
        /*
        Chain call of:
        - Clean Square
        - Clean Horizontal
        - Clean Vertical
         */

        return cleanVerticalBasedOnCell(
                cleanHorizontalBasedOnCell(
                        cleanSquareBasedOnCell(
                                board,
                                cell
                        ), cell
                ), cell
        );
    }

    /**
     * Remove all candidate based on Cell's value from Cell's horizontal.
     * Cell Value must not be empty
     *
     * @param board
     * @param cell
     * @return
     */
    public static Board cleanHorizontalBasedOnCell(Board board, Cell cell){
        Board result = new Board(board.getSquares());
        int arrayIndex = cell.getyPosition() - 1;
        int candidateIndexToBeRemoved = cell.getValue() - 1;

        for(int i = 0; i<result.getSquares().size(); i++) {
            //3*DIV(row,3)+DIV(i,3)
            //3*MOD(row,3)+MOD(i,3)
            Cell cellToBeModified = result
                    .getSquares()
                    .get(3 * (arrayIndex / 3) + i / 3)
                    .getCells()
                    .get(3 * (arrayIndex % 3) + i % 3);

            if (cellToBeModified.getValue() == null) {
                removeCandidateValueFromCell(cellToBeModified, candidateIndexToBeRemoved);
                System.out.printf("Remove H Candidate with index %s in coordinate x = %s and y = %s%n",
                        candidateIndexToBeRemoved, cellToBeModified.getxPosition(), cellToBeModified.getyPosition());
            }
        }

        return result;
    }

    /**
     * Remove all candidate based on Cell's value from Cell's vertical.
     * Cell Value must not be empty
     *
     * @param board
     * @param cell
     * @return
     */
    public static Board cleanVerticalBasedOnCell(Board board, Cell cell){
        Board result = new Board(board.getSquares());
        int arrayIndex = cell.getxPosition() - 1;
        int candidateIndexToBeRemoved = cell.getValue() - 1;

        for(int i = 0; i<result.getSquares().size(); i++) {
            //DIV(row,3)+3*DIV(i,3)
            //MOD(row,3)+3*MOD(i,3)
            Cell cellToBeModified = board
                    .getSquares()
                    .get(arrayIndex/3 + 3*(i/3))
                    .getCells()
                    .get(arrayIndex%3 + 3*(i%3));

            if (cellToBeModified.getValue() == null) {
                removeCandidateValueFromCell(cellToBeModified, candidateIndexToBeRemoved);
                System.out.printf("Remove V Candidate with index %s in coordinate x = %s and y = %s%n",
                        candidateIndexToBeRemoved, cellToBeModified.getxPosition(), cellToBeModified.getyPosition());
            }
        }

        return result;
    }


    /**
     * Remove all candidate based on Cell's value from Cell's Square.
     * Cell Value must not be empty
     *
     * @param board
     * @param cell
     * @return
     */
    public static Board cleanSquareBasedOnCell(Board board, Cell cell){
        Board result = new Board(board.getSquares());
        int xIndex = cell.getxPosition() - 1;
        int yIndex = cell.getyPosition() - 1;
        int candidateIndexToBeRemoved = cell.getValue() - 1;

        //DIV(x,3) + 3*DIV(y,3)
        Square squareToBeModified = board
                .getSquares()
                .get(xIndex/3 + 3*(yIndex/3));

        for(Cell cellToBeModified : squareToBeModified.getCells()) {
            if (cellToBeModified.getValue() == null) {
                removeCandidateValueFromCell(cellToBeModified, candidateIndexToBeRemoved);
                System.out.printf("Remove S Candidate with index %s in coordinate x = %s and y = %s%n",
                        candidateIndexToBeRemoved, cellToBeModified.getxPosition(), cellToBeModified.getyPosition());
            }
        }

        return result;
    }

}
