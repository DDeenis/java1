package step.learning.oop;

import com.google.gson.JsonObject;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Serializable
public class Hologram extends Literature {
    @Required
    private int version;
    private static List<String> requiredFieldsNames;

    public Hologram(String title, int version) {
        super.setTitle(title);
        this.setVersion(version);
    }

    private static List<String> getRequiredFieldsNames() {
        if(requiredFieldsNames == null) {
            Field[] fields = Hologram.class.getDeclaredFields();
            Field[] fieldsSuper = Hologram.class.getSuperclass().getDeclaredFields();
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
        String[] requiredFields = getRequiredFieldsNames().toArray(new String[0]);
        for (String field : requiredFields) {
            if(!jsonObject.has(field)) throw new ParseException(String.format("Field '%s' is required", field), 0);
        }

        return new Hologram(
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
