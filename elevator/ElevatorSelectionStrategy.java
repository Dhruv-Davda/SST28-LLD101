package elevator;

import java.util.List;

public interface ElevatorSelectionStrategy {
    Elevator selectElevator(List<Elevator> elevators, int fromFloor, Direction direction) throws Exception;
}
