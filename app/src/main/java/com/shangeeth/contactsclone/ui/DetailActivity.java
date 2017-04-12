package com.shangeeth.contactsclone.ui;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        init();

        Toolbar lToolbar = (Toolbar)findViewById(R.id.toolbar);
        CollapsingToolbarLayout lCollapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);

        setSupportActionBar(lToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String lContactId = getIntent().getStringExtra("_ID");

        Cursor lPhoneCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=? " , new String[]{lContactId},
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");

        Cursor lEmailCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                ContactsContract.CommonDataKinds.Email.CONTACT_ID + "=? " , new String[]{lContactId},
                ContactsContract.CommonDataKinds.Email.DISPLAY_NAME + " ASC");

        if (lPhoneCursor.moveToFirst()) {
            lCollapsingToolbarLayout.setTitle(lPhoneCursor.getString(lPhoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));


            Picasso.with(this)
                    .load(lPhoneCursor.getString(lPhoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI)))
                    .placeholder(R.drawable.contact_bg)
                    .into((ImageView)findViewById(R.id.image_toolbar));

            createMobileLayout(lPhoneCursor.getString(lPhoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));

        }

        while(lPhoneCursor.moveToNext()){
            createMobileLayout(lPhoneCursor.getString(lPhoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
        }

        if(lEmailCursor.moveToFirst()){
            createEmailLayout(lEmailCursor.getString(lEmailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS)));
            while(lEmailCursor.moveToNext()){
                createEmailLayout(lEmailCursor.getString(lEmailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS)));
            }
        }

    }


    public void createMobileLayout(final String pMobileNumber){

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
        lMobileNoTV.setTextColor(getResources().getColor(R.color.black));
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
    public void createEmailLayout(final String pEmailId){

        LinearLayout lLinearLayout = new LinearLayout(this);
        LinearLayout.LayoutParams layoutParams= new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0,30,0,0);
        lLinearLayout.setLayoutParams(layoutParams);

        LinearLayout.LayoutParams lLayoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,1.0f);


        final TextView lEmailIdTV = new TextView(this);
        lEmailIdTV.setText(pEmailId);
        lEmailIdTV.setLayoutParams(lLayoutParams);
        lEmailIdTV.setTextColor(getResources().getColor(R.color.black));
        lEmailIdTV.setGravity(Gravity.CENTER);
        lEmailIdTV.setTextSize(22.0f);

        lEmailIdTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_SENDTO,Uri.parse("mailto:"+lEmailIdTV.getText())));
            }
        });

        lLinearLayout.addView(lEmailIdTV);

        ((LinearLayout)findViewById(R.id.container_linear_layout)).addView(lLinearLayout);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                super.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void init() {

        mDisplayNameTV = (TextView) findViewById(R.id.contact_name);

    }

}
