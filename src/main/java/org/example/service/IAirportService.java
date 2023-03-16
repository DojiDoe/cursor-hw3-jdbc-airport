package org.example.service;

import org.example.pilots.Pilots;
import org.example.planes.Planes;

import java.util.List;
import java.util.Scanner;

public interface IAirportService {
    void createPilot(Scanner scanner);

    List<Pilots> findAllPilots();

    void deletePilot(Scanner scanner);

    void updatePilot(Scanner scanner);

    void createPlane(Scanner scanner);

    List<Planes> findAllPlanes();

    void deletePlane(Scanner scanner);

    void updatePlane(Scanner scanner);

    int passengersCanServe();
}
