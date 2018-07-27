import de.webtwob.the.base.game.api.service.IBaseModFactory;

/**
 * Created by BB20101997 on 10. Jul. 2018.
 */
open module de.webtwob.the.base.game.main {
    requires de.webtwob.the.base.game.base;
    requires org.lwjgl;
    requires org.lwjgl.glfw;
    requires org.lwjgl.opengl;
    requires org.apache.logging.log4j;
    requires de.webtwob.the.base.game.api;
    requires org.apache.logging.log4j.core;
    requires java.desktop;

    uses IBaseModFactory;

    exports de.webtwob.the.base.game.main;

}
