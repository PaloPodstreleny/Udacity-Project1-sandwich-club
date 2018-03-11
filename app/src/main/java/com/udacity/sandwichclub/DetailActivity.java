package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView placeOfOriginTextView;
    private LinearLayout placeOfOriginLayout;

    private TextView knowAsTextView;
    private LinearLayout knowAsLinearLayout;

    private TextView descriptionTextView;
    private LinearLayout descriptionLayout;

    private TextView ingredientsTextView;
    private LinearLayout ingredientsLayout;

    private Sandwich sandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        placeOfOriginTextView = findViewById(R.id.origin_tv);
        placeOfOriginLayout = findViewById(R.id.origin_ll);

        knowAsTextView = findViewById(R.id.also_known_tv);
        knowAsLinearLayout = findViewById(R.id.also_known_ll);

        descriptionTextView = findViewById(R.id.description_tv);
        descriptionLayout = findViewById(R.id.description_ll);

        ingredientsTextView = findViewById(R.id.ingredients_tv);
        ingredientsLayout = findViewById(R.id.ingredients_ll);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {

        if(sandwich.getPlaceOfOrigin() != null) {
            placeOfOriginLayout.setVisibility(View.VISIBLE);
            placeOfOriginTextView.setText(sandwich.getPlaceOfOrigin());
        }


        if(sandwich.getAlsoKnownAs() != null){
            knowAsLinearLayout.setVisibility(View.VISIBLE);
            appendString(knowAsTextView,sandwich.getAlsoKnownAs());
        }

        if(sandwich.getDescription() != null){
            descriptionLayout.setVisibility(View.VISIBLE);
            descriptionTextView.setText(sandwich.getDescription());
        }

        if(sandwich.getIngredients() != null){
            ingredientsLayout.setVisibility(View.VISIBLE);
            appendString(ingredientsTextView,sandwich.getIngredients());
        }

    }

    private void appendString(TextView textView, List<String> data){
        StringBuilder builder = new StringBuilder();
        for (String actualData: data){
            builder.append(actualData+", ");
        }
        //Remove last , from string
        textView.setText(builder.toString().substring(0,builder.toString().length()-2));
    }
}
