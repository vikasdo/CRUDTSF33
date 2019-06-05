package com.example.donth.crudtsf;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class Session {
        // Shared Preferences
        SharedPreferences pref;

        // Editor for Shared preferences
        SharedPreferences.Editor editor;

        // Context
        Context context;

        // Shared pref mode
        int PRIVATE_MODE = 0;

        // Sharedpref file name
        private static final String PREF_NAME = "user";

        // All Shared Preferences Keys
        private static final String IS_LOGIN = "IsLoggedIn";

        // User id
        public static final String KEY_USERID = "id";

        // User Email address
        public static final String KEY_EMAIL = "email";

        public static final String KEY_DELETE_ID = "deleteId";

        // Constructor
        public Session(Context context) {
            this.context = context;
            pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
            editor = pref.edit();
        }

        /**
         * Create login session(using UserId and password)
         */
        public  void createLoginSession(String id, String email) {
            // Storing login value as TRUE
            editor.putBoolean(IS_LOGIN, true);

            // Storing user id in pref
            editor.putString(KEY_USERID, id);

            // Storing email in pref
            editor.putString(KEY_EMAIL, email);

            // commit changes
            editor.commit();
        }

        public void createDeleteId(String id){
            editor.putString(KEY_DELETE_ID, id);
            editor.commit();
        }

        /**
         * Get stored session data(UserId, email)
         */
        public HashMap<String, String> getUserDetails() {
            HashMap<String, String> user = new HashMap<String, String>();
            // user id
            user.put(KEY_USERID, pref.getString(KEY_USERID, null));

            // user email id
            user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

            user.put(KEY_DELETE_ID, pref.getString(KEY_DELETE_ID, null));
            // return user with its id and email
            return user;
        }

        /**
         * Clear session details
         */
        public void logoutUser() {
            // Clearing all data from Shared Preferences
            editor.clear();
            editor.commit();

            // After logout redirect user to Loing Activity
            Intent i = new Intent(context, login.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            context.startActivity(i);
        }

        /**
         * Quick check for login
         **/
        // Get Login State
        public boolean isLoggedIn() {
            return pref.getBoolean(IS_LOGIN, false);
        }


}
