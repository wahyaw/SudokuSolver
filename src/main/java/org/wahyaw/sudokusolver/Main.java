package org.wahyaw.sudokusolver;

import org.wahyaw.sudokusolver.entity.Board;
import org.wahyaw.sudokusolver.problem.Hard;

import java.util.logging.Logger;

import static org.wahyaw.sudokusolver.utility.BoardSolverUtil.solveBoard;
import static org.wahyaw.sudokusolver.utility.HelperUtil.printBoard;

/**
 * Created by wahyaw on 11/20/2017.
 */
public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args){
        Board board = solveBoard(Hard.hard1);
        printBoard(board);
        LOGGER.info("FINISHED");
    }
}
