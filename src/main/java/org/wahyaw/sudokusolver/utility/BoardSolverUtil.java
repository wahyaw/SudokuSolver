package org.wahyaw.sudokusolver.utility;

import org.wahyaw.sudokusolver.Main;
import org.wahyaw.sudokusolver.entity.Board;
import org.wahyaw.sudokusolver.entity.Cell;
import org.wahyaw.sudokusolver.entity.Square;

import java.util.logging.Logger;

import static org.wahyaw.sudokusolver.utility.ConverterUtil.convertBoardArrayIntoBoard;
import static org.wahyaw.sudokusolver.utility.RemoverUtil.cleanBoardBasedOnCell;
import static org.wahyaw.sudokusolver.utility.RemoverUtil.cleanNakedValueFromBoardCandidates;
import static org.wahyaw.sudokusolver.utility.SolverUtil.checkSingleCandidateInCell;

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
        //1. FIRST CLEANING
        Board cleanedBoard = cleanNakedValueFromBoardCandidates(board);

        //2. SEARCH AND SET SINGLE POSSIBLE CANDIDATE IN A CELL
        boolean secondStepLoopFlag;
        do {
            secondStepLoopFlag = false;
            for(Square square : cleanedBoard.getSquares()){
                Cell singleCandidateCell = checkSingleCandidateInCell(square);

                if(singleCandidateCell != null){
                    secondStepLoopFlag = true;
                    cleanedBoard = cleanBoardBasedOnCell(cleanedBoard, singleCandidateCell);
                    System.out.printf("Board cleaned for value %s in x = %s and y = %s%n",
                            singleCandidateCell.getValue(), singleCandidateCell.getxPosition(), singleCandidateCell.getyPosition());
                }
            }
        } while (secondStepLoopFlag);

        return cleanedBoard;
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
}
