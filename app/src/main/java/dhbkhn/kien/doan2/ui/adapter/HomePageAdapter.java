package dhbkhn.kien.doan2.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import dhbkhn.kien.doan2.ui.history.HistoryFragment;
import dhbkhn.kien.doan2.ui.room.RoomFragment;

/**
 * Created by kiend on 5/16/2017.
 */

public class HomePageAdapter extends FragmentStatePagerAdapter{

    private static final int PAGE_COUNT = 3;
    private static final String TAB_TITLES[] = new String[]{
            "Room One",
            "Room Two",
            "History"
    };

    public HomePageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return RoomFragment.newInstance(1);
            case 1:
                return RoomFragment.newInstance(2);
            case 2:
                return HistoryFragment.newInstance();
            default:
                throw new RuntimeException("Invalid tab position: " + position);
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TAB_TITLES[position];
    }
}
