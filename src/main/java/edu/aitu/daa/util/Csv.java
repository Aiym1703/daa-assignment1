package edu.aitu.daa.util;
import java.io.*;
import java.nio.charset.StandardCharsets;
public class Csv {
    private final File file;
    private boolean headerWritten = false;
    public Csv(String path) {
        this.file = new File(path);
    }

    public void writeHeader(String... cols) throws IOException {
        if (headerWritten && file.length() > 0) return;
        try (Writer w = new OutputStreamWriter(new FileOutputStream(file, false), StandardCharsets.UTF_8)) {
            w.write(String.join(",", cols));
            w.write("\n");
        }
        headerWritten = true;
    }

    public void append(Object... cols) throws IOException {
        try (Writer w = new OutputStreamWriter(new FileOutputStream(file, true), StandardCharsets.UTF_8)) {
            for (int i = 0; i < cols.length; i++) {
                if (i > 0) w.write(",");
                w.write(String.valueOf(cols[i]));
            }
            w.write("\n");
        }
    }
}
