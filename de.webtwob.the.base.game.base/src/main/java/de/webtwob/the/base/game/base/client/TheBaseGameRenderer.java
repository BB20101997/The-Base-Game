package de.webtwob.the.base.game.base.client;

import de.webtwob.the.base.game.api.event.KeyEvent;
import de.webtwob.the.base.game.api.exceptions.ShaderCompileException;
import de.webtwob.the.base.game.api.exceptions.ShaderProgramLinkException;
import de.webtwob.the.base.game.api.gui.*;
import de.webtwob.the.base.game.api.interfaces.IRenderer;
import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryStack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;
import java.util.stream.Collectors;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

/**
 * Created by BB20101997 on 11. Jul. 2018.
 */
public class TheBaseGameRenderer implements IRenderer {

    private static final System.Logger LOGGER = System.getLogger(TheBaseGameRenderer.class.getName());
    private              String        fragmentSource;
    private              String        vertexSource;

    private int r;
    private int g;
    private int b;
    private int a;

    private int           uniModel;
    private float         previousAngle  = 0f;
    private float         angle          = 0f;
    private float         anglePerSecond = 50f;
    private int           vertBuffObj;
    private ShaderProgram shaderProgram;
    private Shader        vertexShader;
    private Shader        fragmentShader;
    private int           vertArrObj;

    @Override
    public void init(IWindow window, ICurrentContext context) {
        LOGGER.log(System.Logger.Level.INFO, "Initializing TheBaseGameRenderer");
        try {
            var res = TheBaseGameRenderer.class.getResource("/de/webtwob/the/base/game/base/shader/screen.vert");
            LOGGER.log(System.Logger.Level.TRACE, () -> "Loading Vertex Shader " + res);
            try (var reader = new BufferedReader(new InputStreamReader(res.openStream()))) {
                vertexSource = reader.lines().collect(Collectors.joining("\n"));
            }
        } catch (IOException ignore) {
        }

        try {
            var res = TheBaseGameRenderer.class.getResource("/de/webtwob/the/base/game/base/shader/screen.frag");
            LOGGER.log(System.Logger.Level.TRACE, () -> "Loading Fragment Shader " + res);
            try (var reader = new BufferedReader(new InputStreamReader(res.openStream()))) {
                fragmentSource = reader.lines().collect(Collectors.joining("\n"));
            }
        } catch (IOException ignore) {
        }

        window.setBackgroundColor(Color.BASE);

        vertArrObj = glGenVertexArrays();
        glBindVertexArray(vertArrObj);

        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer vertices = stack.mallocFloat(3 * 6);
            vertices.put(-0.6f).put(-0.4f).put(0f).put(1f).put(0f).put(0f);
            vertices.put(0.6f).put(-0.4f).put(0f).put(0f).put(1f).put(0f);
            vertices.put(0f).put(0.6f).put(0f).put(0f).put(0f).put(1f);
            vertices.flip();

            vertBuffObj = glGenBuffers();
            glBindBuffer(GL_ARRAY_BUFFER, vertBuffObj);
            glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
        }

        vertexShader = new Shader(Shader.ShaderType.VERTEX, vertexSource);
        if (!vertexShader.compile()) {
            throw new ShaderCompileException("Vertex Shader failed to compile!");
        }

        fragmentShader = new Shader(Shader.ShaderType.FRAGMENT, fragmentSource);
        if (!fragmentShader.compile()) {
            throw new ShaderCompileException("Fragment Shader failed to compile!");
        }

        shaderProgram = new ShaderProgram();
        shaderProgram.attachShader(vertexShader);
        shaderProgram.attachShader(fragmentShader);
        shaderProgram.bindFragDataLocation(0, "fragColor");
        if (!shaderProgram.link()) {
            throw new ShaderProgramLinkException("ShaderProgram Failed to Link!");
        }

        shaderProgram.use();

        int floatSize = 4;

        int posAttr = shaderProgram.attributeLocation("position");
        glEnableVertexAttribArray(posAttr);
        glVertexAttribPointer(posAttr, 3, GL_FLOAT, false, 6 * floatSize, 0);

        int colAtrr = shaderProgram.attributeLocation("color");
        glEnableVertexAttribArray(colAtrr);
        glVertexAttribPointer(colAtrr, 3, GL_FLOAT, false, 6 * floatSize, 3L * floatSize);

        uniModel = shaderProgram.uniformLocation("model");
        Matrix4f model = new Matrix4f();
        glUniformMatrix4fv(uniModel, false, model.get(new float[16]));

        int      uniView = shaderProgram.uniformLocation("view");
        Matrix4f view    = new Matrix4f();
        glUniformMatrix4fv(uniView, false, view.get(new float[16]));

        int      uniProjection = shaderProgram.uniformLocation("projection");
        float    ratio         = window.width() / (float) window.height();
        Matrix4f projection    = new Matrix4f().ortho(-ratio, ratio, -1f, 1f, -1f, 1f);
        glUniformMatrix4fv(uniProjection, false, projection.get(new float[16]));
    }

    @Override
    public void destroy(final IWindow window) {
        LOGGER.log(System.Logger.Level.INFO, "Destroying TheBaseGameRenderer");
        glDeleteVertexArrays(vertArrObj);
        glDeleteBuffers(vertBuffObj);
        if (vertexShader != null) {
            vertexShader.close();
        }
        if (fragmentShader != null) {
            fragmentShader.close();
        }
        if (shaderProgram != null) {
            shaderProgram.close();
        }
    }

    @Override
    public void update(final IWindow window, final ICurrentContext context, final long delta) {
        previousAngle = angle;
        angle += delta * anglePerSecond / 1_000_000_000;
    }

    @Override
    public void render(IWindow window, final ICurrentContext context) {
        context.setClearColor(window.getBackgroundColor());
        context.clear();

        float alpha = 0.5f;

        float    lerpAngle = (1f - alpha) * previousAngle + alpha * angle;
        Matrix4f model     = new Matrix4f().rotate(lerpAngle, 0f, 0f, 1f);
        glUniformMatrix4fv(uniModel, false, model.get(new float[16]));

        glDrawArrays(GL_TRIANGLES, 0, 3);
        context.swapBuffer();

    }

    public void keyCallBack(KeyEvent event) {
        if (event.action == GLFW.GLFW_RELEASE) {
            boolean changed = false;
            switch (event.key) {
                case GLFW.GLFW_KEY_G:
                    changed = true;
                    g++;
                    g %= 9;
                    break;
                case GLFW.GLFW_KEY_R:
                    changed = true;
                    r++;
                    r %= 9;
                    break;
                case GLFW.GLFW_KEY_B:
                    changed = true;
                    b++;
                    b %= 9;
                    break;
                case GLFW.GLFW_KEY_A:
                    changed = true;
                    a++;
                    a %= 9;
                    break;
                case GLFW.GLFW_KEY_0:
                    changed = true;
                    r = g = b = a = 0;
                    break;
                default:
                    break;
            }
            if (changed) {
                event.getSource()
                     .setBackgroundColor(Color.BASE.withRed(r * 0.125f)
                                                   .withGreen(g * 0.125f)
                                                   .withBlue(b * 0.125f)
                                                   .withAlpha(a * 0.125f));
            }
        }
    }
}
