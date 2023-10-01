package step.learning.oop;

import com.google.gson.JsonObject;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Serializable
public class Book extends Literature implements Copyable, Printable, Multiple {
    @Required
    private String author;
    private static List<String> requiredFieldsNames;

    public Book(String author, String title) {
        this.setAuthor(author);
        super.setTitle(title);
    }

    private static List<String> getRequiredFieldsNames() {
        if(requiredFieldsNames == null) {
            Field[] fields = Book.class.getDeclaredFields();
            Field[] fieldsSuper = Book.class.getSuperclass().getDeclaredFields();
            requiredFieldsNames = Stream
                    .concat(
                            Arrays.stream(fields),
                            Arrays.stream(fieldsSuper)
                    )
                    .filter(field -> field.isAnnotationPresent(Required.class))
                    .map(Field::getName)
                    .collect(Collectors.toList());
        }

        return requiredFieldsNames;
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
        String[] requiredFields = getRequiredFieldsNames().toArray(new String[0]);;
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
        for (String field : getRequiredFieldsNames()) {
            if(!jsonObject.has(field)) return false;
        }
        return true;
    }
}
