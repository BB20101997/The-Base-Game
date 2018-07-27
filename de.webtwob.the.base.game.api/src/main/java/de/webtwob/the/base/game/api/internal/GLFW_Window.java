package de.webtwob.the.base.game.api.internal;

import de.webtwob.the.base.game.api.MainThreadQueue;
import de.webtwob.the.base.game.api.annotations.RequireMainThread;
import de.webtwob.the.base.game.api.event.KeyEvent;
import de.webtwob.the.base.game.api.exceptions.GLFWInitFailException;
import de.webtwob.the.base.game.api.gui.Color;
import de.webtwob.the.base.game.api.gui.ICurrentContext;
import de.webtwob.the.base.game.api.gui.IMainThreadContext;
import de.webtwob.the.base.game.api.gui.IWindow;
import de.webtwob.the.base.game.api.interfaces.IRenderer;
import de.webtwob.the.base.game.api.interfaces.function.IEventHandler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * Created by BB20101997 on 11. Jul. 2018.
 */
public class GLFW_Window implements IWindow {

    static final Map<Long, GLFW_Window> windowMap = new HashMap<>();

    private static final int                    TRUE              = GLFW_TRUE;
    private static final int                    FALSE             = GLFW_FALSE;
    private static final System.Logger          LOGGER            = System.getLogger(GLFW_Window.class.getName());
    final                RenderLoop             renderLoop        = new RenderLoop(this);
    private final        GLFW_MainThreadContext mainThreadContext = new GLFW_MainThreadContext(this);
    Thread renderThread;
    private              long                   windowId;
    private Set<IEventHandler<IWindow, ? super KeyEvent>> keyEventListeners = new HashSet<>();
    private int                                           width;
    private int                                           height;
    private boolean                                       exitOnEscape      = true;
    private Color                                         backgroundColor   = Color.WHITE.withAlpha(0);

    private GLFW_Window(String title, int width, int height) {

        runWithMainThreadContext(context -> {
            if (!glfwInit()) {
                throw new GLFWInitFailException();
            }
            glfwDefaultWindowHints();
            glfwWindowHint(GLFW_OPENGL_DEBUG_CONTEXT, MainThreadQueue.DEBUG_MODE ? TRUE : FALSE);
            glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3); //minimum major version
            glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2); //minimum minor version
            glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE); //using core profile
            glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, TRUE); //disable deprecated features
            glfwWindowHint(GLFW_RESIZABLE, TRUE); //window is resizeable
            glfwWindowHint(GLFW_VISIBLE, FALSE); // window starts hidden

            this.width = width;
            this.height = height;

            windowId = glfwCreateWindow(width, height, title, NULL, NULL);

            glfwSetWindowSizeCallback(windowId, GLFW_Window::sizeCallBack);
            glfwSetKeyCallback(windowId, GLFW_Window::keyCallback);

            windowMap.put(windowId, this);

            var vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            if (vidMode != null) {
                var size = context.getScreenSize();
                context.setScreenPosition((vidMode.width() - size[0]) / 2, (vidMode.height() - size[1]) / 2);
            }

            addKeyListener(event -> {
                if (exitOnEscape && event.key == GLFW_KEY_ESCAPE && event.action == GLFW_RELEASE) {
                    event.getSource().setShouldClose(true);
                }
            });
        });

        try (var context = makeContextCurrent()) {
            context.setSwapInterval(1);
        }
    }

    private static void keyCallback(final long windowId, final int key, final int scancode, final int action, final int mod) {
        var window = getWindowForId(windowId);
        var event  = new KeyEvent(window, key, scancode, action, mod);
        //TODO use a different Queue for Events to not block the Main Thread
        MainThreadQueue.invokeLater(() -> window.keyEventListeners.forEach(handler -> handler.handleEvent(event)));
    }

    static GLFW_Window getWindowForId(long windowId) {
        return windowMap.get(windowId);
    }

    private static void sizeCallBack(long windowId, int width, int height) {
        var window = getWindowForId(windowId);
        window.height = height;
        window.width = width;

        LOGGER.log(
                System.Logger.Level.DEBUG,
                () -> String.format("Window %d has been resized to %dx%d", windowId, width, height)
        );

        //TODO dispatch IEvent to EventQueue
    }

    @RequireMainThread
    public static IWindow createWindow(String title) {
        return new GLFW_Window(title, 300, 300);
    }

    @Override
    public int width() {
        return width;
    }

    @Override
    public int height() {
        return height;
    }

    @Override
    public void setShouldClose(boolean shouldClose) {
        glfwSetWindowShouldClose(windowId, shouldClose);
    }

    @Override
    public Color getBackgroundColor() {
        return backgroundColor;
    }

    @Override
    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    @Override
    public ICurrentContext makeContextCurrent() {
        return new GLFW_WindowContext(this);
    }

    @Override
    public void addKeyListener(IEventHandler<IWindow, KeyEvent> keyEventIEventHandler) {
        keyEventListeners.add(keyEventIEventHandler);
    }

    @Override
    public void runWithMainThreadContext(Runnable run) {
        MainThreadQueue.waitAndInvoke(run);
    }

    @Override
    public void runWithMainThreadContext(Consumer<IMainThreadContext> mainThreadRunner) {
        MainThreadQueue.waitAndInvoke(() -> mainThreadRunner.accept(mainThreadContext));
    }

    @Override
    public boolean runWithMainThreadContext(Predicate<IMainThreadContext> mainThreadContextPredicate) {
        return MainThreadQueue.waitAndInvoke(() -> mainThreadContextPredicate.test(mainThreadContext));
    }

    @Override
    public void removeAllListeners() {
        keyEventListeners.clear();
    }

    @Override
    public void setMainRenderer(final IRenderer renderer) {
        renderLoop.setChild(renderer);
    }

    public long getWindowId() {
        return windowId;
    }
}
