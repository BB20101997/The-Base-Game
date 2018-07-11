package de.webtwob.the.base.game.api.exceptions;

/**
 * Created by BB20101997 on 11. Jul. 2018.
 */
public class GLFWInitFailException extends RuntimeException {

    public GLFWInitFailException(){
        super("glfwInit() failed!");
    }

}
