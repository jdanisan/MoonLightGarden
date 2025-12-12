package com.example.moonlightgarden.fragment.viewsFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moonlightgarden.R;
import com.example.moonlightgarden.adapter.OpinionAdapter;
import com.example.moonlightgarden.adapter.ThreadAdapter;
import com.example.moonlightgarden.models.ThreadItem;
import com.example.moonlightgarden.models.Opinion;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class TopicThreadsFragment extends Fragment {

    private RecyclerView recyclerThreads;
    private ThreadAdapter adapter;   //usamos ThreadAdapter para hilos
    private ArrayList<ThreadItem> threadList;

    private RecyclerView recyclerOpiniones;
    private OpinionAdapter opinionAdapter;
    private ArrayList<Opinion> opinionList;

    private DatabaseReference dbRef;
    private String topicId;

    public TopicThreadsFragment() {}

    public static TopicThreadsFragment newInstance(String topicId, String topicTitle) {
        TopicThreadsFragment fragment = new TopicThreadsFragment();
        Bundle args = new Bundle();
        args.putString("topicId", topicId);
        args.putString("topicTitle", topicTitle);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_topic_threads, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String title = getArguments().getString("topicTitle");
        topicId = getArguments().getString("topicId");

        TextView tvTitle = view.findViewById(R.id.tvTopicTitle);
        tvTitle.setText(title);

        recyclerThreads = view.findViewById(R.id.recyclerThreads);
        recyclerThreads.setLayoutManager(new LinearLayoutManager(getContext()));

        threadList = new ArrayList<>();
        adapter = new ThreadAdapter(threadList, thread -> {
            Log.d("TopicThreadsFragment", "Click en hilo: " + thread.getTitle());
        });
        recyclerThreads.setAdapter(adapter);

        dbRef = FirebaseDatabase.getInstance()
                .getReference("forum_topics")
                .child(topicId)
                .child("threads");

        // 🔹 Input de opinión
        EditText etOpinion = view.findViewById(R.id.etOpinion);
        Button btnEnviarOpinion = view.findViewById(R.id.btnEnviarOpinion);

        btnEnviarOpinion.setOnClickListener(v -> {
            String texto = etOpinion.getText().toString().trim();
            if (!texto.isEmpty()) {
                // Obtener usuario autenticado
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String cliente = (user != null && user.getDisplayName() != null)
                        ? user.getDisplayName()
                        : "Anónimo";

                long tiempo = System.currentTimeMillis();
                Opinion opinion = new Opinion(cliente, texto, tiempo);

                dbRef.child("opiniones").push().setValue(opinion)
                        .addOnSuccessListener(aVoid -> {
                            Log.d("TopicThreadsFragment", "Opinión guardada");
                            etOpinion.setText("");
                            loadOpiniones(); // refrescar lista
                        })
                        .addOnFailureListener(e -> Log.e("TopicThreadsFragment", "Error al guardar opinión", e));
            }
        });

        // 🔹 Configurar RecyclerView de opiniones
        recyclerOpiniones = view.findViewById(R.id.recyclerOpiniones);
        recyclerOpiniones.setLayoutManager(new LinearLayoutManager(getContext()));
        opinionList = new ArrayList<>();
        opinionAdapter = new OpinionAdapter(opinionList);
        recyclerOpiniones.setAdapter(opinionAdapter);

        // Cargar datos
        loadOpiniones();
        loadThreads();
    }

    private void loadThreads() {
        dbRef.get().addOnSuccessListener(snapshot -> {
            threadList.clear();
            for (DataSnapshot s : snapshot.getChildren()) {
                ThreadItem t = s.getValue(ThreadItem.class);
                if (t != null) threadList.add(t);
            }
            adapter.notifyDataSetChanged();
        }).addOnFailureListener(e -> Log.e("TopicThreadsFragment", "Error al cargar hilos", e));
    }

    private void loadOpiniones() {
        dbRef.child("opiniones").get().addOnSuccessListener(snapshot -> {
            opinionList.clear();
            for (DataSnapshot s : snapshot.getChildren()) {
                Opinion op = s.getValue(Opinion.class);
                if (op != null) opinionList.add(op);
            }
            opinionAdapter.notifyDataSetChanged();
        }).addOnFailureListener(e -> Log.e("TopicThreadsFragment", "Error al cargar opiniones", e));
    }
}
