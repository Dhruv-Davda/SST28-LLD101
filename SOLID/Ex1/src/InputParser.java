import java.util.*;

public class InputParser {
    public RawStudentData parse(String raw) {
        Map<String, String> kv = new LinkedHashMap<>();
        for (String part : raw.split(";")) {
            String[] t = part.split("=", 2);
            if (t.length == 2) kv.put(t[0].trim(), t[1].trim());
        }
        return new RawStudentData(
            kv.getOrDefault("name", ""),
            kv.getOrDefault("email", ""),
            kv.getOrDefault("phone", ""),
            kv.getOrDefault("program", "")
        );
    }
}
