package io.tylerchesley.rendered.matcher;

import io.tylerchesley.rendered.renderer.Renderer;
import io.tylerchesley.rendered.util.Util;

public class SingleViewTypeMatcher<E> implements ViewTypeMatcher<E> {

    public static <E> SingleViewTypeMatcher<E> from(Class<? extends Renderer<E>> rendererClass) {
        return from(Util.getViewType(rendererClass));
    }

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
