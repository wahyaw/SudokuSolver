package org.wahyaw.sudokusolver.utility;

import org.wahyaw.sudokusolver.Main;
import org.wahyaw.sudokusolver.entity.Board;
import org.wahyaw.sudokusolver.entity.Cell;
import org.wahyaw.sudokusolver.entity.NakedCell;
import org.wahyaw.sudokusolver.entity.Square;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.wahyaw.sudokusolver.utility.ConverterUtil.convertBoardHorizontalIntoSquare;
import static org.wahyaw.sudokusolver.utility.ConverterUtil.convertBoardVerticalIntoSquare;
import static org.wahyaw.sudokusolver.utility.HelperUtil.checkHorizontalLineCandidateInSquare;
import static org.wahyaw.sudokusolver.utility.HelperUtil.checkVerticalLineCandidateInSquare;
import static org.wahyaw.sudokusolver.utility.HelperUtil.determineSquareIndexInBoardByCell;

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

            if (cellToBeModified.getValue() == null
                    && cellToBeModified.getCandidates().getCandidateList().get(candidateIndexToBeRemoved)) {
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

            if (cellToBeModified.getValue() == null
                    && cellToBeModified.getCandidates().getCandidateList().get(candidateIndexToBeRemoved)) {
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
            if (cellToBeModified.getValue() == null
                    && cellToBeModified.getCandidates().getCandidateList().get(candidateIndexToBeRemoved)) {
                removeCandidateValueFromCell(cellToBeModified, candidateIndexToBeRemoved);
                System.out.printf("Remove S Candidate with index %s in coordinate x = %s and y = %s%n",
                        candidateIndexToBeRemoved, cellToBeModified.getxPosition(), cellToBeModified.getyPosition());
            }
        }

        return result;
    }

    /**
     * Removing duplicate value in horizontal/vertical naked list.
     * Take the first, since the only coordinate matters is the Y or X coordinate
     *
     * @param nakedCellList
     * @return
     */
    public static List<NakedCell> removeDuplicateNakedCells(List<NakedCell> nakedCellList){
        List<NakedCell> result = new ArrayList<>();
        List<Integer> helperList = new ArrayList<>();

        //check whether value already added to helperList
        for(NakedCell nakedCell : nakedCellList){
            if(!helperList.contains(nakedCell.getValue())){
                helperList.add(nakedCell.getValue());
                result.add(nakedCell);
            }
        }

        return result;
    }


    /**
     * Find candidate in a horizontal line, and set to false if the cell is in different square than the input nakedCell
     *
     * @param board
     * @param nakedCell
     * @return
     */
    public static Board cleanHorizontalLineFromAnotherSquare(Board board, NakedCell nakedCell){
        Square processedSquare = convertBoardHorizontalIntoSquare(board, nakedCell.getyPosition());
        Board result = new Board(board.getSquares());
        int candidateIndexToBeRemoved = nakedCell.getValue() - 1;

        for(Cell cellToBeModified : processedSquare.getCells()) {
            if (cellToBeModified.getValue() == null
                    && determineSquareIndexInBoardByCell(cellToBeModified) != determineSquareIndexInBoardByCell(nakedCell)
                    && cellToBeModified.getCandidates().getCandidateList().get(candidateIndexToBeRemoved)) {
                removeCandidateValueFromCell(cellToBeModified, candidateIndexToBeRemoved);
                System.out.printf("LINE - Remove H Candidate with index %s in coordinate x = %s and y = %s%n",
                        candidateIndexToBeRemoved, cellToBeModified.getxPosition(), cellToBeModified.getyPosition());
            }
        }

        return result;
    }

    /**
     * Find candidate in a vertical line, and set to false if the cell is in different square than the input nakedCell
     *
     * @param board
     * @param nakedCell
     * @return
     */
    public static Board cleanVerticalLineFromAnotherSquare(Board board, NakedCell nakedCell){
        Square processedSquare = convertBoardVerticalIntoSquare(board, nakedCell.getxPosition());
        Board result = new Board(board.getSquares());
        int candidateIndexToBeRemoved = nakedCell.getValue() - 1;

        for(Cell cellToBeModified : processedSquare.getCells()) {
            if (cellToBeModified.getValue() == null
                    && determineSquareIndexInBoardByCell(cellToBeModified) != determineSquareIndexInBoardByCell(nakedCell)
                    && cellToBeModified.getCandidates().getCandidateList().get(candidateIndexToBeRemoved)) {
                removeCandidateValueFromCell(cellToBeModified, candidateIndexToBeRemoved);
                System.out.printf("LINE - Remove V Candidate with index %s in coordinate x = %s and y = %s%n",
                        candidateIndexToBeRemoved, cellToBeModified.getxPosition(), cellToBeModified.getyPosition());
            }
        }

        return result;
    }

    /**
     * Find candidate in a horizontal line, and set to false if the cell is in different square than the input nakedCell
     *
     * @param board
     * @param nakedCellList
     * @return
     */
    public static Board cleanHorizontalLineFromAnotherSquare(Board board, List<NakedCell> nakedCellList){
        Board result = new Board(board.getSquares());

        for(NakedCell nakedCell : nakedCellList){
            cleanHorizontalLineFromAnotherSquare(board, nakedCell);
        }

        return result;
    }

    /**
     * Find candidate in a vertical line, and set to false if the cell is in different square than the input nakedCell
     *
     * @param board
     * @param nakedCellList
     * @return
     */
    public static Board cleanVerticalLineFromAnotherSquare(Board board, List<NakedCell> nakedCellList){
        Board result = new Board(board.getSquares());

        for(NakedCell nakedCell : nakedCellList){
            cleanVerticalLineFromAnotherSquare(board, nakedCell);
        }

        return result;
    }

    /**
     * Find candidate in a vertical line, and set to false if the cell is in different square
     * @param board
     * @return
     */
    public static Board cleanHorizontalVerticalByLiningCandidatesInSquare(Board board){
        Board resultBoard = new Board(board.getSquares());

        List<NakedCell> nakedCellHorizontal = checkHorizontalLineCandidateInSquare(resultBoard.getSquares());
        List<NakedCell> nakedCellVertical = checkVerticalLineCandidateInSquare(resultBoard.getSquares());

        resultBoard = cleanVerticalLineFromAnotherSquare(resultBoard, nakedCellVertical);
        resultBoard = cleanHorizontalLineFromAnotherSquare(resultBoard, nakedCellHorizontal);

        return resultBoard;
    }

}
