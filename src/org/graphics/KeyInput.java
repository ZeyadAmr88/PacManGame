package org.graphics;

import com.jogamp.newt.event.KeyAdapter;
import com.jogamp.newt.event.KeyEvent;

public class KeyInput extends KeyAdapter {
    private EventListener eventListener;

    public KeyInput(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        // Handle key press events to move Pac-Man
        switch (key) {
            case KeyEvent.VK_UP:
                eventListener.movePacMan(0, 5); // Move Pac-Man up
                break;
            case KeyEvent.VK_DOWN:
                eventListener.movePacMan(0, -5); // Move Pac-Man down
                break;
            case KeyEvent.VK_LEFT:
                eventListener.movePacMan(-5, 0); // Move Pac-Man left
                break;
            case KeyEvent.VK_RIGHT:
                eventListener.movePacMan(5, 0); // Move Pac-Man right
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Handle key release events if needed
    }
}
