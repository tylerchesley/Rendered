package io.tylerchesley.rendered;

import android.database.Cursor;

public class CursorDataProvider implements DataProvider<Cursor> {

    private final Cursor cursor;

    public CursorDataProvider(Cursor cursor) {
        if (cursor == null) {
            throw new NullPointerException("Cursor may not be null");
        }
        this.cursor = cursor;
    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public Cursor get(int position) {
        cursor.moveToPosition(position);
        return cursor;
    }

}
