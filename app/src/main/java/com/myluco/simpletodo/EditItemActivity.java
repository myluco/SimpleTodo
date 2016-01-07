package com.myluco.simpletodo;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditItemActivity extends AppCompatActivity {
    private String itemText;
    private String itemDate;
    private EditText etDescription;
    private EditText etDate;
    // REQUEST_CODE can be any value we like, used to determine the result type later
    private final int REQUEST_CODE = 20;
    private DatePickerDialog datePickerDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        itemText = getIntent().getStringExtra("itemText");
        itemDate = getIntent().getStringExtra("itemDate");
        etDescription = (EditText) findViewById(R.id.etItemEdit);
        etDate = (EditText)findViewById(R.id.etDateItem);

        etDescription.setText(itemText);
        etDescription.requestFocus();
        etDescription.setSelection(itemText.length());

        etDate.setText(itemDate);
        etDate.setInputType(InputType.TYPE_NULL);
        setupDateListener();
    }

    private void setupDateListener() {
        etDate.setOnClickListener(new View.OnClickListener() {

                                      @Override
                                      public void onClick(View v) {
                                          datePickerDialog.show();
                                      }
                                  }

        );
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                etDate.setText(Utilities.dateStringFromLong(newDate.getTime().getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

    public void onSave(View view) {
        Intent data = new Intent();
        data.putExtra("itemDescription", etDescription.getText().toString());
        data.putExtra("itemDate",etDate.getText().toString());
        //Log.v("OnSave-EditItemActivity", itemViewText.getText().toString());
        setResult(RESULT_OK, data);
        //Log.v("OnSave-EditItemActivity", "After setResult");
        //Toast.makeText(this, "Returning!", Toast.LENGTH_SHORT).show();
        finish();
    }


}
