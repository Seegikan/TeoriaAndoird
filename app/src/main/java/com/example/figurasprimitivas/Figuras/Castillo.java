package com.example.figurasprimitivas.Figuras;

import android.opengl.GLES20;

import com.example.figurasprimitivas.MyGLRenderer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class Castillo
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
                    11,0,12,   //0
                    12,10,11,  //1
                    13,12,1,   //2
                    1,2,13,    //3
                    3,4,5,     //4
                    5,13,3,    //5
                    7,8,9,     //6
                    9,6,7,     //7


            };//nuevo-----------------------------------------------


    static final int COORDS_PER_VERTEX = 3;
    static float[] coordenadasTriangulo =
    {
        -0.9f, 0.9f, 0.0f,    //0
        -0.9f, -0.9f, 0.0f,   //1
        0.9f, -0.9f,0.0f,     //2
        0.9f, 0.9f, 0.0f,     //3
        0.5f, 0.9f, 0.0f,     //4
        0.5f, 0.6f, 0.0f,     //5
        0.3f, 0.6f, 0.0f,     //6
        0.3f, 0.9f, 0.0f,     //7
        -0.3f, 0.9f, 0.0f,    //8
        -0.3f, 0.6f, 0.0f,    //9
        -0.5f, 0.6f, 0.0f,    //10
        -0.5f, 0.9f, 0.0f,    //11

        -0.9f, 0.6f, 0.0f,    //12----
        0.9f, 0.6f, 0.0f      //13----

    };

    float[] color = {0.4f, 0.0f, 0.6f, 1.0f};

    private final int mProgram;

    //Constructor para cuando crea el objeto
    public Castillo()
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
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, drawOrder.length, GLES20.GL_UNSIGNED_SHORT, drawListBuffer); //nuevo----------------------------------

        GLES20.glDisableVertexAttribArray(positionHandle);
    }
}
