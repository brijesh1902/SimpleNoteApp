package com.bpal.simplenoteapp.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bpal.simplenoteapp.Database.DBHelper;
import com.bpal.simplenoteapp.Database.DBNotes;
import com.bpal.simplenoteapp.R;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private ArrayList<DBNotes> data;
    private Context context;

    public NotesAdapter(Context c, ArrayList<DBNotes> list ) {
        this.context = c;
        this.data = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.view_note, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DBNotes notes = data.get(position);

        holder.title.setText(notes.getTitle());
        holder.desc.setText(notes.getDesc());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DBHelper(context).deleteNote(notes.getTime());
                notifyItemRemoved(position);
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getRootView().getContext());
                ViewGroup viewGroup = v.findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.editnotes, viewGroup, false);
                alertDialogBuilder.setView(dialogView);
                final AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();

                EditText edt_title, edit_desc;
                Button edit;

                edit = dialogView.findViewById(R.id.btn_edit1);
                edt_title = dialogView.findViewById(R.id.edt_title1);
                edit_desc = dialogView.findViewById(R.id.edit_desc1);

                edt_title.setText(notes.getTitle());
                edit_desc.setText(notes.getDesc());

                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        final String Title = edt_title.getText().toString().trim();
                        final String Desc = edit_desc.getText().toString().trim();

                        if (Title.isEmpty()) {
                            Toast.makeText(context, "Enter title!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (Desc.isEmpty()) {
                            Toast.makeText(context, "Enter Note!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        DBNotes note = new DBNotes(Title, Desc, notes.getTime(), notes.getUser());

                        new DBHelper(context).addNotes(note);
                        Toast.makeText(context, "Note edited Successfully.", Toast.LENGTH_SHORT).show();
                        notifyItemChanged(position);
                        alertDialog.dismiss();

                    }
                });
            }
        });

        DBNotes notes = data.get(position);

        holder.title.setText(notes.getTitle());
        holder.desc.setText(notes.getDesc());

    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, desc;
        Button edit, delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.txt_title);
            desc = itemView.findViewById(R.id.txt_desc);
            edit = itemView.findViewById(R.id.btn_edit);
            delete = itemView.findViewById(R.id.btn_delete);

        }
    }
}
