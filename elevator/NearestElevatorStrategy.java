package elevator;

import java.util.List;

public class NearestElevatorStrategy implements ElevatorSelectionStrategy {

    public Elevator selectElevator(List<Elevator> elevators, int fromFloor, Direction direction) throws Exception {
        Elevator best = null;
        int minDistance = Integer.MAX_VALUE;

        for (Elevator e : elevators) {
            if (e.getState() == ElevatorState.MAINTENANCE) continue;
            if (e.isAlarmOn()) continue;

            int distance = Math.abs(e.getCurrentFloor() - fromFloor);

            if (e.getState() == ElevatorState.IDLE) {
                if (distance < minDistance) {
                    minDistance = distance;
                    best = e;
                }
            } else if (e.getState() == ElevatorState.UP && direction == Direction.UP && e.getCurrentFloor() <= fromFloor) {
                if (distance < minDistance) {
                    minDistance = distance;
                    best = e;
                }
            } else if (e.getState() == ElevatorState.DOWN && direction == Direction.DOWN && e.getCurrentFloor() >= fromFloor) {
                if (distance < minDistance) {
                    minDistance = distance;
                    best = e;
                }
            }
        }

        if (best == null) {
            throw new Exception("No elevator available right now.");
        }
        return best;
    }
}
