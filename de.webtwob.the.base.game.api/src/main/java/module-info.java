
/**
 * Created by BB20101997 on 10. Jul. 2018.
 */
module de.webtwob.the.base.game.api {
    requires org.lwjgl.glfw;
    requires org.lwjgl.opengl;
    requires io.netty.all;
    requires org.apache.logging.log4j;
    requires org.apache.logging.log4j.core;
    requires annotations;

    //make sure the internal package is not exported

    exports de.webtwob.the.base.game.api;
    exports de.webtwob.the.base.game.api.service;
    exports de.webtwob.the.base.game.api.util;
    exports de.webtwob.the.base.game.api.gui;
    exports de.webtwob.the.base.game.api.event;
    exports de.webtwob.the.base.game.api.interfaces;
    exports de.webtwob.the.base.game.api.exceptions;
    exports de.webtwob.the.base.game.api.network;

    provides System.LoggerFinder with de.webtwob.the.base.game.api.internal.LoggerFinder;

}
