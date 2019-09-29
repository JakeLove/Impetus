package io.qepl.impetus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBar;

import android.hardware.SensorEvent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private final Uri HOST = Uri.parse("http://localhost/log");

    private String latestEntry;

    private SensorLogger sensorLogger;
    private PromptQueue promptQueue;
    private Mailman mailman;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        sensorLogger = new SensorLogger(this);
        promptQueue = new PromptQueue(this);
        mailman = new Mailman(this);


        List<Integer> pinButtonIDs = Arrays.asList(R.id.btnPin0, R.id.btnPin1, R.id.btnPin2, R.id.btnPin3, R.id.btnPin4, R.id.btnPin5, R.id.btnPin6, R.id.btnPin7, R.id.btnPin8, R.id.btnPin9);

        for (Integer ID: pinButtonIDs) {
            findViewById(ID).setOnTouchListener(handleTouch);
        }

    }


    private View.OnTouchListener handleTouch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()) {

                case MotionEvent.ACTION_DOWN:
                    handleTouchDown(view);
                    break;

                case MotionEvent.ACTION_UP:
                    handleTouchUp();
                    break;
            }

            view.performClick();
            return true;
        }
    };


    private void handleTouchUp() {

        ArrayList<SensorEvent> sensorLog = sensorLogger.finishLogging();
        sensorLogger.reset();

        mailman.deliver(sensorLog, latestEntry, "http://192.168.1.12:1066/sensorfeed");

    }


    private void handleTouchDown(View view) {
        sensorLogger.startLogging();
        promptQueue.next();

        Button btn = (Button)view;
        latestEntry = btn.getText().toString();
    }
}
