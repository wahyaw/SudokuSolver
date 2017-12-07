package org.wahyaw.sudokusolver.entity;

import org.apache.commons.collections.list.FixedSizeList;

import java.util.Arrays;
import java.util.List;

/**
 * Created by wahyaw on 11/20/2017.
 *
 * Use this to mark cell value candidateList.
 * All candidateList FALSE means cell is solved
 */
public class Candidates {
    private List<Boolean> candidateList;

    public List<Boolean> getCandidateList() {
        return candidateList;
    }

    public void setCandidateList(List<Boolean> candidateList) {
        this.candidateList = candidateList;
    }


    //DEFAULT CONSTRUCTOR - ALL VALUE = TRUE//
    public Candidates() {
        this.candidateList = assignAllCandidatesValue(true);
    }

    public Candidates(List<Boolean> candidateList){
        this.candidateList = candidateList;
    }

    public Candidates(Boolean value) {
        this.candidateList = assignAllCandidatesValue(value);
    }

    public List<Boolean> assignAllCandidatesValue(boolean value){
        Boolean[] candidateArray = new Boolean[9];
        Arrays.fill(candidateArray, value);

        return FixedSizeList.decorate(Arrays.asList(candidateArray));
    }

    public Candidates(Candidates candidates){
        this.candidateList = candidates.candidateList;
    }


    //SPLITTING//
    public boolean getCandidate1() {
        return candidateList.get(0);
    }

    public void setCandidate1(boolean candidate) {
        candidateList.set(0, candidate);
    }

    public boolean getCandidate2() {
        return candidateList.get(1);
    }

    public void setCandidate2(boolean candidate) {
        candidateList.set(1, candidate);
    }

    public boolean getCandidate3() {
        return candidateList.get(2);
    }

    public void setCandidate3(boolean candidate) {
        candidateList.set(2, candidate);
    }

    public boolean getCandidate4() {
        return candidateList.get(3);
    }

    public void setCandidate4(boolean candidate) {
        candidateList.set(3, candidate);
    }

    public boolean getCandidate5() {
        return candidateList.get(4);
    }

    public void setCandidate5(boolean candidate) {
        candidateList.set(4, candidate);
    }

    public boolean getCandidate6() {
        return candidateList.get(5);
    }

    public void setCandidate6(boolean candidate) {
        candidateList.set(5, candidate);
    }

    public boolean getCandidate7() {
        return candidateList.get(6);
    }

    public void setCandidate7(boolean candidate) {
        candidateList.set(6, candidate);
    }

    public boolean getCandidate8() {
        return candidateList.get(7);
    }

    public void setCandidate8(boolean candidate) {
        candidateList.set(8, candidate);
    }

    public boolean getCandidate9() {
        return candidateList.get(8);
    }

    public void setCandidate9(boolean candidate) {
        candidateList.set(9, candidate);
    }


    //CHECKER WHETHER THERE IS ONLY ONE CANDIDATE TRUE//
    public Integer returnOneCandidateValue(){
        Integer found = null;

        //loop through candidateList
        int counter = 1;
        for(Boolean candidate: candidateList){
            if(found==null){
                if(candidate){
                    found = counter;
                }
            } else {
                //return null because there's more than one candidate
                return null;
            }
        }

        return found;
    }
}
