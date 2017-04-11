package com.shangeeth.contactsclone;

import android.Manifest;
import android.content.CursorLoader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int REQUEST_CODE = 100;
    ListView mListView;

    private RecyclerView mRecyclerView;
    private SimpleCursorAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // mListView = (ListView) findViewById(R.id.contacts_list);
        mRecyclerView = (RecyclerView) findViewById(R.id.contacts_list);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M   &&  ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            ensurePermissions();

        } else {
            loadContactsToList();
        }

    }

    public void loadContactsToList() {


        mAdapter = new SimpleCursorAdapter(this, R.layout.contact_list_item, null,
                new String[]{ContactsContract.Contacts.DISPLAY_NAME},
                new int[]{R.id.contact_name}, 0);

        mRecyclerView.setAdapter(mAdapter);

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

        // Should we show an explanation?
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_CONTACTS)) {

            // Show an explanation to the user *asynchronously* -- don't block
            // this thread waiting for the user's response! After the user
            // sees the explanation, try again to request the permission.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    REQUEST_CODE);

        } else {

            // No explanation needed, we can request the permission.

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
