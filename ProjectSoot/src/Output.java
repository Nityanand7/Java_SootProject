import java.util.HashMap;
import java.util.Map;

public class Output {
    private final Map<String, String> hashMap = new HashMap<>();

    public void put(String k, String v) {
        hashMap.put(k, v);
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            output.append(entry.getKey()).append(" => ").append(entry.getValue()).append("\n");
        }
        return output.toString();
    }
}
