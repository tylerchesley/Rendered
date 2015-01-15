package io.tylerchesley.rendered.matcher;

public class ClassPropertyGetter<E> implements PropertyGetter<Class<? extends E>, E> {

    @Override
    public Class<E> get(E item) {
        return (Class<E>) item.getClass();
    }

}
