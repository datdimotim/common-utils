package com.dimotim.utils.rx_java_utils;

import io.reactivex.rxjava3.core.Observable;

import java.util.function.Function;
import java.util.function.Predicate;

public class RxJavaUtil {
    public static <T> Observable<T> backtracking(T root, Function<T, Observable<T>> childGenerator, Predicate<T> solutionValidator){
        return Observable.concat(
                Observable.just(root).filter(solutionValidator::test),
                childGenerator.apply(root)
                        .flatMap(ch->backtracking(ch,childGenerator,solutionValidator))
        );
    }
}
