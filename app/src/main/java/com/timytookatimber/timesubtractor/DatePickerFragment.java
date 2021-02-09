package com.timytookatimber.timesubtractor;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
//import android.view.View;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
        static String[] months = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
@Override
public Dialog onCreateDialog(Bundle savedInstanceState) {
// Use the current date as the default date in the picker
final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
                if(MainActivity.switch_pos) {
                        MainActivity.start_calendar.set(Calendar.DAY_OF_MONTH, day);
                        MainActivity.start_calendar.set(Calendar.MONTH, month);
                        MainActivity.start_calendar.set(Calendar.YEAR, year);
                        String s = String.valueOf(day) + months[month] + String.valueOf(year);
                        MainActivity.start_dateTextView.setText(s);
                }else{
                        MainActivity.end_calendar.set(Calendar.DAY_OF_MONTH, day);
                        MainActivity.end_calendar.set(Calendar.MONTH, month);
                        MainActivity.end_calendar.set(Calendar.YEAR, year);
                        String s = String.valueOf(day) + months[month] + String.valueOf(year);
                        MainActivity.end_dateTextView.setText(s);
                }
        }
}

