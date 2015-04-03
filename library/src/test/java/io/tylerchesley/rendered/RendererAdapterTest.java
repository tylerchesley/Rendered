package io.tylerchesley.rendered;

import android.view.View;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

import io.tylerchesley.rendered.data.DataProvider;
import io.tylerchesley.rendered.data.ListDataProvider;
import io.tylerchesley.rendered.factory.RendererFactory;
import io.tylerchesley.rendered.factory.SimpleRendererFactory;
import io.tylerchesley.rendered.matcher.SingleViewTypeMatcher;
import io.tylerchesley.rendered.matcher.ViewTypeMatcher;
import io.tylerchesley.rendered.renderer.Renderer;
import io.tylerchesley.rendered.renderer.ViewType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class RendererAdapterTest {

    private static final int TEST_VIEW_TYPE = 666;

    private DataProvider<Object> provider;
    private RendererFactory<Object> factory;
    private ViewTypeMatcher<Object> matcher;

    @Before
    public void setup() {
        provider = ListDataProvider.from(new ArrayList<>());
        factory = SimpleRendererFactory.from(TestRenderer.class);
        matcher = SingleViewTypeMatcher.from(TestRenderer.class);
    }

    @Test(expected = NullPointerException.class)
    public void testNullFactory() {
        new RendererAdapter<>(null,
                matcher,
                provider);
    }

    @Test(expected = NullPointerException.class)
    public void testNullMatcher() {
        new RendererAdapter<>(
                factory,
                null,
                provider);
    }

    @Test(expected = NullPointerException.class)
    public void testNullDataProvider() {
        new RendererAdapter<>(
                factory,
                matcher,
                null);
    }

    @Test
    public void testBuilder() {
        RendererAdapter<Object> adapter = RendererAdapter
                .from(provider)
                .factory(factory)
                .matcher(matcher)
                .build();

        assertEquals(factory, adapter.getFactory());
        assertEquals(matcher, adapter.getMatcher());
    }

    @Test
    public void testSingleRendererClass() {
        final RendererAdapter<Object> adapter = RendererAdapter
                .from(provider)
                .renderer(TestRenderer.class)
                .build();

        assertTrue(adapter.getFactory() instanceof SimpleRendererFactory);
        assertTrue(adapter.getMatcher() instanceof SingleViewTypeMatcher);
    }

    @ViewType(TEST_VIEW_TYPE)
    public static final class TestRenderer extends Renderer<Object> {

        public TestRenderer(View itemView) {
            super(itemView);
        }

        @Override
        public void bindView(Object data) {

        }

    }

}
