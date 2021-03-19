package com.example.figurasprimitivas;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;

import com.example.figurasprimitivas.Figuras.Castillo;
import com.example.figurasprimitivas.Figuras.Cuadro;
import com.example.figurasprimitivas.Figuras.Hexagono;
import com.example.figurasprimitivas.Figuras.Piramide;
import com.example.figurasprimitivas.Figuras.Tarea;
import com.example.figurasprimitivas.Figuras.Triangulo;
import com.example.figurasprimitivas.Figuras.TriangulosLocos;
import com.example.figurasprimitivas.Figuras.VentanasCastillo;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;



public class MyGLRenderer implements  GLSurfaceView.Renderer
{
    private Triangulo mTriangulo;
    private Cuadro mCuadro;
    private Tarea mTarea;
    private Castillo mCastillo;
    private VentanasCastillo mVentanas;
    private Piramide mPiramide;
    private Hexagono mHexagono ;
    private TriangulosLocos mLocos ;
    private  volatile  float mAngle; //que puede cambiar
    //Variables para la MVP
    private final float[] vPMatrix = new float[16];
    private final float[] projectionMatrix = new float[16];
    private final float[] viewMatrix = new float[16];
    private float[] rotationMatrix = new float[16];

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) //Lienzo
    {
        GLES20.glClearColor( 1.0f, 1.0f,1.0f,1.0f);

            mCastillo = new Castillo();
            mPiramide = new Piramide();
            mVentanas = new VentanasCastillo();
            mTarea = new Tarea();
            mCuadro = new Cuadro();
            mTriangulo = new Triangulo();
            mHexagono = new Hexagono();
            mLocos = new TriangulosLocos();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height)
    {
        GLES20.glViewport(0,0,width,height);
        //aplicacion ed la proyeccion as las coordenadas del objeto en el metodo
        //convulocion
        float ratio = (float) width / height;
        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
    }

    @Override
    public void onDrawFrame(GL10 gl)
    {
        float[] scratch = new float[16];

        /*long time = SystemClock.uptimeMillis() % 4000L;
        float angle = 0.090f * ((int) time);*/

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        //POscicionamos la camara para la matriz de proyeccion
        Matrix.setLookAtM(viewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        //Se calcula la trasformada de la proyaccion y la vustra se convulicioon formando la MVP
        Matrix.multiplyMM(vPMatrix, 0,projectionMatrix,0,viewMatrix,0);

        //aplicamos la matriz de rotacion
        Matrix.setRotateM(rotationMatrix, 0, mAngle, 0, 0, -1.0f);
        Matrix.multiplyMM(vPMatrix, 0, projectionMatrix, 0, viewMatrix, 0);
        Matrix.multiplyMV(scratch, 0, vPMatrix, 0, rotationMatrix, 0);

        mLocos.draw(scratch);
       // mHexagono.draw();
        //mPiramide.draw();
        //mCastillo.draw();
       // mVentanas.draw();
        //mTarea.draw();
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

    public float getAngle()
    {
        return mAngle;
    }

    public void setAngle(float angle)
    {
        mAngle = angle;
    }
}
