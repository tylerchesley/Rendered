package io.tylerchesley.rendered;

public interface DataProvider<E> {

    int getCount();

    E get(int position);

}
