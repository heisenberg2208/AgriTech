package com.example.pankaj.farmguide;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class CropAdapter extends RecyclerView.Adapter<CropAdapter.CropViewHolder> {

    private Context mCtx;

    private List<Crop> list;

    public CropAdapter(Context mCtx, List<Crop> list){
        this.mCtx = mCtx;
        this.list = list;
    }

    public CropViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_cropdetails, null);
        return new CropViewHolder(view);
    }

    public void onBindViewHolder(final CropViewHolder holder, final int position) {
        //getting the product of the specified position
        //  Crop crop = list.get(position);

        //binding the data with the viewholder views
        holder.tvCropData.setText(list.get(position).getData());
        holder.tvCost.setText(list.get(position).getCost());

        holder.ivCropImg.setImageDrawable(mCtx.getResources().getDrawable(list.get(position).getImage()));
        holder.btnDemSup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mCtx, Integer.toString(position), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(mCtx,DemandSupplyActivity.class);
                i.putExtra("crop",holder.tvCropData.getText().toString());
                mCtx.startActivity(i);

            }
        });




    }

    public int getItemCount() {
        return list.size();
    }










    class CropViewHolder extends RecyclerView.ViewHolder{

        TextView tvCropData, tvCost;
        ImageView ivCropImg;
        Button btnDemSup;
        public CropViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCropData = itemView.findViewById(R.id.tvCropData);
            tvCost = itemView.findViewById(R.id.tvCost);
            ivCropImg = itemView.findViewById(R.id.ivCropImg);
            btnDemSup = itemView.findViewById(R.id.btnDemSup);
        }
    }


}
