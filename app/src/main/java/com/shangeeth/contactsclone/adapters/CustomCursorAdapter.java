package com.shangeeth.contactsclone.adapters;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shangeeth.contactsclone.R;
import com.shangeeth.contactsclone.util.RoundedTransformation;
import com.squareup.picasso.Picasso;

/**
 * Created by user on 11/04/17.
 */

public class CustomCursorAdapter extends CursorAdapter {

    LayoutInflater mCursorInflator;

    public CustomCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        mCursorInflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        return mCursorInflator.inflate(R.layout.contact_list_item,parent,false);

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView lContactNameTV = (TextView) view.findViewById(R.id.contact_name);
        ImageView lContactImageIV = (ImageView) view.findViewById(R.id.contact_image);

        String title = cursor.getString( cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME) );
        lContactNameTV.setText(title);

        Picasso.with(view.getContext())
                .load(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_URI)))
                .resize(100, 100)
                .centerCrop()
                .placeholder(R.drawable.contact_placeholder)
                .transform(new RoundedTransformation(100,1))
                .into(lContactImageIV);

    }
}
