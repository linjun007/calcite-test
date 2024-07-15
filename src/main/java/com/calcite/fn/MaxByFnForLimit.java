package com.calcite.fn;


import java.util.TreeSet;


public class MaxByFnForLimit extends AbstractMaxBy {


    public MaxByFnForLimit() {
    }

    public TreeSet<String> add(TreeSet<String> accumulator, String rtnCol, Long compareCol, Integer limit) {
        return eval(accumulator, rtnCol, compareCol, limit);
    }
}