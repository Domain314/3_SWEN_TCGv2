package at.domain314.backend.database;
import java.io.*;
import java.net.URL;

public abstract class AbstractDataBase {

    protected InputStream input;
    protected OutputStream output;

    public void openReadUrl(String url) throws IOException {
        var conn = new URL(url).openConnection();
        this.input = conn.getInputStream();
    }

    public void openReadFile(String filename) throws FileNotFoundException {
        var file = new File(filename);
        this.input = new FileInputStream(file);
    }

    public void openWriteFile(String filename) throws FileNotFoundException {
        var file = new File(filename);
        this.output = new FileOutputStream(file);
    }

    public void openWriteConsole() {
        this.output = System.out;
    }


}
