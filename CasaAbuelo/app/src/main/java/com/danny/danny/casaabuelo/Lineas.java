package com.danny.danny.casaabuelo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

/**
 * Created by danny on 01/05/2016.
 */
public class Lineas extends View {

    int color;
    float x =1000;
    float y = 500;
    int grosor;
    String DIBUJAR ="dibujar";
    Path patch = new Path();
    Paint pinta = new Paint(Paint.ANTI_ALIAS_FLAG);

    public Lineas(Context context,int color,int grosor) {


        super(context);
        this.patch.close();
        this.color = color;
        this.grosor= grosor;

        setWillNotDraw(false);

    }


   // public void onDraw(Canvas canvas){
      //  super.onDraw(canvas);


    //    Paint pinta = new Paint(Paint.ANTI_ALIAS_FLAG);
    //    pinta.setStyle(Paint.Style.STROKE);
   //     pinta.setStrokeWidth(grosor);
   //     pinta.setColor(color);
   //     int ancho = canvas.getWidth();


  //      if(DIBUJAR=="move"){patch.lineTo(x,y);}
   //     if(DIBUJAR=="down"){patch.moveTo(x,y);}

  //      canvas.drawPath(patch,pinta);



  //  }

    @Override
    public boolean onTouchEvent(MotionEvent e) {

        x = e.getX();
        y = e.getY();
        if(e.getAction()== MotionEvent.ACTION_DOWN){DIBUJAR = "down";
        }


        if(e.getAction()== MotionEvent.ACTION_MOVE){DIBUJAR = "move";
        }

       invalidate();

        refreshDrawableState();
        return true;
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        pinta.setStyle(Paint.Style.STROKE);
        pinta.setStrokeWidth(grosor);
        pinta.setColor(color);
        int ancho = canvas.getWidth();


        if(DIBUJAR=="move"&& x!=0 && y !=0 ){patch.lineTo(x,y);}
        if(DIBUJAR=="down"&& x!=0 && y !=0){patch.moveTo(x,y);}

        canvas.drawPath(patch,pinta);

    }
}
