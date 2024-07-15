package com.calcite.fn;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

/**
 * TODO 这里的实现不是很优雅 应该使用语法文件这种方式
 */
public abstract class AbstractMaxBy {

    Map<String, Comparable> tempData = null;

    TreeSet<String> data = new TreeSet<>(new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {
            return -tempData.get(o1).compareTo(tempData.get(o2));
        }
    });


    public AbstractMaxBy() {
        tempData = new HashMap<>();
    }

    public TreeSet<String> init() {
        return data;
    }

    protected TreeSet<String> eval(TreeSet<String> accumulator, String rtnCol, Comparable compareCol, Integer limit) {
        tempData.put(rtnCol, compareCol);
        accumulator.add(rtnCol);
        if (accumulator.size() > limit) {
            String str = accumulator.last();
            accumulator.remove(str);
            tempData.remove(str);
        }
        return accumulator;
    }

    public TreeSet<String> merge(TreeSet<String> accumulator0, TreeSet<String> accumulator1) {
        return accumulator0;
    }

    public TreeSet<String> result(TreeSet<String> accumulator) {
        return accumulator;
    }
}
