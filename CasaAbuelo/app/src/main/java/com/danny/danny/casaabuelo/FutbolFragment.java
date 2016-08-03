package com.danny.danny.casaabuelo;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FutbolFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class FutbolFragment extends android.app.Fragment {

    private OnFragmentInteractionListener mListener;
    protected TextView equipoa, equipob, hora;
    public FutbolFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_futbolfragment, container, false);
        equipoa = (TextView) vista.findViewById(R.id.equipoa);
        equipob = (TextView) vista.findViewById(R.id.equipob);
        hora = (TextView) vista.findViewById(R.id.hora_partidos);
        SharedPreferences pref = getActivity().getSharedPreferences("menus", Context.MODE_PRIVATE);


        equipoa.setText(pref.getString("equipoa", " "));
        equipob.setText(pref.getString("equipob", " "));
        hora.setText(pref.getString("hora", " "));

        return vista;
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
