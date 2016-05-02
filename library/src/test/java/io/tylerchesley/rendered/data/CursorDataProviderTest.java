package io.tylerchesley.rendered.data;

import android.database.Cursor;
import android.database.MatrixCursor;

import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CursorDataProviderTest {

    private static Cursor mockCursor() {
        final Cursor cursor = mock(Cursor.class);
        when(cursor.getCount()).thenReturn(3);
        when(cursor.moveToPosition(0)).thenReturn(true);
        when(cursor.moveToPosition(1)).thenReturn(true);
        when(cursor.moveToPosition(2)).thenReturn(true);
        when(cursor.getString(0)).thenAnswer(new Answer<String>() {
            private int called = -1;
            @Override
            public String answer(InvocationOnMock invocationOnMock) throws Throwable {
                called++;
                switch (called) {
                    case 0:
                        return "a";
                    case 1:
                        return "b";
                    case 2:
                        return "c";
                    default:
                        return "d";
                }
            }

        });
        return cursor;
    }

    @Test(expected = NullPointerException.class)
    public void testNullCursor() {
        new CursorDataProvider(null);
    }

    @Test
    public void testGetCount() {
        final Cursor cursor = mockCursor();
        final CursorDataProvider provider = new CursorDataProvider(cursor);
        assertEquals(3, provider.getCount());
    }

    @Test
    public void testGet() {
        final Cursor cursor = mockCursor();
        final CursorDataProvider provider = new CursorDataProvider(cursor);
        assertEquals("a", provider.get(0).getString(0));
        assertEquals("b", provider.get(1).getString(0));
        assertEquals("c", provider.get(2).getString(0));
    }

}
