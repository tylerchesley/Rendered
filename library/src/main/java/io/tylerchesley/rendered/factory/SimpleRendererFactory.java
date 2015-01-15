package io.tylerchesley.rendered.factory;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import io.tylerchesley.rendered.renderer.Layout;
import io.tylerchesley.rendered.renderer.Renderer;
import io.tylerchesley.rendered.renderer.ViewType;

public class SimpleRendererFactory<E> implements RendererFactory<E> {

    public static <E> SimpleRendererFactory<E> from(Class<? extends Renderer<E>>... classes) {
        final Builder<E> builder = new Builder<>();
        for (Class<? extends Renderer<E>> rendererClass : classes) {
            builder.add(rendererClass);
        }
        return builder.build();
    }

    private final Map<Integer, RendererConfig<E>> configs;

    public SimpleRendererFactory(@NonNull Map<Integer, RendererConfig<E>> configs) {
        if (configs == null) {
            throw new NullPointerException("Configs may not be null");
        }
        this.configs = configs;
    }

    @Override
    public Renderer<E> createRenderer(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(viewType, parent, false);
        final RendererConfig<E> config = configs.get(viewType);
        if (config == null) {
            throw new IllegalArgumentException("No renderer configuration found for viewType "
                    + viewType);
        }
        return config.create(view);
    }

    public static final class RendererConfig<E> implements CreationStrategy<E> {

        public final int layoutRes;
        public final CreationStrategy<E> strategy;

        public RendererConfig(@LayoutRes int layoutRes, @NonNull CreationStrategy<E> strategy) {
            if (strategy == null) {
                throw new NullPointerException("CreationStrategy may not be null");
            }
            this.layoutRes = layoutRes;
            this.strategy = strategy;
        }

        @Override
        public Renderer<E> create(View view) {
            return strategy.create(view);
        }

    }

    public static interface CreationStrategy<E> {

        Renderer<E> create(View view);

    }

    public static class SimpleCreationStrategy<E> implements CreationStrategy<E> {

        private final Constructor<? extends Renderer<E>> constructor;

        public SimpleCreationStrategy(Class<? extends Renderer<E>> rendererClass) {
            try {
                this.constructor = rendererClass.getConstructor(View.class);
            } catch (NoSuchMethodException e) {
                throw new IllegalArgumentException("Renderer must have a constructor that takes " +
                        "only a view instance", e);
            }
        }

        @Override
        public Renderer<E> create(View view) {
            try {
                return constructor.newInstance(view);
            } catch (Exception e) {
                e.printStackTrace();
                throw new IllegalArgumentException("Renderer must have a constructor that takes " +
                        "only a view instance", e);
            }
        }

    }

    public static final class Builder<E> {

        private final Map<Integer, RendererConfig<E>> creators;

        public Builder() {
            this.creators = new HashMap<>();
        }

        public Builder<E> add(Class<? extends Renderer<E>> rendererClass) {
            final ViewType viewType = rendererClass.getAnnotation(ViewType.class);
            if (viewType == null) {
                throw new IllegalArgumentException("Renderer class must have a ViewType " +
                        "annotation");
            }

            final Layout layout = rendererClass.getAnnotation(Layout.class);
            return add(viewType.value(),
                    layout != null ? layout.value() : viewType.value(), rendererClass);
        }

        public Builder<E> add(@LayoutRes int viewType,
                              Class<? extends Renderer<E>> rendererClass) {
            return add(viewType, viewType, new SimpleCreationStrategy<>(rendererClass));
        }

        public Builder<E> add(int viewType, @LayoutRes int layout,
                              Class<? extends Renderer<E>> rendererClass) {
            return add(viewType, layout, new SimpleCreationStrategy<>(rendererClass));
        }

        public Builder<E> add(int viewType, CreationStrategy<E> strategy) {
            creators.put(viewType, new RendererConfig<E>(viewType, strategy));
            return this;
        }

        public Builder<E> add(int viewType, int layout, CreationStrategy<E> strategy) {
            creators.put(viewType, new RendererConfig<E>(layout, strategy));
            return this;
        }

        public SimpleRendererFactory<E> build() {
            return new SimpleRendererFactory<>(creators);
        }

    }

}
