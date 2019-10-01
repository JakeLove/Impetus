package io.qepl.impetus;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.util.Log;
import android.view.MotionEvent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

class SensorLog {

    private ArrayList<Long> LINEAR_ACCELERATION_t = new ArrayList<>();
    private ArrayList<Float> LINEAR_ACCELERATION_X = new ArrayList<>();
    private ArrayList<Float> LINEAR_ACCELERATION_Y = new ArrayList<>();
    private ArrayList<Float> LINEAR_ACCELERATION_Z = new ArrayList<>();

    private ArrayList<Long> ACCELEROMETER_t = new ArrayList<>();
    private ArrayList<Float> ACCELEROMETER_X = new ArrayList<>();
    private ArrayList<Float> ACCELEROMETER_Y = new ArrayList<>();
    private ArrayList<Float> ACCELEROMETER_Z = new ArrayList<>();

    private ArrayList<Long> ORIENTATION_t = new ArrayList<>();
    private ArrayList<Float> ORIENTATION_X = new ArrayList<>();
    private ArrayList<Float> ORIENTATION_Y = new ArrayList<>();
    private ArrayList<Float> ORIENTATION_Z = new ArrayList<>();

    private ArrayList<Long> GYROSCOPE_t = new ArrayList<>();
    private ArrayList<Float> GYROSCOPE_X = new ArrayList<>();
    private ArrayList<Float> GYROSCOPE_Y = new ArrayList<>();
    private ArrayList<Float> GYROSCOPE_Z = new ArrayList<>();


    SensorLog() {

    }


    void update(SensorEvent sensorEvent) {

        long t = sensorEvent.timestamp;

        float[] values = sensorEvent.values.clone();
        float x = values[0];
        float y = values[1];
        float z = values[2];

        switch (sensorEvent.sensor.getStringType()) {

            case "android.sensor.linear_acceleration":

                LINEAR_ACCELERATION_t.add(t);
                LINEAR_ACCELERATION_X.add(x);
                LINEAR_ACCELERATION_Y.add(y);
                LINEAR_ACCELERATION_Z.add(z);

                break;

            case "android.sensor.accelerometer":

                ACCELEROMETER_t.add(t);
                ACCELEROMETER_X.add(x);
                ACCELEROMETER_Y.add(y);
                ACCELEROMETER_Z.add(z);

                break;

            case "android.sensor.orientation":

                ORIENTATION_t.add(t);
                ORIENTATION_X.add(x);
                ORIENTATION_Y.add(y);
                ORIENTATION_Z.add(z);

                break;

            case "android.sensor.gyroscope":

                GYROSCOPE_t.add(t);
                GYROSCOPE_X.add(x);
                GYROSCOPE_Y.add(y);
                GYROSCOPE_Z.add(z);
                break;


        }
    }


    JSONObject getJSON() {

        JSONObject res = new JSONObject();

        try {

            res.put("LINEAR_ACCELERATION_t", new JSONArray(LINEAR_ACCELERATION_t));
            res.put("LINEAR_ACCELERATION_X", new JSONArray(LINEAR_ACCELERATION_X));
            res.put("LINEAR_ACCELERATION_Y", new JSONArray(LINEAR_ACCELERATION_Y));
            res.put("LINEAR_ACCELERATION_Z", new JSONArray(LINEAR_ACCELERATION_Z));

            res.put("ACCELEROMETER_t", new JSONArray(ACCELEROMETER_t));
            res.put("ACCELEROMETER_X", new JSONArray(ACCELEROMETER_X));
            res.put("ACCELEROMETER_Y", new JSONArray(ACCELEROMETER_Y));
            res.put("ACCELEROMETER_Z", new JSONArray(ACCELEROMETER_Z));

            res.put("ORIENTATION_t", new JSONArray(ORIENTATION_t));
            res.put("ORIENTATION_X", new JSONArray(ORIENTATION_X));
            res.put("ORIENTATION_Y", new JSONArray(ORIENTATION_Y));
            res.put("ORIENTATION_Z", new JSONArray(ORIENTATION_Z));

            res.put("GYROSCOPE_t", new JSONArray(GYROSCOPE_t));
            res.put("GYROSCOPE_X", new JSONArray(GYROSCOPE_X));
            res.put("GYROSCOPE_Y", new JSONArray(GYROSCOPE_Y));
            res.put("GYROSCOPE_Z", new JSONArray(GYROSCOPE_Z));

        } catch(JSONException e) {

            Log.e("UH OH", "UH OH");

        }


        return res;

    }

}
