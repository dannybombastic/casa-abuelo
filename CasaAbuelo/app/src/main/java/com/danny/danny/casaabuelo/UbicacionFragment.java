package com.danny.danny.casaabuelo;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class UbicacionFragment extends android.app.Fragment {
     WebView web;


    private OnFragmentInteractionListener mListener;

    public UbicacionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_ubicacionfragment,container,false);
        web = (WebView)vista.findViewById(R.id.webView);
        WebSettings webS = web.getSettings();
        webS.setBuiltInZoomControls(true);
        webS.setSupportZoom(true);
        webS.setJavaScriptEnabled(true);
        web.setWebViewClient(new WebViewClient());

        web.loadUrl("https://www.google.es/maps/place/Restaurante+Bar+Casa+El+Abuelo/@36.5447507,-4.6442026,19z/data=!4m5!3m4!1s0x0000000000000000:0xa52117499bcd4b52!8m2!3d36.544609!4d-4.6442905");
               // web.loadDataWithBaseURL(null,"UBICACION CASA EL ABUELO", "text/html", "UTF-8", null);

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
