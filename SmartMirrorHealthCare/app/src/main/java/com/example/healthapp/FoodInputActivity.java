package com.example.healthapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class FoodInputActivity extends AppCompatActivity {

    private EditText nameEditText, caloriesEditText, proteinEditText, fatEditText, carbsEditText;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_input);

        nameEditText = findViewById(R.id.nameEditText);
        caloriesEditText = findViewById(R.id.caloriesEditText);
        proteinEditText = findViewById(R.id.proteinEditText);
        fatEditText = findViewById(R.id.fatEditText);
        carbsEditText = findViewById(R.id.carbsEditText);
        addButton = findViewById(R.id.addButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();
                int calories = Integer.parseInt(caloriesEditText.getText().toString());
                int protein = Integer.parseInt(proteinEditText.getText().toString());
                int fat = Integer.parseInt(fatEditText.getText().toString());
                int carbs = Integer.parseInt(carbsEditText.getText().toString());

                Food food = new Food(name, calories, protein, fat, carbs);
                Intent resultIntent = new Intent();
                resultIntent.putExtra("food", food);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}
