package com.bpal.simplenoteapp.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bpal.simplenoteapp.Database.Constant;
import com.bpal.simplenoteapp.Database.DBHelper;
import com.bpal.simplenoteapp.Database.DBNotes;
import com.bpal.simplenoteapp.R;

public class AddNoteFragment extends Fragment {

    EditText edt_title, edt_dec;
    Button add;
    DBHelper dbHelper;
    String time = String.valueOf(System.currentTimeMillis());

    public AddNoteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_note, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edt_title = view.findViewById(R.id.id_title);
        edt_dec = view.findViewById(R.id.id_desc);
        add = view.findViewById(R.id.btn_save);

        dbHelper = new DBHelper(getActivity());

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Title = edt_title.getText().toString().trim();
                String Desc = edt_dec.getText().toString().trim();

                if (Title.isEmpty()) {
                    Toast.makeText(getContext(), "Enter title!", Toast.LENGTH_SHORT).show();
                    return ;
                }
                if (Desc.isEmpty()) {
                    Toast.makeText(getContext(), "Enter Note!", Toast.LENGTH_SHORT).show();
                    return ;
                }

                DBNotes notes = new DBNotes(Title, Desc, time, Constant.email);

                dbHelper.addNotes(notes);
                Toast.makeText(getContext(), "Note added Successfully.", Toast.LENGTH_SHORT).show();
                loadFragment(new HomeFragment());

            }
        });


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