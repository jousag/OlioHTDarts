package com.example.oliohtdarts;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.util.Map;

public class CheckoutData {

    ObjectMapper objectMapper = new ObjectMapper(); // -//-
    private Context context;
    private RequestQueue requestQueue;

    public interface CheckoutCallback {
        void onResult(String checkout);
        void onError(Exception e);
    }
    public CheckoutData(Context context, RequestQueue requestQueue) {
        this.context = context;
        this.requestQueue = com.android.volley.toolbox.Volley.newRequestQueue(context);
    }

    public void fetchCheckoutData(int score, CheckoutCallback callback) {
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET,
                "https://mocki.io/v1/b33ccff8-afc8-4185-a1dd-37484cc1f180",
                null, response -> handleCheckoutResponse(response, score, callback),
                error -> error.printStackTrace()
        );
        requestQueue.add(jsonRequest);
    }
    private void handleCheckoutResponse(JSONObject response, int score, CheckoutCallback callback) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> checkoutMap = objectMapper.readValue(response.toString(),
                    new TypeReference<Map<String, String>>() {
            });


            String checkout = checkoutMap.get(String.valueOf(score));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
