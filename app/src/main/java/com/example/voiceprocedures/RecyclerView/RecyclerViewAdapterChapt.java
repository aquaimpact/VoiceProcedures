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
    private onChaptClickListener mOnChaptClickListener;

    public RecyclerViewAdapterChapt(Context context, List<Chapters> chapts1, onChaptClickListener mOnChaptClickListener){
        this.context = context;
        this.chapts = chapts1;
        this.mOnChaptClickListener = mOnChaptClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items_recycler_chapt, parent, false);
        return new ViewHolder(v, mOnChaptClickListener);
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView CHAPTERNAME, COMMTYPE, Numberofsubs;
        onChaptClickListener onChaptClickListener;

        public ViewHolder(@NonNull View itemView, onChaptClickListener onChaptClickListener) {
            super(itemView);

            CHAPTERNAME = itemView.findViewById(R.id.CHAPTNAMEPLACEHOLDER);
            COMMTYPE = itemView.findViewById(R.id.COMMTYPEPLACEHOLDER);
            Numberofsubs = itemView.findViewById(R.id.NUMBEROFSUBS);
            this.onChaptClickListener = onChaptClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onChaptClickListener.onChaptClick(getAdapterPosition());
        }
    }

    public interface onChaptClickListener{
        void onChaptClick(int position);
    }
}
