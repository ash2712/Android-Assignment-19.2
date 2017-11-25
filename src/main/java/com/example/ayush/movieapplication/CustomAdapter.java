package com.example.ayush.movieapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 11/15/2017.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private ArrayList<Data> arrayList;
    private Context context;

    //declaring list and context
    //parametrized constructor with context and arraylist as parameters
    public CustomAdapter(Context context,ArrayList<Data> list) {
        super();
        this.arrayList = list;
        this.context = context;

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
        //setting this view to viewholder
        ViewHolder viewholder = new ViewHolder(view);
        //returning view holder
        return viewholder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv_name.setText(arrayList.get(position).getName());
        holder.tv_rating.setText("Rating: " + arrayList.get(position).getRating());
        holder.tv_id.setText("ID: " + arrayList.get(position).getId());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_name;
        public TextView tv_rating;
        public TextView tv_id;
        public ViewHolder(View itemView) {

            super(itemView);
            //associating cardlayout and Textview from row.XML
            tv_name = (TextView) itemView.findViewById(R.id.textView);
            tv_rating = (TextView) itemView.findViewById(R.id.textView2);
            tv_id = (TextView) itemView.findViewById(R.id.textView3);
        }


    }
}
