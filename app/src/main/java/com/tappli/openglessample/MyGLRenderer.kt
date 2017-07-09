package com.tappli.openglessample

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

fun loadShader(type: Int, shaderCode: String): Int {
    val shader = GLES20.glCreateShader(type)

    GLES20.glShaderSource(shader, shaderCode)
    GLES20.glCompileShader(shader)

    return shader
}

class MyGLRenderer : GLSurfaceView.Renderer {

    var triangle: Triangle = Triangle()

    private val mvpMatrix = FloatArray(16, {i -> 0.0f})
    private val projectionMatrix = FloatArray(16, {i -> 0.0f})
    private val viewMatrix = FloatArray(16, {i -> 0.0f})

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        GLES20.glClearColor(0f, 0f, 0f, 1f)

        triangle = Triangle()
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height);

        val ratio = width.toFloat() / height
        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1f, 1f, 3f, 7f)
    }

    override fun onDrawFrame(gl: GL10?) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)

        Matrix.setLookAtM(viewMatrix, 0, 0f, 0f, -3f, 0f, 0f, 0f, 0f, 1.0f, 0.0f)
        Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, viewMatrix, 0)

        triangle.draw(mvpMatrix)
    }
}
