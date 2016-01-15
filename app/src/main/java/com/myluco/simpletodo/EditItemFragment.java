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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class EditItemFragment extends DialogFragment  {


    private TodoItem item;

    private EditText etDescription;
    private EditText etDate;
    private EditText etNotes;
    private RadioGroup rgStatus;
    private RadioGroup rgPriority;
    private Button btSave;
    private Button btCancel;


    private DatePickerDialog datePickerDialog;

    public interface EditItemDialogListener {
            void onFinishEditItemDialog(TodoItem item);
    }

    public EditItemFragment() {

        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param item
     * @return A new instance of fragment EditItemFragment.
     */

    public static EditItemFragment newInstance(TodoItem item) {
        EditItemFragment fragment = new EditItemFragment();
        Bundle args = new Bundle();
        args.putString("itemText", item.description);
        args.putString("itemDate", item.dueDateString);
        args.putSerializable("itemStatus", item.status);
        args.putSerializable("itemPriority", item.priority);
        args.putString("itemNotes", item.notes);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            item = new TodoItem();
            item.description = getArguments().getString("itemText");
            item.dueDateString = getArguments().getString("itemDate");
            item.notes = getArguments().getString("itemNotes");
            item.priority = (TodoItem.Priority)getArguments().getSerializable("itemPriority");
            item.status= (TodoItem.Status)getArguments().getSerializable("itemStatus");
        }
        getViewById(view);
        setListeners();
        populate();


    }

    private void populate() {
        etDescription.setText(item.description);
        etDescription.requestFocus();
        etDescription.setSelection(item.description.length());

        etDate.setText(item.dueDateString);
        etDate.setInputType(InputType.TYPE_NULL);

        etNotes.setText(item.notes);

        setPriority();
        setStatus();


    }
    private void setPriority() {
        switch (item.priority) {
            case HIGH:
                rgPriority.check(R.id.rbHigh);
                break;
            case MEDIUM:
                rgPriority.check(R.id.rbMedium);
                break;
            case LOW:
                rgPriority.check(R.id.rbLow);
                break;

        }

    }

    private void setStatus() {
        switch(item.status){
            case TODO:
                rgStatus.check(R.id.rbTodo);
                break;
            case DONE:
                rgStatus.check(R.id.rbDone);
        }
    }
    private void setListeners() {
        rgPriority.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected

                if (checkedId == R.id.rbLow) {
                    item.setPriority(TodoItem.Priority.LOW);
                } else if (checkedId == R.id.rbMedium) {
                    item.setPriority(TodoItem.Priority.MEDIUM);
                } else {//High Priority
                    item.setPriority(TodoItem.Priority.HIGH);
                }
                Log.v("Fragment-Priority", item.priority.name());
            }
        });
        rgStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if (checkedId == R.id.rbTodo) {
                    item.setStatus(TodoItem.Status.TODO);

                } else {//High Priority
                    item.setStatus(TodoItem.Status.DONE);
                }
                Log.v("Fragment-Status", item.status.name());
            }
        });
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSave(v);
            }
        });
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCancel(v);
            }
        });
        setupDateListener();

    }
    private void getViewById(View view) {
        etDescription = (EditText) view.findViewById(R.id.etItemEdit);
        etDate = (EditText)view.findViewById(R.id.etDateItem);
        etNotes = (EditText)view.findViewById(R.id.etNotes);
        rgPriority=(RadioGroup)view.findViewById(R.id.rgPriority);
        rgStatus = (RadioGroup)view.findViewById(R.id.rgStatus);
        btSave = (Button) view.findViewById(R.id.btSave);
        btCancel = (Button) view.findViewById(R.id.btCancel);
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
        final Calendar newCalendar = new GregorianCalendar();

        datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                newCalendar.set(year, monthOfYear, dayOfMonth);
                newCalendar.set(GregorianCalendar.HOUR_OF_DAY, 23);
                newCalendar.set(GregorianCalendar.MINUTE, 59);
                newCalendar.set(GregorianCalendar.SECOND, 0);
                etDate.setText(Utilities.dateStringFromLong(newCalendar.getTimeInMillis()));
                item.dueDateString = etDate.getText().toString();
            }

        },newCalendar.get(GregorianCalendar.YEAR), newCalendar.get(GregorianCalendar.MONTH), newCalendar.get(GregorianCalendar.DAY_OF_MONTH));

    }

    public void onSave(View view) {
        EditItemDialogListener listener = (EditItemDialogListener) getActivity();
        item.notes = etNotes.getText().toString();
        item.description = etDescription.getText().toString();
        listener.onFinishEditItemDialog(item);
        dismiss();

    }
    public void onCancel(View view) {
        dismiss();
    }

}
