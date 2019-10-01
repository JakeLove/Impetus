package io.qepl.impetus;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

class SensorLog {

    private HashMap<String, MultiVariateTimeSeries> sensorData = new HashMap<>();


    SensorLog(ArrayList<String> sensorsToUseNames){

        for (String sensorName: sensorsToUseNames) {


            sensorData.put(sensorName, new MultiVariateTimeSeries());

        }

    }


    void update(SensorEvent sensorEvent) {

        long t = sensorEvent.timestamp;

        MultiVariateTimeSeries data = sensorData.get(sensorEvent.sensor.getStringType());

        data.update(t, sensorEvent.values.clone());

    }


    JSONObject getJSON() {

        JSONObject result = new JSONObject();

        try {

            for (String sensor_name: sensorData.keySet()) {

                MultiVariateTimeSeries data = sensorData.get(sensor_name);

                JSONObject sensorJSON = new JSONObject();

                sensorJSON.put("domain", new JSONArray(data.domain));
                sensorJSON.put("values", new JSONArray(data.values));

                result.put(sensor_name, sensorJSON);

            }

        } catch (JSONException e) {

            return result;

        }

        return result;

    }

}
