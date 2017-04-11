package com.shangeeth.contactsclone.adapters;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.shangeeth.contactsclone.R;

/**
 * Created by user on 11/04/17.
 */

public class CustomCursorAdapter extends CursorAdapter {


    public CustomCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        return null;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
//        TextView textViewTitle = (TextView) view.findViewById(R.id.articleTitle);
//        String title = cursor.getString( cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME) );
//        textViewTitle.setText(title);

    }
}
