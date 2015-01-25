package io.tylerchesley.rendered.data;

import android.database.MatrixCursor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class CursorDataProviderTest {

    @Test(expected = NullPointerException.class)
    public void testNullCursor() {
        new CursorDataProvider(null);
    }

    @Test
    public void testGetCount() {
        final MatrixCursor cursor = new MatrixCursor(new String[] {"a"});
        cursor.newRow().add("a");
        cursor.newRow().add("b");
        cursor.newRow().add("c");
        final CursorDataProvider provider = new CursorDataProvider(cursor);
        assertEquals(3, provider.getCount());
    }

    @Test
    public void testGet() {
        final MatrixCursor cursor = new MatrixCursor(new String[] {"a"});
        cursor.newRow().add("a");
        cursor.newRow().add("b");
        cursor.newRow().add("c");
        final CursorDataProvider provider = new CursorDataProvider(cursor);
        assertEquals("a", provider.get(0).getString(0));
        assertNotEquals("a", provider.get(2).getString(0));
    }

}
