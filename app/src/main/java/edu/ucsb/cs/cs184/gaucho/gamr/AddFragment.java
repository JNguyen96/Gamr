package edu.ucsb.cs.cs184.gaucho.gamr;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class AddFragment extends android.app.DialogFragment {

    final int ACTIVITY_SELECT_IMAGE = 4;
    String titleText = "";
    String desText = "";

    private OnFragmentInteractionListener mListener;

    public AddFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, @Nullable final Bundle savedInstanceState) {
        TextView titlePrompt = (TextView)view.findViewById(R.id.title);
        EditText titleRes = (EditText)view.findViewById(R.id.titleRes);
        titleRes.setText(getArguments().getString("title"));
        TextView desPrompt = (TextView)view.findViewById(R.id.description);
        EditText desRes = (EditText)view.findViewById(R.id.descriptionRes);
        desRes.setText(getArguments().getString("desc"));
        Button addImage = (Button)view.findViewById(R.id.addImage);
        addImage.setText(getArguments().getString("buttonTxt"));
        Button save = (Button)view.findViewById(R.id.saveButton);
        Button cancel = (Button)view.findViewById(R.id.cancelButton);


        titleRes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                titleText = editable.toString();
            }
        });

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LaunchGallery();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TITLE", titleText);
                Log.d("DESCRIPTION", desText);
                //TODO: Save title, description, and image to SQLite DB
                dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TITLE", titleText);
                dismiss();
            }
        });

    }

    public void LaunchGallery() {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(i, ACTIVITY_SELECT_IMAGE);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public static AddFragment newInstance(String title, String description, String buttonTxt){
        AddFragment af = new AddFragment();
        Bundle args = new Bundle();
        args.putString("title",title);
        args.putString("desc",description);
        args.putString("buttonTxt", buttonTxt);
        af.setArguments(args);
        return af;
    }
}
