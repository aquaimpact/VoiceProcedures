package com.example.voiceprocedures.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.voiceprocedures.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapterChapt extends RecyclerView.Adapter<RecyclerViewAdapterChapt.ViewHolder> {

    Context context;
    List<Chapters> chapts = new ArrayList<>();

    public RecyclerViewAdapterChapt(Context context, List<Chapters> chapts1){
        this.context = context;
        this.chapts = chapts1;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items_recycler_chapt, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.CHAPTERNAME.setText(chapts.get(position).chaptname);
        if(chapts.get(position).comtype.equals("0")){
            holder.COMMTYPE.setText("External Communications");
        }else if(chapts.get(position).comtype.equals("1")){
            holder.COMMTYPE.setText("On-board Communications");
        }
        holder.Numberofsubs.setText(chapts.get(position).numberOfSubchapter);
//
//        System.out.println("##################");
    }

    @Override
    public int getItemCount() {
        return chapts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView CHAPTERNAME, COMMTYPE, Numberofsubs;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            CHAPTERNAME = itemView.findViewById(R.id.CHAPTNAMEPLACEHOLDER);
            COMMTYPE = itemView.findViewById(R.id.COMMTYPEPLACEHOLDER);
            Numberofsubs = itemView.findViewById(R.id.NUMBEROFSUBS);
        }
    }
}
