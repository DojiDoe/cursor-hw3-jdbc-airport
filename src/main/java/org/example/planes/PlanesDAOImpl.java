package org.example.planes;

import org.example.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlanesDAOImpl implements PlanesDAO {
    @Override
    public List<Planes> findAll(Connection con) {
        PreparedStatement ps = null;
        ResultSet res = null;
        List<Planes> planesList = new ArrayList<>();
        try {
            con = ConnectionManager.getConnection();
            ps = con.prepareStatement("SELECT * FROM planes");
            res = ps.executeQuery();
            while (res.next()) {
                planesList.add(new Planes(res.getInt("plane_id"),
                        res.getString("model"),
                        res.getString("serial_number"),
                        res.getInt("seats")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionManager.close(con);
            ConnectionManager.close(ps);
            ConnectionManager.close(res);
        }
        return planesList;
    }

    @Override
    public void add(Planes plane, Connection con) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("INSERT INTO planes(model, serial_number, seats) VALUES (?,?,?)");
            ps.setString(1, plane.getModel());
            ps.setString(2, plane.getSerialNumber());
            ps.setInt(3, plane.getSeats());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionManager.close(ps);
        }
    }

    @Override
    public void update(Planes plane, Connection con) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("UPDATE planes SET model=?, serial_number=?, seats=? WHERE plane_id=?");
            ps.setString(1, plane.getModel());
            ps.setString(2, plane.getSerialNumber());
            ps.setInt(3, plane.getSeats());
            ps.setInt(4, plane.getPlaneId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionManager.close(ps);
        }
    }

    @Override
    public void delete(int planeID, Connection con) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("DELETE FROM planes WHERE plane_id=?");
            ps.setInt(1, planeID);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionManager.close(ps);
        }
    }
}
