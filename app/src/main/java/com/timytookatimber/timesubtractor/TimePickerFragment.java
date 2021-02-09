package com.timytookatimber.timesubtractor;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
//import android.view.View;
import android.widget.TimePicker;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {
    int class_hour = 1;
    int class_minute = 1;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if(MainActivity.switch_pos) {
            MainActivity.start_calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            MainActivity.start_calendar.set(Calendar.MINUTE, minute);
            String s = String.valueOf(hourOfDay) + ":";
            if(minute < 10)
                s += "0";
            s += String.valueOf(minute);

            MainActivity.start_timeTextView.setText(s);
        }else{
            MainActivity.end_calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            MainActivity.end_calendar.set(Calendar.MINUTE, minute);
            String s = String.valueOf(hourOfDay) + ":";
            if(minute < 10)
                s += "0";
            s += String.valueOf(minute);
            MainActivity.end_timeTextView.setText(s);
        }
    }
}
