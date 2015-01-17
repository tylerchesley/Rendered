package io.tylerchesley.rendered.factory;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class DelegatingRendererFactoryTest {

    @Test(expected = NullPointerException.class)
    public void testNullRendererConfigs() {
        new DelegatingRendererFactory<>(null);
    }

    @Test
    public void testViewTypeAnnotation() {

    }

}
