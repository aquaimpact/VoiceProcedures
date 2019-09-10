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

public class RecyclerViewAdapterSubchapt extends RecyclerView.Adapter<RecyclerViewAdapterSubchapt.ViewHolder>{

    Context context;
    List<Subchapt> subchapts = new ArrayList<>();
    
    private RecyclerViewAdapterSubchapt.onsSubChaptClickListener mOnSubChaptClickListener;

    public RecyclerViewAdapterSubchapt(Context context, List<Subchapt> subchapts1, RecyclerViewAdapterSubchapt.onsSubChaptClickListener mOnSubChaptClickListener){
        this.context = context;
        this.subchapts = subchapts1;
        this.mOnSubChaptClickListener = mOnSubChaptClickListener;
    }

    @Override
    public RecyclerViewAdapterSubchapt.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items_recycler_chapt, parent, false);
        return new RecyclerViewAdapterSubchapt.ViewHolder(v, mOnSubChaptClickListener);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapterSubchapt.ViewHolder holder, int position) {

        holder.CHAPTERNAME.setText(subchapts.get(position).subchaptname);
        holder.COMMTYPE.setText(subchapts.get(position).chaptlinked);
        holder.Numberofsubs.setText(subchapts.get(position).noOfSects);
        holder.CHAPTERNAMEHEADER.setText("Chapter Linked To:");
        holder.SUBCHAPTERNAMEHEADER.setText("Sub-chapter Name:");
        holder.NUMBERSECT.setText("Number of Sections");
//
//        System.out.println("##################");
    }

    @Override
    public int getItemCount() {
        return subchapts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView CHAPTERNAME, COMMTYPE, Numberofsubs, SUBCHAPTERNAMEHEADER, CHAPTERNAMEHEADER, NUMBERSECT;
        RecyclerViewAdapterSubchapt.onsSubChaptClickListener onSubChaptClickListener;

        public ViewHolder(@NonNull View itemView, RecyclerViewAdapterSubchapt.onsSubChaptClickListener onSubChaptClickListener) {
            super(itemView);

            CHAPTERNAME = itemView.findViewById(R.id.CHAPTNAMEPLACEHOLDER);
            COMMTYPE = itemView.findViewById(R.id.COMMTYPEPLACEHOLDER);
            Numberofsubs = itemView.findViewById(R.id.NUMBEROFSUBS);

            SUBCHAPTERNAMEHEADER = itemView.findViewById(R.id.CHAPTERNAME);
            CHAPTERNAMEHEADER = itemView.findViewById(R.id.COMMTYPE);
            NUMBERSECT = itemView.findViewById(R.id.nosub);

            this.onSubChaptClickListener = onSubChaptClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onSubChaptClickListener.onSubChaptClick(getAdapterPosition());
        }
    }

    public interface onsSubChaptClickListener{
        void onSubChaptClick(int position);
    }
}
