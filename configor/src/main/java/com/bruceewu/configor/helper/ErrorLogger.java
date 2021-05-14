package com.bruceewu.configor.helper;


import com.bruceewu.configor.IConfigor;

public interface ErrorLogger {
    void log(Exception ex);

    void log(String content);

    static void logError(Exception content) {
        ErrorLogger logger = IConfigor.configor().getLogger();
        if (logger != null) {
            logger.log(content);
        }
    }
}
