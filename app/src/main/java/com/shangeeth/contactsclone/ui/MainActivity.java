package com.shangeeth.contactsclone.ui;

import android.Manifest;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.shangeeth.contactsclone.R;
import com.shangeeth.contactsclone.adapters.CustomCursorAdapter;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int REQUEST_CODE = 100;
    private ListView mListView;
    private CustomCursorAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.contacts_list);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M   &&  ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            ensurePermissions();

        } else
            loadContactsToList();

        setOnClickListeners();

    }

    public void setOnClickListeners(){

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Cursor lCursor = (Cursor) mListView.getItemAtPosition(position);

                String lContactId = lCursor.getString(lCursor.getColumnIndex(ContactsContract.Contacts._ID));

                startActivity(new Intent(MainActivity.this,DetailActivity.class).putExtra("_ID",lContactId));
            }
        });

    }
    public void loadContactsToList() {

        mAdapter = new CustomCursorAdapter(this,null,0);

        mListView.setAdapter(mAdapter);

        getLoaderManager().initLoader(1,null,this);

    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, ContactsContract.Contacts.CONTENT_URI,
                null, ContactsContract.Contacts.HAS_PHONE_NUMBER+"=?",new String[]{"1"}, ContactsContract.Contacts.DISPLAY_NAME + " ASC");
    }

    @Override
    public void onLoadFinished(Loader loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader loader) {
        mAdapter.swapCursor(null);
    }


    private void ensurePermissions() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_CONTACTS)) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    REQUEST_CODE);

        } else {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    REQUEST_CODE);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        boolean LAllPermissionsGranted = false;

        if (requestCode == REQUEST_CODE) {
            for (int lResult : grantResults) {
                LAllPermissionsGranted = lResult == PackageManager.PERMISSION_GRANTED;
            }
        }

        if (LAllPermissionsGranted) {
            loadContactsToList();
        } else {
            finish();
        }
    }
}
