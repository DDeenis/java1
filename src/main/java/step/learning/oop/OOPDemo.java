package step.learning.oop;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.sound.midi.Soundbank;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.Objects;

public class OOPDemo {
    public void run() {
        Library library = new Library() ;
        try {
            library.add(new Book("A. Mogus", "Art of impostor"));
            library.add(new Newspaper("Daily Mail", "2023-02-28"));
            library.add(new Journal("Quantum Mechanics", 1));
            library.add(new Hologram("Holograms isn't real", 616));
            library.save();
        }
        catch (Exception ex) {
            System.err.printf("Literature creation error: %s%n", ex.getMessage());
        }
        library.printAllCards();
        System.out.println("----------- COPYABLE -----------");
        library.printCopyable();
        System.out.println("----------- NON COPYABLE -----------");
        library.printNonCopyable();
        System.out.println("----------- PERIODIC -----------");
        library.printPeriodic();
        System.out.println("----------- NON PERIODIC -----------");
        library.printNonPeriodic();
        System.out.println("----------- PRINTABLE -----------");
        library.printPrintable();
        System.out.println("----------- NON PRINTABLE -----------");
        library.printNonPrintable();
        System.out.println("----------- MULTIPLE -----------");
        library.printMultiple();
        System.out.println("----------- NON MULTIPLE -----------");
        library.printNonMultiple();
    }

    public void run1() {
        Gson gson = new Gson();
        String str = "{\"author\": \"A. Mogus\", \"title\": \"Art of impostor\"}";
        Book book = gson.fromJson(str, Book.class);
        System.out.println(book.getCard());
        System.out.println(gson.toJson(book));
        book.setAuthor(null);
        System.out.println(gson.toJson(book));

        Gson gson2 = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        System.out.println(gson2.toJson(book));

        try(
            InputStream stream = this.getClass().getClassLoader().getResourceAsStream("book.json");
            InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(stream))
        ) {
            book = gson.fromJson(reader, Book.class);
            System.out.println(book.getCard());
        }
        catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void run3() {
        String str = "{\"author\": \"A. Mogus\", \"title\": \"Art of impostor\"}";
        JsonObject literatureObj = JsonParser.parseString(str).getAsJsonObject();
        Literature literature = null;
        if(literatureObj.has("author")) {
            literature = new Book(literatureObj.get("author").getAsString(), literatureObj.get("title").getAsString());
        }
        else if(literatureObj.has("number")) {
            literature = new Journal(literatureObj.get("title").getAsString(), literatureObj.get("number").getAsInt());
        }
        else if(literatureObj.has("date")) {
            try {
                literature = new Newspaper(literatureObj.get("title").getAsString(), literatureObj.get("date").getAsString());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println(Objects.requireNonNull(literature).getCard());
    }

    public void run4() {
        Library library = new Library();
        try {
            library.load();
            library.printAllCards();
        }
        catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }
}
