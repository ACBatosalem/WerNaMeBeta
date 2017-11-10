package edu.dlsu.mobapde.wername;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Angel on 01/11/2017.
 */

public class JourneyAdapter
        extends CursorRecyclerViewAdapter<JourneyAdapter.JourneyViewHolder>{

    public JourneyAdapter(Context context, Cursor cursor) {
        super(context, cursor);
    }

    @Override
    public void onBindViewHolder(JourneyViewHolder holder, Cursor cursor) {
        String source = cursor.getString(cursor.getColumnIndex(Journey.COLUMN_SOURCE));
        String destination = cursor.getString(cursor.getColumnIndex(Journey.COLUMN_DESTINATION));
        String plateNumber = cursor.getString(cursor.getColumnIndex(Journey.COLUMN_PLATENUMBER));

        holder.tvSource.setText(source);
        holder.tvDesti.setText(destination);
        holder.tvPNum.setText(plateNumber);
    }

    @Override
    public JourneyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout item_restaurant
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_journey, parent, false);
        return new JourneyViewHolder(v);
    }

    //@Override
    /*public int getItemCount() {
        return journeys.size();
    }*/

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
