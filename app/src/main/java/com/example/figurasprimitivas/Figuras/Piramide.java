package com.example.figurasprimitivas.Figuras;

import android.opengl.GLES20;

import com.example.figurasprimitivas.MyGLRenderer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class Piramide
{
    private FloatBuffer vertexBuffer;

    private ShortBuffer drawListBuffer;//nuevo-------------------------------------------------

    private final String vertexShaderCode =
            "attribute vec4 vPosition;" +
                    "void main() {" +
                    "gl_Position =  vPosition;" +
                    "}";
    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";

    private short drawOrder[] =
            {
                    0,1,2,0,3,2,1,3
            };//nuevo-----------------------------------------------


    static final int COORDS_PER_VERTEX = 3;
    static float[] coordenadasTriangulo =
            {
                    0.0f, 0.4f, 0.0f,      //0

                    -0.4f, -0.4f, 0.4f,    //1
                    0.4f, -0.4f, 0.4f,     //2

                    0.0f, -0.3f, -0.2f,    //3
            };

    float[] color = {0.3f, 0.3f, 0.3f, 1.0f};// color

    private final int mProgram;

    //Constructor para cuando crea el objeto
    public Piramide()
    {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(coordenadasTriangulo.length * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        vertexBuffer = byteBuffer.asFloatBuffer();
        vertexBuffer.put(coordenadasTriangulo);
        vertexBuffer.position(0);

        ByteBuffer dlb = ByteBuffer.allocateDirect(drawOrder.length * 2); //nuevo------------
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(drawOrder);
        drawListBuffer.position(0); //-----------------------------------------------

        int vertexShader = MyGLRenderer.loadsheader(GLES20.GL_VERTEX_SHADER, vertexShaderCode); //Asignar shader para continuar
        int fragmentShader = MyGLRenderer.loadsheader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        //Creamos un programa de OpenGL vacío
        mProgram = GLES20.glCreateProgram();

        //añadimos los vertex shader al programa con todos sus atributos
        GLES20.glAttachShader(mProgram, vertexShader);

        //añadimos los fragmentos shader al programa con todos sus atributos
        GLES20.glAttachShader(mProgram, fragmentShader);

        //Creamos ejecutable de OpenGL para poderlo ejecutar
        GLES20.glLinkProgram(mProgram);
    }

    private int positionHandle, colorHandle; //Posición y color a usar

    private final int vertexCount = coordenadasTriangulo.length / COORDS_PER_VERTEX;
    private final int vertexStride = COORDS_PER_VERTEX * 4; //Vertex stride

    public void draw()
    {
        //Añadir nuestro programa al entorno de OpenGL
        GLES20.glUseProgram(mProgram);

        //Obtener el identificador del miembro vPosition del sombreado de vértices
        positionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

        GLES20.glEnableVertexAttribArray(positionHandle);
        GLES20.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, vertexBuffer );
        colorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
        GLES20.glUniform4fv(colorHandle, 1, color, 0);
        //GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, vertexCount); //PARA EL CUADRADO NECESITAREMOS FAN----------------------------------------------------------
        GLES20.glDrawElements(GLES20.GL_LINE_STRIP, drawOrder.length, GLES20.GL_UNSIGNED_SHORT, drawListBuffer); //nuevo----------------------------------

        GLES20.glDisableVertexAttribArray(positionHandle);
    }
}
