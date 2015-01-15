package io.tylerchesley.rendered.matcher;

public interface ViewTypeMatcher<E> {

    public static final int NO_MATCH = -1;

    int match(E item);

}
