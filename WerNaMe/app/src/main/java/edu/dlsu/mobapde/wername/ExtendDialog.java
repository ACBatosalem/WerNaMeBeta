package edu.dlsu.mobapde.wername;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Angel on 15/12/2017.
 */

public class ExtendDialog extends DialogFragment {

    public interface ExtendDialogListener {
        public void extendTime(long newTime, long id);
    }

    ExtendDialog.ExtendDialogListener tListener;
    Spinner etMinutes, etHours;
   // DatabaseHelper databaseHelper;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            tListener = (ExtendDialog.ExtendDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_extend, null);



        // Spinner for ETA Hours
        etHours = v.findViewById(R.id.ex_hours);
        ArrayList<String> hours = new ArrayList<>();
        for(int i=0;i<24;i++)
            hours.add(String.valueOf(i));

        // Adapter for ETA Hours Spinner
        ArrayAdapter<String> hoursArrayAdapter = new ArrayAdapter<String>(this.getContext(),
                android.R.layout.simple_spinner_item, hours);
        hoursArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        etHours.setAdapter(hoursArrayAdapter);

        // Spinner for ETA Minutes
        etMinutes = v.findViewById(R.id.ex_minutes);
        ArrayList<String> minutes = new ArrayList<>();
        for(int i=0;i<60;i++)
            minutes.add(String.valueOf(i));

        // Adapter for ETA Minutes Spinner
        ArrayAdapter<String> minutesArrayAdapter = new ArrayAdapter<String>(this.getContext(),
                android.R.layout.simple_spinner_item, minutes);
        minutesArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        etMinutes.setAdapter(minutesArrayAdapter);

        AlertDialog.Builder builder
                = new AlertDialog.Builder(getActivity())
                .setTitle("Extend Time")
                .setView(v);

        builder.setPositiveButton("Extend", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int minutes = Integer.parseInt(etMinutes.getSelectedItem().toString());
                int hours = Integer.parseInt(etHours.getSelectedItem().toString());

                long elapsedTime = minutes*1000*60 + hours*60*60*1000;

                SharedPreferences dsp =
                        PreferenceManager.getDefaultSharedPreferences(getContext());
                long trip = dsp.getLong("trip", -1);

                if (minutes == 0 && hours == 0) {
                    Toast.makeText(getContext(),
                            "Please select the time",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    tListener.extendTime(elapsedTime, trip);
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dismiss();
            }
        });
        return builder.create();
    }
}
