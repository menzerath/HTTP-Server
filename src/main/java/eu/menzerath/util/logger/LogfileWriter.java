package eu.menzerath.util.logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;

public class LogfileWriter implements Runnable {
    private BlockingQueue<String> queue;
    private File logfile;

    /**
     * Konstruktor; speichert die BlockingQueue und die Log-Datei
     * @param queue     Queue, die neue Log-Einträge bereithält
     * @param logfile   Datei, in die neue Log-Einträge geschrieben werden sollen
     */
    public LogfileWriter(BlockingQueue<String> queue, File logfile) {
        this.queue = queue;
        this.logfile = logfile;
    }

    /**
     * Schreibt in einem bestimmten Intervall die neuen Log-Einträge in die Log-Datei
     */
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try (PrintWriter printWriter = new PrintWriter(new FileOutputStream(logfile, true))) {
                printWriter.append(queue.take()).append("\r\n");
                printWriter.close();
            } catch (IOException | InterruptedException ignored) {
            }

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}