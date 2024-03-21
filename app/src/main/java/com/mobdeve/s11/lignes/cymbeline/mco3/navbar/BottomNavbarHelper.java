package com.mobdeve.s11.lignes.cymbeline.mco3.navbar;

import android.app.Activity;
import android.content.Intent;
import android.widget.ImageView;
import android.view.View;
import com.mobdeve.s11.lignes.cymbeline.mco3.R;
import com.mobdeve.s11.lignes.cymbeline.mco3.activity.NotificationActivity;
import com.mobdeve.s11.lignes.cymbeline.mco3.activity.UserProfileActivity;

public class BottomNavbarHelper {
    public static void setProfileIconClickListener(Activity activity) {
        ImageView profileIcon = activity.findViewById(R.id.profileIcon);
        profileIcon.setOnClickListener(v -> {
            Intent intent = new Intent(activity, UserProfileActivity.class);
            activity.startActivity(intent);
        });

        ImageView notificationIcon = activity.findViewById(R.id.notificationIcon);
        notificationIcon.setOnClickListener(v -> {
            Intent intent = new Intent(activity, NotificationActivity.class);
            activity.startActivity(intent);
        });
    }
}
