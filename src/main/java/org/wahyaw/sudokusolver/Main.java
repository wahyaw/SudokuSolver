package org.wahyaw.sudokusolver;

import org.wahyaw.sudokusolver.entity.Board;

import java.util.logging.Logger;

import static org.wahyaw.sudokusolver.utility.BoardSolverUtil.solveBoard;
import static org.wahyaw.sudokusolver.utility.ConverterUtil.*;

/**
 * Created by wahyaw on 11/20/2017.
 */
public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args){
        Integer[][] boardArray = new Integer[][]{
                {0,3,0,   0,1,0,   0,8,0},
                {0,0,0,   6,0,5,   0,0,0},
                {0,2,1,   9,0,3,   4,5,0},
              //-------------------------
                {0,8,7,   0,0,0,   5,2,0},
                {6,0,0,   0,2,0,   0,0,9},
                {0,1,4,   0,0,0,   7,6,0},
              //-------------------------
                {0,4,5,   7,0,1,   2,9,0},
                {0,0,0,   2,0,4,   0,0,0},
                {0,9,0,   0,3,0,   0,1,0}
        };

        Board testBoard = convertBoardArrayIntoBoard(boardArray);
        Board board2 = solveBoard(testBoard);
        LOGGER.info("FINISHED");
    }
}
