package de.webtwob.the.base.game.api.gui;

import org.lwjgl.glfw.GLFW;

import java.io.Closeable;

import static org.lwjgl.opengl.GL20.*;

/**
 * Created by BB20101997 on 21. Jul. 2018.
 */
public class Shader implements Closeable {

    private static final System.Logger LOGGER = System.getLogger(Shader.class.getName());

    final int shaderId;

    public Shader(ShaderType type, String source) {
        this(type.id, source);
    }

    public Shader(int type, String source) {
        shaderId = glCreateShader(type);
        glShaderSource(shaderId, source);

    }

    public boolean compile() {
        glCompileShader(shaderId);
        var status = glGetShaderi(shaderId, GL_COMPILE_STATUS);
        if (status != GLFW.GLFW_TRUE) {
            LOGGER.log(System.Logger.Level.ERROR, "Shader Compile Failed!");
            LOGGER.log(System.Logger.Level.ERROR, ()->glGetShaderInfoLog(shaderId));
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void close() {
        glDeleteShader(shaderId);
    }

    public enum ShaderType {
        VERTEX(GL_VERTEX_SHADER), FRAGMENT(GL_FRAGMENT_SHADER);

        public final int id;

        ShaderType(int id) {
            this.id = id;
        }
    }

}
