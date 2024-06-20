package com.example.healthapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class FoodMainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FoodAdapter adapter;
    private List<Food> foodList;
    private ProgressBar calorieBar;
    private TextView summaryTextView;
    private TextView recommendationTextView;
    private int totalCalories = 0;
    private int totalProtein = 0;
    private int totalFat = 0;
    private int totalCarbs = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_main);

        recyclerView = findViewById(R.id.recyclerView);
        calorieBar = findViewById(R.id.calorieBar);
        summaryTextView = findViewById(R.id.summaryTextView);
        recommendationTextView = findViewById(R.id.recommendationTextView);
        FloatingActionButton fab = findViewById(R.id.fab);

        foodList = new ArrayList<>();
        adapter = new FoodAdapter(foodList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodMainActivity.this, FoodInputActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        updateRecommendations();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Food food = (Food) data.getSerializableExtra("food");
            foodList.add(food);
            adapter.notifyDataSetChanged();
            updateSummary(food);
            updateRecommendations();
        }
    }

    private void updateSummary(Food food) {
        totalCalories += food.getCalories();
        totalProtein += food.getProtein();
        totalFat += food.getFat();
        totalCarbs += food.getCarbs();

        calorieBar.setProgress(totalCalories);
        String summaryText = "Total Calories: " + totalCalories + " kcal\n" +
                "Protein: " + totalProtein + " g\n" +
                "Fat: " + totalFat + " g\n" +
                "Carbs: " + totalCarbs + " g";
        summaryTextView.setText(summaryText);
    }

    private void updateRecommendations() {
        List<Food> recommendedFoods = new ArrayList<>();
        if (totalProtein < 50) {
            recommendedFoods.add(new Food("Chicken Breast", 165, 31, 3, 0));
        }
        if (totalFat < 70) {
            recommendedFoods.add(new Food("Almonds", 579, 21, 50, 22));
        }
        if (totalCarbs < 300) {
            recommendedFoods.add(new Food("Oatmeal", 158, 6, 3, 27));
        }

        StringBuilder recommendations = new StringBuilder("Recommended Foods:\n");
        for (Food food : recommendedFoods) {
            recommendations.append(food.getName())
                    .append(" - Calories: ").append(food.getCalories())
                    .append(", Protein: ").append(food.getProtein())
                    .append(", Fat: ").append(food.getFat())
                    .append(", Carbs: ").append(food.getCarbs()).append("\n");
        }
        recommendationTextView.setText(recommendations.toString());
    }
}
