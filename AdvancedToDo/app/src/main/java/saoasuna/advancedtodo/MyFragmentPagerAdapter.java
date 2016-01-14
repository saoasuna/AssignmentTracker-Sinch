package saoasuna.advancedtodo;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Ryan on 17/11/2015.
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    final int TAB_COUNT = 3;
    private String tabTitles[] = new String[] {"All", "Weekly", "Completed"};
    /* positions: 0 : All
                  1 : Weekly
                  2 : Completed
     */
    private Context mContext;

    public MyFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return AllListFragment.newInstance(); // should check to see if a fragment already exists
            case 1:
                return AllListFragment.newInstance();
            default:
                return AllListFragment.newInstance();
        }

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
