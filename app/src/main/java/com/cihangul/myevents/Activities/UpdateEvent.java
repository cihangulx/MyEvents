package com.cihangul.myevents.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.cihangul.myevents.DB.Data.EventViewModel;
import com.cihangul.myevents.DB.Models.Event;
import com.cihangul.myevents.R;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class UpdateEvent extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {


    public static final String EXTRA_ID = "eventId";
    public static final String EXTRA_TITLE = "eventTitle";
    public static final String EXTRA_SUBTITLE = "eventSubTitle";
    public static final String EXTRA_DATE = "eventDate";

    private Calendar calendar = Calendar.getInstance(); // Today's date
    private Date selectedDate = new Date(); // User selected date
    private TextInputEditText title, subTitle; //Event Title and Sub Title
    private TextView date_text; //View of selected date
    Event event; // Current Event Object instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        //Back Button
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        title = findViewById(R.id.event_title);
        subTitle = findViewById(R.id.event_sub_title);
        date_text = findViewById(R.id.date);

        // Now editing Event
        this.event = new Gson().fromJson(getIntent().getStringExtra("event"), Event.class);
        title.setText(event.getTitle());
        subTitle.setText(event.getSubTitle());
        selectedDate = event.getDate();
        date_text.setText(DateFormat.format("dd-MM-yyyy", selectedDate));

    }

    /**
     *
     * if v is a Set Date button show Date Picker Dialog
     *
     * if v is a Save Button call updateEvent method
     *
     *
     * @param v
     */
    public void buttonClick(View v) {
        if (v.getId() == R.id.set_date) {
            DatePickerDialog datePicker = new DatePickerDialog(UpdateEvent.this,
                    this,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));
            datePicker.setTitle(R.string.set_date);
            datePicker.setButton(DatePickerDialog.BUTTON_POSITIVE, getString(R.string.set), datePicker);
            datePicker.setButton(DatePickerDialog.BUTTON_NEGATIVE, getString(R.string.cancel), datePicker);
            datePicker.show();
        } else if (v.getId() == R.id.add) {
            updateEvent(v);
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        selectedDate = new GregorianCalendar(year, month, dayOfMonth).getTime();
        date_text.setText(DateFormat.format("dd-MM-yyyy", selectedDate));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateEvent(View v) {

        // Title and subtitle  will not be empty if did Show warning
        if (title.getText().toString().trim().isEmpty() || subTitle.getText().toString().trim().isEmpty()) {
            Snackbar.make(v, "Please insert all fields!", Snackbar.LENGTH_LONG).show();
            return;
        }

        //Send back written data wia result intent
        Intent intent = new Intent();
        intent.putExtra(EXTRA_ID, event.getEventId());
        intent.putExtra(EXTRA_SUBTITLE, subTitle.getText().toString().trim());
        intent.putExtra(EXTRA_TITLE, title.getText().toString().trim());
        intent.putExtra(EXTRA_DATE, selectedDate.getTime());
        setResult(RESULT_OK, intent);
        finish();
    }

}
