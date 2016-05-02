package io.tylerchesley.rendered.factory;

import android.view.ViewGroup;

import io.tylerchesley.rendered.renderer.Renderer;

public interface RendererFactory<E> {

    Renderer<E> createRenderer(ViewGroup parent, int viewType);

}
