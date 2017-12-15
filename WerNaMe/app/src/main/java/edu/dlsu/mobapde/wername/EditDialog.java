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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by Troy Mirafuentes on 12/15/2017.
 */

public class EditDialog extends DialogFragment {
    public interface EditDialogListener {
         void editPlateNum(long id, String plateNum);
         void cancelEdit();
        void deleteJourney(long id);
    }

    EditDialog.EditDialogListener tListener;
    EditText newPlateNum;
    long journId;

    /*@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            tListener = (EditDialog.EditDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }*/

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_edit, null);

        newPlateNum = v.findViewById(R.id.edit_plateNum);

        AlertDialog.Builder builder
                = new AlertDialog.Builder(getActivity())
                .setTitle("Edit Plate Number")
                .setView(v);

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SharedPreferences dsp =
                        PreferenceManager.getDefaultSharedPreferences(getContext());
                long trip = dsp.getLong("editJourney", -1);

                String newPlate = newPlateNum.getText().toString();

                if (newPlate.equals("")) {
                    Toast.makeText(getContext(),
                            "Please input text",
                            Toast.LENGTH_SHORT).show();
                }
                else {

                    tListener.editPlateNum(trip, newPlate);
                }
            }
        })
        .setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                tListener.cancelEdit();
            }
        })
        .setNeutralButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SharedPreferences dsp =
                        PreferenceManager.getDefaultSharedPreferences(getContext());
                long trip = dsp.getLong("editJourney", -1);
                tListener.deleteJourney(trip);
            }
        });
        return builder.create();
    }
}
