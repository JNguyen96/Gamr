package edu.ucsb.cs.cs184.gaucho.gamr;

import android.app.DialogFragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MatchContactFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class MatchContactFragment extends DialogFragment {

    private OnFragmentInteractionListener mListener;

    public MatchContactFragment() {
        // Required empty public constructor
    }
    String gameList = "";
    TextView games;

    @Override
    public void onViewCreated(View view, @Nullable final Bundle savedInstanceState) {
        TextView name = (TextView)view.findViewById(R.id.name);
        TextView phone = (TextView)view.findViewById(R.id.phone_num_m);
        TextView email = (TextView)view.findViewById(R.id.email_m);
        games = (TextView)view.findViewById(R.id.games_m);
        ArrayList<String> gameIds = getArguments().getStringArrayList("saleIds");


        name.setText("Name: " + getArguments().getString("fName") + " " + getArguments().getString("lName"));
        phone.setText("Phone Number: " + getArguments().getString("phone"));
        email.setText("Email Address: " + getArguments().getString("email"));

        for(String game:gameIds){
            DBWrapper.getInstance().getSale(game, new DBWrapper.SaleTransactionListener() {
                @Override
                public void onComplete(Sale sale) {
                    gameList += (sale.getName() + ", ");
                    games.setText("Games: " + gameList);
                }

                @Override
                public void onFailure(FailureReason reason) {

                }
            });
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_match_contact, container, false);
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

    public static MatchContactFragment newInstance(String fName, String lName, String phone, String email, String userId, ArrayList<String> saleIds){
        MatchContactFragment mcf = new MatchContactFragment();
        Bundle args = new Bundle();
        args.putString("fName",fName);
        args.putString("lName",lName);
        args.putString("phone",phone);
        args.putString("email",email);
        args.putString("userId", userId);
        args.putStringArrayList("saleIds", saleIds);
        mcf.setArguments(args);
        return mcf;

    }
}
