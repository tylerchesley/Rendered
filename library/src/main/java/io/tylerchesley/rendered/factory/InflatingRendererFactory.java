package io.tylerchesley.rendered.factory;

import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.tylerchesley.rendered.renderer.Renderer;

public abstract class InflatingRendererFactory<E> implements RendererFactory<E> {

    public static final int INVALID_LAYOUT = -1;

    private final int layoutRes;

    public InflatingRendererFactory() {
        this(INVALID_LAYOUT);
    }

    public InflatingRendererFactory(@LayoutRes int layoutRes) {
        this.layoutRes = layoutRes;
    }

    @Override
    public Renderer<E> createRenderer(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(layoutRes > 0 ? layoutRes : viewType, parent, false);
        return createRenderer(viewType, view);
    }

    protected abstract Renderer<E> createRenderer(int viewType, View view);

}
