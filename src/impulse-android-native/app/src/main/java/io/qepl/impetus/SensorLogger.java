package io.qepl.impetus;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.util.ArrayList;

public class SensorLogger implements SensorEventListener{
    
    private Boolean isLogging = false;

    private ArrayList<SensorEventData> sensorLog;


    SensorLogger(Context context) {

        int sensorDelay = SensorManager.SENSOR_DELAY_FASTEST;
        int[] sensorTypes = {Sensor.TYPE_LINEAR_ACCELERATION, Sensor.TYPE_ACCELEROMETER, Sensor.TYPE_ORIENTATION, Sensor.TYPE_GYROSCOPE};

        android.hardware.SensorManager manager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);

        for (int sensorType: sensorTypes) {
            manager.registerListener(this, manager.getDefaultSensor(sensorType), sensorDelay);
        }

        reset();
    }

    ArrayList<SensorEventData> finishLogging() {

        isLogging = false;

        return sensorLog;

    }

    void startLogging() {

        isLogging = true;

    }


    void reset() {

        sensorLog = new ArrayList<>();

    }


    public void onSensorChanged(SensorEvent event) {

        if(isLogging) {

            sensorLog.add(new SensorEventData(event));

        }

    }


    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

}
