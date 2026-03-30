package elevator;

import java.util.ArrayList;
import java.util.List;

public class Elevator {
    private String id;
    private int currentFloor;
    private ElevatorState state;
    private double weightLimit;
    private double currentWeight;
    private boolean doorOpen;
    private boolean alarmOn;
    private List<Integer> pendingRequests;

    public Elevator(String id, double weightLimit) {
        this.id = id;
        this.currentFloor = 0;
        this.state = ElevatorState.IDLE;
        this.weightLimit = weightLimit;
        this.currentWeight = 0;
        this.doorOpen = false;
        this.alarmOn = false;
        this.pendingRequests = new ArrayList<>();
    }

    public String getId() { return id; }
    public int getCurrentFloor() { return currentFloor; }
    public ElevatorState getState() { return state; }
    public double getWeightLimit() { return weightLimit; }
    public double getCurrentWeight() { return currentWeight; }
    public boolean isDoorOpen() { return doorOpen; }
    public boolean isAlarmOn() { return alarmOn; }

    public void setState(ElevatorState state) { this.state = state; }

    public void addWeight(double weight) {
        this.currentWeight += weight;
        System.out.println("[" + id + "] Weight updated: " + currentWeight + " kg");
        if (currentWeight > weightLimit) {
            System.out.println("[" + id + "] OVERWEIGHT! Limit is " + weightLimit + " kg. Please reduce load.");
            openDoor();
            triggerAlarm();
        }
    }

    public void removeWeight(double weight) {
        this.currentWeight -= weight;
        if (currentWeight < 0) currentWeight = 0;
        System.out.println("[" + id + "] Weight updated: " + currentWeight + " kg");
        if (currentWeight <= weightLimit && alarmOn) {
            stopAlarm();
        }
    }

    public void addRequest(int floor) {
        if (!pendingRequests.contains(floor)) {
            pendingRequests.add(floor);
        }
    }

    public void openDoor() {
        doorOpen = true;
        System.out.println("[" + id + "] Door opened at floor " + currentFloor);
    }

    public void closeDoor() {
        if (currentWeight > weightLimit) {
            System.out.println("[" + id + "] Cannot close door — overweight!");
            return;
        }
        doorOpen = false;
        System.out.println("[" + id + "] Door closed at floor " + currentFloor);
    }

    public void triggerAlarm() {
        alarmOn = true;
        System.out.println("[" + id + "] ALARM TRIGGERED!");
    }

    public void stopAlarm() {
        alarmOn = false;
        System.out.println("[" + id + "] Alarm stopped.");
    }

    public void emergencyStop() {
        state = ElevatorState.IDLE;
        alarmOn = true;
        openDoor();
        pendingRequests.clear();
        System.out.println("[" + id + "] EMERGENCY STOP at floor " + currentFloor);
    }

    public void moveToFloor(int targetFloor, List<Floor> floors) {
        if (state == ElevatorState.MAINTENANCE) {
            System.out.println("[" + id + "] Under maintenance — cannot move.");
            return;
        }
        if (alarmOn) {
            System.out.println("[" + id + "] Alarm is on — resolve before moving.");
            return;
        }
        if (currentWeight > weightLimit) {
            System.out.println("[" + id + "] Overweight — cannot move.");
            return;
        }

        Floor target = null;
        for (Floor f : floors) {
            if (f.getFloorNumber() == targetFloor) {
                target = f;
                break;
            }
        }

        if (target == null) {
            System.out.println("[" + id + "] Floor " + targetFloor + " does not exist.");
            return;
        }

        if (target.isUnderMaintenance()) {
            System.out.println("[" + id + "] Floor " + targetFloor + " is under maintenance — skipping.");
            return;
        }

        if (targetFloor > currentFloor) {
            state = ElevatorState.UP;
        } else if (targetFloor < currentFloor) {
            state = ElevatorState.DOWN;
        }

        System.out.println("[" + id + "] Moving " + state + " from floor " + currentFloor + " to floor " + targetFloor);
        currentFloor = targetFloor;
        state = ElevatorState.IDLE;
        openDoor();
        System.out.println("[" + id + "] Arrived at floor " + currentFloor);
    }

    public void processRequests(List<Floor> floors) {
        while (!pendingRequests.isEmpty()) {
            int next = pendingRequests.remove(0);
            moveToFloor(next, floors);
        }
    }

    public void setMaintenance(boolean status) {
        if (status) {
            state = ElevatorState.MAINTENANCE;
            pendingRequests.clear();
            System.out.println("[" + id + "] Elevator is now under maintenance.");
        } else {
            state = ElevatorState.IDLE;
            System.out.println("[" + id + "] Elevator maintenance completed.");
        }
    }

    public void showStatus() {
        System.out.println("[" + id + "] Floor=" + currentFloor + " State=" + state +
                " Weight=" + currentWeight + "/" + weightLimit + "kg Door=" + (doorOpen ? "OPEN" : "CLOSED") +
                " Alarm=" + (alarmOn ? "ON" : "OFF"));
    }
}
