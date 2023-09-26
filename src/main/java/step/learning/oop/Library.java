package step.learning.oop;

import com.google.gson.*;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Library {
    private final List<Literature> literature;

    public Library() {
        literature = new LinkedList<>();
    }

    public void add(Literature item) {
        literature.add(item);
    }

    public List<Literature> getLiterature() {
        return this.literature;
    }

    public void save() throws IOException {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        FileWriter writer = new FileWriter("./src/main/resources/library.json");
        writer.write(gson.toJson(getLiterature()));
        writer.close();
    }

    public void load() {
        try (
            InputStreamReader reader = new InputStreamReader(
                Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("library.json"))
            )
        ) {
            for (JsonElement item : JsonParser.parseReader(reader).getAsJsonArray()) {
                literature.add(this.fromJson(item.getAsJsonObject()));
            }
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public Literature fromJson(JsonObject jsonObject) throws ParseException {
        if(Book.isParseableFromJson(jsonObject)) {
            return Book.fromJson(jsonObject);
        }
        else if(Journal.isParseableFromJson(jsonObject)) {
            return Journal.fromJson(jsonObject);
        }
        else if(Hologram.isParseableFromJson(jsonObject)) {
            return Hologram.fromJson(jsonObject);
        }
        else if(Newspaper.isParseableFromJson(jsonObject)) {
            return Newspaper.fromJson(jsonObject);
        }
        throw new ParseException("Literature type unrecognized", 0);
    }

    public void printAllCards() {
        for(Literature item : literature) {
            System.out.println(item.getCard());
        }
    }

    public void printCopyable() {
        for(Literature item : literature) {
            if(isCopyable(item)) {
                System.out.println(item.getCard());
            }
        }
    }

    public void printNonCopyable() {
        for(Literature item : literature) {
            if(!isCopyable(item)) {
                System.out.println(item.getCard());
            }
        }
    }

    public void printPeriodic() {
        for(Literature item : literature) {
            if(isPeriodic(item)) {
                Periodic itemPeriodic = (Periodic)item;
                System.out.printf("%s %s%n", itemPeriodic.getPeriod(), item.getCard());
            }
        }
    }

    public void printPeriodicDuck() {
        for(Literature item : literature) {
            try {
                Method getPeriodMethod = literature.getClass().getDeclaredMethod("getPeriod");
                System.out.printf("%s %s%n", getPeriodMethod.invoke(literature), item.getCard());
            } catch (Exception ignored) {
            }
        }
    }

    public void printNonPeriodic() {
        for(Literature item : literature) {
            if(!isPeriodic(item)) {
                System.out.println(item.getCard());
            }
        }
    }

    public void printPrintable() {
        for(Literature item : literature) {
            if(isPrintable(item)) {
                System.out.println(item.getCard());
            }
        }
    }

    public void printNonPrintable() {
        for(Literature item : literature) {
            if(!isPrintable(item)) {
                System.out.println(item.getCard());
            }
        }
    }

    public void printMultiple() {
        for(Literature item : literature) {
            if(isMultiple(item)) {
                Multiple itemMultiple = (Multiple) item;
                System.out.printf("%s (%d items)%n", item.getCard(), itemMultiple.getCount());
            }
        }
    }

    public void printNonMultiple() {
        for(Literature item : literature) {
            if(!isMultiple(item)) {
                System.out.println(item.getCard());
            }
        }
    }

    private boolean isCopyable(Literature literature) {
        return literature instanceof Copyable;
    }

    private boolean isPeriodic(Literature literature) {
        return literature instanceof Periodic;
    }

    private boolean isPrintable(Literature literature) {
        return literature instanceof Printable;
    }

    private boolean isMultiple(Literature literature) {
        return literature instanceof Multiple;
    }
}
