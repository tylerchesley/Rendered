package io.tylerchesley.rendered.matcher;

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

public class MapViewTypeMatcher<E, T> implements ViewTypeMatcher<E> {

    public static <E, T> Builder<E, T> from(PropertyGetter<T, E> getter) {
        return new Builder<>(getter);
    }

    private final PropertyGetter<T, E> getter;
    private final Map<T, Integer> map;

    public MapViewTypeMatcher(@NonNull PropertyGetter<T, E> getter, @NonNull Map<T, Integer> map) {
        if (getter == null) {
            throw new NullPointerException("PropertyGetter may not be null");
        }
        if (map == null) {
            throw new NullPointerException("Map may not be null");
        }
        this.getter = getter;
        this.map = map;
    }

    @Override
    public int match(E item) {
        final T value = getter.get(item);
        if (value != null && map.containsKey(value)) {
            return map.get(value);
        }
        return NO_MATCH;
    }

    public static final class Builder<E, T> {

        private final PropertyGetter<T, E> getter;
        private final Map<T, Integer> map;

        public Builder(PropertyGetter<T, E> getter) {
            this.getter = getter;
            this.map = new HashMap<>();
        }

        public Builder<E, T> add(int viewType, T value) {
            if (map.containsKey(value)) {
                throw new IllegalArgumentException("Value has already been mapped for view type "
                        + viewType);
            }
            map.put(value, viewType);
            return this;
        }

        public MapViewTypeMatcher<E, T> build() {
            return new MapViewTypeMatcher<>(getter, map);
        }

    }

}
