package com.mobdeve.s11.lignes.cymbeline.mco3.navbar;

import android.app.Activity;
import android.content.Intent;
import android.widget.TextView;

import com.mobdeve.s11.lignes.cymbeline.mco3.R;

public class TopNavBarHelper {
    public static void setCancelClickListener(Activity activity) {
        TextView cancelTextView = activity.findViewById(R.id.tvCancel);
        cancelTextView.setOnClickListener(v -> {
            activity.onBackPressed(); // This will navigate back to the previous page
        });
    }
}
