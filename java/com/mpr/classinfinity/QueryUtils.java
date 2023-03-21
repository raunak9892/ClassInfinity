package com.mpr.classinfinity;

import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.mpr.classinfinity.Model.Courses;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class QueryUtils {

    private static String LOG_TAG = QueryUtils.class.getSimpleName();

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static ArrayList<Courses> fetchCoursesData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);         //makeHttpRequest is taking url object
            Log.i(LOG_TAG, "JsonResponse had been taken by httpReq");
        } catch (IOException e) {
            Log.i(LOG_TAG, "Error closing input stream", e);
        }

        // Extract relevant fields from the JSON response and create an {@link Event} object
        ArrayList<Courses> coursesInfo = extractFeatureFromJson(jsonResponse);

        // Return the {@link Event}
        return coursesInfo;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
            Log.i(LOG_TAG, "Successfully Url object created");
        } catch (MalformedURLException e) {
            Log.i(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();

            String clientId = "VhIhgQugkF4y70v7ZjGw0ktFNOUq7cdMSouQHgd7";
            String clientSecret = "21TmoWv68iYLasQjLwpa5OEXtTj7IKIeA1EJLLhajxkMHyIpCZhEO5ojA7dExpDnugshVkutsBgYDwGNLaf2XXh6lxemGPjwU8N6fvhu3fTFlsO3hh46JZPOtMU1IfIx";

            byte[] loginBytes = (clientId+":"+clientSecret).getBytes();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Basic ")
                    .append(Base64.encodeToString(loginBytes,Base64.NO_WRAP));

            urlConnection.setRequestProperty("Authorization",stringBuilder.toString());
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setDoInput(true);

            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
                Log.i(LOG_TAG, "Http Request Successfully initiated");
            } else {
                Log.i(LOG_TAG,"Header: "+urlConnection.getHeaderField("Authorization"));
                Log.i(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.i(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        Log.i(LOG_TAG, "Reading from Stream");
      //  Log.d(LOG_TAG,output.toString());
        return output.toString();
    }

    private static ArrayList<Courses> extractFeatureFromJson(String coursesJsonResponse) {
        // If the JSON string is empty or null, then return early.
        ArrayList<Courses> coursesArrayList = new ArrayList<>();
        if (TextUtils.isEmpty(coursesJsonResponse)) {
            return coursesArrayList;
        }

        try {
            JSONObject baseJasonObject = new JSONObject(coursesJsonResponse);

            JSONArray courseArray = null;

            if(baseJasonObject.has("results")){
                courseArray = baseJasonObject.getJSONArray("results");
            }

            for (int i = 0;i<courseArray.length();i++) {

                JSONObject currentCourse = courseArray.getJSONObject(i);

                ArrayList<Courses.instructors> instructors = new ArrayList<>();
                if (currentCourse.has("visible_instructors")) {
                    JSONArray arrayInstructors = currentCourse.getJSONArray("visible_instructors");
                    for (int j = 0; j < arrayInstructors.length(); j++) {
                        Courses.instructors instructors1 = new Courses.instructors();
                        JSONObject instructorObj = arrayInstructors.getJSONObject(j);
                        instructors1.setName(instructorObj.getString("name"));
                        instructors1.setPhoto(instructorObj.getString("image_100x100"));
                        instructors.add(instructors1);
                    }

                }

                String title = null;
                if (currentCourse.has("title")) {
                    title = currentCourse.getString("title");
                }

                String url = null;
                if (currentCourse.has("url")) {
                    url = currentCourse.getString("url");
                }

                boolean isPaid = false;                        //Please handle this
                if (currentCourse.has("is_paid")) {
                    isPaid = currentCourse.getBoolean("is_paid");
                }

                String price = null;                         //need to convert this to big INTEGER
                if (currentCourse.has("price")) {
                    price = currentCourse.getString("price");
                }

                int courseId = currentCourse.getInt("id");

                String courseThumbnail = null;
                if (currentCourse.has("image_480x270")) {
                    courseThumbnail = currentCourse.getString("image_480x270");
                }

                String discription = null;
                if (currentCourse.has("headline")) {
                    discription = currentCourse.getString("headline");
                }

                Courses courses = new Courses();
                 courses.setId(courseId);
                courses.setTittle(title);
                courses.setDescription(discription);
                courses.setUrl(url);
                courses.setPaid(isPaid);
                courses.setPrice(price);
                courses.setInstructorsList(instructors);
                courses.setCourseThumbnail(courseThumbnail);

                coursesArrayList.add(courses);

                Log.i(LOG_TAG, "Extracting Json Features");

            }
            return coursesArrayList;
        } catch (JSONException e) {
            Log.i(LOG_TAG, "Problem parsing the earthquake JSON results", e);
        }
        return coursesArrayList;

    }
}


