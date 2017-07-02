package com.tappli.openglessample

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

class Triangle {
    val vertexBuffer : FloatBuffer

    init {
        val triangleCoods = floatArrayOf(
                0.0f,  0.622008459f, 0.0f, // top
                -0.5f, -0.311004243f, 0.0f, // bottom left
                0.5f, -0.311004243f, 0.0f  // bottom right
        )

        val bb = ByteBuffer.allocateDirect(triangleCoods.size * 4)
        bb.order(ByteOrder.nativeOrder())

        vertexBuffer = bb.asFloatBuffer()
        vertexBuffer.put(triangleCoods)
        vertexBuffer.position(0)
    }
}