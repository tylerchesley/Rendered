package io.tylerchesley.rendered;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.security.InvalidParameterException;

public class SimpleRendererFactory<E> implements RendererFactory<E> {

    public static <E> SimpleRendererFactory<E> from(Class<? extends Renderer<E>> rendererClass) {
        final RendererLayout layoutAnnotation = rendererClass.getAnnotation(RendererLayout.class);
        final RendererViewType typeAnnotation = rendererClass.getAnnotation(RendererViewType.class);
        if (layoutAnnotation == null) {
            throw new NullPointerException("Render class must have a RendererLayout " +
                    "annotation");
        }

        final int layout = layoutAnnotation.value();
        int type = typeAnnotation != null ? typeAnnotation.value() : layout;

        return new SimpleRendererFactory<>(layout, type,
                new SimpleRendererCreator<>(rendererClass));
    }

    private final int layoutRes;
    private final int viewType;
    private final RendererCreator<E> creater;

    public SimpleRendererFactory(@LayoutRes int layoutRes, @IdRes int viewType,
                                 @NonNull RendererCreator<E> creater) {
        this.layoutRes = layoutRes;
        this.viewType = viewType;
        this.creater = creater;
    }

    @Override
    public Renderer<E> createRenderer(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(layoutRes, parent, false);
        return creater.create(view);
    }

    @Override
    public int getViewType(E item) {
        return viewType;
    }

    public static interface RendererCreator<E> {

        Renderer<E> create(View view);

    }

    public static class SimpleRendererCreator<E> implements RendererCreator<E> {

        private final Constructor<? extends Renderer<E>> constructor;

        public SimpleRendererCreator(Class<? extends Renderer<E>> rendererClass) {
            try {
                this.constructor = rendererClass.getConstructor(View.class);
            } catch (NoSuchMethodException e) {
                throw new InvalidParameterException("Renderer must have a constructor with one view argument");
            }
        }

        @Override
        public Renderer<E> create(View view) {
            try {
                return constructor.newInstance(view);
            } catch (Exception e) {
                throw new InvalidParameterException("Renderer must have a constructor with one view argument");
            }
        }

    }

}
