package io.tylerchesley.rendered;

import java.util.List;

public class ListDataProvider<E> implements DataProvider<E> {

    private final List<E> items;

    public ListDataProvider(List<E> items) {
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
