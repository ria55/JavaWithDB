package application.logger;

import application.helpers.PropertiesHandler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public record LogHandler(Class<?> cl, String filename) {

    private void log(String methodName, LogType logType, String message) {
        LogMessage toLog = new LogMessage(cl, methodName, logType, message);
        writeLog(toLog.toString());
    }

    public void info(String methodName, String message) {
        log(methodName, LogType.INFO, message);
    }

    public void warning(String methodName, String message) {
        log(methodName, LogType.WARNING, message);
    }

    public void error(String methodName, String message) {
        log(methodName, LogType.ERROR, message);
    }

    private void writeLog(String text) {
        String filePath = PropertiesHandler.getInstance().getProperty("log-root-directory") + filename;
        File file = new File(filePath);
        String toWrite = (file.exists() ? "\n" : "") + text;

        try {
            FileWriter writer = new FileWriter(filePath, true);
            writer.write(toWrite);
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
