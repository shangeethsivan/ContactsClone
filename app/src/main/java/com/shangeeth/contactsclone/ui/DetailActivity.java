package com.shangeeth.contactsclone.ui;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shangeeth.contactsclone.R;
import com.shangeeth.contactsclone.util.RoundedTransformation;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private TextView mDisplayNameTV;

    private ImageView mProfilePicIV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        init();

        String lContactId = getIntent().getStringExtra("_ID");

        Cursor lCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=? " , new String[]{lContactId},
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");

        if (lCursor.moveToFirst()) {

            mDisplayNameTV.setText(lCursor.getString(lCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
            Picasso.with(this)
                    .load(lCursor.getString(lCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI)))
                    .resize(100, 100)
                    .centerCrop()
                    .placeholder(R.drawable.contact_placeholder)
                    .transform(new RoundedTransformation(100, 1))
                    .into(mProfilePicIV);
            createMobileNumerLayout(lCursor.getString(lCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));

        }

        while(lCursor.moveToNext()){
            createMobileNumerLayout(lCursor.getString(lCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
        }

    }


    public void createMobileNumerLayout(final String pMobileNumber){

        LinearLayout lLinearLayout = new LinearLayout(this);
        LinearLayout.LayoutParams layoutParams= new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0,30,0,0);
        lLinearLayout.setLayoutParams(layoutParams);

        LinearLayout.LayoutParams lLayoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,1.0f);


        final TextView lMobileNoTV = new TextView(this);
        lMobileNoTV.setText(pMobileNumber);
        lMobileNoTV.setLayoutParams(lLayoutParams);
        lMobileNoTV.setGravity(Gravity.CENTER);
        lMobileNoTV.setTextSize(22.0f);
        lMobileNoTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_DIAL).setData(Uri.parse("tel:"+lMobileNoTV.getText())));
            }
        });

        final ImageView lMessageIV = new ImageView(this);
        lLayoutParams = new LinearLayout.LayoutParams(0, 60,0.2f);
        lMessageIV.setLayoutParams(lLayoutParams);
        lMessageIV.setImageDrawable(getResources().getDrawable(R.drawable.message_icon));
        lMessageIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_SENDTO,Uri.parse("smsto:"+pMobileNumber)));
            }
        });

        lLinearLayout.addView(lMobileNoTV);
        lLinearLayout.addView(lMessageIV);

        ((LinearLayout)findViewById(R.id.container_linear_layout)).addView(lLinearLayout);

    }



    public void init() {

        mDisplayNameTV = (TextView) findViewById(R.id.contact_name);

        mProfilePicIV = (ImageView) findViewById(R.id.profile_pic);

    }

}
