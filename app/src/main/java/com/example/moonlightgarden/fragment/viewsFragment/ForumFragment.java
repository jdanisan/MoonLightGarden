package com.example.moonlightgarden.fragment.viewsFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moonlightgarden.R;
import com.example.moonlightgarden.adapter.TopicAdapter;
import com.example.moonlightgarden.models.Topic;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ForumFragment extends Fragment {

    public ForumFragment() {}

    private RecyclerView rvTopics;
    private TopicAdapter adapter;
    private DatabaseReference topicsRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.forum_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvTopics = view.findViewById(R.id.rvTopics);
        rvTopics.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Inicializamos el adapter con lista vacía
        adapter = new TopicAdapter(new ArrayList<>(), topic -> {
            TopicThreadsFragment frag = TopicThreadsFragment.newInstance(topic.getId(), topic.getTitle());
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, frag)   // usa el mismo contenedor que MainActivity
                    .commit();   //  sin addToBackStack

        });
        rvTopics.setAdapter(adapter);

        // Referencia a Firebase
        topicsRef = FirebaseDatabase.getInstance().getReference("topics");

        // Escuchar cambios en la base de datos
        topicsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Topic> topics = new ArrayList<>();
                for (DataSnapshot topicSnap : snapshot.getChildren()) {
                    Topic topic = topicSnap.getValue(Topic.class);
                    if (topic != null) {
                        topics.add(topic);
                    }
                }
                adapter.setTopics(topics); // Método que actualiza la lista en el adapter
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("ForumFragment", "Error al leer temas", error.toException());
            }
        });
    }
}
