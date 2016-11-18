package com.rohan.temp;

import retrofit.RestAdapter;

/**
 * Created by Rohan on 25-Oct-16.
 */

public class RetrofitAdapter {

    private APIService apiService;

    public RetrofitAdapter(String baseURL) {

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(baseURL)
                .build();

        apiService = restAdapter.create(APIService.class);

    }

    public APIService getApiService() {
        return apiService;
    }
}
