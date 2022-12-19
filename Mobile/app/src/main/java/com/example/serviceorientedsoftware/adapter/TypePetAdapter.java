package com.example.serviceorientedsoftware.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.serviceorientedsoftware.R;
import com.example.serviceorientedsoftware.activity.PetsActivity;
import com.example.serviceorientedsoftware.model.TypePet;

import java.util.List;

public class TypePetAdapter extends RecyclerView.Adapter<TypePetAdapter.ViewHolder>{

    private List<TypePet> types;
    private Context context;

    public TypePetAdapter(List<TypePet> types, Context context) {
        this.types = types;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.chosen_pets, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TypePet type = types.get(position);
        holder.imageType.setImageResource(type.getImage());
        holder.nameType.setText(type.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((PetsActivity) context).setPets(type.getType());

            }
        });
    }

    @Override
    public int getItemCount() {
        return types.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardType;
        ImageView imageType;
        TextView nameType;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardType    = itemView.findViewById(R.id.item_card_view);
            imageType   = itemView.findViewById(R.id.item_type_pets_chosen);
            nameType    = itemView.findViewById(R.id.item_name_pets_chosen);
        }
    }
}
