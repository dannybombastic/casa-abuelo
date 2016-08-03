package com.danny.danny.casaabuelo;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ZoomButtonsController;

import java.lang.reflect.Method;

/**
 * Created by danny on 20/05/2016.
 */
public class CartaWeb extends WebView {


    private ZoomButtonsController zoomButtons;

    public CartaWeb(Context context) {
        super(context);
        init();

       // disableControls();
    }


    public CartaWeb(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        //disableControls();
    }

    public CartaWeb(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
         init();
        //disableControls();
    }
    private void disableControls(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            // Use the API 11+ calls to disable the controls
            this.getSettings().setBuiltInZoomControls(true);
            this.getSettings().setDisplayZoomControls(false);
        } else {
            // Use the reflection magic to make it work on earlier APIs
            getControlls();
        }
    }
    private void getControlls() {
        try {
            Class webview = Class.forName("android.webkit.WebView");
            Method method = webview.getMethod("getZoomButtonsController");
            zoomButtons = (ZoomButtonsController) method.invoke(this, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {
        getSettings().setBuiltInZoomControls(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getSettings().setDisplayZoomControls(true);
        } else {
            try {
                Method method = getClass()
                        .getMethod("getControlls");

                zoomButtons = (ZoomButtonsController) method.invoke(this);

                this.zoomButtons.setVisible(true);
            } catch (Exception e) {
                // pass
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        boolean result = super.onTouchEvent(ev);
        if (zoomButtons != null) {
            zoomButtons.setVisible(false);
           // zoomButtons.getZoomControls().setVisibility(View.GONE);
        }
        invalidate();
        return result;
    }


}
