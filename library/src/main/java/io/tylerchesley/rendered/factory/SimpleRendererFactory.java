package io.tylerchesley.rendered.factory;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;

import java.lang.reflect.Constructor;

import io.tylerchesley.rendered.renderer.Renderer;
import io.tylerchesley.rendered.util.Util;

public class SimpleRendererFactory<E> extends InflatingRendererFactory<E> {

    public static <E> SimpleRendererFactory<E> from(Class<? extends Renderer<E>> rendererClass) {
        return from(Util.getLayoutValue(rendererClass, INVALID_LAYOUT), rendererClass);
    }

    public static <E> SimpleRendererFactory<E> from(@LayoutRes int layout,
                                                    Class<? extends Renderer<E>> rendererClass) {
        return new SimpleRendererFactory<>(layout, rendererClass);
    }

    private final Constructor<? extends Renderer<E>> constructor;

    public SimpleRendererFactory(Class<? extends Renderer<E>> rendererClass) {
        this(INVALID_LAYOUT, rendererClass);
    }

    public SimpleRendererFactory(@LayoutRes int layout,
                                 @NonNull Class<? extends Renderer<E>> rendererClass) {
        super(layout);
        try {
            this.constructor = rendererClass.getConstructor(View.class);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("Renderer must have a constructor that takes " +
                    "only a view instance", e);
        }
    }

    @Override
    protected Renderer<E> createRenderer(int viewType, View view) {
        try {
            return constructor.newInstance(view);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Renderer must have a constructor that takes " +
                    "only a view instance", e);
        }
    }

}
