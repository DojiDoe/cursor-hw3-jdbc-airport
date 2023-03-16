package org.example.pilots;

import java.sql.Connection;
import java.util.List;

public interface PilotsDAO {

    List<Pilots> findAll(Connection con);

    void add(Pilots pilot, Connection con);

    void update(Pilots pilot, Connection con);

    void delete(int pilotId,Connection con);
}
