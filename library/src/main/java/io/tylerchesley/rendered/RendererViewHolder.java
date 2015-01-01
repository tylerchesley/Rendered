package io.tylerchesley.rendered;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class RendererViewHolder<E> extends RecyclerView.ViewHolder {

    public RendererViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bindView(E data);

}
