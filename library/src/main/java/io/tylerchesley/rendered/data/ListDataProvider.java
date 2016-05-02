package io.tylerchesley.rendered.data;

import android.support.annotation.NonNull;

import java.util.Arrays;
import java.util.List;

public class ListDataProvider<E> implements DataProvider<E> {

    public static <E> ListDataProvider<E> from(List<E> items) {
        return new ListDataProvider<>(items);
    }

    public static <E> ListDataProvider<E> from(E... items) {
        return new ListDataProvider<>(Arrays.asList(items));
    }

    private final List<E> items;

    public ListDataProvider(@NonNull List<E> items) {
        if (items == null) {
            throw new NullPointerException("List may not be null");
        }

        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public E get(int position) {
        return items.get(position);
    }

}
