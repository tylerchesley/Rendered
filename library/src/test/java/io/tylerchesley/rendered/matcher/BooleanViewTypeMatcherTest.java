package io.tylerchesley.rendered.matcher;

import org.junit.Test;

import io.tylerchesley.rendered.matcher.BooleanViewTypeMatcher;
import io.tylerchesley.rendered.matcher.PropertyGetter;
import io.tylerchesley.rendered.matcher.ViewTypeMatcher;

import static org.junit.Assert.assertEquals;

public class BooleanViewTypeMatcherTest {

    private static final int TRUE_VIEW_TYPE = 1;
    private static final int FALSE_VIEW_TYPE = 0;

    private static final PropertyGetter<Boolean, Mother> getter =
            new PropertyGetter<Boolean, Mother>() {
                @Override
                public Boolean get(Mother item) {
                    return item.isFat;
                }
            };

    private static BooleanViewTypeMatcher<Mother> createMatcher() {
        return BooleanViewTypeMatcher
                .from(new PropertyGetter<Boolean, Mother>() {
                    @Override
                    public Boolean get(Mother item) {
                        return item.isFat;
                    }
                })
                .falseViewType(0)
                .trueViewType(1)
                .build();
    }

    @Test(expected = NullPointerException.class)
    public void testNullPropertyGetter() {
        new BooleanViewTypeMatcher(null, ViewTypeMatcher.NO_MATCH, ViewTypeMatcher.NO_MATCH);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTrueAndFalseAreTheSame() {
        new BooleanViewTypeMatcher<>(getter, ViewTypeMatcher.NO_MATCH, ViewTypeMatcher.NO_MATCH);
    }

    @Test
    public void testTrueMatch() {
        final BooleanViewTypeMatcher<Mother> matcher = createMatcher();
        assertEquals(TRUE_VIEW_TYPE, matcher.match(new Mother(true)));
    }

    @Test
    public void testFalseMatch() {
        final BooleanViewTypeMatcher<Mother> matcher = createMatcher();
        assertEquals(FALSE_VIEW_TYPE, matcher.match(new Mother(false)));
    }

    public final class Mother {

        public final boolean isFat;

        public Mother(boolean isFat) {
            this.isFat = isFat;
        }

    }

}
