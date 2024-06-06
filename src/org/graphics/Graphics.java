package org.graphics;

import com.jogamp.opengl.GL2;

public class Graphics {
    public static void drawCircle(GL2 gl, float x, float y, float radius, float red, float green, float blue) {
        gl.glColor3f(red, green, blue); // Set the color
        gl.glBegin(GL2.GL_POLYGON); // Begin drawing a polygon (circle)
        for (int i = 0; i <= 360; i++) {
            double angle = Math.toRadians(i);
            gl.glVertex2d(x + Math.cos(angle) * radius, y + Math.sin(angle) * radius); // Define each vertex of the circle
        }
        gl.glEnd(); // End drawing
    }
}
