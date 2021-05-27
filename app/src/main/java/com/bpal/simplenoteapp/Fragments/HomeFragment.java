package com.bpal.simplenoteapp.Fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bpal.simplenoteapp.Adapter.NotesAdapter;
import com.bpal.simplenoteapp.Database.Constant;
import com.bpal.simplenoteapp.Database.DBHelper;
import com.bpal.simplenoteapp.Database.DBNotes;
import com.bpal.simplenoteapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    NotesAdapter adapter;
    DBHelper dbHelper;
    ArrayList<DBNotes> list;
    FloatingActionButton floatingActionButton;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.rv_notes);
        floatingActionButton = view.findViewById(R.id.floatingActionButton);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new AddNoteFragment());
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        list = new ArrayList<>();

        dbHelper = new DBHelper(getContext());
        list = dbHelper.getNotes(Constant.email);
        adapter = new NotesAdapter(getContext(), list);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

    }

    private boolean loadFragment(Fragment fragment) {
        if ( fragment != null) {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

}