package com.example.figurasprimitivas;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;



public class MyGLRenderer implements  GLSurfaceView.Renderer
{
    private Triangulo mTriangulo;
    private  Cuadro mCuadro;
    private  Tarea mTarea;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) //Lienzo
    {
        GLES20.glClearColor( 1.0f, 1.0f,1.0f,1.0f);

            mTarea = new Tarea();
        //mCuadro = new Cuadro(); //Segundo
        //mTriangulo = new Triangulo();//Primero
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height)
    {
        GLES20.glViewport(0,0,width,height);

    }

    @Override
    public void onDrawFrame(GL10 gl)
    {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        mTarea.draw();
       //mCuadro.draw();
       //mTriangulo.draw();

    }

    public static int loadsheader(int type, String shederCode)
    {
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, shederCode);
        GLES20.glCompileShader(shader);

        return shader;
    }
}
