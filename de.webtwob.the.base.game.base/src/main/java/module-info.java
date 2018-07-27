import de.webtwob.the.base.game.api.service.IBaseModFactory;
import de.webtwob.the.base.game.base.TheBaseMode;

/**
 * Created by BB20101997 on 10. Jul. 2018.
 */
module de.webtwob.the.base.game.base {
    exports de.webtwob.the.base.game.base;
    requires de.webtwob.the.base.game.api;
    requires org.lwjgl.glfw;
    requires org.lwjgl.opengl;
    requires org.joml;
    requires io.netty.all;
    requires annotations;

    provides IBaseModFactory with TheBaseMode;
}
