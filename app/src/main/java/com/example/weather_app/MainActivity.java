package com.example.weather_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.hardware.input.InputManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;

import com.example.weather_app.Models.MausamData;
import com.example.weather_app.Models.main;
import com.example.weather_app.Models.weather;
import com.example.weather_app.databinding.ActivityMainBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        constraintLayout = findViewById(R.id.constraintLayout);

        SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy");
        String current_date = format.format(new Date());


        binding.date.setText(current_date);

        fetchWeather("Vadodara");

        binding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide_keyboard();
                if (TextUtils.isEmpty(binding.searchCity.getText().toString())){

                    binding.searchCity.setError("Please Enter City ");
                    return;
                }
                String CITY_NAME = binding.searchCity.getText().toString();
                fetchWeather (CITY_NAME);
            }
        });
    }
    void hide_keyboard(){
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(binding.constraintLayout.getApplicationWindowToken(),0);
    }
    void fetchWeather(String city_name){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        InterfaceApi interfaceApi = retrofit.create(InterfaceApi.class);

        Call<MausamData> call = interfaceApi.getData(city_name,"f5fe2ae10a1e8ef68b994f8da605e0ab","metric");

        call.enqueue(new Callback<MausamData>() {
            @Override
            public void onResponse(Call<MausamData> call, Response<MausamData> response) {

                if(response.isSuccessful()){

                    MausamData mausamData = response.body();

                    main to = mausamData.getMain();

                    binding.temprature.setText(String.valueOf(to.getTemp()) + "\u2103");
                    binding.tempratureMax.setText(String.valueOf(to.getTemp_max()));
                    binding.tempratureMin.setText(String.valueOf(to.getTemp_min()));
                    binding.tempraturePressure.setText(String.valueOf(to.getPressure()));
                    binding.tempratureHumidity.setText(String.valueOf(to.getHumidity()));
                    binding.cityName.setText(mausamData.getName());

                    List<weather> description = mausamData.getWeather();
                    for (weather data:description) {
                        binding.tempratureName.setText(data.getDescription());
                    }
                }
            }

            @Override
            public void onFailure(Call<MausamData> call, Throwable t) {

            }
        });



    }
}