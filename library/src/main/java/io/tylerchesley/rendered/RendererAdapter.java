package io.tylerchesley.rendered;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class RendererAdapter<E> extends RecyclerView.Adapter<RendererViewHolder<E>> {

    public static <E> Builder<E> from(List<E> data) {
        return from(new ListDataProvider<E>(data));
    }

    public static <E> Builder<E> from(DataProvider<E> provider) {
        return new Builder<>(provider);
    }

    public static final class Builder<E> {

        private final DataProvider<E> provider;
        private RendererFactory<E> factory;
        private List<Renderer<E>> renderers;

        public Builder(DataProvider<E> provider) {
            this.provider = provider;
        }

        public Builder<E> add(Renderer<E> renderer) {
            if (renderers == null) {
                renderers = new ArrayList<>();
            }
            renderers.add(renderer);
            return this;
        }

        public Builder<E> factory(RendererFactory<E> factory) {
            this.factory = factory;
            return this;
        }

        public RendererAdapter<E> build() {
            if (factory == null && renderers == null) {
                throw new NullPointerException("Please add a Renderer or set a RendererFactory");
            }

            if (renderers != null) {
                factory = new RendererTypeFactory<>(renderers);
            }

            return new RendererAdapter<>(factory, provider);
        }

        public void into(RecyclerView view) {
            view.setAdapter(build());
        }

    }

    private final RendererFactory<E> factory;
    private final DataProvider<E> provider;

    public RendererAdapter(RendererFactory<E> factory, DataProvider<E> provider) {
        if (factory == null) {
            throw new NullPointerException("RenderFactory may not be null");
        }
        if (provider == null) {
            throw new NullPointerException("DataProvider may not be null");
        }
        this.factory = factory;
        this.provider = provider;
    }

    @Override
    public RendererViewHolder<E> onCreateViewHolder(ViewGroup group, int type) {
        final Renderer<E> renderer = factory.getRenderer(type);
        if (renderer == null) {
            throw new NullPointerException("Renderer not found for type " + type);
        }
        return renderer.createViewHolder(group);
    }

    @Override
    public void onBindViewHolder(RendererViewHolder<E> viewHolder, int position) {
        final E item = provider.get(position);
        viewHolder.bindView(item);
    }

    @Override
    public int getItemViewType(int position) {
        final E item = provider.get(position);
        return factory.getViewType(item);
    }

    @Override
    public int getItemCount() {
        return provider.getCount();
    }

}
