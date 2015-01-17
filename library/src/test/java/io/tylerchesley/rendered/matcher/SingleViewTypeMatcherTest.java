package io.tylerchesley.rendered.matcher;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SingleViewTypeMatcherTest {

    private final static int TEST_VIEW_TYPE = 666;

    @Test
    public void testMatch() {
        final SingleViewTypeMatcher<Object> matcher = SingleViewTypeMatcher.from(TEST_VIEW_TYPE);
        assertEquals(TEST_VIEW_TYPE, matcher.match(new Object()));
    }

}
