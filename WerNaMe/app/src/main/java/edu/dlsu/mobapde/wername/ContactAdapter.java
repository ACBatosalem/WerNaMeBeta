package edu.dlsu.mobapde.wername;

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
        extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder>{
    ArrayList<Contact> contacts;

    public ContactAdapter(ArrayList<Contact> data){
        this.contacts = data;
    }

    @Override
    public ContactAdapter.ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout item_restaurant
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contact, parent, false);
        return new ContactAdapter.ContactViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ContactAdapter.ContactViewHolder holder, int position) {
        Contact currentContact = contacts.get(position);
        holder.tvName.setText(currentContact.getName());
        holder.tvNumber.setText(currentContact.getNumber());
    }

    @Override
    public int getItemCount() {
        return contacts.size();
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
