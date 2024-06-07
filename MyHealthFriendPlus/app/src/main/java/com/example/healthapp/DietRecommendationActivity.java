package com.example.healthapp;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class DietRecommendationActivity extends AppCompatActivity {
    private TextView recommendationTextView;
    private List<Food> allFoods;
    private List<Food> currentDiet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_recommendation);

        recommendationTextView = findViewById(R.id.dietRecommendationText);

        // 임의의 음식 데이터베이스
        allFoods = new ArrayList<>();
        allFoods.add(new Food("Chicken Breast", 165, 31, 3, 0));
        allFoods.add(new Food("Broccoli", 55, 4, 0, 11));
        allFoods.add(new Food("Almonds", 579, 21, 50, 22));
        allFoods.add(new Food("Oatmeal", 158, 6, 3, 27));

        // 사용자가 먹은 현재 식단 (테스트용 데이터)
        currentDiet = new ArrayList<>();
        currentDiet.add(new Food("Apple", 95, 0, 0, 25));
        currentDiet.add(new Food("Rice", 206, 4, 0, 45));

        // 추천 알고리즘 실행
        List<Food> recommendedFoods = recommendFoods(currentDiet);

        // 추천 결과 표시
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

    private List<Food> recommendFoods(List<Food> currentDiet) {
        int totalProtein = 0, totalFat = 0, totalCarbs = 0;
        for (Food food : currentDiet) {
            totalProtein += food.getProtein();
            totalFat += food.getFat();
            totalCarbs += food.getCarbs();
        }

        List<Food> recommendations = new ArrayList<>();
        for (Food food : allFoods) {
            if (totalProtein < 50 && food.getProtein() > 10) {
                recommendations.add(food);
            } else if (totalFat < 70 && food.getFat() > 10) {
                recommendations.add(food);
            } else if (totalCarbs < 300 && food.getCarbs() > 20) {
                recommendations.add(food);
            }
        }
        return recommendations;
    }
}
