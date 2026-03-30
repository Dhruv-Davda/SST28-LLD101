package elevator;

import java.util.List;

public class FCFSStrategy implements ElevatorSelectionStrategy {

    public Elevator selectElevator(List<Elevator> elevators, int fromFloor, Direction direction) throws Exception {
        for (Elevator e : elevators) {
            if (e.getState() == ElevatorState.MAINTENANCE) continue;
            if (e.isAlarmOn()) continue;

            if (e.getState() == ElevatorState.IDLE) {
                return e;
            }
        }

        throw new Exception("No elevator available right now.");
    }
}
