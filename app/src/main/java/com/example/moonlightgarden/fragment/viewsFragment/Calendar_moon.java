package com.example.moonlightgarden.fragment.viewsFragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.moonlightgarden.API.WeatherApiService;
import com.example.moonlightgarden.R;
import com.example.moonlightgarden.models.foodItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Calendar_moon extends Fragment {

    private TextView moonPhaseText;
    private ImageView imageViewMoonPhase;
    private LinearLayout scrollContent;
    private Button buttonSelectDate;
    private DatabaseReference databaseRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calendar_moon, container, false);

        // Referencias UI
        moonPhaseText = view.findViewById(R.id.moonPhaseText);
        imageViewMoonPhase = view.findViewById(R.id.imageViewMoonPhase);
        scrollContent = view.findViewById(R.id.scroll_content);
        buttonSelectDate = view.findViewById(R.id.button_select_date);

        // Referencia a Firebase
        databaseRef = FirebaseDatabase.getInstance().getReference("food");

        // Llama al API con la fecha actual al iniciar
        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Calendar.getInstance().getTime());
        fetchMoonPhase(today);

        // Botón para mostrar selector de fecha
        buttonSelectDate.setOnClickListener(v -> showDatePicker());

        return view;
    }

    /**
     * Muestra un diálogo de selector de fecha y actualiza la fase lunar al seleccionar.
     */
    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                (view, year, month, dayOfMonth) -> {
                    calendar.set(year, month, dayOfMonth);
                    String selectedDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.getTime());
                    fetchMoonPhase(selectedDate);
                    // Actualiza el CalendarView a la fecha seleccionada
                    CalendarView calendarView = getView().findViewById(R.id.calendarView);
                    if (calendarView != null) {
                        calendarView.setDate(calendar.getTimeInMillis(), true, true);
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis()); // No permite fechas futuras
        datePickerDialog.show();
    }

    /**
     * Llama a la API con la fecha seleccionada y actualiza la UI con los datos recibidos.
     *
     * @param date Fecha seleccionada en formato "yyyy-MM-dd"
     */
    private void fetchMoonPhase(String date) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.weatherapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherApiService service = retrofit.create(WeatherApiService.class);

        String apiKey = "6fb0876a5d8144f6b30100754252205";
        String location = "España";

        Call<AstronomyResponse> call = service.getAstronomy(apiKey, location, date);

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<AstronomyResponse> call, @NonNull Response<AstronomyResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String moonPhase = response.body().astronomy.astro.moon_phase;
                    moonPhaseText.setText("Fase Lunar: " + moonPhase);
                    setMoonImage(moonPhase);
                    populateFoodCards(moonPhase.toLowerCase());
                } else {
                    Toast.makeText(getContext(), "Datos no disponibles para esta fecha", Toast.LENGTH_SHORT).show();
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
     * Muestra las tarjetas de alimentos relacionadas con la fase lunar actual.
     */
    private void populateFoodCards(String moonPhase) {
        scrollContent.removeAllViews(); // Limpiar vistas anteriores

        databaseRef.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean found = false;

                for (DataSnapshot data : snapshot.getChildren()) {
                    foodItem item = data.getValue(foodItem.class);
                    if (item == null) continue;

                    String description = item.getFood_description().toLowerCase();
                    if (matchesMoonPhase(description, moonPhase)) {
                        found = true;

                        View cardView = LayoutInflater.from(getContext())
                                .inflate(R.layout.food_card_item, scrollContent, false);

                        TextView name = cardView.findViewById(R.id.product_name);
                        TextView desc = cardView.findViewById(R.id.product_description);
                        ImageView image = cardView.findViewById(R.id.product_image);

                        name.setText(item.getFood_name());
                        desc.setText(item.getFood_description());
                        Picasso.get().load(item.getfood_image_url()).into(image);

                        // Expandir/cerrar descripción al hacer clic
                        cardView.setOnClickListener(v -> {
                            if (desc.getMaxLines() == 1) {
                                desc.setMaxLines(Integer.MAX_VALUE);
                            } else {
                                desc.setMaxLines(1);
                            }
                        });

                        scrollContent.addView(cardView);
                    }
                }

                if (!found) {
                    Toast.makeText(getContext(), "No hay alimentos para esta fase lunar", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error al acceder a la base de datos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Compara la descripción del alimento con la fase lunar para decidir si mostrarlo.
     */
    private boolean matchesMoonPhase(String description, String moonPhase) {
        switch (moonPhase) {
            case "new moon":
                return description.contains("luna nueva");
            case "waxing crescent":
                return description.contains("luna creciente");
            case "first quarter":
                return description.contains("primer cuarto");
            case "waxing gibbous":
                return description.contains("luna gibosa creciente");
            case "full moon":
                return description.contains("luna llena");
            case "waning gibbous":
                return description.contains("luna gibosa menguante");
            case "last quarter":
                return description.contains("cuarto menguante");
            case "waning crescent":
                return description.contains("luna menguante");
            default:
                return false;
        }
    }

    /**
     * Muestra la imagen correspondiente a la fase lunar.
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
                resId = R.drawable.ic_moon;
                break;
        }

        imageViewMoonPhase.setImageResource(resId);
    }
}
