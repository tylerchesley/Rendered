package io.tylerchesley.rendered.matcher;

public interface ViewTypeMatcher<E> {

    int NO_MATCH = 0;

    int match(E item);

}
