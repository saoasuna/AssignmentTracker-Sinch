package saoasuna.advancedtodo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by Ryan on 18/11/2015.
 */
public class NewAssignmentActivity extends SingleFragmentActivity {




    @Override
    protected Fragment createFragment() {
        return NewAssignmentFragment.newInstance();
    }

    public static Intent newIntent(Context packageContext) {
        return new Intent(packageContext, NewAssignmentActivity.class);
    }


}
