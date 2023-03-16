package org.example.planes;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Planes {
    private int planeId;
    private String model;
    private String serialNumber;
    private int seats;

    public Planes(String model, String serialNumber, int seats) {
        this.model = model;
        this.serialNumber = serialNumber;
        this.seats = seats;
    }
}
