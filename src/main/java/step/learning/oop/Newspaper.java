package step.learning.oop;

import com.google.gson.JsonObject;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Serializable
public class Newspaper extends Literature implements Periodic, Printable {
    @Required
    private Date date;
    public static final SimpleDateFormat sqlDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat cardDateFormat = new SimpleDateFormat("yyyy.MM.dd");
    private static List<String> requiredFieldsNames;

    public Newspaper(String title, String date) throws ParseException {
        super.setTitle(title);
        this.setDate(sqlDateFormat.parse(date));
    }

    private static List<String> getRequiredFieldsNames() {
        if(requiredFieldsNames == null) {
            Field[] fields = Newspaper.class.getDeclaredFields();
            Field[] fieldsSuper = Newspaper.class.getSuperclass().getDeclaredFields();
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String getCard() {
        return String.format("Newspaper: '%s' from %s", getTitle(), cardDateFormat.format(getDate()));
    }

    @Override
    public String getPeriod() {
        return "daily";
    }

    @FromJsonParser
    public static Newspaper fromJson(JsonObject jsonObject) throws ParseException {
        String[] requiredFields = getRequiredFieldsNames().toArray(new String[0]);
        for (String field : requiredFields) {
            if(!jsonObject.has(field)) throw new ParseException(String.format("Field '%s' is required", field), 0);
        }

        return new Newspaper(
                jsonObject.get(requiredFields[1]).getAsString(),
                jsonObject.get(requiredFields[0]).getAsString()
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
