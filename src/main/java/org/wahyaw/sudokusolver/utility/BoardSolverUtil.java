package org.wahyaw.sudokusolver.utility;

import org.wahyaw.sudokusolver.Main;
import org.wahyaw.sudokusolver.entity.Board;
import org.wahyaw.sudokusolver.entity.Cell;
import org.wahyaw.sudokusolver.entity.Square;

import java.util.logging.Logger;

import static org.wahyaw.sudokusolver.utility.ConverterUtil.convertBoardArrayIntoBoard;
import static org.wahyaw.sudokusolver.utility.HelperUtil.substituteCellOfBoard;
import static org.wahyaw.sudokusolver.utility.RemoverUtil.cleanBoardBasedOnCell;
import static org.wahyaw.sudokusolver.utility.RemoverUtil.cleanNakedValueFromBoardCandidates;
import static org.wahyaw.sudokusolver.utility.SolverUtil.checkSingleCandidateInCell;
import static org.wahyaw.sudokusolver.utility.SolverUtil.checkSingleCandidateInSquare;

/**
 * Created by wahyaw on 11/21/2017.
 */
public class BoardSolverUtil {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    /**
     * MAIN METHOD TO SOLVE BOARD
     *
     * @param board
     * @return
     */
    public static Board solveBoard(Board board){
        Board resultBoard = new Board(board.getSquares());
        //while (!board.isSolved()){
            //1. FIRST CLEANING
            resultBoard = cleanNakedValueFromBoardCandidates(resultBoard);

            //2. SEARCH AND SET SINGLE POSSIBLE CANDIDATE IN A CELL
            resultBoard = searchAndSetSingleCandidateInCell(resultBoard);

            //3. SEARCH AND SET SINGLE POSSIBLE CANDIDATE IN A SQUARE
            resultBoard = searchAndSetSingleCandidateInSquare(resultBoard);

        //}

        return resultBoard;
    }

    /**
     * MAIN METHOD TO SOLVE BOARD
     *
     * @param boardArray
     * @return
     */
    public static Board solveBoard(Integer[][] boardArray){
        return solveBoard(convertBoardArrayIntoBoard(boardArray));
    }


    /**
     * Searching Cell which has only one Candidate available.
     * Set that value into the Cell, and remove Candidate of that value from H, V, and S.
     * Returning updated Board in result.
     *
     * @param board
     * @return
     */
    public static Board searchAndSetSingleCandidateInCell(Board board){
        Board result = new Board(board.getSquares());
        boolean flag;
        do {
            flag = false;
            for(Square square : board.getSquares()){
                Cell singleCandidateCell = checkSingleCandidateInCell(square);

                if(singleCandidateCell != null){
                    flag = true;
                    result = cleanBoardBasedOnCell(board, singleCandidateCell);
                    System.out.printf("searchAndSetSingleCandidateInCell: Board cleaned for value %s in x = %s and y = %s%n",
                            singleCandidateCell.getValue(), singleCandidateCell.getxPosition(), singleCandidateCell.getyPosition());
                }
            }
        } while (flag);

        return result;
    }

    /**
     * Searching Candidate in a square which only available on ONE Cell.
     * Set that value into the Cell, and remove Candidate of that value from H, V, and S.
     * Returning updated Board in result.
     *
     * @param board
     * @return
     */
    public static Board searchAndSetSingleCandidateInSquare(Board board){
        Board result = new Board(board.getSquares());
        boolean flag;
        do {
            flag = false;
            for(Square square : board.getSquares()){
                Cell singleCandidateCell = checkSingleCandidateInSquare(square);

                if (singleCandidateCell != null){
                    flag = true;
                    board = substituteCellOfBoard(board, singleCandidateCell);
                    board = cleanBoardBasedOnCell(board, singleCandidateCell);
                    System.out.printf("searchAndSetSingleCandidateInSquare: Board cleaned for value %s in x = %s and y = %s%n",
                            singleCandidateCell.getValue(), singleCandidateCell.getxPosition(), singleCandidateCell.getyPosition());
                }
            }
        } while (flag);

        return result;
    }
}
