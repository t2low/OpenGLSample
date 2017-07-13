package com.tappli.openglessample

import android.opengl.GLES20
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

class Triangle {
    companion object {
        const val COORDS_PER_VERTEX = 3
        val triangleCoords = floatArrayOf(
                0.0f, 0.622008459f, 0.0f, // top
                -0.5f, -0.311004243f, 0.0f, // bottom left
                0.5f, -0.311004243f, 0.0f  // bottom right
        )
    }

    private val vertexShaderCode =
            "uniform mat4 uMVPMatrix;" +
            "attribute vec4 vPosition;" +
            "void main() {" +
            "  gl_Position = uMVPMatrix * vPosition;" +
            "}"

    private val fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}"

    private var mvpMatrixHandle = 0;

    val vertexBuffer: FloatBuffer
    val color = floatArrayOf(0.63671875f, 0.76953125f, 0.22265625f, 1.0f)
    val program: Int

    var positionHandle: Int = 0
    var colorHandle: Int = 0

    private val vertexCount = triangleCoords.size
    private val vertexStride = COORDS_PER_VERTEX * 4

    init {
        val bb = ByteBuffer.allocateDirect(triangleCoords.size * 4)
        bb.order(ByteOrder.nativeOrder())

        vertexBuffer = bb.asFloatBuffer()
        vertexBuffer.put(triangleCoords)
        vertexBuffer.position(0)

        val vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode)
        val fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode)

        program = GLES20.glCreateProgram()
        GLES20.glAttachShader(program, vertexShader)
        GLES20.glAttachShader(program, fragmentShader)
        GLES20.glLinkProgram(program)
    }

    fun draw(mvpMatrix : FloatArray) {
        GLES20.glUseProgram(program)

        positionHandle = GLES20.glGetAttribLocation(program, "vPosition")
        GLES20.glEnableVertexAttribArray(positionHandle)
        GLES20.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, vertexBuffer)

        colorHandle = GLES20.glGetUniformLocation(program, "vColor")
        GLES20.glUniform4fv(colorHandle, 1, color, 0)

        mvpMatrixHandle = GLES20.glGetUniformLocation(program, "uMVPMatrix")
        GLES20.glUniformMatrix4fv(mvpMatrixHandle, 1, false, mvpMatrix, 0)

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount)
        GLES20.glDisableVertexAttribArray(positionHandle)
    }
}
