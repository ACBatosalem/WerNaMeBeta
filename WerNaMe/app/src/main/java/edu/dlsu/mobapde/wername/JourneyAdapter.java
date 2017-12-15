package edu.dlsu.mobapde.wername;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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
        long id = cursor.getLong(cursor.getColumnIndex(Journey.COLUMN_ID));
        String source = cursor.getString(cursor.getColumnIndex(Journey.COLUMN_SOURCE));
        String destination = cursor.getString(cursor.getColumnIndex(Journey.COLUMN_DESTINATION));
        String plateNumber = cursor.getString(cursor.getColumnIndex(Journey.COLUMN_PLATENUMBER));
        String textSentTo = cursor.getString(cursor.getColumnIndex(Journey.COLUMN_TEXTSENTTO));
        long actualTA = cursor.getLong(cursor.getColumnIndex(Journey.COLUMN_ACTUALTA));
        long estimatedTA = cursor.getLong(cursor.getColumnIndex(Journey.COLUMN_ESTIMATEDTA));
        long startTime = cursor.getLong(cursor.getColumnIndex(Journey.COLUMN_STARTTIME));
        String message = cursor.getString(cursor.getColumnIndex(Journey.COLUMN_MESSAGE));

        long remTime = actualTA - startTime;

        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.SHORT);
        String newTravelTime = ((remTime / (1000*60*60)) % 24) + "h " +
                ((remTime / (1000*60)) % 60) + "m";
        String newActualTA = df.format(new Date(actualTA));
        String newStartTime = df.format(new Date(startTime));

        Journey currJourney = new Journey(source, destination, plateNumber, startTime, estimatedTA, actualTA, textSentTo, message);
        currJourney.setId(id);

        holder.ivEdit.setTag(currJourney);
        holder.tvId.setText(String.valueOf(id));
        holder.tvSrc.setText(source);
        holder.tvDest.setText(destination);
        holder.tvPlateNum.setText(plateNumber);
        holder.tvMessage.setText(textSentTo);
        holder.tvStartTime.setText(newStartTime);
        holder.tvEndTime.setText(newActualTA);
        holder.tvTravelTime.setText(newTravelTime);

        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Journey j = (Journey) view.getTag();
                onItemClickListener.onItemClick(j);
            }
        });
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

        TextView tvStartTime, tvEndTime, tvPlateNum, tvTravelTime, tvMessage, tvSrc, tvDest, tvId;
        ImageView ivEdit;

        public JourneyViewHolder(View itemView) {
            super(itemView);
            // TODO initialize tvRestaurant
            //what is itemView == item_restaurant
            tvId = itemView.findViewById(R.id.tv_id);
            tvStartTime = itemView.findViewById(R.id.tv_time_start);
            tvEndTime = itemView.findViewById(R.id.tv_time_end);
            tvPlateNum = itemView.findViewById(R.id.tv_platenum);
            tvTravelTime = itemView.findViewById(R.id.tv_travelTime);
            tvMessage = itemView.findViewById(R.id.tv_message);
            tvSrc = itemView.findViewById(R.id.tv_src);
            tvDest = itemView.findViewById(R.id.tv_dst);
            ivEdit = itemView.findViewById(R.id.iv_edit);
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(Journey j);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener (OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
