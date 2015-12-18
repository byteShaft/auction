package com.byteshaft.auction.utils;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Helpers {

    private static SharedPreferences getPrefrenceManager() {
        return PreferenceManager.getDefaultSharedPreferences(AppGlobals.getContext());
    }

    public static void userLogin(boolean value) {
        SharedPreferences sharedPreferences = getPrefrenceManager();
        sharedPreferences.edit().putBoolean(AppGlobals.user_login_key, value).apply();
    }

    public static  boolean isUserLoggedIn() {
        SharedPreferences sharedPreferences = getPrefrenceManager();
        return sharedPreferences.getBoolean(AppGlobals.user_login_key, false);
    }

    public static void saveLastFragmentOpend(String value) {
        SharedPreferences sharedPreferences = getPrefrenceManager();
        sharedPreferences.edit().putString(AppGlobals.lastFragment, value).apply();
    }

    public static String getLastFragment() {
        SharedPreferences sharedPreferences = getPrefrenceManager();
        return sharedPreferences.getString(AppGlobals.lastFragment, "");
    }

    public static void saveCategoryStatus(String key, boolean value) {
        SharedPreferences sharedPreferences = getPrefrenceManager();
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    public static boolean getCategoryStatue(String key) {
        SharedPreferences sharedPreferences = getPrefrenceManager();
        return sharedPreferences.getBoolean(key, false);
    }

    public static void saveDataToSharedPreferences(String key, String value) {
        SharedPreferences sharedPreferences = getPrefrenceManager();
        sharedPreferences.edit().putString(key, value).apply();
    }

    public static Boolean getBooleanValueFromSharedPrefrence(String key) {
        SharedPreferences sharedPreferences = getPrefrenceManager();
        return sharedPreferences.getBoolean(key, false);
    }

    public static String getStringDataFromSharedPreference(String key) {
        SharedPreferences sharedPreferences = getPrefrenceManager();
        return sharedPreferences.getString(key, "");
    }

    public static int sendRegisterData(String email, String password, String userName,
                                        String phoneNumber, String city, String address)
            throws IOException, JSONException {
        URL url;
        HttpURLConnection urlConnection;
        url = new URL (AppGlobals.REGISTER_URL);
        urlConnection =(HttpURLConnection) url.openConnection();
        urlConnection.setRequestProperty("Content-Type", "application/json");
        urlConnection.setRequestMethod("POST");
        urlConnection.connect();
        String data = String.format("{\"username\": \"%s\", \"password\": \"%s\"," +
                "\"email\": \"%s\", \"address\": \"%s\"," +
                "\"city\": \"%s\", \"phone_number\": \"%s\" }", userName, password, email, address, city, phoneNumber);
        byte[] bytes = data.getBytes("UTF-8");
        OutputStream os = urlConnection.getOutputStream();
        os.write(bytes);
        os.close();
        return urlConnection.getResponseCode();
    }

    public static int checkIfUserExist(String userName) throws IOException, JSONException {
        URL url;
        HttpURLConnection urlConnection;
        url = new URL (AppGlobals.USER_EXIST_URL + userName);
        urlConnection =(HttpURLConnection) url.openConnection();
//        urlConnection.setRequestProperty("Content-Type", "application/json");
//        urlConnection.setRequestMethod("GET");
        urlConnection.connect();
//        JSONObject jsonObject = readResponse(urlConnection);
//        System.out.println(jsonObject);
        return urlConnection.getResponseCode();
    }

    private static JSONObject readResponse(HttpURLConnection connection)
            throws IOException, JSONException {

        InputStream is = connection.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        String line;
        StringBuilder response = new StringBuilder();
        while((line = rd.readLine()) != null) {
            response.append(line);
            response.append('\r');
        }
        return new JSONObject(response.toString());
    }

}
