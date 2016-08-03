package com.danny.danny.casaabuelo;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DibujarFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class DibujarFragment extends android.app.Fragment implements View.OnClickListener {
    RelativeLayout relativ ;

    Button rosa,azul,blanco,borrar;

    private OnFragmentInteractionListener mListener;

    public DibujarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista =  inflater.inflate(R.layout.fragment_dibujarfragment, container, false);
        rosa = (Button)vista.findViewById(R.id.rosa);
        azul = (Button)vista.findViewById(R.id.azul);
        blanco = (Button)vista.findViewById(R.id.blanco);
        borrar = (Button)vista.findViewById(R.id.borrar);
        rosa.setOnClickListener(this);
        azul.setOnClickListener(this);
        blanco.setOnClickListener(this);
        borrar.setOnClickListener(this);
        relativ =(RelativeLayout)vista.findViewById(R.id.layDibujar);

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

    @Override
    public void onClick(View v) {

        Lineas  vista= new  Lineas(getActivity().getApplicationContext(), Color.MAGENTA,10);
        Lineas vista2 = new  Lineas(getActivity().getApplicationContext(),Color.BLUE,10);
        Lineas  vista3 = new   Lineas(getActivity().getApplicationContext(),Color.WHITE,10);
        Lineas [] vistas = {vista,vista2,vista3};
        switch (v.getId()){
            case R.id.rosa:


                relativ.addView(vistas[0]);


                break;
            case R.id.azul:


                relativ.addView(vista2);


                break;
            case R.id.blanco:
                Canvas canvas = new Canvas();

                relativ.addView(vista3);



                break;
            case R.id.borrar:
                vista = new   Lineas(getActivity().getApplicationContext(),Color.argb(255,52,88,74),100);
                relativ.addView(vista);


                break;

        }



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
