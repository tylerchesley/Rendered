package io.tylerchesley.rendered;

public interface RendererFactory<E> {

    Renderer<E> getRenderer(int type);

    int getViewType(E item);

}
