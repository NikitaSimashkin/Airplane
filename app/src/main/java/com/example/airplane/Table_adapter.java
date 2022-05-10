package com.example.airplane;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Table_adapter extends RecyclerView.Adapter<Table_adapter.Note>{
    private Context context;
    private ArrayList<String> names;
    private ArrayList<Integer> points;

    private int item_count = MainActivity.rate_table_kolvo, current_item = 0;

    public Table_adapter(Context context){
        this.context = context;

        names = new ArrayList<>();
        points = new ArrayList<>();
    }


    @NonNull
    @Override
    public Note onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.note_rate_table, parent, false);
        Note note = new Note(view);

        ViewGroup.LayoutParams params = note.layout_base.getLayoutParams();
        params.height = (int)(2 * parent.getHeight()/9);
        note.layout_base.setLayoutParams(params);

        note.number.setText(Integer.toString(current_item+1));
        note.name.setText(names.get(current_item));
        note.points.setText(Integer.toString(points.get(current_item)));
        current_item++;

        return note;
    }

    @Override
    public void onBindViewHolder(@NonNull Note holder, int position) {
        holder.number.setText(Integer.toString(position+1));
        holder.name.setText(names.get(position));
        holder.points.setText(Integer.toString(points.get(position)));
    }

    @Override
    public int getItemCount() {
        return item_count;
    }

    public void change(Player[] players){
        names.clear();
        points.clear();
        for (int i = 0; i < MainActivity.rate_table_kolvo; i++){
            names.add(players[i].getNickname());
            points.add(players[i].getPoints());
        }
    }

    class Note extends RecyclerView.ViewHolder{

        ConstraintLayout layout_base;
        TextView number, name, points;

        public Note(@NonNull View itemView) {
            super(itemView);
            layout_base = itemView.findViewById(R.id.layout_base);
            number = itemView.findViewById(R.id.number_rate_table);
            name = itemView.findViewById(R.id.name_rate_table);
            points = itemView.findViewById(R.id.points_rate_table);
        }
    }
}
