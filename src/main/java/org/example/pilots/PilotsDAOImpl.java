package org.example.pilots;

import org.example.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PilotsDAOImpl implements PilotsDAO {
    @Override
    public List<Pilots> findAll(Connection con) {
        PreparedStatement ps = null;
        ResultSet res = null;
        List<Pilots> pilotsList = new ArrayList<>();
        try {
            con = ConnectionManager.getConnection();
            ps = con.prepareStatement("SELECT * FROM pilots");
            res = ps.executeQuery();
            while (res.next()) {
                pilotsList.add(new Pilots(res.getInt("pilots_id"),
                        res.getString("name"),
                        res.getInt("age"),
                        res.getString("model_can_fly")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionManager.close(con);
            ConnectionManager.close(ps);
            ConnectionManager.close(res);
        }
        return pilotsList;
    }

    @Override
    public void add(Pilots pilot, Connection con) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("INSERT INTO pilots(name, age, model_can_fly) VALUES (?,?,?) ");
            ps.setString(1, pilot.getName());
            ps.setInt(2, pilot.getAge());
            ps.setString(3, pilot.getModelCanFly());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionManager.close(ps);
        }
    }

    @Override
    public void update(Pilots pilot, Connection con) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("UPDATE pilots SET name=?, age=?, model_can_fly=? WHERE pilots_id=?");
            ps.setString(1, pilot.getName());
            ps.setInt(2, pilot.getAge());
            ps.setString(3, pilot.getModelCanFly());
            ps.setInt(4, pilot.getPilotsId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionManager.close(ps);
        }
    }

    @Override
    public void delete(int pilotId, Connection con) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("DELETE FROM pilots WHERE pilots_id=?");
            ps.setInt(1, pilotId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionManager.close(ps);
        }
    }
}
