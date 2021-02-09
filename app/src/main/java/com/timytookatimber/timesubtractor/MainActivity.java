package com.timytookatimber.timesubtractor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

//import android.app.Dialog;
//import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
//import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CompoundButton;
//import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
//import android.widget.ToggleButton;

//import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MINUTE;

public class MainActivity extends AppCompatActivity {

    static TextView start_timeTextView, end_timeTextView, diff_timeTextView, start_dateTextView, end_dateTextView;
    static EditText add_daysTextView, add_hoursTextView, add_minutesTextView;
    static Switch aSwitch;
    static boolean switch_pos = true;
    Button calculate_button, add_button, reset_button;
    static Calendar start_calendar= Calendar.getInstance();
    static Calendar end_calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start_timeTextView = (TextView) findViewById(R.id.textView3);
        end_timeTextView = (TextView) findViewById(R.id.textView4);
        diff_timeTextView = (TextView) findViewById(R.id.textView6);
        start_dateTextView = (TextView) findViewById(R.id.textView7);
        end_dateTextView = (TextView) findViewById(R.id.textView8);

        add_daysTextView = (EditText) findViewById(R.id.addDaysTextBox);
        add_hoursTextView = (EditText) findViewById(R.id.addHoursTextBox);
        add_minutesTextView = (EditText) findViewById(R.id.addMinutesTextBox);

        start_timeTextView.setText(formatTime(start_calendar));
        end_timeTextView.setText(formatTime(end_calendar));
        start_dateTextView.setText(formatDate(start_calendar));
        end_dateTextView.setText(formatDate(end_calendar));
        aSwitch = (Switch) findViewById(R.id.switch1);
        calculate_button = findViewById(R.id.button3);
        add_button = findViewById(R.id.button);
        reset_button = findViewById(R.id.resetButton);

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    switch_pos = false;
                    aSwitch.setText("End");
                } else {
                    switch_pos = true;
                    aSwitch.setText("Beginning");
                }
            }});

        calculate_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                diff_timeTextView.setText(calcDiffTime());
            }
        });

        add_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                end_calendar.add(Calendar.DAY_OF_YEAR, Integer.valueOf(String.valueOf(add_daysTextView.getText())));
                end_calendar.add(Calendar.HOUR, Integer.valueOf(String.valueOf(add_hoursTextView.getText())));
                end_calendar.add(Calendar.MINUTE, Integer.valueOf(String.valueOf(add_minutesTextView.getText())));

                //update days
                diff_timeTextView.setText(calcDiffTime());
                String s = String.valueOf(end_calendar.get(Calendar.DAY_OF_MONTH)) + DatePickerFragment.months[end_calendar.get(Calendar.MONTH)] + String.valueOf(end_calendar.get(Calendar.YEAR));
                end_dateTextView.setText(s);

                //update time
                s = String.valueOf(end_calendar.get(HOUR_OF_DAY)) + ":";
                if(end_calendar.get(MINUTE) < 10)
                    s += "0";
                s += String.valueOf(end_calendar.get(MINUTE));
                end_timeTextView.setText(s);
            }
        });

        reset_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                start_calendar = Calendar.getInstance();
                end_calendar = Calendar.getInstance();

                //update days
                String s = String.valueOf(start_calendar.get(Calendar.DAY_OF_MONTH)) + DatePickerFragment.months[start_calendar.get(Calendar.MONTH)] + String.valueOf(start_calendar.get(Calendar.YEAR));
                start_dateTextView.setText(s);
                s = String.valueOf(end_calendar.get(Calendar.DAY_OF_MONTH)) + DatePickerFragment.months[end_calendar.get(Calendar.MONTH)] + String.valueOf(end_calendar.get(Calendar.YEAR));
                end_dateTextView.setText(s);

                //update time
                s = String.valueOf(start_calendar.get(HOUR_OF_DAY)) + ":";
                if(start_calendar.get(MINUTE) < 10)
                    s += "0";
                s += String.valueOf(start_calendar.get(MINUTE));
                start_timeTextView.setText(s);
                s = String.valueOf(end_calendar.get(HOUR_OF_DAY)) + ":";
                if(end_calendar.get(MINUTE) < 10)
                    s += "0";
                s += String.valueOf(end_calendar.get(MINUTE));
                end_timeTextView.setText(s);

                add_daysTextView.setText("0");
                add_hoursTextView.setText("0");
                add_minutesTextView.setText("0");

                diff_timeTextView.setText(calcDiffTime());
            }
        });
    }

    public void onClickStartTime(View v){
        aSwitch.setChecked(false);
        showTimePickerDialog(v);
    }

    public void onClickEndTime(View v){
        aSwitch.setChecked(true);
        showTimePickerDialog(v);
    }
    public void onClickStartDate(View v){
        aSwitch.setChecked(false);
        showDatePickerDialog(v);
    }
    public void onClickEndDate(View v){
        aSwitch.setChecked(true);
        showDatePickerDialog(v);
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public String formatDate(Calendar c){
        String s;
        s = String.valueOf(c.get(Calendar.DAY_OF_MONTH)) + DatePickerFragment.months[c.get(Calendar.MONTH)] + String.valueOf(c.get(Calendar.YEAR));
        return s;
    }

    public String formatTime(Calendar c){
        String s = String.valueOf(c.get(HOUR_OF_DAY)) + ":";
        if(c.get(MINUTE) < 10)
            s += "0";
        s += String.valueOf(c.get(MINUTE));
        return s;
    }

    public String calcDiffTime(){

        long start_time = start_calendar.getTimeInMillis();
        long end_time = end_calendar.getTimeInMillis(); // subtract these two

        String days = String.valueOf(TimeUnit.MILLISECONDS.toDays(Math.abs(end_time - start_time)));
        String hrs = String.valueOf(TimeUnit.MILLISECONDS.toHours(Math.abs(end_time - start_time)) - 24 * TimeUnit.MILLISECONDS.toDays(Math.abs(end_time - start_time)));
        String mins = String.valueOf(TimeUnit.MILLISECONDS.toMinutes(Math.abs(end_time - start_time)) - 60 * TimeUnit.MILLISECONDS.toHours(Math.abs(end_time - start_time)));
        String time = days + " days, " + hrs + " hrs, " + mins + " mins";
        if(end_calendar.before(start_calendar)){
            time = "- " + time;
        }
        return time;
    }
}