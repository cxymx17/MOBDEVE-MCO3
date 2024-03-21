package com.mobdeve.s11.lignes.cymbeline.mco3.model;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.mobdeve.s11.lignes.cymbeline.mco3.R;

public class NotificationViewHolder extends RecyclerView.ViewHolder {
    private TextView titleTextView;
    private TextView messageTextView;
    private TextView timeTextView;

    public NotificationViewHolder(@NonNull View itemView) {
        super(itemView);
        titleTextView = itemView.findViewById(R.id.notificationTitleTextView);
        messageTextView = itemView.findViewById(R.id.notificationMessageTextView);
        timeTextView = itemView.findViewById(R.id.notificationTimeTextView);
    }

    public void bind(String title, String message, String time) {
        titleTextView.setText(title);
        messageTextView.setText(message);
        timeTextView.setText(time);
    }
}


