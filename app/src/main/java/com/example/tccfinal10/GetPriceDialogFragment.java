package com.example.tccfinal10;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.view.View;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

public class GetPriceDialogFragment extends DialogFragment {

    private  EditText etPrice;
    private static final String TAG = "DialogFragment";
    private TextView mActionOk, mActionCancel;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.dialog_eprice, container, false);
        mActionCancel = view.findViewById(R.id.btnCancelPrice);
        mActionOk = view.findViewById(R.id.btnAcceptPrice);
        etPrice = view.findViewById(R.id.etKWhPrice);

        mActionCancel.setOnClickListener(
                new View.OnClickListener() {
                    @Override public void onClick(View v)
                    {
                        Log.d(TAG, "onClick: closing dialog");
                        getDialog().dismiss();
                    }
                });

        mActionOk.setOnClickListener(
                new View.OnClickListener() {
                    @Override public void onClick(View v)
                    {
                        Log.d(TAG, "onClick: capturing input");

                        try {

                            MainActivity.kwhPrice = Float.parseFloat((etPrice.getText().toString()));

                            getDialog().dismiss();

                        } catch (NumberFormatException e) {
                            MainActivity.kwhPrice = 0;
                            e.printStackTrace();
                            getDialog().dismiss();
                        }



                    }
                });

        return view;
    }


    /*
    @NonNull
    @SuppressLint("InflateParams")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();





        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.dialog_eprice, null))
                // Add action buttons

                .setPositiveButton(R.string.dialogPriceOk, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        String name = ((EditText)dialog.findViewById(R.id.nameText)).getText().toString();
                        MainActivity.kwhPrice = Long.parseLong(etPrice.getText().toString());


                    }
                })
                .setNegativeButton(R.string.dialogPriceCancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        return builder.create();
    }

*/

}
