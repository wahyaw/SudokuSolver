package org.wahyaw.sudokusolver.utility;

import org.apache.commons.collections.list.FixedSizeList;
import org.wahyaw.sudokusolver.Main;
import org.wahyaw.sudokusolver.entity.Board;
import org.wahyaw.sudokusolver.entity.Cell;
import org.wahyaw.sudokusolver.entity.Square;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by wahyaw on 11/20/2017.
 *
 * Used to convert between one class into another class.
 * e.g. from horizontal line into Square
 */
public class ConverterUtil {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
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

    /**
     * Convert Integer into Cell class.
     * When value of integer is 0, then Cell value is null with all Candidates set to True
     * When value of integer is in range of 1-9, then Cell value is set and all Candidates set to False
     * When value of integer is greater than 9, then Cell value is null with all Candidates set to True
     *
     * @param value
     * @return Cell object of converted value
     */
    public static Cell convertIntegerIntoCell(Integer value){
        Cell result;
        if(value >= 1 || value <= 9){
            result = new Cell(value);
        } else {
            result = new Cell();
        }

        return  result;
    }

    /**
     * Convert Integer with its Coordinate into Cell class.
     * When value of integer is 0, then Cell value is null with all Candidates set to True
     * When value of integer is in range of 1-9, then Cell value is set and all Candidates set to False
     * When value of integer is greater than 9, then Cell value is null with all Candidates set to True
     *
     * @param value
     * @param xCoordinate
     * @param yCoordinate
     * @return Cell object of converted value
     */
    public static Cell convertIntegerWithCoordinateIntoCell(Integer value, int xCoordinate, int yCoordinate){
        Cell result;
        if(value >= 1 && value <= 9){
            result = new Cell(value, xCoordinate, yCoordinate);
        } else {
            result = new Cell(xCoordinate, yCoordinate);
        }

        return  result;
    }


    /**
     * Converting boardArray (Integer[][]) into 2D List of Cells.
     * Complete with the coordinate.
     *
     * @param boardArray
     * @return
     */
    public static List<List<Cell>> convertBoardArrayIntoCell2d(Integer[][] boardArray){
        List<List<Cell>> result = new ArrayList<>();

        for(int y=0; y<boardArray.length; y++){
            List<Cell> horizontal = new ArrayList<>();

            for(int x=0; x<boardArray[y].length; x++){
                Cell cell = convertIntegerWithCoordinateIntoCell(
                        boardArray[y][x],
                        x+1, //increase by one because array start from 0 while board start from 1
                        y+1 //increase by one because array start from 0 while board start from 1
                );
                horizontal.add(cell);
            }
            result.add(horizontal);
        }

        result = FixedSizeList.decorate(result);
        return result;
    }


    /**
     * Convert 2D List of Cells into Squares, and then into Board.
     * Complete with coordinates.
     *
     * @param cell2d
     * @return
     */
    public static Board convertCell2dIntoBoard(List<List<Cell>> cell2d){
        List<Square> result = new ArrayList<>();

        for(int y=0; y<cell2d.size(); y++){
            List<Cell> square = new ArrayList<>();

            for(int x=0; x<cell2d.get(y).size(); x++){
                //3*DIV(y,3) + DIV(x,3)
                //3*MOD(y,3)+MOD(x,3)
                square.add(
                        cell2d
                                .get(3*(y/3)+(x/3))
                                .get(3*(y%3)+(x%3))

                );

            }
            result.add(new Square(square));
        }

        result = FixedSizeList.decorate(result);
        return new Board(result);
    }

    /**
     * Converting boardArray (Integer[][]) into Board.
     * Complete with the coordinate.
     *
     * @param boardArray
     * @return
     */
    public static Board convertBoardArrayIntoBoard(Integer[][] boardArray){
        return convertCell2dIntoBoard(convertBoardArrayIntoCell2d(boardArray));
    }

    /**
     * Convert back from Board into boardArray(Integer[][]).
     *
     * @param board
     * @return
     */
    public static Integer[][] convertBoardIntoBoardArray(Board board){
        Integer[][] result = new Integer[9][9];

        for (int i = 1; i<=9; i++){
            Integer[] res2 = new Integer[9];
            Square sq = convertBoardHorizontalIntoSquare(board, i);

            for (int j = 0; j < sq.getCells().size(); j++){
                res2[j] = sq.getCells().get(j).getValue();
            }
            result[i-1]= res2;
        }

        return result;
    }


    /**
     * Take certain row of Board, and convert it into Square.
     * Row to be taken is from 1-9.
     *
     * 123|456|789
     *
     * @param board
     * @param rowToBeTaken
     * @return
     */
    public static Square convertBoardHorizontalIntoSquare(Board board, int rowToBeTaken){
        int arrayIndex = rowToBeTaken - 1;
        List<Cell> cells = new ArrayList<>();

        for(int i = 0; i<board.getSquares().size(); i++){
            //3*DIV(row,3)+DIV(i,3)
            //3*MOD(row,3)+MOD(i,3)
            Cell cell = board
                            .getSquares()
                            .get(3*(arrayIndex/3) + i/3)
                            .getCells()
                            .get(3*(arrayIndex%3) + i%3);

            cells.add(cell);

            //simple validation
            if(!cell.getyPosition().equals(rowToBeTaken)){
                System.out.printf("wrong cell taken on convertBoardHorizontalIntoSquare " + i);
            }
        }

        return new Square(cells);
    }


    /**
     * Take certain column of Board, and convert it into Square.
     * Column to be taken is from 1-9.
     *
     * 1
     * 2
     * 3
     * -
     * 4
     * 5
     * 6
     * -
     * 7
     * 8
     * 9
     *
     * @param board
     * @param columnToBeTaken
     * @return
     */
    public static Square convertBoardVerticalIntoSquare(Board board, int columnToBeTaken){
        int arrayIndex = columnToBeTaken - 1;
        List<Cell> cells = new ArrayList<>();

        for(int i = 0; i<board.getSquares().size(); i++){
            //DIV(row,3)+3*DIV(i,3)
            //MOD(row,3)+3*MOD(i,3)
            Cell cell = board
                    .getSquares()
                    .get(arrayIndex/3 + 3*(i/3))
                    .getCells()
                    .get(arrayIndex%3 + 3*(i%3));

            cells.add(cell);

            //simple validation
            if(!cell.getxPosition().equals(columnToBeTaken)){
                System.out.printf("wrong cell taken on convertBoardVerticalIntoSquare " + i);
            }
        }

        return new Square(cells);
    }
}
