package io.tylerchesley.rendered.data;

public interface DataProvider<E> {

    int getCount();

    E get(int position);

}
