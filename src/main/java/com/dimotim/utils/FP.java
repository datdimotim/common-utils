package com.dimotim.utils;

import io.reactivex.rxjava3.core.Observable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FP {
    public static <T> Stream<T> flatify(T root, Function<T,Stream<T>> childExtractor){
        return Stream.concat(
                Stream.of(root),
                childExtractor.apply(root)
                        .flatMap(child->flatify(child,childExtractor))
        );
    }

    public static <T> Collector<T,List<List<T>>,List<List<T>>> batchCollector(int batchSize){
        return Collector.of(
                ()->{
                    ArrayList<T> in=new ArrayList<>();
                    ArrayList<List<T>> out=new ArrayList<>();
                    out.add(in);
                    return out;
                },
                (lls,s)->{
                    if(lls.get(lls.size()-1).size()==batchSize){
                        lls.add(new ArrayList<>());
                    }
                    lls.get(lls.size()-1).add(s);
                },
                (ls,rs)-> Stream.concat(ls.stream(),rs.stream()).collect(Collectors.toList())
        );
    }
}
