package com.example.figurasprimitivas;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class MyGLSurfaceView extends GLSurfaceView
{
    private final MyGLRenderer renderer;

    public MyGLSurfaceView (Context context)
    {
        super(context);
        //Iniciación del metodo

        setEGLContextClientVersion(2);
        //Se obtiene la version 2.0 de Open GL

        renderer = new MyGLRenderer();

        setRenderer(renderer);
        //Contiene tod0 el pinzel

    }
}
