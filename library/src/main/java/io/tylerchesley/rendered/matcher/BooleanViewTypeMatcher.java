package io.tylerchesley.rendered.matcher;

import android.support.annotation.NonNull;

public class BooleanViewTypeMatcher<E> implements ViewTypeMatcher<E> {

    public static <E> Builder<E> from(PropertyGetter<E, Boolean> getter) {
        return new Builder<E>().getter(getter);
    }

    private final int trueViewType;
    private final int falseViewType;
    private final PropertyGetter<E, Boolean> getter;

    public BooleanViewTypeMatcher(@NonNull PropertyGetter<E, Boolean> getter, int trueViewType,
                                  int falseViewType) {
        if (getter == null) {
            throw new NullPointerException("PropertyGetter may not be null");
        }

//        if (trueViewType < 0) {
//            throw new IllegalArgumentException("trueViewType must be >= 0");
//        }
//
//        if (falseViewType < 0) {
//            throw new IllegalArgumentException("falseViewType must be >= 0");
//        }

        if (trueViewType == falseViewType) {
            throw new IllegalArgumentException("true view type may not be equal to the " +
                    "false view type");
        }

        this.getter = getter;
        this.trueViewType = trueViewType;
        this.falseViewType = falseViewType;
    }

    @Override
    public int match(E item) {
        return getter.get(item) ? trueViewType : falseViewType;
    }

    public static final class Builder<E> {

        private PropertyGetter<E, Boolean> getter;
        private int trueViewType = NO_MATCH;
        private int falseViewType = NO_MATCH;

        public Builder<E> getter(PropertyGetter<E, Boolean> getter) {
            this.getter = getter;
            return this;
        }

        public Builder<E> trueViewType(int trueViewType) {
            this.trueViewType = trueViewType;
            return this;
        }

        public Builder<E> falseViewType(int falseViewType) {
            this.falseViewType = falseViewType;
            return this;
        }

        public BooleanViewTypeMatcher<E> build() {
            return new BooleanViewTypeMatcher<>(getter, trueViewType, falseViewType);
        }

    }

}
