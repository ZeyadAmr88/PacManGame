package org.graphics;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import java.io.IOException;
import java.io.InputStream;

public class ImageResource {
    private Texture texture;

    public void loadTexture(GL2 gl, String filePath) {
        try {
            InputStream stream = getClass().getResourceAsStream(filePath);
            texture = TextureIO.newTexture(stream, false, TextureIO.PNG);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void bind(GL2 gl) {
        if (texture != null) {
            texture.bind(gl);
        }
    }
}
