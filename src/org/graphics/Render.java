package org.graphics;

import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.util.FPSAnimator;

public class Render {
    private EventListener eventListener;
    private GLWindow window;
    private FPSAnimator animator;

    public Render() {
        // Initialize the game window
        initGameWindow();
    }

    private void initGameWindow() {
        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);

        window = GLWindow.create(capabilities);
        window.setSize(800, 600);
        window.setTitle("Pac-Man Game");
        window.setVisible(true);
        window.addWindowListener(new com.jogamp.newt.event.WindowAdapter() {
            @Override
            public void windowDestroyNotify(com.jogamp.newt.event.WindowEvent e) {
                animator.stop();
                System.exit(0);
            }
        });

        eventListener = new EventListener();
        window.addGLEventListener(eventListener);
        window.addKeyListener(new KeyInput(eventListener));

        animator = new FPSAnimator(window, 60);
        animator.start();
    }

    public static void main(String[] args) {
        // Start the game by creating an instance of Render, which initializes everything
        new Render();
    }
}
