package com.example.testapp1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.datasharing.DataReceiver;
import com.example.datasharing.SendData;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private EditText inputOne;
    private EditText inputTwo;

    private Button addButton;
    private Button subButton;

    private TextView inputOneValue;
    private TextView inputTwoValue;
    private TextView actionPerformed;
    private TextView outputValue;

    private String ACTION;
    private DataReceiver dataReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputOne = findViewById(R.id.input_one);
        inputTwo = findViewById(R.id.input_two);

        addButton = findViewById(R.id.add_button);
        subButton = findViewById(R.id.sub_button);

        inputOneValue = findViewById(R.id.input_one_value);
        inputTwoValue = findViewById(R.id.input_two_value);
        actionPerformed = findViewById(R.id.action_value);
        outputValue = findViewById(R.id.output_value);
        addButton.setOnClickListener(addClickListener);
        subButton.setOnClickListener(subClickListener);

        IntentFilter intentFilter = new IntentFilter("com.example.datasharing");
        if (intentFilter != null) {
            dataReceiver = new DataBroadcastReceiver();
            registerReceiver(dataReceiver, intentFilter);
        }
    }

    private void startSecondActivity(String action, int a, int b) {
        Log.d(TAG, "startSecondActivity: here");
        new SendData(this, action, a, b, 0, "com.example.addsubsecondapp");
    }

    private void showToastMsg() {
        Toast.makeText(this, "Cannot accept empty value", Toast.LENGTH_LONG).show();
    }

    private boolean checkEditTextField() {
        return TextUtils.isEmpty(inputOne.getText()) || TextUtils.isEmpty(inputTwo.getText());
    }

    private final View.OnClickListener addClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (checkEditTextField()) {
                showToastMsg();
            } else {
                Log.d(TAG, "onClick: add");
                ACTION = "ADDITION";
                int a = Integer.parseInt(inputOne.getText().toString());
                int b = Integer.parseInt(inputTwo.getText().toString());
                startSecondActivity(ACTION, a, b);
            }
        }
    };

    private final View.OnClickListener subClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (checkEditTextField()) {
                showToastMsg();
            } else {
                Log.d(TAG, "onClick: sub");
                ACTION = "SUBTRACTION";
                int a = Integer.parseInt(inputOne.getText().toString());
                int b = Integer.parseInt(inputTwo.getText().toString());
                startSecondActivity(ACTION, a, b);
            }
        }
    };

    class DataBroadcastReceiver extends DataReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            super.onReceive(context, intent);
            if (intent != null) {
                Log.d(TAG, "onReceive: action == " + intent.getAction());
                final int res = intent.getIntExtra("result", -1);
                final String action = intent.getStringExtra("action");
                final int a = intent.getIntExtra("firstNum", -1);
                final int b = intent.getIntExtra("secondNum", -1);

                if (action == null) {
                    Toast.makeText(context, "Please try again", Toast.LENGTH_SHORT).show();
                } else {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void run() {
                            inputOneValue.setText("Input One - " + a);
                            inputTwoValue.setText("Input Two - " + b);
                            actionPerformed.setText("Action - " + action);
                            outputValue.setText("Output is - " + res);
                        }
                    });
                }
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(dataReceiver);
    }
}