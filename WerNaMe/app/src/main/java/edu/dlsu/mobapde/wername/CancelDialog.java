package edu.dlsu.mobapde.wername;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Created by Angel on 15/12/2017.
 */

public class CancelDialog  extends DialogFragment {

    public interface CancelDialogListener {
        public void onCancelClick();
    }

    CancelDialog.CancelDialogListener tListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            tListener = (CancelDialog.CancelDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        AlertDialog.Builder builder
                = new AlertDialog.Builder(getActivity())
                .setTitle("Cancel Journey")
                .setMessage("Do you want to cancel this journey?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        tListener.onCancelClick();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dismiss();

                    }
                });
        return builder.create();
    }
}
