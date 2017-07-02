package com.tappli.openglessample

import android.content.Context
import android.opengl.GLSurfaceView

class MyGLSurfaceView(context: Context) : GLSurfaceView(context) {

    val renderer : MyGLRenderer

    init {
        setEGLContextClientVersion(2)

        renderer = MyGLRenderer()
        setRenderer(renderer)
    }
}
