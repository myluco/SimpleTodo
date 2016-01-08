package com.myluco.simpletodo;

import android.app.DatePickerDialog;
import android.app.Dialog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;


public class EditItemFragment extends DialogFragment  {


    private String itemText;
    private String itemDate;
    private EditText etDescription;
    private EditText etDate;
    private DatePickerDialog datePickerDialog;

    public interface EditItemDialogListener {
            void onFinishEditItemDialog(String newItemText,String newItemDate);
    }

    public EditItemFragment() {

        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param itemText
     * @param itemDate
     * @return A new instance of fragment EditItemFragment.
     */

    public static EditItemFragment newInstance(String itemText, String itemDate) {
        EditItemFragment fragment = new EditItemFragment();
        Bundle args = new Bundle();
        args.putString("itemText", itemText);
        args.putString("itemDate", itemDate);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            itemText = getArguments().getString("itemText");
            itemDate = getArguments().getString("itemDate");
        }

        etDescription = (EditText) view.findViewById(R.id.etItemEdit);
        etDate = (EditText)view.findViewById(R.id.etDateItem);

        etDescription.setText(itemText);
        etDescription.requestFocus();
        etDescription.setSelection(itemText.length());

        etDate.setText(itemDate);
        etDate.setInputType(InputType.TYPE_NULL);
        setupDateListener();
    }
    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_edit_item, container);
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
        datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                etDate.setText(Utilities.dateStringFromLong(newDate.getTime().getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

    public void onSave(View view) {
        EditItemDialogListener listener = (EditItemDialogListener) getActivity();
        listener.onFinishEditItemDialog(etDescription.getText().toString(),etDate.getText().toString());
        dismiss();

    }
}
