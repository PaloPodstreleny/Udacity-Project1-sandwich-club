package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    private static final String LOG = JsonUtils.class.getName();

    private static final int EMPTY_VALUE = 0;

    private static final String SANDWICH_NAME = "name";
    private static final String SANDWICH_MAIN_NAME = "mainName";
    private static final String SANDWICH_KNOWN_AS = "alsoKnownAs";
    private static final String SANDWICH_PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String SANDWICH_DESCRIPTION = "description";
    private static final String SANDWICH_IMAGE = "image";
    private static final String SANDWICH_INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) {

        // Check if json parameter != null
        if (json == null) {
            return null;
        }


        Sandwich detailSandwich;

        try {

            detailSandwich = new Sandwich();

            JSONObject root = new JSONObject(json);
            JSONObject name = root.getJSONObject(SANDWICH_NAME);

            //Check if mainName is defined
            String mainName = name.getString(SANDWICH_MAIN_NAME);
            if (!mainName.isEmpty()) {
                detailSandwich.setMainName(mainName);
            }

            JSONArray alsoKnownAs = name.getJSONArray(SANDWICH_KNOWN_AS);
            final ArrayList<String> listOfKnownName = new ArrayList<>();
            for (int i = 0; i < alsoKnownAs.length(); i++) {
                listOfKnownName.add(alsoKnownAs.getString(i));
            }

            //Check if list has at least one item
            if (listOfKnownName.size() > EMPTY_VALUE) {
                detailSandwich.setAlsoKnownAs(listOfKnownName);
            }

            // Check if placeOfOrigin is defined
            String placeOfOrigin = root.getString(SANDWICH_PLACE_OF_ORIGIN);
            if (!placeOfOrigin.isEmpty()) {
                detailSandwich.setPlaceOfOrigin(placeOfOrigin);
            }

            // Check if description is defined
            String description = root.getString(SANDWICH_DESCRIPTION);
            if (!description.isEmpty()) {
                detailSandwich.setDescription(description);
            }

            // Check if image is defined
            String image = root.getString(SANDWICH_IMAGE);
            if (!image.isEmpty()) {
                detailSandwich.setImage(image);
            }

            final ArrayList<String> listOfIngredients = new ArrayList<>();
            JSONArray ingredients = root.getJSONArray(SANDWICH_INGREDIENTS);
            for (int i = 0; i < ingredients.length(); i++) {
                listOfIngredients.add(ingredients.getString(i));
            }

            // Check if list has at list one item
            if (listOfIngredients.size() > EMPTY_VALUE) {
                detailSandwich.setIngredients(listOfIngredients);
            }




        } catch (JSONException exception) {
            Log.e(LOG, "Problem with parsing JSON Data");
            return null;
        }

        return detailSandwich;

    }

}

