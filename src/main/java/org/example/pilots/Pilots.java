package org.example.pilots;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pilots {
    private int pilotsId;
    private String name;
    private int age;
    private String modelCanFly;

    public Pilots(String name, int age, String modelCanFly) {
        this.name = name;
        this.age = age;
        this.modelCanFly = modelCanFly;
    }
}
