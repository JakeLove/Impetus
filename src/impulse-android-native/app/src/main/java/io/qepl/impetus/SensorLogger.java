package io.qepl.impetus;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import org.json.JSONObject;

import java.util.ArrayList;

class SensorLogger implements SensorEventListener{
    
    private Boolean isLogging = false;

    private SensorLog sensorLog;

    private ArrayList<String> sensorsToUseNames = new ArrayList<>();


    SensorLogger(Context context, int[] sensorTypes) {

        int sensorDelay = SensorManager.SENSOR_DELAY_FASTEST;

        android.hardware.SensorManager manager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);

        for (int sensorType: sensorTypes) {

            Sensor defaultSensor = manager.getDefaultSensor(sensorType);

            manager.registerListener(this, defaultSensor, sensorDelay);
            sensorsToUseNames.add(defaultSensor.getStringType());

        }

        reset();
    }


    JSONObject finishLogging() {

        isLogging = false;

        return sensorLog.getJSON();

    }


    void startLogging() {

        isLogging = true;

    }


    void reset() {

        sensorLog = new SensorLog(sensorsToUseNames);

    }


    public void onSensorChanged(SensorEvent event) {

        if(isLogging) {

            sensorLog.update(event);

        }

    }


    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

}
