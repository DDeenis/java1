package step.learning.oop;

import com.google.gson.JsonObject;

import java.text.ParseException;

@Serializable
public class Journal extends Literature implements Copyable, Periodic, Printable, Multiple {
    private int number;

    public Journal(String title, int number) {
        super.setTitle(title);
        this.setNumber(number);
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
        String[] requiredFields = {"title", "number"};
        for (String field : requiredFields) {
            if(!jsonObject.has(field)) throw new ParseException(String.format("Field '%s' is required", field), 0);
        }

        return new Journal(
                jsonObject.get(requiredFields[0]).getAsString(),
                jsonObject.get(requiredFields[1]).getAsInt()
        );
    }

    @ParseChecker
    public static boolean isParseableFromJson(JsonObject jsonObject) {
        String[] requiredFields = {"title", "number"};
        for (String field : requiredFields) {
            if(!jsonObject.has(field)) return false;
        }
        return true;
    }
}
