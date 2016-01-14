package saoasuna.advancedtodo;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by Ryan on 30/09/2015.
 */
public class TimePickerFragment extends android.support.v4.app.DialogFragment implements TimePickerDialog.OnTimeSetListener {


    public static final String ARG_HOUR = "hour";
    public static final String ARG_MIN = "min";
    public static final String EXTRA_HOUR = "com.bignerdranch.android.criminalintent.hour";
    public static final String EXTRA_MIN = "com.bignerdranch.android.criminalintent.min";

    private TimePicker mTimePicker;
    private int hour;
    private int min;

    public static TimePickerFragment newInstance(){
        TimePickerFragment fragment = new TimePickerFragment();
        return fragment;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {



        Calendar calendar = Calendar.getInstance();
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        min = calendar.get(Calendar.MINUTE);
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_time, null);

        mTimePicker = (TimePicker) v.findViewById(R.id.dialog_time_time_picker);
        if(android.os.Build.VERSION.SDK_INT<23) {
            mTimePicker.setCurrentHour(hour);
            mTimePicker.setCurrentMinute(min);
        }
        else
        {
            mTimePicker.setHour(hour);
            mTimePicker.setMinute(min);
        }

        return new AlertDialog.Builder(getActivity()).setView(v).setTitle(R.string.time_picker_title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick (DialogInterface dialog, int which) {
                        int tempHour;
                        int tempMinute;
                        if(android.os.Build.VERSION.SDK_INT<23) {
                            tempHour = mTimePicker.getCurrentHour();
                            tempMinute= mTimePicker.getCurrentMinute();
                        }
                        else
                        {
                            tempHour = mTimePicker.getHour();
                            tempMinute = mTimePicker.getMinute();
                        };
                        sendResult(Activity.RESULT_OK, tempHour, tempMinute);
                    }
                }).create();

    }

    private void sendResult(int resultCode, int hour, int min) {
        if (getTargetFragment() == null) {
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_HOUR, hour);
        intent.putExtra(EXTRA_MIN, min);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {}

}
