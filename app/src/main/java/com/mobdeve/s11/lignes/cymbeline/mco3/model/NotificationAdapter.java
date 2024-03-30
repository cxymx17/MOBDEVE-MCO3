package com.mobdeve.s11.lignes.cymbeline.mco3.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.s11.lignes.cymbeline.mco3.R;

import java.util.List;

/**
 * NotificationAdapter is a RecyclerView adapter for displaying Notification items.
 * It binds the data to the views for display.
 */
public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private final List<Notification> notificationList;

    /**
     * Constructs a NotificationAdapter with the specified list of notifications.
     *
     * @param notificationList the list of notifications to be displayed
     */
    public NotificationAdapter(List<Notification> notificationList) {
        this.notificationList = notificationList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Notification notification = notificationList.get(position);
        holder.notificationTitleTextView.setText(notification.getTitle());
        holder.notificationMessageTextView.setText(notification.getMessage());
        holder.notificationTimeTextView.setText(notification.getTime());
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    /**
     * Adds a new notification to the list and notifies the adapter of the inserted item.
     *
     * @param notification the notification to be added
     */
    public void addNotification(Notification notification) {
        notificationList.add(notification);
        notifyItemInserted(notificationList.size() - 1); // Notify adapter about the new item
    }

    /**
     * ViewHolder class represents each item's view in the RecyclerView.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView notificationTitleTextView;
        TextView notificationMessageTextView;
        TextView notificationTimeTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            notificationTitleTextView = itemView.findViewById(R.id.notificationTitleTextView);
            notificationMessageTextView = itemView.findViewById(R.id.notificationMessageTextView);
            notificationTimeTextView = itemView.findViewById(R.id.notificationTimeTextView);
        }
    }
}