package com.example.healthapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExerciseInputData extends AppCompatActivity {
    private EditText exerNameEdit;
    private EditText exerDurationEdit;
    private EditText exerSetNumEdit;
    private EditText exerRepNumEdit;
    private Button exerInputButton;
    private SQLiteDatabase database;
    private exerciseDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exerinput);

        exerNameEdit = findViewById(R.id.exerNameEdit);
        exerDurationEdit = findViewById(R.id.exerDurationEdit);
        exerSetNumEdit = findViewById(R.id.exerSetNumEdit);
        exerRepNumEdit = findViewById(R.id.exerRepNumEdit);
        exerInputButton = findViewById(R.id.exerInputButton);

        dbHelper = new exerciseDBHelper(this);

        exerInputButton.setOnClickListener(v -> {
            String exerName = exerNameEdit.getText().toString();
            String exerDuration = exerDurationEdit.getText().toString();
            String exerSetNum = exerSetNumEdit.getText().toString();
            String exerRepNum = exerRepNumEdit.getText().toString();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_hh:mm:ss");
            String exerDate = dateFormat.format(new Date((long) System.currentTimeMillis()));
            if (!TextUtils.isEmpty(exerName) && !TextUtils.isEmpty(exerSetNum) && !TextUtils.isEmpty(exerRepNum)) {
                try {
                    int duration = Integer.parseInt(exerDuration);
                    int set = Integer.parseInt(exerSetNum);
                    int rep = Integer.parseInt(exerRepNum);

                    // 데이터베이스에 저장
                    database = dbHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("exer_date", exerDate);
                    values.put("exer_name", exerName);
                    values.put("exer_duration", duration);
                    values.put("num_sets", set);
                    values.put("num_reps", rep);
                    database.insert("exerciseTBL", null, values);

                    Intent intent = new Intent(ExerciseInputData.this, MainScreen.class);
                    intent.putExtra("exerName", exerName);
                    intent.putExtra("exerDuration", duration);
                    startActivity(intent);
                } catch (NumberFormatException e) {
                    Toast.makeText(ExerciseInputData.this, "숫자를 올바르게 입력하세요", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(ExerciseInputData.this, "모든 데이터를 입력하세요", Toast.LENGTH_SHORT).show();
            }

        });
    }

    public static class exerciseDBHelper extends SQLiteOpenHelper {
        public exerciseDBHelper(Context context) {
            super(context, "exerciseDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
//            DB 생성. 운동시간(텍스트), 운동이름(텍스트), 운동지속시간(정수), 세트수(정수), 세트당렙수(정수)
            db.execSQL("CREATE TABLE exerciseTBL (" +
                    "exer_datetime TEXT PRIMARY KEY," +
                    "exer_name TEXT," +
                    "num_duration INTEGER," +
                    "num_sets INTEGER," +
                    "num_reps INTEGER);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS exerciseTBL");
            onCreate(db);
        }
    }
}
