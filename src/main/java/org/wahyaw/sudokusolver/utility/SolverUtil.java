package org.wahyaw.sudokusolver.utility;


import org.wahyaw.sudokusolver.Main;
import org.wahyaw.sudokusolver.entity.Cell;
import org.wahyaw.sudokusolver.entity.Square;

import java.util.List;
import java.util.logging.Logger;

import static org.wahyaw.sudokusolver.utility.HelperUtil.generateUnsolvedCellValueList;

/**
 * Created by wahyaw on 11/20/2017.
 */
public class SolverUtil {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    /**
     * MUTATION
     * Checking whether an unsolved Cell in a Square has only one possibility for value (one candidate is true).
     * If any, set value into that Cell.
     *
     * @param square
     * @return
     */
    public static Cell checkSingleCandidateInCell(Square square){
        Square resultSquare = new Square(square);

        for(Cell cell : resultSquare.getCells()){
            if(cell.getValue() == null){
                //check each cell whether there's any single candidate
                List<Boolean> candidates = cell.getCandidates().getCandidateList();
                Integer trueCandidate = null;
                for(int index = 0; index < candidates.size(); index++){
                    if (trueCandidate == null){
                        if(candidates.get(index)){
                            trueCandidate = index +1;
                        }
                    } else {
                        if(candidates.get(index)){
                            //if more than one Candidate in a Cell, reset and continue into next Cell
                            trueCandidate = null;
                            break;
                        }
                    }
                }

                if(trueCandidate != null){
                    cell.setValue(trueCandidate);
                    System.out.printf("checkSingleCandidateInCell: Set value %s in coordinate x = %s and y = %s%n",
                            trueCandidate, cell.getxPosition(), cell.getyPosition());
                    return cell;
                }
            }
        }

        return null;
    }

    /**
     * Checking whether a Square has only one possibility for value (one candidate is true).
     * If any, set value into that Cell.
     *
     * @param square
     * @return
     */
    public static Cell checkSingleCandidateInSquare(Square square){
        List<Integer> unsolvedCellValueList = generateUnsolvedCellValueList(square);
        Square resultSquare = new Square(square.getCells());
        Cell result = null;

        for(int value : unsolvedCellValueList){
            boolean isCandidateAlreadyFound = false;

            for(Cell cell : resultSquare.getCells()){
                int index = value - 1;
                if(cell.getValue() == null){
                    if(!isCandidateAlreadyFound){
                        if(cell.getCandidates().getCandidateList().get(index)) {
                            isCandidateAlreadyFound = true;
                            result = new Cell(value, cell.getxPosition(), cell.getyPosition());
                        }
                    } else {
                        if(cell.getCandidates().getCandidateList().get(index)) {
                            //break loop for this value
                            result = null;
                            break;
                        }
                    }
                }
            }

            if (result != null){
                return result;
            }
        }

        return result;
    }



}
