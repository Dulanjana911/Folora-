package com.dsmini.folorauserapp;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TabsAccessorAdapter extends FragmentPagerAdapter {

    public TabsAccessorAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position)
        {
            case 0:return new HomeFragment();
           case 1:return new BikeFragment();
            case 2:return new TukFragment();
            case 3:return new CarOrVanFragment();
            case 4:return new BusFragment();
            case 5:return new ChatsFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position)
        {
            case 0:return "Home";
           case 1:return "Bike";
            case 2:return "Tuk";
            case 3:return "Car";
            case 4:return "Bus";
            case 5:return "Chat";
        }
        return null;
    }

}
