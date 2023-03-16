package org.example.service;

import org.example.ConnectionManager;
import org.example.pilots.Pilots;
import org.example.pilots.PilotsDAOImpl;
import org.example.planes.Planes;
import org.example.planes.PlanesDAOImpl;

import java.util.*;
import java.util.stream.Collectors;

public class AirportService implements IAirportService {

    public void createPilot(Scanner scanner) {
        Pilots pilot = getPilot(scanner);
        new PilotsDAOImpl().add(pilot, ConnectionManager.getConnection());
    }

    public List<Pilots> findAllPilots() {
        return new PilotsDAOImpl().findAll(ConnectionManager.getConnection());
    }

    public void deletePilot(Scanner scanner) {
        System.out.println("Enter pilot id to delete:");
        int pilotId = scanner.nextInt();
        scanner.nextLine();
        new PilotsDAOImpl().delete(pilotId, ConnectionManager.getConnection());
    }

    public void updatePilot(Scanner scanner) {
        System.out.println("Enter pilot id to update info:");
        int pilotId = scanner.nextInt();
        scanner.nextLine();
        Pilots pilots = getPilot(scanner);
        pilots.setPilotsId(pilotId);
        new PilotsDAOImpl().update(pilots, ConnectionManager.getConnection());
    }

    private Pilots getPilot(Scanner scanner) {
        System.out.println("Enter pilot name: ");
        String name = scanner.nextLine();
        System.out.println("Enter pilot age: ");
        int age = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter pilot Model pilot can fly:");
        String modelCanFly = scanner.nextLine();
        return new Pilots(name, age, modelCanFly);
    }

    public void createPlane(Scanner scanner) {
        Planes plane = getPlane(scanner);
        new PlanesDAOImpl().add(plane, ConnectionManager.getConnection());
    }

    private Planes getPlane(Scanner scanner) {
        System.out.println("Enter plane model: ");
        String model = scanner.nextLine();
        System.out.println("Enter pilot serial number: ");
        String serialNumber = scanner.nextLine();
        System.out.println("Enter number od seats:");
        int seats = scanner.nextInt();
        scanner.nextLine();
        return new Planes(model, serialNumber, seats);
    }

    public List<Planes> findAllPlanes() {
        return new PlanesDAOImpl().findAll(ConnectionManager.getConnection());
    }

    public void deletePlane(Scanner scanner) {
        System.out.println("Enter plane id to delete:");
        int planeId = scanner.nextInt();
        scanner.nextLine();
        new PlanesDAOImpl().delete(planeId, ConnectionManager.getConnection());
    }

    public void updatePlane(Scanner scanner) {
        System.out.println("Enter plane id to update info:");
        int planeId = scanner.nextInt();
        scanner.nextLine();
        Planes plane = getPlane(scanner);
        plane.setPlaneId(planeId);
        new PlanesDAOImpl().update(plane, ConnectionManager.getConnection());
    }

    public int passengersCanServe() {

        List<Pilots> pilotsList = findAllPilots();
        List<Planes> planesList = findAllPlanes();

        Map<String, Long> pilotMap = pilotsList.stream().collect(Collectors.groupingBy(Pilots::getModelCanFly, Collectors.counting()));
        Map<String, Double> planeMap = planesList.stream().collect(Collectors.groupingBy(Planes::getModel, Collectors.averagingLong(Planes::getSeats)));
        List<String> toDelete = new ArrayList<>();
        // находимо кількість потенційних пілотів для кожного літака
        for (Map.Entry<String, Long> s : pilotMap.entrySet()) {
            if (s.getKey().contains(",")) {
                List<String> models = Arrays.stream(s.getKey().split(",")).map(String::trim).collect(Collectors.toList());
                for (String model : models) {
                    if (pilotMap.containsKey(model)) {
                        pilotMap.put(model, pilotMap.get(model) + s.getValue());
                    }
                }
                toDelete.add(s.getKey());
            }
        }
        toDelete.forEach(pilotMap::remove);
        //находимо кількість можливих повних команд
        pilotMap.forEach((k, v) -> pilotMap.merge(k, 2L, Long::divideUnsigned));

        // обчислення кількості пасажирів, яких  може обслуговувати аеропорт
        int sum = 0;
        for (Map.Entry<String, Long> pilot : pilotMap.entrySet()) {
            if (planeMap.containsKey(pilot.getKey())) {
                sum += pilot.getValue() * planeMap.get(pilot.getKey());
            }

        }
        return sum;
    }
}
