package saoasuna.advancedtodo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Ryan on 18/11/2015.
 */
public class NewAssignmentFragment extends Fragment {

    private EditText mTitle;
    private EditText mDetails;
    private EditText mRepeats;
    private Spinner mSpinner;
    private Button mDateChooseButton;
    private Button mTimeChooseButton;
    private TextView mDateText;
    private TextView mTimeText;
    private Button mSaveButton;
    private static final String DIALOG_DATE = "DialogDate";
    private static final String DIALOG_TIME = "DialogTime";

    private ScheduleClient mScheduleClient;

    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 1;
    private String title;
    private String details;
    private int repeats;
    private Date date;
    private int hour;
    private int minute;

    public static NewAssignmentFragment newInstance() {
        return new NewAssignmentFragment();
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScheduleClient = new ScheduleClient(getActivity()); // create service client and bind activity to service
        mScheduleClient.doBindService();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // load the view defined in the xml
        View v = inflater.inflate(R.layout.fragment_new_assignment, container, false);
        mTitle = (EditText)v.findViewById(R.id.edit_text_assignment_title);
        mDetails = (EditText)v.findViewById(R.id.edit_text_assignment_details);
        mRepeats = (EditText)v.findViewById(R.id.edit_text_number_of);
        mDateText=(TextView)v.findViewById(R.id.text_view_date);
        mTimeText=(TextView)v.findViewById(R.id.text_view_time);

        mSpinner = (Spinner)v.findViewById(R.id.spinner_time);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_array, android.R
                .layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mSpinner.setAdapter(adapter);
        mDateChooseButton = (Button)v.findViewById(R.id.button_date);
        mDateChooseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create a datepickerfragment
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(new Date());
                dialog.setTargetFragment(NewAssignmentFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });

        mTimeChooseButton = (Button)v.findViewById(R.id.button_time);
        mTimeChooseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                FragmentManager manager = getFragmentManager();
                TimePickerFragment dialog = TimePickerFragment.newInstance();
                dialog.setTargetFragment(NewAssignmentFragment.this, REQUEST_TIME);
                dialog.show(manager, DIALOG_TIME);
            }
        });
        mSaveButton = (Button)v.findViewById(R.id.button_save);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // should implement a check here to make sure the fields have been filled out properly

                title = mTitle.getText().toString();
                details = mDetails.getText().toString();
                repeats = 0;
                date.setHours(hour);
                date.setMinutes(minute);
                try {
                    repeats = Integer.parseInt(mRepeats.getText().toString());
                } catch (NumberFormatException io){

                }
                if (mSpinner.getSelectedItem().toString().equals("Days")) {
                    repeats = repeats*24*60;
                }
                else if (mSpinner.getSelectedItem().toString().equals("Hours")) {
                    repeats = repeats*60;
            }
                Assignment newAssignment = new Assignment(title, details, repeats, date);
                AssignmentList.get(getActivity()).addAssignment(newAssignment);
                //Calendar c = Calendar.getInstance();
                //c.setTime(date);
                mScheduleClient.setAlarmForNotification(newAssignment); // tell service to set alarm for this date
                // (talks to client which then talks to service)
                if (mScheduleClient!=null) {
                    mScheduleClient.doUnbindService();
                }
                getActivity().finish();

            }
        });
        return v;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_DATE) {
            // apparently date member functions are outdated, so starting to switch to Calendar
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            this.date = date;
            mDateText.setText(date.getDay() + ", " + date.getMonth() + " " + date.getDate() + " " + date.getYear());
        }
        if (requestCode == REQUEST_TIME) {
            hour = data.getIntExtra(TimePickerFragment.EXTRA_HOUR, 0);
            minute = data.getIntExtra(TimePickerFragment.EXTRA_MIN, 0);
            mTimeText.setText(hour + ":" + minute);
        }
    }
}
