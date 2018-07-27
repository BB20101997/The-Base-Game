package de.webtwob.the.base.game.main.client;

import de.webtwob.the.base.game.api.gui.ICurrentContext;
import de.webtwob.the.base.game.api.gui.IWindow;
import de.webtwob.the.base.game.api.interfaces.IRenderer;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.DoubleStream;

import static org.lwjgl.opengl.GL11.GL_POLYGON;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.*;

/**
 * Created by BB20101997 on 26. Jul. 2018.
 */
public class MainMenuRenderer implements IRenderer {

    private int vertArrayObj;
    private int vertBuffObj[];

    @Override
    public void init(final IWindow window, final ICurrentContext context) {
        vertArrayObj = glGenVertexArrays();
        glBindVertexArray(vertArrayObj);

        Font font = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts()[0];
        GlyphVector vector  = font.createGlyphVector(new FontRenderContext(new AffineTransform(),true,true), "Test");

        int size = vector.getNumGlyphs();

        vertBuffObj = new int[size];

        for(int i = 0;i<size;i++){

            var shape = vector.getGlyphOutline(i);

            List<Float > positions = new LinkedList<>();

            var path = shape.getPathIterator(null);

            float[]              coords = new float[6];
            DoubleStream.Builder stream;

            while (!path.isDone()){
                var type = path.currentSegment(coords);
                stream = DoubleStream.builder().add(coords[0]).add(coords[1]).add(0) //XYZ
                      .add(1).add(0).add(1).add(1); //RGBA
                path.next();
                vertBuffObj[i] = glGenBuffers();
                glBindBuffer(GL_ARRAY_BUFFER,vertBuffObj[i]);
                glBufferData(GL_ARRAY_BUFFER,stream.build().toArray(),GL_STATIC_DRAW);
            }


        }


    }

    @Override
    public void destroy(final IWindow window) {
        glDeleteVertexArrays(vertArrayObj);
        for(int i = 0;vertBuffObj!=null&&i<vertBuffObj.length;i++) {
            glDeleteBuffers(vertBuffObj[i]);
        }
    }

    @Override
    public void render(final IWindow window, final ICurrentContext context) {
        context.setClearColor(window.getBackgroundColor());
        context.clear();

        for(int buf: vertBuffObj){
            glBindBuffer(GL_ARRAY_BUFFER,buf);
            glDrawArrays(GL_POLYGON,0,buf/7);
            //TODO make it draw
        }


    }

    @Override
    public void update(final IWindow window, final ICurrentContext context, final long delta) {

    }
}
