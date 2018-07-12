package de.webtwob.the.base.game.api.gui;

import de.webtwob.the.base.game.api.MainThreadQueue;
import de.webtwob.the.base.game.api.exceptions.GLFWInitFailException;
import de.webtwob.the.base.game.api.util.RequireMainThread;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * Created by BB20101997 on 11. Jul. 2018.
 */
public class GLFW_Window {

    static final Map<Long,GLFW_Window> windowMap = new HashMap<>();

    private static final int           TRUE   = GLFW_TRUE;
    private static final int           FALSE  = GLFW_FALSE;
    private static       System.Logger logger = System.getLogger(GLFW_Window.class.getName());

    long windowId;
    private int width;
    private int height;

    private Color backgroundColor = new Color(1,1,1,0);



    static GLFW_Window getWindowForId(long windowId){
        return windowMap.get(windowId);
    }

    private GLFW_Window(String title) {

        runWithMainThreadContext(context -> {
            if (!glfwInit()) {
                throw new GLFWInitFailException();
            }
            glfwDefaultWindowHints();
            //TODO add a flag to enable this: glfwWindowHint(GLFW_OPENGL_DEBUG_CONTEXT,GLFW_TRUE);
            glfwWindowHint(GLFW_RESIZABLE, TRUE);
            glfwWindowHint(GLFW_VISIBLE, FALSE);

            windowId = glfwCreateWindow(300, 300, title, NULL, NULL);

            windowMap.put(windowId,this);

            var vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            if (vidMode != null) {
                var size = context.getScreenSize();

                context.setScreenPosition((vidMode.width() - size[0]) / 2, (vidMode.height() - size[1]) / 2);

            }
        });

        try (var context = makeContextCurrent()) {
            context.setSwapIntervall(1);
        }
    }

    @RequireMainThread
    public static GLFW_Window createWindow(String title) {
       return new GLFW_Window(title);
    }

    public void setShouldClose(boolean shouldClose){
        glfwSetWindowShouldClose(windowId,shouldClose);
    }

    public Color getBackgroundColor(){
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor){
        this.backgroundColor = backgroundColor;
    }

    public GLFW_WindowContext makeContextCurrent() {
        return new GLFW_WindowContext(this);
    }

    public void runWithMainThreadContext(Consumer<GLFW_MainThreadContext> mainThreadRunner) {
        MainThreadQueue.waitAndInvoke(()->mainThreadRunner.accept(new GLFW_MainThreadContext(this)));
    }

    public <R> R runWithMainThreadContext(Function<GLFW_MainThreadContext, R> mainThreadContextFunction) {
       return MainThreadQueue.waitAndInvoke(()->mainThreadContextFunction.apply(new GLFW_MainThreadContext(this)));
    }

    public boolean runWithMainThreadContext(Predicate<GLFW_MainThreadContext> mainThreadContextPredicate) {
        return MainThreadQueue.waitAndInvoke(()->mainThreadContextPredicate.test(new GLFW_MainThreadContext(this)));
    }

}
