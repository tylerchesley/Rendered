package io.tylerchesley.rendered.data;

import android.database.Cursor;
import android.support.annotation.NonNull;

public class CursorDataProvider implements DataProvider<Cursor> {

    private final Cursor cursor;

    public CursorDataProvider(@NonNull Cursor cursor) {
        if (cursor == null) {
            throw new NullPointerException("Cursor may not be null");
        }
        this.cursor = cursor;
        this.cursor.moveToFirst();
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
