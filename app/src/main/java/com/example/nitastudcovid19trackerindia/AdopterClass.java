package com.example.nitastudcovid19trackerindia;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class AdopterClass extends RecyclerView.Adapter<AdopterClass.AdopterViewHolder> {

    private Context mContext;
    private ArrayList<AdopterItems> adopterItems;


    public AdopterClass(Context context, ArrayList<AdopterItems> items) {
        mContext = context;
        adopterItems = items;
    }

    @NonNull
    @Override
    public AdopterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.items_adopter, parent, false);
        return new AdopterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdopterViewHolder holder, int position) {
        AdopterItems madopterItems=adopterItems.get(position);
        String data=madopterItems.getState();
        String active=madopterItems.getActive();
        holder.mstate.setText(data);
        holder.inract.setText(madopterItems.getaChange());
        holder.inrcnf.setText(madopterItems.gettChange());
        holder.inrrec.setText(madopterItems.getrChange());
        holder.inrdec.setText(madopterItems.getdChange());
        holder.act.setText(madopterItems.getActive());
        holder.rec.setText(madopterItems.getRecover());
        holder.cnf.setText(madopterItems.getTotal());
        holder.dec.setText(madopterItems.getDeath());
        holder.mline.setBackgroundColor(Color.parseColor(madopterItems.getaLColors()));
        holder.cardView.setCardBackgroundColor(Color.parseColor(madopterItems.getaColors()));

    }

    @Override
    public int getItemCount() {
        return adopterItems.size();
    }

    public class AdopterViewHolder extends RecyclerView.ViewHolder{

        public TextView mstate,inrcnf,cnf,inract,act,inrrec,rec,inrdec,dec;
        public View mline;
        public CardView cardView;
        public AdopterViewHolder(@NonNull View itemView) {
            super(itemView);
            mstate=itemView.findViewById(R.id.states);
            mline=itemView.findViewById(R.id.view);
            cardView=itemView.findViewById(R.id.card_view);
            inrcnf=itemView.findViewById(R.id.confirmed2);
            inract=itemView.findViewById(R.id.recover2);
            inrrec=itemView.findViewById(R.id.active2);
            inrdec=itemView.findViewById(R.id.death2);
            cnf=itemView.findViewById(R.id.confirmed1);
            act=itemView.findViewById(R.id.active1);
            rec=itemView.findViewById(R.id.recover1);
            dec=itemView.findViewById(R.id.death1);
        }
    }

}
