package elevator;

import java.util.ArrayList;
import java.util.List;

public class ElevatorController {
    private List<Elevator> elevators;
    private List<Floor> floors;
    private ElevatorSelectionStrategy selectionStrategy;

    public ElevatorController(ElevatorSelectionStrategy selectionStrategy) {
        this.elevators = new ArrayList<>();
        this.floors = new ArrayList<>();
        this.selectionStrategy = selectionStrategy;
    }

    public void setStrategy(ElevatorSelectionStrategy strategy) {
        this.selectionStrategy = strategy;
        System.out.println("Strategy changed to: " + strategy.getClass().getSimpleName());
    }

    public void addElevator(Elevator elevator) {
        elevators.add(elevator);
    }

    public void addFloor(Floor floor) {
        floors.add(floor);
    }

    public void requestFromOutside(int fromFloor, Direction direction) {
        System.out.println("\n--- Outside Request: Floor " + fromFloor + " Direction " + direction + " ---");
        try {
            Elevator selected = selectionStrategy.selectElevator(elevators, fromFloor, direction);
            System.out.println("Assigned: " + selected.getId());
            selected.moveToFloor(fromFloor, floors);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void requestFromInside(String elevatorId, int targetFloor) {
        System.out.println("\n--- Inside Request: Elevator " + elevatorId + " -> Floor " + targetFloor + " ---");
        Elevator elevator = findElevator(elevatorId);
        if (elevator == null) {
            System.out.println("Elevator " + elevatorId + " not found.");
            return;
        }
        elevator.moveToFloor(targetFloor, floors);
    }

    public void pressOpenDoor(String elevatorId) {
        Elevator elevator = findElevator(elevatorId);
        if (elevator != null) elevator.openDoor();
    }

    public void pressCloseDoor(String elevatorId) {
        Elevator elevator = findElevator(elevatorId);
        if (elevator != null) elevator.closeDoor();
    }

    public void pressEmergency(String elevatorId) {
        Elevator elevator = findElevator(elevatorId);
        if (elevator != null) elevator.emergencyStop();
    }

    public void pressAlarm(String elevatorId) {
        Elevator elevator = findElevator(elevatorId);
        if (elevator != null) {
            elevator.triggerAlarm();
            elevator.openDoor();
            System.out.println("[" + elevatorId + "] Alarm pressed — elevator stopped.");
        }
    }

    public void setFloorMaintenance(int floorNumber, boolean status) {
        for (Floor f : floors) {
            if (f.getFloorNumber() == floorNumber) {
                f.setUnderMaintenance(status);
                return;
            }
        }
        System.out.println("Floor " + floorNumber + " not found.");
    }

    public void setElevatorMaintenance(String elevatorId, boolean status) {
        Elevator elevator = findElevator(elevatorId);
        if (elevator != null) elevator.setMaintenance(status);
    }

    public void showAllStatus() {
        System.out.println("\n=== Elevator System Status ===");
        for (Elevator e : elevators) {
            e.showStatus();
        }
        System.out.println("==============================\n");
    }

    private Elevator findElevator(String id) {
        for (Elevator e : elevators) {
            if (e.getId().equals(id)) return e;
        }
        return null;
    }
}
