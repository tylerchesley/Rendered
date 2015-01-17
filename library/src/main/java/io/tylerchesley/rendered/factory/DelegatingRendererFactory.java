package io.tylerchesley.rendered.factory;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.SparseArray;
import android.view.ViewGroup;

import io.tylerchesley.rendered.renderer.Renderer;
import io.tylerchesley.rendered.renderer.ViewType;

public class DelegatingRendererFactory<E> implements RendererFactory<E> {

    @SafeVarargs
    public static <E> DelegatingRendererFactory<E> from(Class<? extends Renderer<E>>... classes) {
        final Builder<E> builder = new Builder<>();
        for (Class<? extends Renderer<E>> rendererClass : classes) {
            builder.add(rendererClass);
        }
        return builder.build();
    }

    private final SparseArray<RendererFactory<E>> factories;

    public DelegatingRendererFactory(@NonNull SparseArray<RendererFactory<E>> factories) {
        if (factories == null) {
            throw new NullPointerException("Configs may not be null");
        }
        this.factories = factories;
    }

    @Override
    public Renderer<E> createRenderer(ViewGroup parent, int viewType) {
        final RendererFactory<E> factory = factories.get(viewType);
        if (factory == null) {
            throw new IllegalArgumentException("No renderer configuration found for viewType "
                    + viewType);
        }
        return factory.createRenderer(parent, viewType);
    }

    public static final class Builder<E> {

        private final SparseArray<RendererFactory<E>> creators;

        public Builder() {
            this.creators = new SparseArray<>();
        }

        public Builder<E> add(Class<? extends Renderer<E>> rendererClass) {
            final ViewType viewType = rendererClass.getAnnotation(ViewType.class);
            if (viewType == null) {
                throw new IllegalArgumentException("Renderer class must have a ViewType " +
                        "annotation");
            }

            return add(viewType.value(), rendererClass);
        }

        public Builder<E> add(@LayoutRes int viewType,
                              Class<? extends Renderer<E>> rendererClass) {
            return add(viewType, SimpleRendererFactory.from(rendererClass));
        }

        public Builder<E> add(int viewType, @LayoutRes int layout,
                              Class<? extends Renderer<E>> rendererClass) {
            return add(viewType, SimpleRendererFactory.from(layout, rendererClass));
        }

        public Builder<E> add(int viewType, RendererFactory<E> factory) {
            creators.put(viewType, factory);
            return this;
        }

        public DelegatingRendererFactory<E> build() {
            return new DelegatingRendererFactory<>(creators);
        }

    }

}
