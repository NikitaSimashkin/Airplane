package com.example.airplane.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.airplane.Activities.MainActivity;
import com.example.airplane.Player;
import com.example.airplane.R;

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

        if (current_item == 0){
            note.in.setBackgroundColor(context.getResources().getColor(R.color.in_table_first, null));
            note.out.setBackgroundColor(context.getResources().getColor(R.color.out_table_first, null));
        }

        note.num = current_item+1;
        note.number.setText(Integer.toString(current_item+1));
        note.name.setText(names.get(current_item));
        note.points.setText(Integer.toString(points.get(current_item)));
        current_item++;

        return note;
    }

    @Override
    public void onBindViewHolder(@NonNull Note holder, int position) {
        holder.num = position+1;
        if (holder.num == 1) {
            holder.in.setBackgroundColor(context.getResources().getColor(R.color.in_table_first, null));
            holder.out.setBackgroundColor(context.getResources().getColor(R.color.out_table_first, null));
        } else {
            holder.in.setBackgroundColor(context.getResources().getColor(R.color.in_table, null));
            holder.out.setBackgroundColor(context.getResources().getColor(R.color.out_table, null));
        }

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
        LinearLayout in, out;
        TextView number, name, points;
        int num;

        public Note(@NonNull View itemView) {
            super(itemView);
            layout_base = itemView.findViewById(R.id.layout_base);
            number = itemView.findViewById(R.id.number_rate_table);
            name = itemView.findViewById(R.id.name_rate_table);
            points = itemView.findViewById(R.id.points_rate_table);
            in = itemView.findViewById(R.id.in);
            out = itemView.findViewById(R.id.out);
        }
    }
}
