package io.tylerchesley.rendered;

import android.view.ViewGroup;

public interface RendererFactory<E> {

    public static final int INVALID_VIEW_TYPE = -1;

    Renderer<E> createRenderer(ViewGroup parent, int viewType);

    int getViewType(E item);

}
