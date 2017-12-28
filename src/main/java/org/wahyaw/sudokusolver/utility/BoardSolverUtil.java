package org.wahyaw.sudokusolver.utility;

import org.wahyaw.sudokusolver.Main;
import org.wahyaw.sudokusolver.entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.wahyaw.sudokusolver.utility.ConverterUtil.convertBoardArrayIntoBoard;
import static org.wahyaw.sudokusolver.utility.ConverterUtil.convertBoardHorizontalIntoSquare;
import static org.wahyaw.sudokusolver.utility.ConverterUtil.convertBoardVerticalIntoSquare;
import static org.wahyaw.sudokusolver.utility.HelperUtil.*;
import static org.wahyaw.sudokusolver.utility.RemoverUtil.*;
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
        Board boardBackup = new Board();
        Board resultBoard = new Board(board.getSquares());

        //1. FIRST CLEANING
        resultBoard = cleanNakedValueFromBoardCandidates(resultBoard);

        while (!resultBoard.isSolved() && !isBoardDeepEqual(boardBackup, resultBoard)) {
            while (!resultBoard.isSolved() && !isBoardDeepEqual(boardBackup, resultBoard)) {
                while (!resultBoard.isSolved() && !isBoardDeepEqual(boardBackup, resultBoard)) {
                    while (!resultBoard.isSolved() && !isBoardDeepEqual(boardBackup, resultBoard)) {
                        boardBackup = new Board(resultBoard);

                        //2. SEARCH AND SET SINGLE POSSIBLE CANDIDATE IN A CELL
                        resultBoard = searchAndSetSingleCandidateInCell(resultBoard);

                        //3. SEARCH AND SET SINGLE POSSIBLE CANDIDATE
                        resultBoard = searchAndSetSingleCandidate(resultBoard);
                    }
                    //4. SEARCH AND SET LINED CANDIDATE IN SQUARE, REMOVE FROM OTHER SQUARE
                    resultBoard = cleanHorizontalVerticalByLiningCandidatesInSquare(resultBoard);
                }
                //5. HIDDEN PAIR, AND REMOVE CANDIDATES BASED ON IT
                resultBoard = cleanBoardFromNakedPair(resultBoard);
            }
            //6. XWING, AND REMOVE CANDIDATES BASED ON IT
            resultBoard = cleanBoardFromXWing(resultBoard);
        }
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
                    board = substituteCellOfBoard(board, singleCandidateCell);
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
     * Searching Candidate in a square, horizontal, or vertical which only available on ONE Cell.
     * Set that value into the Cell, and remove Candidate of that value from H, V, and S.
     * Returning updated Board in result.
     *
     * @param board
     * @return
     */
    public static Board searchAndSetSingleCandidate(Board board){
        Board result = new Board(board.getSquares());
        boolean flag;
        do {
            flag = false;
            for(int i = 0; i < board.getSquares().size(); i++){
                //Square
                Square square = board.getSquares().get(i);
                Cell singleCandidateCell = checkSingleCandidateInSquare(square);

                //Horizontal
                if (singleCandidateCell == null){
                    square = convertBoardHorizontalIntoSquare(board, i+1);
                    singleCandidateCell = checkSingleCandidateInSquare(square);
                }

                //Vertical
                if (singleCandidateCell == null){
                    square = convertBoardVerticalIntoSquare(board, i+1);
                    singleCandidateCell = checkSingleCandidateInSquare(square);
                }

                if (singleCandidateCell != null){
                    flag = true;
                    board = substituteCellOfBoard(board, singleCandidateCell);
                    board = cleanBoardBasedOnCell(board, singleCandidateCell);
                    System.out.printf("searchAndSetSingleCandidate: Board cleaned for value %s in x = %s and y = %s%n",
                            singleCandidateCell.getValue(), singleCandidateCell.getxPosition(), singleCandidateCell.getyPosition());
                }
            }
        } while (flag);

        return result;
    }
}
