package com.axway.academy.model.entity;

import java.util.Arrays;
import java.util.List;

public enum DecisionType {
    APPROVED,REJECTED,UNDECIDED;

    public static List<DecisionType> getTypes(){
        return Arrays.asList(DecisionType.APPROVED,REJECTED,UNDECIDED);
    }
}
