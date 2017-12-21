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
public class ContactFragment extends android.app.DialogFragment {

    final int ACTIVITY_SELECT_IMAGE = 4;
    String firstName = "";
    String lastName = "";
    String phoneText = "";
    String emailText = "";

    private OnFragmentInteractionListener mListener;

    public ContactFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, @Nullable final Bundle savedInstanceState) {
        final User user;
        final TextView phoneNum = (TextView)view.findViewById(R.id.phone_num);
        final EditText phoneRes = (EditText)view.findViewById(R.id.phoneRes);
//        phoneNum.setText("Phone Number");
        TextView email = (TextView)view.findViewById(R.id.email_prompt);
        final EditText emailRes = (EditText)view.findViewById(R.id.emailRes);
//        email.setText("Email Address");
        final EditText fNameRes = (EditText)view.findViewById(R.id.fNameRes);
        final EditText lNameRes = (EditText)view.findViewById(R.id.lNameRes);
        Button save = (Button)view.findViewById(R.id.saveCButton);
        Button cancel = (Button)view.findViewById(R.id.cancelCButton);
        DBWrapper.getInstance().getUser(getArguments().getString("userId"), new DBWrapper.UserTransactionListener() {
            @Override
            public void onComplete(User user) {
                if (phoneRes.getText().length() == 0)
                    phoneRes.setText(user.phone);
                if (emailRes.getText().length() == 0)
                    emailRes.setText(user.email);
                if (lNameRes.getText().length() ==0)
                    lNameRes.setText(user.lName);
                if (fNameRes.getText().length() == 0)
                    fNameRes.setText(user.fName);
            }
        });

        fNameRes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                firstName = editable.toString();
            }
        });

        lNameRes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                lastName = editable.toString();
            }
        });

        phoneRes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                phoneText = editable.toString();
            }
        });

        emailRes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                emailText = editable.toString();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("PHONE NUMBER", phoneText);
                Log.d("EMAIL ADDRESS", emailText);
                //TODO: Save title, description, and image to DB
                DBWrapper.getInstance().getUser(getArguments().getString("userId"), new DBWrapper.UserTransactionListener() {
                    @Override
                    public void onComplete(User user) {
                        user.fName = firstName;
                        user.lName = lastName;
                        user.email = emailText;
                        user.phone = phoneText;
                        DBWrapper.getInstance().updateUser(user);
                    }
                });
                dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        return inflater.inflate(R.layout.fragment_contact, container, false);
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


    public static ContactFragment newInstance(String fName, String lName, String phone, String email, String userId){
        ContactFragment cf = new ContactFragment();
        Bundle args = new Bundle();
        args.putString("fName",fName);
        args.putString("lName",lName);
        args.putString("phone",phone);
        args.putString("email",email);
        args.putString("userId", userId);
        cf.setArguments(args);
        return cf;

    }
}
