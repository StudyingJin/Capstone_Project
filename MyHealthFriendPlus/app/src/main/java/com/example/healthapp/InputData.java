package com.example.healthapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;

public class InputData extends AppCompatActivity {

    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private EditText editText4;
    private EditText editText5;
    private Spinner spinnerGender;
    private String selectedGender;
    private Button confirmButton;

    private static final String PREFS_NAME = "MyPrefs";
    private static final String KEY_WEIGHT = "weight";
    private static final String KEY_AGE = "age";
    private static final String KEY_HEIGHT = "height";
    private static final String KEY_NECK_CIRCUMFERENCE = "neckCircumference";
    private static final String KEY_WAIST_CIRCUMFERENCE = "waistCircumference";
    private static final String KEY_GENDER = "gender";

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inputdata);

        editText1 = findViewById(R.id.editText1); // 체중
        editText2 = findViewById(R.id.editText2); // 나이
        editText3 = findViewById(R.id.editText3); // 키
        editText4 = findViewById(R.id.editText4); // 목둘레
        editText5 = findViewById(R.id.editText5); // 허리둘레
        spinnerGender = findViewById(R.id.spinnerGender); // 성별
        confirmButton = findViewById(R.id.confirmButton);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(adapter);
        spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedGender = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Default to "Male" if nothing is selected
                selectedGender = "Male";
            }
        });

        /*
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input1 = editText1.getText().toString(); // 체중
                String input2 = editText2.getText().toString(); // 나이
                String input3 = editText3.getText().toString(); // 키
                String input4 = editText4.getText().toString(); // 목둘레
                String input5 = editText5.getText().toString(); // 허리둘레
                String input6 = spinnerGender.toString(); // 성별

                if (!input1.isEmpty() && !input2.isEmpty() && !input3.isEmpty() && !input4.isEmpty() && !input5.isEmpty()) {
                    try {
                        int inputData1 = Integer.parseInt(input1);
                        int inputData2 = Integer.parseInt(input2);
                        int inputData3 = Integer.parseInt(input3);
                        int inputData4 = Integer.parseInt(input4);
                        int inputData5 = Integer.parseInt(input5);

                        String message = "입력된 값: " + inputData1 + ", " + inputData2 + ", " + inputData3 + ", " + input4 + ", " + input5;
                        Toast.makeText(InputData.this, message, Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(InputData.this, MainScreen.class);
                        intent.putExtra("weight", inputData1);
                        intent.putExtra("age", inputData2);
                        intent.putExtra("height", inputData3);
                        intent.putExtra("neckCircumference", input4);
                        intent.putExtra("waistCircumference", input5);
                        intent.putExtra("gender", input6);
                        startActivity(intent);
                    } catch (NumberFormatException e) {
                        Toast.makeText(InputData.this, "숫자를 올바르게 입력하세요", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(InputData.this, "모든 데이터를 입력하세요", Toast.LENGTH_SHORT).show();
                }
            }
        });
        */
        dbHelper = new DatabaseHelper(this);

        confirmButton.setOnClickListener(v -> {
            String weightStr = editText1.getText().toString();
            String ageStr = editText2.getText().toString();
            String heightStr = editText3.getText().toString();
            String neckCircumference = editText4.getText().toString();
            String waistCircumference = editText5.getText().toString();
            String gender = selectedGender;

            if (!TextUtils.isEmpty(weightStr) && !TextUtils.isEmpty(ageStr) && !TextUtils.isEmpty(heightStr) &&
                    !TextUtils.isEmpty(neckCircumference) && !TextUtils.isEmpty(waistCircumference)) {
                try {
                    int weight = Integer.parseInt(weightStr);
                    int age = Integer.parseInt(ageStr);
                    int height = Integer.parseInt(heightStr);

                    // 데이터베이스에 저장
                    saveDataToDatabase(weight, age, height, neckCircumference, waistCircumference, gender);

                    // SharedPreferences에 저장
                    saveDataToPreferences(weight, age, height, neckCircumference, waistCircumference, gender);

                    // 화면 전환
                    Intent intent = new Intent(InputData.this, MainScreen.class);
                    intent.putExtra("weight", weight);
                    intent.putExtra("age", age);
                    intent.putExtra("height", height);
                    intent.putExtra("neckCircumference", neckCircumference);
                    intent.putExtra("waistCircumference", waistCircumference);
                    intent.putExtra("gender", gender);
                    startActivity(intent);
                } catch (NumberFormatException e) {
                    Toast.makeText(InputData.this, "숫자를 올바르게 입력하세요", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(InputData.this, "모든 데이터를 입력하세요", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveDataToDatabase(int weight, int age, int height, String neckCircumference, String waistCircumference, String gender) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_WEIGHT, weight);
        values.put(DatabaseHelper.COLUMN_AGE, age);
        values.put(DatabaseHelper.COLUMN_HEIGHT, height);
        values.put(DatabaseHelper.COLUMN_NECK_CIRCUMFERENCE, neckCircumference);
        values.put(DatabaseHelper.COLUMN_WAIST_CIRCUMFERENCE, waistCircumference);
        values.put(DatabaseHelper.COLUMN_GENDER, gender);

        long newRowId = db.insert(DatabaseHelper.TABLE_NAME, null, values);

        if (newRowId == -1) {
            Toast.makeText(this, "Error saving data to database", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Data saved to database successfully", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveDataToPreferences(int weight, int age, int height, String neckCircumference, String waistCircumference, String gender) {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_WEIGHT, weight);
        editor.putInt(KEY_AGE, age);
        editor.putInt(KEY_HEIGHT, height);
        editor.putString(KEY_NECK_CIRCUMFERENCE, neckCircumference);
        editor.putString(KEY_WAIST_CIRCUMFERENCE, waistCircumference);
        editor.putString(KEY_GENDER, gender);
        editor.apply(); // 또는 editor.commit();
    }
}
