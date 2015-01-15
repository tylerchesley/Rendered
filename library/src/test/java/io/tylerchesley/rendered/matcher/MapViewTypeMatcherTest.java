package io.tylerchesley.rendered.matcher;

import org.junit.Test;

import io.tylerchesley.rendered.matcher.ClassPropertyGetter;
import io.tylerchesley.rendered.matcher.MapViewTypeMatcher;
import io.tylerchesley.rendered.matcher.ViewTypeMatcher;

import static org.junit.Assert.assertEquals;

public class MapViewTypeMatcherTest {

    public static enum ViewType {
        OBJECT1,
        OBJECT2,
        OBJECT3,
        CHILD1
    }

    @Test(expected = NullPointerException.class)
    public void testNullPropertyGetter() {
        new MapViewTypeMatcher<Base, Class>(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void testNullMap() {
        new MapViewTypeMatcher<>(new ClassPropertyGetter<Base>(), null);
    }

    @Test
    public void testMatchClass() {
        final ViewTypeMatcher<Base> matcher = MapViewTypeMatcher
                .from(new ClassPropertyGetter<Base>())
                .add(ViewType.OBJECT1.ordinal(), Object1.class)
                .add(ViewType.OBJECT2.ordinal(), Object2.class)
                .add(ViewType.OBJECT3.ordinal(), Object3.class)
                .add(ViewType.CHILD1.ordinal(), Child1.class)
                .build();

        assertEquals(ViewType.OBJECT1.ordinal(), matcher.match(new Object1()));
        assertEquals(ViewType.OBJECT2.ordinal(), matcher.match(new Object2()));
        assertEquals(ViewType.OBJECT3.ordinal(), matcher.match(new Object3()));
        assertEquals(ViewType.CHILD1.ordinal(), matcher.match(new Child1()));
        assertEquals(ViewTypeMatcher.NO_MATCH, matcher.match(new NoMatch()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddingMappedValueMoreThanOnce() {
        MapViewTypeMatcher
                .from(new ClassPropertyGetter<Base>())
                .add(ViewType.OBJECT1.ordinal(), Object1.class)
                .add(ViewType.OBJECT1.ordinal(), Object1.class)
                .build();
    }

    public static class Base {}
    public static class Object1 extends Base {}
    public static class Object2 extends Base {}
    public static class Object3 extends Base {}
    public static class Child1 extends Object1 {}
    public static class NoMatch extends Base {}

}
