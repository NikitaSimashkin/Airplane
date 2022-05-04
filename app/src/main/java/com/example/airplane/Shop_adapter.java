package com.example.airplane;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.text.BoringLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Shop_adapter extends RecyclerView.Adapter<Shop_adapter.Item>{

    private int number_items, current_item = 0;
    private int bullet, ship, base;

    private ArrayList<Bitmap> pictures = new ArrayList<>();
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> sp_names = new ArrayList<>();

    private SharedPreferences pref;

    private Context context;
    private Handler handler;

    private int[] colors; // хранит цвета
    private int[] closed = new int[3];

    private boolean flag = true;

    public int[] get_colors(){
        return colors;
    }

    public ArrayList<String> get_names(){
        return names;
    }

    public ArrayList<Bitmap> get_pictures(){
        return pictures;
    }

    public void set_handler(Handler handler){
        this.handler = handler;
    }

    public Shop_adapter(Context context) {
        this.context = context;
        this.handler = handler;

        pictures.add(ImageResource.BULLET_BLUE.getBitmap(context));
        names.add("Bullet_blue");
        sp_names.add("default_bullet");

        pictures.add(ImageResource.BULLET_GREEN.getBitmap(context));
        names.add("Bullet_green");
        sp_names.add("cool_ship");

        pictures.add(ImageResource.BULLET_YELLOW.getBitmap(context));
        names.add("Bullet_yellow");
        sp_names.add("enemy_ship");
        closed[0] = 2;

        bullet = 3;

        pictures.add(ImageResource.BULLET_RED.getBitmap(context));
        names.add("Bullet_red");
        sp_names.add("default_ship");

        pictures.add(ImageResource.MEGABULLET.getBitmap(context));
        names.add("Megabullet");
        sp_names.add("cool_bullet");

        pictures.add(ImageResource.ENEMY_MEGABULLET.getBitmap(context));
        names.add("Enemy_Megabullet");
        sp_names.add("enemy_bullet");
        closed[1] = 5;

        ship = bullet + 3;

        pictures.add(ImageResource.BULLET_RED.getBitmap(context));
        names.add("Bullet_red");
        sp_names.add("default_base");

        pictures.add(ImageResource.MEGABULLET.getBitmap(context));
        names.add("Megabullet");
        sp_names.add("cool_base");

        pictures.add(ImageResource.ENEMY_MEGABULLET.getBitmap(context));
        names.add("Enemy_Megabullet");
        sp_names.add("enemy_base");
        closed[2] = 8;

        base = ship + 3;

        pref = context.getSharedPreferences("Main", Context.MODE_PRIVATE);
        boolean level10 = pref.getBoolean("level_10", false);

        if (!level10){
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.lock);
            String text = context.getResources().getString(R.string.closed);
            for (int i = 0; i < closed.length; i++){
                pictures.set(closed[i], bitmap);
                names.set(closed[i], text);
            }
        }

        int size = pictures.size();
        number_items = size;
        colors = new int[size];

        String
                ship_selected = pref.getString("ship", ""),
                base_selected = pref.getString("base", ""),
                bullet_selected = pref.getString("bullet", "");

        for (int i = 0; i < size; i++){
            String current = sp_names.get(i);
            if (!level10 && (closed[0] == i || closed[1] == i || closed[2] == i))
                colors[i] = R.color.gray;
            else if (ship_selected.equals(current) || base_selected.equals(current) || bullet_selected.equals(current))
                colors[i] = R.color.shop_selected;
            else
                colors[i] = R.color.shop_not_selected;

        }
    }

    @NonNull
    @Override
    public Item onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item, parent, false);
        Item item = new Item(view);

        ViewGroup.LayoutParams params = item.layout.getLayoutParams();
        if ((current_item == bullet || current_item == ship || current_item == base)) {
            params.width = 4 * parent.getWidth() / 10;

            ViewGroup.LayoutParams break_params = item.break_.getLayoutParams();
            break_params.width = 1 * parent.getWidth() / 10;
            item.break_.setLayoutParams(break_params);

            item.break_.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.barrier));
        } else {
            params.width = 3 * parent.getWidth() / 10;
        }
        item.layout.setLayoutParams(params);
        item.item_picture.setImageBitmap(pictures.get(current_item));
        item.name.setText(names.get(current_item));
        item.id = current_item;

        current_item++;
            item.skin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (closed[0] == item.id || closed[1] == item.id || closed[2] == item.id){
                        item.closed = true;
                    } else {
                        item.closed = false;
                    }
                    if (!item.closed) {
                        int one, count;
                        String type;
                        if (item.id < bullet) {
                            one = 0;
                            count = bullet;
                            type = "bullet";
                        } else if (item.id < ship) {
                            one = bullet;
                            count = ship - bullet;
                            type = "ship";
                        } else {
                            one = ship;
                            count = base - ship;
                            type = "base";
                        }
                        for (int i = one; i < count + one; i++) {
                            boolean temp = !(closed[0] == i || closed[1] == i || closed[2] == i);
                            if (temp)
                                colors[i] = R.color.shop_not_selected;
                        }
                        item.set_color(R.color.shop_selected);
                        pref.edit().putString(type, sp_names.get(item.id)).apply();
                        colors[item.id] = R.color.shop_selected;
                        int last = current_item;
                        current_item = one;
                        handler.sendMessage(Message.obtain(handler, 0, one, count));
                        current_item = last;
                    }
                }
            });

        return item;
    }

    private boolean first_update = false;
    @Override
    public void onBindViewHolder(@NonNull Item holder, int position) {
        holder.id = holder.getAdapterPosition();
        holder.bind(pictures.get(position), names.get(position), sp_names.get(position));
        holder.set_color(colors[holder.id]);
    }

    @Override
    public int getItemCount() {
        return number_items;
    }

    class Item extends RecyclerView.ViewHolder{
        LinearLayout layout, skin;
        ImageView item_picture, break_;
        TextView name;
        int id, color;
        String shared_preference_name;
        boolean closed = false;

        public Item(@NonNull View itemView) {
            super(itemView);
            item_picture = itemView.findViewById(R.id.item_picture);
            name = itemView.findViewById(R.id.name_s);
            layout = itemView.findViewById(R.id.layout_item);
            break_ = itemView.findViewById(R.id.break_);
            skin = itemView.findViewById(R.id.skin);
        }

        public void bind(Bitmap picture, String name, String shared_preference_name){
            item_picture.setImageBitmap(picture);
            this.name.setText(name);
            this.shared_preference_name = shared_preference_name;
        }

        public void set_color(int color){
            if (this.color != color) {
                skin.setBackgroundColor(context.getResources().getColor(color, null));
                this.color = color;
                colors[id] = color;
            }
        }
    }
}
