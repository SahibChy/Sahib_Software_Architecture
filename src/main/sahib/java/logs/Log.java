package main.sahib.java.logs;
import java.io.FileWriter;
import java.io.IOException;

public class Log {
    private static Log instance = null;
    private StringBuilder logData;

    private Log() {
        logData = new StringBuilder();
    }

    public static Log getInstance() {
        if (instance == null) {
            instance = new Log();
        }
        return instance;
    }

    public void addEntry(String entry) {
        logData.append(entry).append("\n");
    }

    public void saveLog(String filename) throws IOException {
        FileWriter writer = new FileWriter(filename);
        writer.write(logData.toString());
        writer.close();
    }

    public String getLogs() {
        return logData.toString();
    }
}
