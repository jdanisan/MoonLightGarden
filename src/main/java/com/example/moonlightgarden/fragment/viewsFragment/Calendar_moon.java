package com.example.moonlightgarden.fragment.viewsFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.moonlightgarden.API.WeatherApiService;
import com.example.moonlightgarden.R;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Calendar_moon extends Fragment {

    private TextView moonPhaseText;

    public Calendar_moon() {
        // Constructor vacío
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calendar_moon, container, false);
        moonPhaseText = view.findViewById(R.id.moonPhaseText);

        fetchMoonPhase();

        return view;
    }

    private void fetchMoonPhase() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.weatherapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherApiService service = retrofit.create(WeatherApiService.class);

        String apiKey = "6fb0876a5d8144f6b30100754252205";
        String location = "Mexico";
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        Call<AstronomyResponse> call = service.getAstronomy(apiKey, location, date);

        call.enqueue(new Callback<>() {
            /**
             * Invoked for a received HTTP response.
             *
             * <p>Note: An HTTP response may still indicate an application-level failure such as a 404 or 500.
             * Call {@link Response#isSuccessful()} to determine if the response indicates success.
             *
             * @param call
             * @param response
             */
            @Override
            public void onResponse(@NonNull Call<AstronomyResponse> call, @NonNull Response<AstronomyResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String moonPhase = response.body().astronomy.astro.moon_phase;
                    moonPhaseText.setText("Fase Lunar: " + moonPhase);
                } else {
                    moonPhaseText.setText("Error obteniendo datos");
                }
            }

            /**
             * Invoked when a network exception occurred talking to the server or when an unexpected exception
             * occurred creating the request or processing the response.
             *
             * @param call
             * @param t
             */
            @Override
            public void onFailure(@NonNull Call<AstronomyResponse> call, @NonNull Throwable t) {
                moonPhaseText.setText("Fallo de conexión");
            }




        });
    }
}

