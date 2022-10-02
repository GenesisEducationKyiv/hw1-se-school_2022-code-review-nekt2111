package com.example.userapi.service.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerServiceImpl implements LoggerService {

    private String className;

    private Logger logger = LoggerFactory.getLogger(getOutputClassName());

    @Override
    public void logInfo(String message) {
        logger.info(message);
    }

    @Override
    public void logError(String message) {
        logger.error(message);
    }

    @Override
    public void logDebug(String message) {
        logger.debug(message);
    }

    @Override
    public void setOutputClassName(String className) {
        this.className = className;
        this.logger = LoggerFactory.getLogger(className);
    }

    @Override
    public String getOutputClassName() {
        if (className == null) {
            return this.getClass().getName();
        }

        return className;
    }
}
