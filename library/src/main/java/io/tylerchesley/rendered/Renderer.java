package io.tylerchesley.rendered;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class Renderer<E> extends RecyclerView.ViewHolder {

    public Renderer(View itemView) {
        super(itemView);
    }

    public abstract void bindView(E data);

}
