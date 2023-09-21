package step.learning.basics;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

public class FilesDemo {
    public void run1() {
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

    public void run2() {
        File dir = new File("./uploads");

        if(dir.exists()) {
            if(dir.isDirectory()) {
                System.out.printf("Directory '%s' already exists%n", dir.getName());
            }
            else {
                System.out.printf("'%s' exists, nut it's not a directory%n", dir.getName());
            }
        }
        else {
            if(dir.mkdir()) {
                System.out.printf("Directory '%s' created%n", dir.getName());
            }
            else {
                System.out.printf("Directory '%s' creation error%n", dir.getName());
            }
        }

        File file = new File("./uploads/whitelist.txt");
        if(file.exists()) {
            if(file.isFile()) {
                System.out.printf("File '%s' already exist%n", file.getName());
            }
            else {
                System.out.printf("'%s' exists, nut it's not a file%n", file.getName());
            }
        }
        else {
            try {
                if(file.createNewFile()) {
                    System.out.printf("File '%s' created%n", file.getName());
                }
                else {
                    System.out.printf("File '%s' creation error%n", file.getName());
                }
            }
            catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void run3() {
        String fileName = "test.txt";

        try(OutputStream writer = new FileOutputStream(fileName)) {
            writer.write("Hello World!\n".getBytes());
            writer.flush();
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        try (FileWriter writer = new FileWriter(fileName, true)) {
            writer.write("Hello world!2\n");
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        StringBuilder sb = new StringBuilder();
        try(InputStream reader = new FileInputStream(fileName)) {
            int c;
            while ((c = reader.read()) != -1) {
                sb.append((char)c);
            }
            System.out.println(sb);
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        ByteArrayOutputStream byteBuilder = new ByteArrayOutputStream(4096);
        byte[] buf = new byte[512];
        try(InputStream reader = new BufferedInputStream(new FileInputStream(fileName))) {
            int cnt;
            while ((cnt = reader.read(buf)) > 0) {
                byteBuilder.write(buf, 0, cnt);
            }
            String content = new String(byteBuilder.toByteArray(), StandardCharsets.UTF_16LE);
            System.out.println(content);
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        try(InputStream reader = new FileInputStream(fileName)) {
            Scanner scanner = new Scanner(reader);
            while (scanner.hasNext()) {
                System.out.println(scanner.next());
            }

        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        Random random = new Random();
    }

    public void writeFile() {
        File file = new File("./test.txt");
        Random random = new Random();

        try (FileWriter writer = new FileWriter(file, false)) {
            for (int i = 0; i < 25; i++) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int j = 0; j < 25; j++) {
                    stringBuilder.append((char)random.nextInt(122 - 65 + 1));
                }
                stringBuilder.append("\n");
                writer.write(stringBuilder.toString());
            }
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void longestStringInFile() {
        File file = new File("./test.txt");

        List<String> list = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        try(InputStream reader = Files.newInputStream(file.toPath())) {
            int i;
            while ((i = reader.read()) != -1) {
                char c = (char)i;

                if(c == '\n') {
                    list.add(sb.toString());
                    sb.setLength(0);
                } else {
                    sb.append(c);
                }
            }
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        System.out.println(list.size());

        String max = "";
        for (String s : list) {
            if(s.length() > max.length()) {
                max = s;
            }
        }

        System.out.printf("Longest string (%d digits) is %s%n", max.length(), max);
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
