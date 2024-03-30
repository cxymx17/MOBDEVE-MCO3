package com.mobdeve.s11.lignes.cymbeline.mco3.model;

/**
 * Notification class represents a notification entity with a title, message, and time.
 * It is used to encapsulate notification data for display purposes.
 */
public class Notification {
    private String title;
    private String message;
    private String time;

    /**
     * Constructs a Notification object with the specified title, message, and time.
     *
     * @param title   the title of the notification
     * @param message the message content of the notification
     * @param time    the time when the notification was received or scheduled
     */
    public Notification(String title, String message, String time) {
        this.title = title;
        this.message = message;
        this.time = time;
    }

    /**
     * Retrieves the title of the notification.
     *
     * @return the title of the notification
     */
    public String getTitle() {
        return title;
    }

    /**
     * Retrieves the message content of the notification.
     *
     * @return the message content of the notification
     */
    public String getMessage() {
        return message;
    }

    /**
     * Retrieves the time when the notification was received or scheduled.
     *
     * @return the time of the notification
     */
    public String getTime() {
        return time;
    }
}

