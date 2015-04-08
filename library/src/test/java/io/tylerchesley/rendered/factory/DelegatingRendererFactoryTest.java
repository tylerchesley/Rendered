package io.tylerchesley.rendered.factory;


import org.junit.Test;

public class DelegatingRendererFactoryTest {

    @Test(expected = NullPointerException.class)
    public void testNullRendererConfigs() {
        new DelegatingRendererFactory<>(null);
    }

    @Test
    public void testViewTypeAnnotation() {

    }

}
