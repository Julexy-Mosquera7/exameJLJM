package com.example.examejljm;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.exameJLJM.R;

import java.util.List;

public class RevistaAdapter extends RecyclerView.Adapter<RevistaAdapter.RevistaViewHolder> {

    private Context context;
    private List<Revista> revistaList;

    public RevistaAdapter(Context context, List<Revista> revistaList) {
        this.context = context;
        this.revistaList = revistaList;
    }

    @NonNull
    @Override
    public RevistaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(com.example.exameJLJM.R.layout.item_revista, parent, false);
        return new RevistaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RevistaViewHolder holder, int position) {
        Revista revista = revistaList.get(position);
        holder.tvNombreRevista.setText(revista.getName());
        Glide.with(context).load(revista.getPortada()).into(holder.ivPortada);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, VolumesActivity.class);
            intent.putExtra("journal_id", revista.getJournal_id());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return revistaList.size();
    }

    public static class RevistaViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPortada;
        TextView tvNombreRevista;

        public RevistaViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPortada = itemView.findViewById(R.id.ivPortada);
            tvNombreRevista = itemView.findViewById(R.id.tvNombreRevista);
        }
    }
}

