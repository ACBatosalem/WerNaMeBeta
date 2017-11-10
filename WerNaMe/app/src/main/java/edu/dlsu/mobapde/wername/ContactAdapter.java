package edu.dlsu.mobapde.wername;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Angel on 01/11/2017.
 */

public class ContactAdapter
        extends CursorRecyclerViewAdapter<ContactAdapter.ContactViewHolder>{

    public ContactAdapter(Context context, Cursor cursor) {
        super(context,cursor);
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout item_restaurant
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contact, parent, false);
        return new ContactViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, Cursor cursor) {
        String number = cursor.getString(cursor.getColumnIndex(Contact.COLUMN_NUMBER));
        String name = cursor.getString(cursor.getColumnIndex(Contact.COLUMN_NAME));
        holder.tvNumber.setText(number);
        holder.tvName.setText(name);


    }

    public class ContactViewHolder
            extends RecyclerView.ViewHolder{

        TextView tvName, tvNumber;

        public ContactViewHolder(View itemView) {
            super(itemView);
            // TODO initialize tvRestaurant
            //what is itemView == item_restaurant
            tvName = itemView.findViewById(R.id.tv_icename);
            tvNumber = itemView.findViewById(R.id.tv_icenum);
        }
    }
}
