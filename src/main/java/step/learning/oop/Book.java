package step.learning.oop;

import com.google.gson.JsonObject;

import java.text.ParseException;

@Serializable
public class Book extends Literature implements Copyable, Printable, Multiple {
    private String author;

    public Book(String author, String title) {
        this.setAuthor(author);
        super.setTitle(title);
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String getCard() {
        return String.format("Book: %s '%s'", getAuthor(), getTitle());
    }

    @Override
    public int getCount() {
        return 1;
    }

    @FromJsonParser
    public static Book fromJson(JsonObject jsonObject) throws ParseException {
        String[] requiredFields = {"title", "author"};
        for (String field : requiredFields) {
            if(!jsonObject.has(field)) throw new ParseException(String.format("Field '%s' is required", field), 0);
        }

        return new Book(
                jsonObject.get(requiredFields[0]).getAsString(),
                jsonObject.get(requiredFields[1]).getAsString()
        );
    }

    @ParseChecker
    public static boolean isParseableFromJson(JsonObject jsonObject) {
        String[] requiredFields = {"title", "author"};
        for (String field : requiredFields) {
            if(!jsonObject.has(field)) return false;
        }
        return true;
    }
}
