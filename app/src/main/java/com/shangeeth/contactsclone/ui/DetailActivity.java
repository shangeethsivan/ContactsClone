package com.shangeeth.contactsclone.ui;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shangeeth.contactsclone.R;
import com.shangeeth.contactsclone.util.RoundedTransformation;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private TextView mDisplayNameTV;
    private TextView mNumberTV;
    private ImageView mCallButtonIV;
    private ImageView mProfilePicIV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        init();

        String lContactId = getIntent().getStringExtra("_ID");

        Cursor lCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", new String[]{lContactId},
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");

        if (lCursor.moveToFirst()) {

            mDisplayNameTV.setText(lCursor.getString(lCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
            mNumberTV.setText(lCursor.getString(lCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
            try {

                Uri imageUri = Uri.parse(lCursor.getString(lCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI)));

                if (imageUri != null){
                    Picasso.with(this)
                            .load(imageUri)
                            .resize(100, 100)
                            .centerCrop()
                            .transform(new RoundedTransformation(100,1))
                            .into(mProfilePicIV);

                }

            }
            catch (Exception e ){
                e.printStackTrace();
            }
        }

        mCallButtonIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mNumberTV.getText())));
                }
                catch(android.content.ActivityNotFoundException e){
                    Toast.makeText(DetailActivity.this, "No app to make a call", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void init() {

        mDisplayNameTV = (TextView) findViewById(R.id.contact_name);
        mNumberTV = (TextView) findViewById(R.id.mobile_number);
        mCallButtonIV = (ImageView) findViewById(R.id.btn_call);
        mProfilePicIV = (ImageView) findViewById(R.id.profile_pic);

    }

}
