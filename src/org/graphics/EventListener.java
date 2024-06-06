package org.graphics;

import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;

import java.util.Random;

public class EventListener implements GLEventListener {
    private float pacManX = 400, pacManY = 300; // Pac-Man starting position
    private float pointX, pointY; // Point position
    private Random random;
    private ImageResource pacManImage;
    private int pointsCollected;
    private Barrier[] barriers;
    private GLUT glut;

    public EventListener() {
        random = new Random();
        pacManImage = new ImageResource();
        pointsCollected = 0;
        glut = new GLUT();

        // Initialize barriers
        barriers = new Barrier[] {
            new Barrier(100, 100, 700, 100),
            new Barrier(100, 500, 700, 500),
            new Barrier(100, 100, 100, 500),
            new Barrier(700, 100, 700, 500),
            new Barrier(200, 200, 600, 200),
            new Barrier(200, 400, 600, 400)
        };
        
        generateRandomPoint();
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        pacManImage.loadTexture(gl, "/res/image/img.jpg"); // Update this line with your image path
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

        // Draw Pac-Man
        drawPacMan(gl);

        // Draw the point
        drawPoint(gl);

        // Draw barriers
        drawBarriers(gl);

        // Display the counter
        displayCounter(gl);

        // Check for collision
        checkCollision();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(0, width, 0, height, -1, 1);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        // Cleanup resources if needed
    }

    private void drawPacMan(GL2 gl) {
        pacManImage.bind(gl);
        gl.glEnable(GL2.GL_TEXTURE_2D);
        gl.glBegin(GL2.GL_POLYGON);
        for (int i = 0; i <= 360; i++) {
            double angle = Math.toRadians(i);
            float x = (float) (pacManX + Math.cos(angle) * 20);
            float y = (float) (pacManY + Math.sin(angle) * 20);
            gl.glTexCoord2f((float) Math.cos(angle) * 0.5f + 0.5f, (float) Math.sin(angle) * 0.5f + 0.5f);
            gl.glVertex2f(x, y);
        }
        gl.glEnd();
        gl.glDisable(GL2.GL_TEXTURE_2D);
    }

    private void drawPoint(GL2 gl) {
        Graphics.drawCircle(gl, pointX, pointY, 10, 1, 0, 0);
    }

    private void drawBarriers(GL2 gl) {
        gl.glColor3f(0, 0, 1); // Set barrier color to blue
        for (Barrier barrier : barriers) {
            gl.glBegin(GL2.GL_LINES);
            gl.glVertex2f(barrier.x1, barrier.y1);
            gl.glVertex2f(barrier.x2, barrier.y2);
            gl.glEnd();
        }
    }

    private void displayCounter(GL2 gl) {
        gl.glColor3f(1, 1, 1);
        gl.glRasterPos2f(10, 580);
        String text = "Points Collected: " + pointsCollected;
        for (char c : text.toCharArray()) {
            glut.glutBitmapCharacter(GLUT.BITMAP_HELVETICA_18, c);
        }
    }

    private void checkCollision() {
        float dx = pacManX - pointX;
        float dy = pacManY - pointY;
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        if (distance < 35) { // If Pac-Man reaches the point
            pointsCollected++;
            generateRandomPoint();
        }
    }

    private void generateRandomPoint() {
        // Ensure the point is generated within the inner area defined by the barriers
        pointX = 150 + random.nextInt(500); // x range: [150, 650]
        pointY = 150 + random.nextInt(350); // y range: [150, 500]
    }

    public void movePacMan(float dx, float dy) {
        float newPacManX = pacManX + dx;
        float newPacManY = pacManY + dy;
        if (!checkBarrierCollision(newPacManX, newPacManY)) {
            pacManX = newPacManX;
            pacManY = newPacManY;
        }
    }

    private boolean checkBarrierCollision(float x, float y) {
        for (Barrier barrier : barriers) {
            if (barrier.isColliding(x, y, 25)) {
                return true;
            }
        }
        return false;
    }

    private class Barrier {
        float x1, y1, x2, y2;

        public Barrier(float x1, float y1, float x2, float y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }

        public boolean isColliding(float x, float y, float radius) {
            float closestX = clamp(x, x1, x2);
            float closestY = clamp(y, y1, y2);
            float dx = x - closestX;
            float dy = y - closestY;
            return (dx * dx + dy * dy) < (radius * radius);
        }

        private float clamp(float value, float min, float max) {
            return Math.max(min, Math.min(max, value));
        }
    }
}
