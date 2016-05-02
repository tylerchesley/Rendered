package io.tylerchesley.rendered.data;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ListDataProviderTest {

    @Test(expected = NullPointerException.class)
    public void testNullList() {
        new ListDataProvider(null);
    }

    @Test
    public void testGetCount() {
        final ListDataProvider<Object> provider = ListDataProvider.from(
                new Object(), new Object(), new Object());
        assertEquals(3, provider.getCount());
    }

    @Test
    public void testGet() {
        final Object o1 = new Object();
        final ListDataProvider<Object> provider = ListDataProvider.from(o1, new Object(), new Object());
        assertEquals(o1, provider.get(0));
        assertNotEquals(o1, provider.get(1));
    }

}



