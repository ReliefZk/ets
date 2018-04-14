package com.reliefzk.middleware.ets.util.log;

import org.slf4j.ILoggerFactory;

/**
 * The type Logger factory.
 */
public class LoggerFactory {

    /**
     * Gets logger.
     *
     * @param name the name
     * @return the logger
     */
    public static Logger getLogger(String name) {
        ILoggerFactory iLoggerFactory = org.slf4j.LoggerFactory.getILoggerFactory();
        org.slf4j.Logger logger = iLoggerFactory.getLogger(name);
        Logger result = new LoggerImpl(logger);
        return result;
    }

    /**
     * Gets logger.
     *
     * @param clazz the clazz
     * @return the logger
     */
    public static Logger getLogger(Class<?> clazz) {
        return getLogger(clazz.getName());
    }
}