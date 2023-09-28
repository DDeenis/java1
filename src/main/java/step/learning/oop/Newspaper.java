package step.learning.oop;

import com.google.gson.JsonObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Serializable
public class Newspaper extends Literature implements Periodic, Printable {
    private Date date;
    public static final SimpleDateFormat sqlDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat cardDateFormat = new SimpleDateFormat("yyyy.MM.dd");

    public Newspaper(String title, String date) throws ParseException {
        super.setTitle(title);
        this.setDate(sqlDateFormat.parse(date));
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
        String[] requiredFields = {"title", "date"};
        for (String field : requiredFields) {
            if(!jsonObject.has(field)) throw new ParseException(String.format("Field '%s' is required", field), 0);
        }

        return new Newspaper(
                jsonObject.get(requiredFields[0]).getAsString(),
                jsonObject.get(requiredFields[1]).getAsString()
        );
    }

    @ParseChecker
    public static boolean isParseableFromJson(JsonObject jsonObject) {
        String[] requiredFields = {"title", "date"};
        for (String field : requiredFields) {
            if(!jsonObject.has(field)) return false;
        }
        return true;
    }
}
