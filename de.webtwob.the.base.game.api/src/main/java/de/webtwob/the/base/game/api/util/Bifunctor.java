package de.webtwob.the.base.game.api.util;

import java.util.function.Function;

/**
 * Created by BB20101997 on 22. Jul. 2018.
 */
public interface Bifunctor<L,R> {

   <LR,RR> Bifunctor<LR,RR> bimap(Function<L,LR> leftMap,Function<R,RR> rightMap);

   default <LR> Bifunctor<LR,R> leftMap(Function<L, LR> leftMap){
       return bimap(leftMap,Function.identity());
   }

   default <RR> Bifunctor<L,RR> rightMap(Function<R,RR> rightMap){
       return bimap(Function.identity(),rightMap);
   }

}
