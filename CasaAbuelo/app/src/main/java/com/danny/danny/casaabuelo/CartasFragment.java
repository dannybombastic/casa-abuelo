package com.danny.danny.casaabuelo;

import android.annotation.TargetApi;
import android.app.Notification;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class CartasFragment extends android.app.Fragment implements View.OnClickListener{

    private OnFragmentInteractionListener mListener;
    Button ensalada, entradas, tapas, bocadillos, postres, pescado, tintos, pizzeria, carne;
    WebView imagen;
    WebSettings webS;

    HorizontalScrollView hc;

    public CartasFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_cartasfragment, container, false);
        ensalada = (Button) vista.findViewById(R.id.ensaladas);

        CartaWeb carta = new CartaWeb(getActivity());
      //  imagen = carta;
        entradas = (Button)  vista.findViewById(R.id.entradas);
entradas.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

    }
});



        hc = (HorizontalScrollView)  vista.findViewById(R.id.hc);
        imagen = (WebView) vista.findViewById(R.id.imagen);



        // asignamos a los componentes a escuchar a  nuetro metodo onclick delegate



        entradas.setOnClickListener(this);

        ensalada.setOnClickListener(this);
        webS = imagen.getSettings();
        webS.setJavaScriptEnabled(true);



        imagen.setWebViewClient(new WebViewClient());

        imagen.loadUrl("http://www.dannybombastic.esy.es/actualizando/abuelo/cartaAbu.png");
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

        switch (v.getId()) {
            case R.id.ensaladas:
                imagen.invalidate();

             ;

                webS = imagen.getSettings();

                webS.setJavaScriptEnabled(true);

                imagen.setWebViewClient(new WebViewClient());

                imagen.loadUrl("http://www.dannybombastic.esy.es/actualizando/abuelo/cartaAbu.png");
                // web.loadDataWithBaseURL(null,"UBICACION CASA EL ABUELO", "text/html", "UTF-8", null);

                break;

            case R.id.entradas:

              //  imagen.getSettings().setDisplayZoomControls(true);
                imagen.getSettings().setBuiltInZoomControls(true);
              //  imagen.getSettings().setSupportZoom(true);

                webS = imagen.getSettings();

                webS.setJavaScriptEnabled(true);

                imagen.setWebViewClient(new WebViewClient());

                imagen.loadUrl("http://www.dannybombastic.esy.es/actualizando/abuelo/cartaAbu2.png");
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
