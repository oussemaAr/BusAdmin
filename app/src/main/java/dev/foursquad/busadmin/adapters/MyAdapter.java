package dev.foursquad.busadmin.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import dev.foursquad.busadmin.R;
import dev.foursquad.busadmin.V2.model.Bus;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

    private List<Bus> busList;
    private BusClickListener busClickListener;

    public void setBusClickListener(BusClickListener busClickListener) {
        this.busClickListener = busClickListener;
    }

    public MyAdapter(List<Bus> busList) {
        this.busList = busList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.bus_item, parent, false);
       return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textView.setText(busList.get(position).getKey());
    }

    @Override
    public int getItemCount() {
        return busList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        View status;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.code);
            status = itemView.findViewById(R.id.status);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    busClickListener.onBusClicked(busList.get(getAdapterPosition()));
                }
            });
        }

    }

    public interface BusClickListener{
        void onBusClicked(Bus bus);
    }
}
