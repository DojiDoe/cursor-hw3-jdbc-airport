package org.example.planes;

import org.example.pilots.Pilots;

import java.sql.Connection;
import java.util.List;

public interface PlanesDAO {
    List<Planes> findAll(Connection con);

    void add(Planes plane, Connection con);

    void update(Planes plane, Connection con);

    void delete(int planeID, Connection con);
}
