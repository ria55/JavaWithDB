package complements.logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogMessage {

    private static final String SEPARATOR = "::";
    //public static final String DOT = ".";

    private final LogType logType;
    private final Class<?> c;
    private final String methodName;
    private final LocalDateTime time;
    private final String message;

    public LogMessage(Class<?> c, String methodName, LogType logType, String message) {
        this.logType = logType;
        this.c = c;
        this.methodName = methodName;
        this.message = message;
        time = LocalDateTime.now();
    }

    private String time() {
        return time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss"));
    }

    @Override
    public String toString() {
        return logType + SEPARATOR +
                time() + SEPARATOR +
                c.getName() + SEPARATOR +
                methodName + SEPARATOR +
                message;
    }
}
