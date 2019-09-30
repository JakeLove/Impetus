package io.qepl.impetus;

import android.hardware.SensorEvent;

public class SensorEventData {

    public float[] values;
    public long timestamp;
    public String sensorName;

    SensorEventData(SensorEvent event) {

        values = event.values.clone();
        timestamp = event.timestamp;
        sensorName = event.sensor.getName();

    }
}
