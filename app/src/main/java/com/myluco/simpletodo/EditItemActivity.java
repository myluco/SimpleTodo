package com.myluco.simpletodo;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class EditItemActivity extends AppCompatActivity {
    private String itemText;
    private EditText itemViewText;

    // REQUEST_CODE can be any value we like, used to determine the result type later
    private final int REQUEST_CODE = 20;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        itemText = getIntent().getStringExtra("itemText");
        itemViewText = (EditText) findViewById(R.id.etItemEdit);
        itemViewText.setText(itemText);
        itemViewText.requestFocus();
        itemViewText.setSelection(itemText.length());
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void onSave(View view) {
        Intent data = new Intent();
        data.putExtra("itemNewText", itemViewText.getText().toString());
        //Log.v("OnSave-EditItemActivity", itemViewText.getText().toString());
        setResult(RESULT_OK, data);
        //Log.v("OnSave-EditItemActivity", "After setResult");
        //Toast.makeText(this, "Returning!", Toast.LENGTH_SHORT).show();
        finish();
    }


}
