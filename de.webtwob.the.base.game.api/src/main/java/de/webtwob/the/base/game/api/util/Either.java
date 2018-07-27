package de.webtwob.the.base.game.api.util;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by BB20101997 on 22. Jul. 2018.
 */
public abstract class Either <L, R> implements Bifunctor<L,R>{

    private Either() {}



    @SuppressWarnings("unchecked")
    public static <S,EL extends S,ER extends S> S value(Either<EL,ER> either){
        return (S) either.value();
    }

    protected abstract Object value();

    public L fromLeft(final L otherwise){
        return otherwise;
    }

    public R fromRight(final R otherwise){
        return otherwise;
    }

    public abstract  <RL,RR> Either<RL,RR> bimap(Function<L,RL> leftMap,Function<R,RR> rightMap);

    public abstract void apply(Consumer<L> leftApply,Consumer<R> rightApply);

    public static final class Left <L, R> extends Either<L, R> {

        private final L left;

        public Left(final L left) {
            super();
            Objects.requireNonNull(left);
            this.left = left;
        }

        @Override
        protected L value() {
            return left;
        }

        @Override
        public L fromLeft(final L otherwise) {
            return left;
        }

        @Override
        public <RL, RR> Either<RL, RR> bimap(final Function<L, RL> leftMap, final Function<R, RR> rightMap) {
            return new Left<>(leftMap.apply(left));
        }

        @Override
        public void apply(final Consumer<L> leftApply, final Consumer<R> rightApply) {
            leftApply.accept(left);
        }
    }

    public static final class Right <L, R> extends Either<L, R> {

        private final R right;

        public Right(final R right) {
            super();
            Objects.requireNonNull(right);
            this.right = right;
        }

        @Override
        protected R value() {
            return right;
        }

        @Override
        public R fromRight(final R otherwise) {
            return right;
        }

        @Override
        public <RL, RR> Either<RL, RR> bimap(final Function<L, RL> leftMap, final Function<R, RR> rightMap) {
            return new Right<>(rightMap.apply(right));
        }

        @Override
        public void apply(final Consumer<L> leftApply, final Consumer<R> rightApply) {
            rightApply.accept(right);
        }
    }
}
