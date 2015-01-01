package io.tylerchesley.rendered;

import android.util.SparseArray;

import java.util.List;

public class RendererTypeFactory<E> implements RendererFactory<E> {

    private final SparseArray<Renderer<E>> rendersByType = new SparseArray<>();

    private final List<Renderer<E>> renderers;

    public RendererTypeFactory(List<Renderer<E>> renderers) {
        this.renderers = renderers;
    }

    @Override
    public Renderer<E> getRenderer(int type) {
        return rendersByType.get(type);
    }

    @Override
    public int getViewType(E item) {
        for (Renderer<E> renderer : renderers) {
            if (renderer.isFor(item)) {
                final int type = renderer.getViewType();
                rendersByType.put(type, renderer);
                return type;
            }
        }
        throw new NullPointerException("No renderer for item " + item);
    }

}
