
        package saoasuna.advancedtodo;

        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v4.app.Fragment;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.CheckBox;
        import android.widget.CompoundButton;
        import android.widget.Spinner;
        import android.widget.TextView;

        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Calendar;
        import java.util.Date;
        import java.util.List;
        import java.util.UUID;

/**
 * Created by Ryan on 17/11/2015.
 */
// purpose : displays weekly assignment fragments, as well as a button to add a new assignment
public class AllListFragment extends Fragment {
    public static final String WEEKLY_PAGE = "WEEKLY_PAGE";
    private RecyclerView mAssignmentRecyclerView;
    private AssignmentRecyclerAdapter mRecyclerAdapter;
    private Button mNewAssignmentButton;
    private Button mSaveChangesButton;
    private List<UUID> toDelete;
    private Spinner mSpinner;

    public static AllListFragment newInstance() {

        return new AllListFragment();
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        toDelete = new ArrayList<UUID>();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // load the view defined in the xml
        View v = inflater.inflate(R.layout.fragment_weekly, container, false);
        mAssignmentRecyclerView = (RecyclerView)v.findViewById(R.id.assignment_recycler_view);
        // get reference to recycler view and set it up
        mAssignmentRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mSpinner = (Spinner)v.findViewById(R.id.spinner_sort);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.sort_array, android.R
                .layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mSpinner.setAdapter(adapter);

        // set up the functionality on the add assignment button
        mNewAssignmentButton = (Button)v.findViewById(R.id.new_assignment_button);
        mNewAssignmentButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Intent i = new Intent(getActivity(), NewAssignmentActivity.class);
                Intent i = NewAssignmentActivity.newIntent(getActivity());
                startActivity(i);
                updateUI();
            }
        });
        mSaveChangesButton = (Button)v.findViewById(R.id.save_changes_button);
        mSaveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = 0;
                while(toDelete.size() > i) {
                    AssignmentList.get(getActivity()).removeAssignmentUsingUUID(toDelete.get(i));
                    i++;
                }
                updateUI();
                toDelete.clear();

            }
        });

        // update the ui whenever the view is reloaded
        updateUI();
        return v;

    }

    private void updateUI() {
        AssignmentList assignmentList = AssignmentList.get(getActivity());
        List<Assignment> assignments = assignmentList.getAssignments();


        // will setup the recyclerview adapter if it has not been done yet
        // adapter manages the view for us
        if (mRecyclerAdapter == null) {
            mRecyclerAdapter = new AssignmentRecyclerAdapter(assignments);
            mAssignmentRecyclerView.setAdapter(mRecyclerAdapter);
        }
        else {
            // update changes
            mRecyclerAdapter.setAssignments(assignments);
            mRecyclerAdapter.notifyDataSetChanged();

        }
    }



    // INNER CLASSES

    private class AssignmentRecyclerAdapter extends RecyclerView.Adapter<AssignmentHolder> {
        private List<Assignment> mAssignments;

        public AssignmentRecyclerAdapter(List<Assignment> assignments) {
            mAssignments = assignments;
        }


        // creating assignment holders which will be binded later
        @Override
        public AssignmentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_assignment, parent, false);
            return new AssignmentHolder(view);
        }


        // setting each view to display the correct title, details, date
        @Override
        public void onBindViewHolder(AssignmentHolder holder, int position) {

            // when implemented with avl tree rather than list, have position 0 correspond to
            // tree-minimum. keep a position counter to find out the # of calls that have to be
            // made to tree-successor or tree-predecessor

            Assignment assignment = mAssignments.get(position);
            holder.bindAssignment(assignment);
        }

        @Override
        public int getItemCount() {
            return mAssignments.size();
        }

        public void setAssignments (List<Assignment> assignments) {
            mAssignments=assignments;
        }
    }

    public class AssignmentHolder extends RecyclerView.ViewHolder {

        // the xml components defined in the list_item_assignment.xml
        private TextView mTitleTextView;
        private TextView mDetailTextView;
        private TextView mDueDateTextView;
        private CheckBox mFinished;

        // the assignment instance
        private Assignment mAssignment;


        public AssignmentHolder(View itemView) {
            super(itemView);
            mTitleTextView = (TextView)itemView.findViewById(R.id.list_item_assignment_title_text_view);
            mDetailTextView = (TextView)itemView.findViewById(R.id.list_item_assignment_details);
            mDueDateTextView = (TextView)itemView.findViewById(R.id.list_item_assignment_due_date);
            mFinished = (CheckBox)itemView.findViewById(R.id.list_item_assignment_finished);
            mFinished.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if(isChecked) {
                        toDelete.add(mAssignment.getUUID());
                    }
                    else {
                        // perhaps HIGHLY inefficient
                        toDelete.remove(mAssignment.getUUID());
                    }
                }
            });
        }

        public void bindAssignment(Assignment assignment) {
            mAssignment = assignment;
            mTitleTextView.setText(mAssignment.getTitle());
            mDetailTextView.setText(mAssignment.getDetails());
            SimpleDateFormat temp = new SimpleDateFormat("E MMM dd HH:mm");
            mDueDateTextView.setText(temp.format(mAssignment.getDueDate()));
            mFinished.setChecked(false);
        }
    }


}