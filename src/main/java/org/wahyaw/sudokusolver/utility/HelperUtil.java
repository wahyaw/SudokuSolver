package org.wahyaw.sudokusolver.utility;

import org.wahyaw.sudokusolver.entity.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.wahyaw.sudokusolver.utility.ConverterUtil.convertBoardHorizontalIntoSquare;
import static org.wahyaw.sudokusolver.utility.ConverterUtil.convertBoardVerticalIntoSquare;
import static org.wahyaw.sudokusolver.utility.ConverterUtil.getCellFromBoardByCoordinate;
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

    /**
     * Find out how many active x (index (0-8)) candidate in a square.
     * @param candidateToBeCheckedIndex
     * @param square
     * @return
     */
    public static int findNumberOfTrueCandidateInSquare(int candidateToBeCheckedIndex, Square square){
        int result = 0;

        for(Cell cell : square.getCells()){
            if(cell.getCandidates().getCandidateList().get(candidateToBeCheckedIndex)){
                result += 1;
            }
        }

        return result;
    }

    /**
     * Generating XWing list which available in board
     * @param board
     * @return
     */
    public static List<XWing> generateXWingListFromBoard(Board board){
        List<XWing> result = new ArrayList<>();

        //loop candidates
        for(int candidateIndex = 0; candidateIndex < 9; candidateIndex++){
            for(int squareIndex = 0; squareIndex < board.getSquares().size(); squareIndex++){
                if(squareIndex%3 !=2 ){
                    Square loopedSquare = board.getSquares().get(squareIndex);

                    for(int upperLeftIndex = 0; upperLeftIndex < loopedSquare.getCells().size(); upperLeftIndex++){
                        Cell upperLeft = loopedSquare.getCells().get(upperLeftIndex);
                        //boolean isContinueToNextUpperLeftIndex = false;

                        //convert board H and V to square
                        Square convertedHBoardTop = convertBoardHorizontalIntoSquare(board, upperLeft.getyPosition());
                        Square convertedVBoardLeft = convertBoardVerticalIntoSquare(board, upperLeft.getxPosition());

                        int hTop = findNumberOfTrueCandidateInSquare(candidateIndex, convertedHBoardTop);
                        int vLeft = findNumberOfTrueCandidateInSquare(candidateIndex, convertedVBoardLeft);

                        ///////^^^^^^^^^^^^^^^^^TOP LEFT^^^^^^^^^^^^^^^^^

                        //if H and V both > 2 candidates available, continue to next cell
                        if(hTop > 2 && vLeft > 2){
                            continue; //means XWing cannot be applied in cell
                        }

                        if(upperLeft.getValue() != null
                                || !upperLeft.getCandidates().getCandidateList().get(candidateIndex)){
                            continue; //continue to next cell if cell is solved or candidate = false
                        }
                        XWing xWing = new XWing(candidateIndex+1, upperLeft);

                        ///////^^^^^^^^^^^^^^^^^TOP RIGHT^^^^^^^^^^^^^^^^^

                        for(int upperRightIndex = 0; upperRightIndex < convertedHBoardTop.getCells().size(); upperRightIndex++){
                            Cell upperRight = convertedHBoardTop.getCells().get(upperRightIndex);
                            if(upperRight.getValue() != null
                                    || !upperRight.getCandidates().getCandidateList().get(candidateIndex)
                                    || upperLeft.getxPosition() >= upperRight.getxPosition()){
                                //continue to next cell in square if cell is not positioned right of current upper left or already removed from candidate
                                continue;
                            }
                            Square convertedVBoardRight = convertBoardVerticalIntoSquare(board, upperRight.getxPosition());
                            int vRight = findNumberOfTrueCandidateInSquare(candidateIndex, convertedVBoardRight);

                            //if H and V both > 2 candidates available, continue to next cell
                            if(hTop > 2 && vRight > 2){
                                //means XWing cannot be applied in cell
                                continue;
                            }

                            xWing.setUpperRight(upperRight);
                            break;
                        }

                        ///////^^^^^^^^^^^^^^^^^BOTTOM LEFT^^^^^^^^^^^^^^^^^
                        if(xWing.getUpperLeft() == null
                                || xWing.getUpperRight() == null){
                            continue;
                        }

                        for(int lowerLeftIndex = 0; lowerLeftIndex < convertedVBoardLeft.getCells().size(); lowerLeftIndex++){
                            Cell lowerLeft = convertedVBoardLeft.getCells().get(lowerLeftIndex);

                            if(lowerLeft.getValue() != null
                                    || !lowerLeft.getCandidates().getCandidateList().get(candidateIndex)
                                    || upperLeft.getyPosition() >= lowerLeft.getyPosition()){
                                //continue to next cell in square if cell is not positioned right of current upper left or already removed from candidate
                                continue;
                            }

                            Square convertedVBoardBoard = convertBoardHorizontalIntoSquare(board, lowerLeft.getyPosition());
                            int hBot = findNumberOfTrueCandidateInSquare(candidateIndex, convertedVBoardBoard);

                            //if H and V both > 2 candidates available, continue to next cell
                            if(hBot > 2 && vLeft > 2){
                                //means XWing cannot be applied in cell
                                break;
                            }

                            xWing.setLowerLeft(lowerLeft);
                            break;
                        }

                        ///////^^^^^^^^^^^^^^^^^BOTTOM RIGHT^^^^^^^^^^^^^^^^^
                        //check all other already filled before filling bottom right
                        if(xWing.getUpperLeft() != null
                                && xWing.getUpperRight() != null
                                && xWing.getLowerLeft() != null){

                            Cell lowerRight = getCellFromBoardByCoordinate(board,
                                    xWing.getUpperRight().getxPosition(),
                                    xWing.getLowerLeft().getyPosition());

                            if(lowerRight.getValue() != null
                                    || !lowerRight.getCandidates().getCandidateList().get(candidateIndex)
                                    || upperLeft.getyPosition() >= lowerRight.getyPosition()){
                                continue;
                            }

                            xWing.setLowerRight(lowerRight);

                            //validate all cell is not on one square
                            if (xWing.isAllCellInOneSquare()){
                                continue;
                            }

                            ///////^^^^^^^^^^^^^^^^^PUSH^^^^^^^^^^^^^^^^^
                            result.add(xWing);
                        }
                    }
                }
            }
        }

        return result;
    }


    /**
     * Search Hidden Pair in board. Only search in horizontal or vertical based on parameter.
     *
     * @param board
     * @param isHorizontal
     * @return
     */
    public static List<HiddenPair> generateHiddenPairListFromBoard(Board board, boolean isHorizontal){
        List<HiddenPair> result = new ArrayList<>();
        for(int indexToBeTaken = 1; indexToBeTaken <= 9; indexToBeTaken++) {
            Square processedSquare = new Square();
            if(isHorizontal) {
                processedSquare = convertBoardHorizontalIntoSquare(board, indexToBeTaken);
            } else {
                processedSquare = convertBoardVerticalIntoSquare(board, indexToBeTaken);
            }
            CandidatesCount candidatesCount = new CandidatesCount(processedSquare);

            if (candidatesCount.getCountOfTwoCandidates() >= 2) {

                for (int firstCandidateIndex = 0; firstCandidateIndex < candidatesCount.getCandidateList().size(); firstCandidateIndex++) {
                    if (candidatesCount.getCandidateList().get(firstCandidateIndex) != 2) {
                        continue;
                    }

                    for (int leftIndex = 0; leftIndex < candidatesCount.getCandidateList().size(); leftIndex++) {
                        Cell leftCell = processedSquare.getCells().get(leftIndex);

                        if (leftCell.getValue() == null
                                && leftCell.getCandidates().getCandidateList().get(firstCandidateIndex)) {
                            for (int secondCandidateIndex = 0; secondCandidateIndex < candidatesCount.getCandidateList().size(); secondCandidateIndex++) {
                                if (candidatesCount.getCandidateList().get(secondCandidateIndex) != 2
                                        || secondCandidateIndex <= firstCandidateIndex
                                        || !leftCell.getCandidates().getCandidateList().get(secondCandidateIndex)) {
                                    continue;
                                }

                                //saving to new object
                                HiddenPair hiddenPair = new HiddenPair(firstCandidateIndex, secondCandidateIndex, leftCell);

                                //RIGHT CELL
                                for (int rightIndex = 0; rightIndex < candidatesCount.getCandidateList().size(); rightIndex++) {
                                    Cell rightCell = processedSquare.getCells().get(rightIndex);

                                    if (rightCell.getCandidates().getCandidateList().get(firstCandidateIndex)
                                            && rightCell.getCandidates().getCandidateList().get(secondCandidateIndex)
                                            && (leftCell.getxPosition() != rightCell.getxPosition() || leftCell.getyPosition() != rightCell.getyPosition())
                                            && (leftCell.getxPosition() <= rightCell.getxPosition() && leftCell.getyPosition() <= rightCell.getyPosition())) {
                                        hiddenPair.setRightCell(rightCell);
                                        break;
                                    }
                                }

                                if (hiddenPair.isAllPropertyNotNull()) {
                                    result.add(hiddenPair);
                                }
                            }
                        }
                    }
                }
            }
        }

        return result;
    }

    /**
     * Search Hidden Pair in board.
     *
     * @param board
     * @return
     */
    public static List<HiddenPair> generateHiddenPairListFromBoard(Board board){
        List<HiddenPair> result = generateHiddenPairListFromBoard(board, true);
        result.addAll(generateHiddenPairListFromBoard(board, false));

        return result;
    }
}
