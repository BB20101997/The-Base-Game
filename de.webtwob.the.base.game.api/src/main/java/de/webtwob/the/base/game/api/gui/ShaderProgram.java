package de.webtwob.the.base.game.api.gui;

import org.lwjgl.glfw.GLFW;

import java.io.Closeable;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindFragDataLocation;

/**
 * Created by BB20101997 on 21. Jul. 2018.
 */
public class ShaderProgram implements Closeable {

    private final int                             programId;
    private System.Logger LOGGER = System.getLogger(ShaderProgram.class.getName());

    public ShaderProgram(){
        programId = glCreateProgram();
    }

    public void attachShader(Shader shader){
        glAttachShader(programId,shader.shaderId);
    }

    public boolean link(){
        glLinkProgram(programId);
        int status = glGetProgrami(programId, GL_LINK_STATUS);
        if (status != GLFW.GLFW_TRUE) {
            LOGGER.log(System.Logger.Level.ERROR, "Shader Program Link Failed!");
            LOGGER.log(System.Logger.Level.ERROR, ()->glGetProgramInfoLog(programId));
            return false;
        } else {
            return true;
        }
    }

    public void bindFragDataLocation(int colorNumber,String name){
        glBindFragDataLocation(programId,colorNumber,name);
    }

    public void use(){
        glUseProgram(programId);
    }

    public int attributeLocation(String name){
        return glGetAttribLocation(programId, name);
    }

    public int uniformLocation(String name){
        return glGetUniformLocation(programId,name);
    }

    public void close() {
        glDeleteProgram(programId);
    }
}
