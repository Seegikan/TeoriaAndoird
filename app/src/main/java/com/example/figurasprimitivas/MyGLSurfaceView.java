package com.example.figurasprimitivas;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public class MyGLSurfaceView extends GLSurfaceView {
    private final MyGLRenderer renderer;

    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private float previousX;
    private float previousY;

    public MyGLSurfaceView(Context context) {
        super(context);
        //Iniciación del metodo

        setEGLContextClientVersion(2);
        //Se obtiene la version 2.0 de Open GL
        //setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        renderer = new MyGLRenderer();

        setRenderer(renderer);
        //Contiene tod0 el pinzel

    }

    @Override
    public boolean onTouchEvent(MotionEvent e) //eventos de toque del celular
    {
        float x = e.getX();
        float y = e.getY();

        switch (e.getAction())
        {
            case MotionEvent.ACTION_MOVE:
                float dx = x - previousX;
                float dy = y - previousY;

                //la figura va a girar al lado contrario de donde nostros tocamos en Y
                if (y > getHeight() / 2)
                {
                    dx = dx * -1;
                }

                renderer.setAngle(renderer.getAngle() + ((dx + dy) * TOUCH_SCALE_FACTOR));
                requestRender();
        }
        previousX = x;
        previousY = y;
        return true;
        }
    }


