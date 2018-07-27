package de.webtwob.the.base.game.main;

import de.webtwob.the.base.game.api.MainThreadQueue;
import de.webtwob.the.base.game.main.client.MainClient;
import de.webtwob.the.base.game.main.server.MainServer;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilderFactory;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;

import java.util.Arrays;

import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwTerminate;

/**
 * Created by BB20101997 on 10. Jul. 2018.
 */
public class Main {

    public static final System.Logger LOGGER;
    public static final String STDOUT = "Stdout";

    static {

        //setup log4j2 programmatically since I cant figure out where to put the config file
         var builder = ConfigurationBuilderFactory.newConfigurationBuilder();
        builder.setStatusLevel(Level.ERROR)
               .setConfigurationName("ConfigName")
               .add(builder.newFilter("ThresholdFilter", Filter.Result.ACCEPT, Filter.Result.NEUTRAL)
                           .addAttribute("level", Level.DEBUG))
               .add(builder.newAppender(STDOUT, "CONSOLE")
                           .addAttribute("target", ConsoleAppender.Target.SYSTEM_OUT)
                           .add(builder.newLayout("PatternLayout")
                                       .addAttribute("pattern", "%d [%t] [%c] %-5level: %msg%n%throwable"))
                           .add(builder.newFilter("MarkerFilter", Filter.Result.DENY, Filter.Result.NEUTRAL)
                                       .addAttribute("marker", "FLOW")))
               .add(builder.newLogger("org.apache.logging.log4j", Level.DEBUG)
                           .add(builder.newAppenderRef(STDOUT)).addAttribute("additivity", false))
               .add(builder.newRootLogger(Level.ERROR).add(builder.newAppenderRef(STDOUT)));
        var ctx = Configurator.initialize(builder.build());

      LOGGER = System.getLogger(Main.class.getName());
    }

    public static void main(String[] args) {



        LOGGER.log(System.Logger.Level.INFO, () -> "Hello LWJGL " + Version.getVersion() + "!");

        MainThreadQueue.MAIN_THREAD_ID = Thread.currentThread().getId();

        try (var cb = GLFWErrorCallback.createPrint(System.err)) {

            cb.set();

            if (Arrays.asList(args).contains("-server")) {
                new MainServer().run();
            } else {
                new MainClient().run();
            }

            MainThreadQueue.runMainThreadQueue();
            glfwTerminate();
            glfwSetErrorCallback(null);
        }
    }

}
