package io.tylerchesley.rendered.util;

import io.tylerchesley.rendered.matcher.ViewTypeMatcher;
import io.tylerchesley.rendered.renderer.Layout;
import io.tylerchesley.rendered.renderer.Renderer;
import io.tylerchesley.rendered.renderer.ViewType;

public class Util {

    public static int getViewType(Class<?> rendererClass) {
        return getViewType(rendererClass, ViewTypeMatcher.NO_MATCH);
    }

    public static int getViewType(Class<?> rendererClass, int defaultValue) {
        final ViewType viewType = rendererClass.getAnnotation(ViewType.class);
        if (viewType != null) {
            return viewType.value();
        }

        return defaultValue;
    }

    public static int getLayoutValue(Class<?> rendererClass, int defaultValue) {
        final Layout layout = rendererClass.getAnnotation(Layout.class);
        if (layout != null) {
            return layout.value();
        }
        return defaultValue;
    }

}
