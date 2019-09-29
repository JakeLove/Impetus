package io.qepl.impetus;

import android.content.Context;
import android.hardware.SensorEvent;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


class Mailman {

    private RequestQueue requestQueue;

    Mailman (Context context) {

        Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());

        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();

    }

    public void deliver(ArrayList<SensorEvent> sensorEvents, String entry, String url) {

        JSONObject parcel = new JSONObject();

        try {

            JSONArray sensorLog = new JSONArray();

            for (SensorEvent sensorEvent: sensorEvents) {

                JSONObject eventData = new JSONObject();
                eventData.put("t", sensorEvent.timestamp);
                eventData.put("sensor", sensorEvent.sensor.getStringType());
                eventData.put("values", new JSONArray(sensorEvent.values));

                sensorLog.put(eventData);
            }

            parcel.put("entry", entry);
            parcel.put("sensors", sensorLog);

        } catch (JSONException e) {
            Log.e("ERROR", "uhoh");
        }


        requestQueue.add(post(parcel, url));

    }

    private StringRequest post(JSONObject JSONbody, String url) {
        final String requestBody = JSONbody.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,

            new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {

                    Log.i("Mailman", response);

                }

            }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("Mailman", error.toString());

            }
        }) {

            @Override
            public String getBodyContentType() {

                return "application/json; charset=utf-8";

            }

            @Override
            public byte[] getBody() throws AuthFailureError {

                return requestBody.getBytes(StandardCharsets.UTF_8);

            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {

                String responseString = "";

                if (response != null) {
                    responseString = String.valueOf(response.statusCode);
                }

                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));

            }
        };

        return stringRequest;

    }

}
