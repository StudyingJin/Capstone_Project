package com.example.healthapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class TempTest_12 extends AppCompatActivity {

    private EditText editText;
    private Button sendButton;
    private TextView textView;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temptest_12);

        editText = findViewById(R.id.editText);
        sendButton = findViewById(R.id.sendButton);
        textView = findViewById(R.id.textView);

        // 네트워크 정책을 설정합니다 (이것은 테스트 목적으로만 사용합니다. 실제 앱에서는 AsyncTask 등을 사용하세요)
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            // 서버 IP와 포트로 연결
            socket = new Socket("서버_컴퓨터의_로컬_IP", 8080);
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // 새로운 스레드에서 서버로부터 메시지를 수신합니다
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String receivedMessage;
                        while ((receivedMessage = in.readLine()) != null) {
                            final String finalMessage = receivedMessage;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    textView.append("서버: " + finalMessage + "\n");
                                }
                            });
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        } catch (Exception e) {
            e.printStackTrace();
        }

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String message = editText.getText().toString();
                    out.println(message);
                    if ("quit".equalsIgnoreCase(message)) {
                        socket.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

