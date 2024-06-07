package com.example.healthapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
public class MainScreen extends AppCompatActivity {

    private boolean backPressedOnce = false;

    private FloatingActionButton inputDataButton;
    private Button intputFoodButton;
    private Button button;
    private PieChart exerciseGoalPieChart;
    private PieChart exerciseTimePieChart;
    private PieChart caloriesBurnedPieChart;
    private ImageView exercisePhoto;
    private TextView exerciseName;
    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputDataButton = findViewById(R.id.inputDataButton);
        // "+" 버튼 클릭 이벤트 설정
        inputDataButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainScreen.this, InputData.class);
            startActivity(intent);
        });

        intputFoodButton = findViewById(R.id.FoodInsertButton);
        intputFoodButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainScreen.this, FoodMainActivity.class);
            startActivity(intent);
        });

        // 운동 현황 파이 차트 삽입
        exerciseGoalPieChart = findViewById(R.id.exerciseGoalPieChart);
        exerciseTimePieChart = findViewById(R.id.exerciseTimePieChart);
        caloriesBurnedPieChart = findViewById(R.id.caloriesBurnedPieChart);

        // 파이 차트 설정
        setupExerciseGoalPieChart();
        setupExerciseTimePieChart();
        setupCaloriesBurnedPieChart();

        // 최근 진행한 운동에 관련한 이미지 삽입
        exercisePhoto = findViewById(R.id.exercisePhoto);

        // drawable 리소스를 사용하여 이미지 설정


        // 인바디 정보 설정
        resultTextView = findViewById(R.id.resultTextView);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        int weight = sharedPreferences.getInt("weight", 0);
        int age = sharedPreferences.getInt("age", 0);
        int height = sharedPreferences.getInt("height", 0);
        String neckCircumference = sharedPreferences.getString("neckCircumference", "");
        String waistCircumference = sharedPreferences.getString("waistCircumference", "");
        String gender = sharedPreferences.getString("gender", "");

        Intent intent = getIntent();
        if (weight != 0 && age != 0 && height != 0 && !neckCircumference.isEmpty() && !waistCircumference.isEmpty() && !gender.isEmpty()) {
            // Perform calculations (These are just example calculations, you should replace them with real formulas)
            double bodyFatPercentage = calculateBodyFatPercentage(weight, height, Integer.parseInt(neckCircumference), Integer.parseInt(waistCircumference), gender);
            double muscleMass = calculateMuscleMass(weight, bodyFatPercentage);

            String result = "체중: " + weight + "kg\n" +
                    "체지방률: " + String.format("%.2f", bodyFatPercentage) + "%\n" +
                    "근육량: " + String.format("%.2f", muscleMass) + "kg";

            resultTextView.setText(result);
        } else {
            resultTextView.setText("데이터가 입력되기 전입니다. 데이터를 입력해주세요.");
        }
        /*
        if (intent.hasExtra("weight") && intent.hasExtra("age") && intent.hasExtra("height")
                && intent.hasExtra("neckCircumference") && intent.hasExtra("waistCircumference")) {
            int weight = intent.getIntExtra("weight", 0);
            int age = intent.getIntExtra("age", 0);
            int height = intent.getIntExtra("height", 0);
            String neckCircumference = intent.getStringExtra("neckCircumference");
            String waistCircumference = intent.getStringExtra("waistCircumference");

            // Perform calculations (These are just example calculations, you should replace them with real formulas)
            double bodyFatPercentage = calculateBodyFatPercentage(weight, height, Integer.parseInt(neckCircumference), Integer.parseInt(waistCircumference));
            double muscleMass = calculateMuscleMass(weight, bodyFatPercentage);

            String result = "체중: " + weight + "kg\n" +
                    "체지방률: " + String.format("%.2f", bodyFatPercentage) + "%\n" +
                    "근육량: " + String.format("%.2f", muscleMass) + "kg";
            resultTextView.setText(result);
        } else {
            resultTextView.setText("데이터가 입력되기 전입니다. 데이터를 입력해주세요.");
        }
        */

        exerciseName = findViewById(R.id.exerciseName);
        String inputExerName = intent.getStringExtra("exerName");
        if (inputExerName != null && intent.getIntExtra("exerDuration", 0) != 0) {
            if (inputExerName.equals("스쿼트"))
                setupExercisePhoto(R.drawable.squart);

            else if (inputExerName.equals("푸쉬업"))
                setupExercisePhoto(R.drawable.pushup);

            else if (inputExerName.equals("덤벨컬"))
                setupExercisePhoto(R.drawable.curl);

            String currentExrcise = intent.getStringExtra("exerName");

            exerciseName.setText(currentExrcise);
        } else {
            exerciseName.setText("운동 기록을 기입해주세요.");
        }

        button = findViewById(R.id.asktomirror);
        button.setOnClickListener(view -> {
            Intent intent01 = new Intent(MainScreen.this, ExerciseInputData.class);
            startActivity(intent01);
        });
    }
    private void setupExerciseGoalPieChart() {
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(75f, "운동 목표 달성률"));
        entries.add(new PieEntry(25f, "남은 목표"));

        PieDataSet dataSet = new PieDataSet(entries, "운동 목표 달성률");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setValueTextSize(16f);

        PieData data = new PieData(dataSet);
        exerciseGoalPieChart.setData(data);
        exerciseGoalPieChart.getDescription().setText("운동 목표 달성률");
        exerciseGoalPieChart.animateY(1000);
    }

    private void setupExerciseTimePieChart() {
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(60f, "운동 시간 달성률"));
        entries.add(new PieEntry(40f, "남은 시간"));

        PieDataSet dataSet = new PieDataSet(entries, "운동 시간 달성률");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setValueTextSize(16f);

        PieData data = new PieData(dataSet);
        exerciseTimePieChart.setData(data);
        exerciseTimePieChart.getDescription().setText("운동 시간 달성률");
        exerciseTimePieChart.animateY(1000);
    }

    private void setupCaloriesBurnedPieChart() {
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(61f, "소모 열량 달성률"));
        entries.add(new PieEntry(39f, "남은 열량"));

        PieDataSet dataSet = new PieDataSet(entries, "소모 열량 달성률");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setValueTextSize(16f);

        PieData data = new PieData(dataSet);
        caloriesBurnedPieChart.setData(data);
        caloriesBurnedPieChart.getDescription().setText("소모 열량 달성률");
        caloriesBurnedPieChart.animateY(1000);
    }

    private void setupExercisePhoto(int drawableResId) {
        exercisePhoto.setImageResource(drawableResId);
    }

    // Dummy calculation for body fat percentage (replace with real formula)
    private double calculateBodyFatPercentage(int weight, int height, int neckCircumference, int waistCircumference, String gender) {
        // Example formula (not accurate)
        double bodyFat = (waistCircumference - neckCircumference) * 0.1 + (height - weight) * 0.1;
        if ("Female".equalsIgnoreCase(gender)) {
            bodyFat += 5;  // Example adjustment for females (not accurate)
        }
        return bodyFat;
    }

    // Dummy calculation for muscle mass (replace with real formula)
    private double calculateMuscleMass(int weight, double bodyFatPercentage) {
        // Example formula (not accurate)
        return weight * (1 - bodyFatPercentage / 100);
    }

    @Override
    public void onBackPressed() {
        // 뒤로 가기 버튼 처리
        if (backPressedOnce) {
            super.onBackPressed();
            finishAffinity();
            return;
        }

        this.backPressedOnce = true;
        Toast.makeText(this, "한 번 더 누르면 종료합니다.", Toast.LENGTH_SHORT).show();

        new android.os.Handler().postDelayed(() -> backPressedOnce = false, 2000);
    }
}