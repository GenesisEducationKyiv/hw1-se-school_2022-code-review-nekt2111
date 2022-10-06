package com.example.userapi.service.logger;

public interface LoggerService {

    void logInfo(String message);

    void logError(String message);

    void logDebug(String message);

    void setOutputClassName(String className);

    String getOutputClassName();


}
