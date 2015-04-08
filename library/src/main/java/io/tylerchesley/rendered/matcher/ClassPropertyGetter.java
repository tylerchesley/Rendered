package io.tylerchesley.rendered.matcher;

public class ClassPropertyGetter<E> implements PropertyGetter<E, Class<? extends E>> {

    @Override
    public Class<E> get(E item) {
        return (Class<E>) item.getClass();
    }

}
