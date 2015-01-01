package io.tylerchesley.rendered;

import android.view.ViewGroup;

public interface Renderer<E> {

    public RendererViewHolder<E> createViewHolder(ViewGroup group);

    int getViewType();

    boolean isFor(E item);

}
