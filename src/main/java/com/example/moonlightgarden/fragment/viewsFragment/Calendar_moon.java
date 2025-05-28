package com.example.moonlightgarden.fragment.viewsFragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.moonlightgarden.API.WeatherApiService;
import com.example.moonlightgarden.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Calendar_moon extends Fragment {

    private TextView moonPhaseText;
    private ImageView imageViewMoonPhase;
    private TextView selectDateText;
    private Calendar selectedDate;

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
        imageViewMoonPhase = view.findViewById(R.id.imageViewMoonPhase);
        selectDateText = view.findViewById(R.id.selectDateText);

        // Inicializar la fecha seleccionada con la fecha actual
        selectedDate = Calendar.getInstance();
        updateSelectedDateText();

        // Configurar el clic en el texto para seleccionar una fecha
        selectDateText.setOnClickListener(v -> showDatePickerDialog());

        // Obtener la fase lunar para la fecha actual
        fetchMoonPhase();

        return view;
    }

    /**
     * Muestra un DatePickerDialog para seleccionar una fecha.
     */
    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                (DatePicker view, int year, int month, int dayOfMonth) -> {
                    // Actualizar la fecha seleccionada
                    selectedDate.set(year, month, dayOfMonth);
                    updateSelectedDateText();
                    fetchMoonPhase(); // Actualizar la fase lunar para la nueva fecha
                },
                selectedDate.get(Calendar.YEAR),
                selectedDate.get(Calendar.MONTH),
                selectedDate.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    /**
     * Actualiza el texto que muestra la fecha seleccionada.
     */
    private void updateSelectedDateText() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        selectDateText.setText("Fecha seleccionada: " + dateFormat.format(selectedDate.getTime()));
    }

    /**
     * Obtiene la fase lunar para la fecha seleccionada.
     */
    private void fetchMoonPhase() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.weatherapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherApiService service = retrofit.create(WeatherApiService.class);

        String apiKey = "6fb0876a5d8144f6b30100754252205";
        String location = "España";
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(selectedDate.getTime());

        Call<AstronomyResponse> call = service.getAstronomy(apiKey, location, date);

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<AstronomyResponse> call, @NonNull Response<AstronomyResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String moonPhase = response.body().astronomy.astro.moon_phase;
                    moonPhaseText.setText("Fase Lunar: " + moonPhase);
                    setMoonImage(moonPhase);
                } else {
                    moonPhaseText.setText("Error obteniendo datos");
                }
            }

            @Override
            public void onFailure(@NonNull Call<AstronomyResponse> call, @NonNull Throwable t) {
                moonPhaseText.setText("Fallo de conexión");
                imageViewMoonPhase.setImageResource(R.drawable.ic_moon); // Imagen por defecto
            }
        });
    }

    /**
     * Actualiza la imagen de la fase lunar según la fase.
     *
     * @param phase Fase lunar.
     */
    private void setMoonImage(String phase) {
        int resId;
        switch (phase.toLowerCase(Locale.ROOT)) {
            case "new moon":
                resId = R.drawable.new_moon;
                break;
            case "waxing crescent":
                resId = R.drawable.crecient_gibos_moon;
                break;
            case "first quarter":
                resId = R.drawable.first_cuart_moon;
                break;
            case "waxing gibbous":
                resId = R.drawable.waxing_gibbous_moon;
                break;
            case "full moon":
                resId = R.drawable.full_moon;
                break;
            case "waning gibbous":
                resId = R.drawable.menguant_gibos_moon;
                break;
            case "last quarter":
                resId = R.drawable.last_cuart_moon;
                break;
            case "waning crescent":
                resId = R.drawable.crecient_moon;
                break;
            default:
                resId = R.drawable.ic_moon; // Imagen por defecto
                break;
        }

        imageViewMoonPhase.setImageResource(resId);
    }
}