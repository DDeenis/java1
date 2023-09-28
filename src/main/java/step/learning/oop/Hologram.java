package step.learning.oop;

import com.google.gson.JsonObject;

import java.text.ParseException;

@Serializable
public class Hologram extends Literature {
    private int version;

    public Hologram(String title, int version) {
        super.setTitle(title);
        this.setVersion(version);
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String getCard() {
        return String.format("Hologram: '%s' v%d", getTitle(), getVersion());
    }

    @FromJsonParser
    public static Hologram fromJson(JsonObject jsonObject) throws ParseException {
        String[] requiredFields = {"title", "version"};
        for (String field : requiredFields) {
            if(!jsonObject.has(field)) throw new ParseException(String.format("Field '%s' is required", field), 0);
        }

        return new Hologram(
                jsonObject.get(requiredFields[0]).getAsString(),
                jsonObject.get(requiredFields[1]).getAsInt()
        );
    }

    @ParseChecker
    public static boolean isParseableFromJson(JsonObject jsonObject) {
        String[] requiredFields = {"title", "version"};
        for (String field : requiredFields) {
            if(!jsonObject.has(field)) return false;
        }
        return true;
    }
}
