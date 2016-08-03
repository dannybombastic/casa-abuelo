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
import android.widget.ProgressBar;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MenusFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MenusFragment extends android.app.Fragment{
    TextView primero, segundos, terceros, fecha;
    ProgressBar interior;
    ProgressBar terraza;
    TextView terraza_progres, interior_progres;
    private OnFragmentInteractionListener mListener;

    public MenusFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_menusfragment, container, false);
        primero = (TextView) vista.findViewById(R.id.primeros);
        segundos = (TextView) vista.findViewById(R.id.segundos);
        terceros = (TextView) vista.findViewById(R.id.terceros);
        terraza_progres = (TextView) vista.findViewById(R.id.terraza_texto);
        interior_progres = (TextView) vista.findViewById(R.id.interior_progres);
        fecha = (TextView) vista.findViewById(R.id.menu);
        interior = (ProgressBar) vista.findViewById(R.id.interior);
        terraza = (ProgressBar) vista.findViewById(R.id.terraza);



        // cargamos los datos a los componentes del XML
        SharedPreferences pref = getActivity().getSharedPreferences("menus", Context.MODE_PRIVATE);
        String barra_dos = "INTERIOR LLENO AL "+pref.getString("mesas","")+" POR CIENTO";
        String barra = "TERRAZA LLENA AL "+pref.getString("terraza","")+" POR CIENTO";
        terraza_progres.setText(barra);
        interior_progres.setText(barra_dos);

        terraza.setProgress(Integer.parseInt(pref.getString("terraza","2")));

        interior.setProgress(Integer.parseInt(pref.getString("mesas","2")));
        primero.setText(pref.getString("primero"," "));
        segundos.setText(pref.getString("segundo"," "));
        terceros.setText(pref.getString("terceros"," "));
        fecha.setText(pref.getString("fecha"," "));


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
