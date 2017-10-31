package edu.dlsu.mobapde.wername;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Angel on 01/11/2017.
 */

public class JourneyAdapter
        extends RecyclerView.Adapter<JourneyAdapter.JourneyViewHolder>{

    ArrayList<Journey> journeys;

    public JourneyAdapter(ArrayList<Journey> data){
        this.journeys = data;
    }

    @Override
    public JourneyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout item_restaurant
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_journey, parent, false);
        return new JourneyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(JourneyViewHolder holder, int position) {
        Journey currentJourney = journeys.get(position);
        holder.tvSource.setText(currentJourney.getSource());
        holder.tvDesti.setText(currentJourney.getDestination());
        holder.tvPNum.setText(currentJourney.getPlate_number());

    }

    @Override
    public int getItemCount() {
        return journeys.size();
    }

    public class JourneyViewHolder
            extends RecyclerView.ViewHolder{

        TextView tvSource, tvDesti, tvPNum;

        public JourneyViewHolder(View itemView) {
            super(itemView);
            // TODO initialize tvRestaurant
            //what is itemView == item_restaurant
            tvSource = itemView.findViewById(R.id.tv_src);
            tvDesti = itemView.findViewById(R.id.tv_dst);
            tvPNum = itemView.findViewById(R.id.tv_platenum);
        }
    }
}
