package com.mobdeve.s11.lignes.cymbeline.mco3.navbar;

import android.app.Activity;
import android.widget.TextView;

import com.mobdeve.s11.lignes.cymbeline.mco3.R;

/**
 * Helper class for setting click listeners for the cancel button in the top navigation bar.
 */
public class TopNavBarHelper {

    /**
     * Sets click listener for the cancel button in the top navigation bar.
     *
     * @param activity the activity where the cancel button is located
     */
    public static void setCancelClickListener(Activity activity) {
        TextView cancelTextView = activity.findViewById(R.id.tvCancel);
        cancelTextView.setOnClickListener(v -> {
            activity.onBackPressed(); // This will navigate back to the previous page
        });
    }
}