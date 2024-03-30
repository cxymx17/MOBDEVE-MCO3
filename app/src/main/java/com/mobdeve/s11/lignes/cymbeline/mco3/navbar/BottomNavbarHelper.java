package com.mobdeve.s11.lignes.cymbeline.mco3.navbar;

import android.app.Activity;
import android.content.Intent;
import android.widget.ImageView;
import com.mobdeve.s11.lignes.cymbeline.mco3.R;
import com.mobdeve.s11.lignes.cymbeline.mco3.activity.NotificationActivity;
import com.mobdeve.s11.lignes.cymbeline.mco3.activity.UserProfileActivity;
import com.mobdeve.s11.lignes.cymbeline.mco3.activity.DashboardActivity;
import com.mobdeve.s11.lignes.cymbeline.mco3.activity.ui.learn.LearnActivity;


/**
 * Helper class for setting click listeners for bottom navigation bar icons.
 */
public class BottomNavbarHelper {

    /**
     * Sets click listeners for bottom navigation bar icons.
     *
     * @param activity the activity where the bottom navigation bar icons are located
     */
    public static void setProfileIconClickListener(Activity activity) {
        // Set click listener for profileIcon
        ImageView profileIcon = activity.findViewById(R.id.profileIcon);
        profileIcon.setOnClickListener(v -> {
            Intent intent = new Intent(activity, UserProfileActivity.class);
            activity.startActivity(intent);
        });

        // Set click listener for notificationIcon
        ImageView notificationIcon = activity.findViewById(R.id.notificationIcon);
        notificationIcon.setOnClickListener(v -> {
            Intent intent = new Intent(activity, NotificationActivity.class);
            activity.startActivity(intent);
        });

        // Set click listener for homeIcon
        ImageView homeIcon = activity.findViewById(R.id.homeIcon);
        homeIcon.setOnClickListener(v -> {
            Intent intent = new Intent(activity, DashboardActivity.class);
            activity.startActivity(intent);
        });

        // Set click listener for tipsIcon
        ImageView tips = activity.findViewById(R.id.tipsIcon);
        tips.setOnClickListener(v -> {
            Intent intent = new Intent(activity, LearnActivity.class);
            activity.startActivity(intent);
        });
    }
}