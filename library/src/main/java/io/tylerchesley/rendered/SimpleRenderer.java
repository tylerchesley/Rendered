package io.tylerchesley.rendered;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class SimpleRenderer<E> implements Renderer<E> {

    private final int layoutRes;
    private final int viewType;

    public SimpleRenderer(int layoutRes, int viewType) {
        this.layoutRes = layoutRes;
        this.viewType = viewType;
    }

    @Override
    public RendererViewHolder<E> createViewHolder(ViewGroup group) {
        final View view = LayoutInflater.from(group.getContext()).inflate(layoutRes, group, false);
        if (view.isClickable()) {

        }
        return onCreateViewHolder(view);
    }

    @Override
    public int getViewType() {
        return viewType;
    }

    abstract protected RendererViewHolder<E> onCreateViewHolder(View view);

}
