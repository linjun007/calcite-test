package com.calcite.fn;

import java.util.HashMap;
import java.util.TreeSet;


public class MaxByFn extends AbstractMaxBy {

    public MaxByFn() {
        tempData = new HashMap<>();
    }

    public TreeSet<String> add(TreeSet<String> accumulator, String rtnCol, String compareCol) {
        return eval(accumulator, rtnCol, compareCol, 1);
    }
}