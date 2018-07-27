package de.webtwob.the.base.game.api.internal;

import org.apache.logging.log4j.LogManager;

import java.util.ResourceBundle;

/**
 * Created by BB20101997 on 21. Jul. 2018.
 */
public class LoggerFinder extends System.LoggerFinder {

    @Override
    public System.Logger getLogger(final String name, final Module module) {
        return new Logger(name, module);
    }

    private class Logger implements System.Logger {

        private final org.apache.logging.log4j.Logger logger;
        private final String                          name;

        public Logger(String name, Module module) {
            this.name = name;
            this.logger = LogManager.getLogger(module.getName() + "-" + name);
        }

        private org.apache.logging.log4j.Level convert(Level level) {
            switch (level) {
                case ALL:
                    return org.apache.logging.log4j.Level.ALL;
                case TRACE:
                    return org.apache.logging.log4j.Level.TRACE;
                case DEBUG:
                    return org.apache.logging.log4j.Level.DEBUG;
                case INFO:
                    return org.apache.logging.log4j.Level.INFO;
                case WARNING:
                    return org.apache.logging.log4j.Level.WARN;
                case ERROR:
                    return org.apache.logging.log4j.Level.ERROR;
                case OFF:
                    return org.apache.logging.log4j.Level.OFF;
                default:
                    return org.apache.logging.log4j.Level.forName(level.getName(), convert(level.getSeverity()));
            }
        }

        private int convert(int severity) {
            int nSeverity = Integer.MAX_VALUE / 2 - severity / 2;
            logger.log(org.apache.logging.log4j.Level.TRACE,
                       () -> "Converting Java Log Level Severity " + severity + " to Log4J2 Level " + nSeverity);
            return nSeverity;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public boolean isLoggable(final Level level) {
            return true;
        }

        @Override
        public void log(final Level level, final ResourceBundle bundle, final String msg, final Throwable thrown) {
            logger.log(convert(level), msg, thrown);
        }

        @Override
        public void log(final Level level, final ResourceBundle bundle, final String format, final Object... params) {
            logger.log(convert(level), format, params);
        }
    }
}
