package com.example.cab302project.Model;

/**
 * The ViewingUser class represents a static context for handling the currently selected user in the application.
 * It stores the username of the selected user and provides methods to set, get, and clear the selected user.
 * This class assumes there will only be one selected user at a time in the application's context.
 */
public class ViewingUser {
    // Stores the username of the selected user
    private static String userName;

    /**
     * Sets the selected user by storing the provided username.
     *
     * @param username The username of the selected user.
     */
    public static void setSelectedUser(String username) {
        userName = username;
    }

    /**
     * Gets the currently selected user's username.
     *
     * @return The username of the selected user, or null if no user is selected.
     */
    public static String getSelectedUser() {
        return userName;
    }

    /**
     * Clears the selected user, setting the username to null.
     * This method is used to deselect or reset the selected user.
     */
    public static void clearSelectedUser() {
        userName = null;
    }
}
