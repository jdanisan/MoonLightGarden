package com.example.moonlightgarden.fragment.viewsFragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.moonlightgarden.R;
import com.example.moonlightgarden.models.foodItem;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class statistics_fragment extends Fragment {

    // Gráfico de líneas
    private LineChart lineChart;
    // Vista de carga (loading_dialog.xml incluida en el layout con <include>)
    private View loadingView;
    // Cola de peticiones para Volley
    private RequestQueue requestQueue;
    // Botón de búsqueda
    private Button btnBuscar;
    // Contenedor de tarjetas de productos
    private LinearLayout scrollContent;
    // Texto para mostrar la media de precios
    private TextView tvMediaValor;
    // Control de visibilidad del foro
    private boolean foroVisible = false;
    // Selector de producto
    private Spinner spinnerProducto;

    // Referencia a la base de datos Firebase
    private DatabaseReference databaseRef;

    // Producto seleccionado por defecto
    private String productoFiltro = "patata";
    // URL de la API externa
    private static final String API_URL = "https://api-precios-productos.onrender.com/api/precios";

    public statistics_fragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflamos el layout XML del fragmento
        return inflater.inflate(R.layout.statistics_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicializamos las vistas
        spinnerProducto = view.findViewById(R.id.spinnerProducto);
        btnBuscar = view.findViewById(R.id.btnBuscar);
        lineChart = view.findViewById(R.id.lineChart);
        loadingView = view.findViewById(R.id.loadingView); // referencia al include
        scrollContent = view.findViewById(R.id.scroll_content);
        tvMediaValor = view.findViewById(R.id.tvMediaValor);
        requestQueue = Volley.newRequestQueue(requireContext());
        databaseRef = FirebaseDatabase.getInstance().getReference("food");

        // Lista de productos disponibles
        String[] productos = {"Patata", "Acelga", "Cebolla", "Zanahoria", "Lechuga_romana", "Manzana_golden", "Pera_agua", "Platano", "Judia_verde", "Limon", "Pimiento_verde", "Calabacin", "Tomate", "Clementina", "Naranja_navel"};
        for (int i = 0; i < productos.length; i++) {
            productos[i] = productos[i].toLowerCase();
        }

        // Botón para abrir/cerrar el foro
        ImageView btnForo = view.findViewById(R.id.btn_forum);
        FrameLayout statContainer = view.findViewById(R.id.stat_container);

        btnForo.setOnClickListener(v -> {
            if (!foroVisible) {
                statContainer.setVisibility(View.VISIBLE);
                getChildFragmentManager()
                        .beginTransaction()
                        .replace(R.id.stat_container, new ForumFragment())
                        .commit();
                foroVisible = true;
            } else {
                statContainer.setVisibility(View.GONE);
                foroVisible = false;
            }
        });

        // Configuramos el spinner con los productos
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, productos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProducto.setAdapter(adapter);

        // Acción del botón buscar
        btnBuscar.setOnClickListener(v -> {
            productoFiltro = spinnerProducto.getSelectedItem().toString();
            // Mostrar loading y ocultar gráfico
            loadingView.setVisibility(View.VISIBLE);
            lineChart.setVisibility(View.GONE);

            // Cargar datos de la API y Firebase
            fetchDataAndPlot();
            fetchProductCard(productoFiltro.toLowerCase());
        });

        // Carga inicial
        loadingView.setVisibility(View.VISIBLE);
        lineChart.setVisibility(View.GONE);
        fetchDataAndPlot();
        fetchProductCard(productoFiltro.toLowerCase());
    }

    // Petición a la API externa
    private void fetchDataAndPlot() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                API_URL,
                null,
                this::parseAndPlotData,
                error -> {
                    // En caso de error de red, ocultamos loading y mostramos un estado sin datos
                    tvMediaValor.setText("No disponible");
                    lineChart.clear();
                    loadingView.setVisibility(View.GONE);
                    lineChart.setVisibility(View.VISIBLE);
                }
        );

        jsonArrayRequest.setRetryPolicy(new com.android.volley.DefaultRetryPolicy(
                10000,
                com.android.volley.DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                com.android.volley.DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        requestQueue.add(jsonArrayRequest);
    }

    // Procesar datos de la API y dibujar el gráfico
    private void parseAndPlotData(JSONArray dataArray) {
        ArrayList<Entry> entriesPrecioP = new ArrayList<>();
        ArrayList<Entry> entriesPrecioM = new ArrayList<>();
        ArrayList<String> semanasLabels = new ArrayList<>();

        float sumaTotal = 0f;
        int contador = 0;

        try {
            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject obj = dataArray.getJSONObject(i);
                String producto = obj.getString("Producto");
                if (!producto.equalsIgnoreCase(productoFiltro)) continue;

                JSONArray preciosArray = obj.getJSONArray("Precios");
                JSONArray semanasArray = obj.getJSONArray("Semanas");

                int totalSemanas = semanasArray.length();
                int startIndex = Math.max(0, totalSemanas - 5);

                for (int j = 0; j < 5; j++) {
                    int semanaIndex = startIndex + j;
                    if (semanaIndex >= totalSemanas) break;

                    semanasLabels.add(semanasArray.getString(semanaIndex));

                    int precioPIndex = semanaIndex * 2;
                    int precioMIndex = precioPIndex + 1;

                    float precio_p = precioPIndex < preciosArray.length() ? (float) preciosArray.getDouble(precioPIndex) : 0f;
                    float precio_m = precioMIndex < preciosArray.length() ? (float) preciosArray.getDouble(precioMIndex) : 0f;

                    entriesPrecioP.add(new Entry(j, precio_p));
                    entriesPrecioM.add(new Entry(j, precio_m));

                    sumaTotal += precio_p + precio_m;
                    contador += 2;
                }
                // Una vez encontrado el producto, salimos del bucle
                break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Calcular y mostrar la media
        if (contador > 0) {
            float media = sumaTotal / contador;
            tvMediaValor.setText(String.format("$%.2f", media));
        } else {
            tvMediaValor.setText("No disponible");
        }

        // Configurar datasets para el gráfico
        LineDataSet dataSetP = new LineDataSet(entriesPrecioP, "Precio P");
        dataSetP.setColor(Color.RED);
        dataSetP.setCircleColor(Color.RED);

        LineDataSet dataSetM = new LineDataSet(entriesPrecioM, "Precio M");
        dataSetM.setColor(Color.BLUE);
        dataSetM.setCircleColor(Color.BLUE);

        LineData lineData = new LineData(dataSetP, dataSetM);
        lineChart.setData(lineData);

        // Configurar eje X con etiquetas de semanas
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(semanasLabels));
        xAxis.setGranularity(1f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelRotationAngle(-45);

        lineChart.invalidate();

        // Ocultar loading y mostrar gráfico
        loadingView.setVisibility(View.GONE);
        lineChart.setVisibility(View.VISIBLE);
    }

    // Cargar tarjeta de producto desde Firebase
    private void fetchProductCard(String searchTerm) {
        // Limpiar tarjetas previas
        scrollContent.removeAllViews();

        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean encontrado = false;
                for (DataSnapshot data : snapshot.getChildren()) {
                    foodItem item = data.getValue(foodItem.class);
                    if (item == null) continue;

                    if (item.getFood_name() != null &&
                            item.getFood_name().toLowerCase().contains(searchTerm)) {

                        View cardView = LayoutInflater.from(getContext())
                                .inflate(R.layout.food_card_item, scrollContent, false);

                        TextView name = cardView.findViewById(R.id.product_name);
                        TextView desc = cardView.findViewById(R.id.product_description);
                        ImageView image = cardView.findViewById(R.id.product_image);

                        name.setText(item.getFood_name());
                        desc.setText(item.getFood_description());
                        Picasso.get().load(item.getfood_image_url()).into(image);

                        // Expandir/contraer descripción al pulsar
                        cardView.setOnClickListener(v -> {
                            if (desc.getMaxLines() == 1) {
                                desc.setMaxLines(Integer.MAX_VALUE);
                            } else {
                                desc.setMaxLines(1);
                            }
                        });

                        scrollContent.addView(cardView);
                        encontrado = true;
                        break; // Mostramos solo una tarjeta
                    }
                }

                // Si no se encontró tarjeta, mostramos un mensaje básico
                if (!encontrado) {
                    TextView empty = new TextView(getContext());
                    empty.setText("No se encontraron tarjetas para ese producto.");
                    scrollContent.addView(empty);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Manejo de error al leer Firebase
                TextView errorText = new TextView(getContext());
                errorText.setText("Error cargando tarjetas: " + error.getMessage());
                scrollContent.addView(errorText);
            }
        });
    }
}
