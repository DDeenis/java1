package step.learning.basics;

import java.io.File;
import java.util.Date;

public class FilesDemo {
    public void run() {
        File dir = new File("./");

        if (!dir.exists()) {
            System.out.printf("Path '%s' not exist", dir.getPath());
            return;
        }

        System.out.printf("Path is %s%n", dir.isDirectory() ? "directory" : "file");
        System.out.println(dir.getAbsolutePath());

        for (String fileName : dir.list()) {
            System.out.printf(fileName);
        }
    }

    public void dir() {
        File directory = new File("./");

        System.out.println("Mode\tLastWriteTime\t\t\t\t\tLength\tName");
        System.out.println("----\t-------------\t\t\t\t\t------\t----");
        for (File file : directory.listFiles()) {
            System.out.printf(
                    "%c%c%c%c%c-\t%s\t%s\t%s%n",
                    file.isDirectory() ? 'd' : '-',
                    file.isFile() ? 'a' : '-',
                    file.canRead() ? 'r' : '-',
                    file.canWrite() ? 'w' : '-',
                    file.canExecute() ? 'x' : '-',
                    new Date(file.lastModified()),
                    file.length(),
                    file.getName()
            );
        }
    }
}
