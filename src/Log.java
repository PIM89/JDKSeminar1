import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Log {
    private String workDir;
    private String fileName;

    public Log() {
        this.workDir = System.getProperty("user.dir");
        this.fileName = (this.workDir + "\\Log.txt").replace("\\", "/");
    }

    private boolean checkFileLog() {
        if (new File(fileName).exists()) return true;
        return false;
    }

    private void keepLog(String msg) {
        try (FileWriter writer = new FileWriter(fileName, true)) {
            writer.write(msg);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    protected void logging(String msg) {
        try (FileWriter writer = new FileWriter("log.txt", true)) {
            writer.write(msg + "\n");
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    protected String loadLog() {
        StringBuilder chatLog = new StringBuilder();
        if (checkFileLog()) {
            try (FileReader fileReader = new FileReader(fileName)) {
                int c;
                while ((c = fileReader.read()) != -1) {
                    chatLog.append((char) c);
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return chatLog.toString();
    }
}