package elevator;

public class Main {
    public static void main(String[] args) {
        ElevatorSelectionStrategy strategy = new NearestElevatorStrategy();
        ElevatorController controller = new ElevatorController(strategy);

        controller.addFloor(new Floor(0));
        controller.addFloor(new Floor(1));
        controller.addFloor(new Floor(2));
        controller.addFloor(new Floor(3));
        controller.addFloor(new Floor(4));
        controller.addFloor(new Floor(5));

        controller.addElevator(new Elevator("E1", 700));
        controller.addElevator(new Elevator("E2", 500));

        controller.showAllStatus();

        controller.requestFromOutside(3, Direction.UP);

        controller.requestFromInside("E1", 5);

        controller.requestFromOutside(0, Direction.UP);

        controller.showAllStatus();

        System.out.println("--- Testing Overweight ---");
        controller.requestFromInside("E1", 2);
        controller.pressCloseDoor("E1");

        controller.requestFromInside("E2", 1);
        controller.pressCloseDoor("E2");
        controller.showAllStatus();

        System.out.println("--- Testing Emergency Stop ---");
        controller.pressEmergency("E1");
        controller.showAllStatus();

        System.out.println("--- Testing Alarm Button ---");
        controller.pressAlarm("E2");
        controller.showAllStatus();

        System.out.println("--- Floor 4 Under Maintenance ---");
        controller.setFloorMaintenance(4, true);
        controller.requestFromInside("E1", 4);

        System.out.println("\n--- Elevator E1 Under Maintenance ---");
        controller.setElevatorMaintenance("E1", true);
        controller.requestFromOutside(2, Direction.DOWN);

        controller.showAllStatus();
    }
}
