package io.tylerchesley.rendered;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import io.tylerchesley.rendered.data.DataProvider;
import io.tylerchesley.rendered.data.ListDataProvider;
import io.tylerchesley.rendered.factory.RendererFactory;
import io.tylerchesley.rendered.factory.SimpleRendererFactory;
import io.tylerchesley.rendered.matcher.SingleViewTypeMatcher;
import io.tylerchesley.rendered.matcher.ViewTypeMatcher;
import io.tylerchesley.rendered.renderer.Renderer;
import io.tylerchesley.rendered.util.Util;

public class RendererAdapter<E> extends RecyclerView.Adapter<Renderer<E>> {

    public static <E> Builder<E> from(List<E> data) {
        return from(new ListDataProvider<>(data));
    }

    public static <E> Builder<E> from(DataProvider<E> provider) {
        return new Builder<>(provider);
    }

    public static <E> RendererAdapter<E> from(DataProvider<E> provider,
                                              Class<? extends Renderer<E>> rendererClass) {
        final int viewType = Util.getViewType(rendererClass);
        return from(provider)
                .factory(SimpleRendererFactory.from(rendererClass))
                .matcher(new SingleViewTypeMatcher<E>(viewType)).build();
    }

    private final RendererFactory<E> factory;
    private final ViewTypeMatcher<E> matcher;
    private final DataProvider<E> provider;

    public RendererAdapter(RendererFactory<E> factory, ViewTypeMatcher<E> matcher,
                           DataProvider<E> provider) {
        if (factory == null) {
            throw new NullPointerException("RenderFactory may not be null");
        }
        if (matcher == null) {
            throw new NullPointerException("ViewTypeMatcher may not be null");
        }
        if (provider == null) {
            throw new NullPointerException("DataProvider may not be null");
        }
        this.factory = factory;
        this.matcher = matcher;
        this.provider = provider;
    }

    @Override
    public Renderer<E> onCreateViewHolder(ViewGroup group, int type) {
        return factory.createRenderer(group, type);
    }

    @Override
    public void onBindViewHolder(Renderer<E> viewHolder, int position) {
        final E item = provider.get(position);
        viewHolder.bindView(item);
    }

    @Override
    public int getItemViewType(int position) {
        final E item = provider.get(position);
        return matcher.match(item);
    }

    @Override
    public int getItemCount() {
        return provider.getCount();
    }

    public static final class Builder<E> {

        private final DataProvider<E> provider;
        private RendererFactory<E> factory;
        private ViewTypeMatcher<E> matcher;

        public Builder(DataProvider<E> provider) {
            this.provider = provider;
        }

        public Builder<E> renderer(Class<? extends Renderer<E>> renderClass) {
            return factory(SimpleRendererFactory.from(renderClass));
        }

        public Builder<E> factory(RendererFactory<E> factory) {
            this.factory = factory;
            return this;
        }

        public Builder<E> matcher(ViewTypeMatcher<E> matcher) {
            this.matcher = matcher;
            return this;
        }

        public RendererAdapter<E> build() {
            if (factory == null) {
                throw new NullPointerException("No RendererFactory set");
            }

            if (matcher == null) {
                throw new NullPointerException("No ViewTypeMatcher set");
            }

            return new RendererAdapter<>(factory, matcher, provider);
        }

        public void into(RecyclerView view) {
            view.setAdapter(build());
        }

    }

}
