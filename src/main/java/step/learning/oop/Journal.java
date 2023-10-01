package step.learning.oop;

import com.google.gson.JsonObject;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Serializable
public class Journal extends Literature implements Copyable, Periodic, Printable, Multiple {
    @Required
    private int number;
    private static List<String> requiredFieldsNames;

    public Journal(String title, int number) {
        super.setTitle(title);
        this.setNumber(number);
    }

    private static List<String> getRequiredFieldsNames() {
        if(requiredFieldsNames == null) {
            Field[] fields = Journal.class.getDeclaredFields();
            Field[] fieldsSuper = Journal.class.getSuperclass().getDeclaredFields();
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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String getCard() {
        return String.format("Journal: '%s' №%d", getTitle(), getNumber());
    }

    @Override
    public String getPeriod() {
        return "monthly";
    }

    @Override
    public int getCount() {
        return 1;
    }

    @FromJsonParser
    public static Journal fromJson(JsonObject jsonObject) throws ParseException {
        String[] requiredFields = getRequiredFieldsNames().toArray(new String[0]);
        for (String field : requiredFields) {
            if(!jsonObject.has(field)) throw new ParseException(String.format("Field '%s' is required", field), 0);
        }

        return new Journal(
                jsonObject.get(requiredFields[1]).getAsString(),
                jsonObject.get(requiredFields[0]).getAsInt()
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
