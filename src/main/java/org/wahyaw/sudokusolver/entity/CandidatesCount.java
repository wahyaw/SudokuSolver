package org.wahyaw.sudokusolver.entity;

import edu.emory.mathcs.backport.java.util.Arrays;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wahyaw on 12/27/2017.
 *
 * Use this to save count of candidate in a Square (either original or converted from H/V)
 */
public class CandidatesCount {
    private List<Integer> candidateCountList;

    public List<Integer> getCandidateList() {
        return candidateCountList;
    }

    public void setCandidateList(List<Integer> candidateCountList) {
        this.candidateCountList = candidateCountList;
    }


    public CandidatesCount() {
        Integer[] candidateCountArray = new Integer[9];
        List<Integer> candidateCountList = Arrays.asList(candidateCountArray);
        this.candidateCountList = candidateCountList;
    }

    public CandidatesCount(List<Integer> candidateCountList) {
        this.candidateCountList = candidateCountList;
    }

    public CandidatesCount(Square square) {
        List<Integer> candidateCountList = new ArrayList<>();
        for (int candidateIndex = 0; candidateIndex < square.getCells().get(0).getCandidates().getCandidateList().size(); candidateIndex++){
            int count = 0;
            for (int cellIndex = 0; cellIndex < square.getCells().size(); cellIndex++){
                Cell cell = square.getCells().get(cellIndex);

                if(cell.getCandidates().getCandidateList().get(candidateIndex)){
                    count++;
                }
            }

            candidateCountList.add(count);
        }

        this.candidateCountList = candidateCountList;
    }

    /**
     * Return number of candidate which only have 2 possibilities in a square (usually converted square)
     * @return
     */
    public int getCountOfTwoCandidates(){
        int result = 0;
        for(Integer candidateCount : this.candidateCountList){
            if(candidateCount == 2){
                result++;
            }
        }
        return result;
    }
}
