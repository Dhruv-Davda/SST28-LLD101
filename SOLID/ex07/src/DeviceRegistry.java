import java.util.*;

public class DeviceRegistry {
    private final List<SmartClassroomDevice> devices = new ArrayList<>();

    public void add(SmartClassroomDevice d) { devices.add(d); }

    @SuppressWarnings("unchecked")
    public <T> T getFirst(Class<T> capability) {
        for (SmartClassroomDevice d : devices) {
            if (capability.isInstance(d)) return (T) d;
        }
        throw new IllegalStateException("Missing: " + capability.getSimpleName());
    }
}
