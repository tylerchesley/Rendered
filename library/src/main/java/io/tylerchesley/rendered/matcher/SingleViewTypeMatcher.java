package io.tylerchesley.rendered.matcher;

public class SingleViewTypeMatcher<E> implements ViewTypeMatcher<E> {

    public static <E> SingleViewTypeMatcher<E> from(int viewType) {
        return new SingleViewTypeMatcher<>(viewType);
    }

    private final int viewType;

    public SingleViewTypeMatcher(int viewType) {
        this.viewType = viewType;
    }

    @Override
    public int match(E item) {
        return viewType;
    }

}
