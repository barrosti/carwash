/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.carwash.dao;

import br.com.carwash.objects.Schedule;
import br.com.carwash.objects.ScheduleType;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author alexandrebarros
 */
public class ScheduleDao extends GenericDao{
    
    private static final long serialVersionUID = 1L;
    
    private static ScheduleDao instance;
    
    public static final String NAME_TABLE = GenericDao.SCHEMA_BASE +".\"T_SCHEDULE\"";
    
    private ScheduleDao(){}
        
    public static ScheduleDao getInstance(){
        if(instance == null)
            instance = new ScheduleDao();
        return instance;
    }          
    
    public int addSchedule(Schedule schedule) throws SQLException {
        Connection conn = getConnection();
        schedule.setId(getNextId(conn,"ID",NAME_TABLE));
        String query = "INSERT INTO "+ NAME_TABLE +" (\"ID\",\"ID_CAR\",\"DATE\",\"TYPE\",\"TOTAL\") values (?,?,?,?,?)";
        executeCommand(conn,query, schedule.getId(), schedule.getCar().getId(), schedule.getDate(), schedule.getType().toString(), schedule.getTotal());
        closeConnection(conn);
        return schedule.getId();
    }

    public void removeSchedule(int idSchedule) throws SQLException {
        Connection conn = getConnection();
        executeCommand(conn,"DELETE FROM "+ NAME_TABLE +" WHERE \"ID\" = ?", idSchedule);
        closeConnection(conn);
    }

    public void updateSchedule(Schedule sd) throws SQLException {
        Connection conn = getConnection();
        String query = "UPDATE "+ NAME_TABLE +" SET \"ID_CAR\" = ?,\"DATE\" = ?,\"TYPE\" = ?,\"TOTAL\" = ? WHERE \"ID\" = ?";
        executeCommand(conn,query, sd.getCar().getId(), sd.getDate(), sd.getType().toString(), sd.getTotal(), sd.getId());
        closeConnection(conn);
    }

    public Schedule getSchedule(int idSchedule) throws SQLException {
        Connection conn = getConnection();
        ResultSet rs = executeQuery(conn,"SELECT * FROM "+ NAME_TABLE +" WHERE \"ID\" = ?", idSchedule);
        Schedule sd = null;
        if (rs.next()) {
            sd = fillSchedule(rs);
        }
        rs.close();
        closeConnection(conn);
        return sd;
    }

    public static Schedule fillSchedule(ResultSet rs) throws SQLException {
        Schedule toReturn = new Schedule();
        toReturn.setId(rs.getInt("ID"));
        toReturn.setDate(rs.getDate("DATE"));
        toReturn.setTotal(rs.getFloat("TOTAL"));
        toReturn.setType(ScheduleType.valueOf(rs.getString("TYPE")));
        toReturn.setCar(CarDao.getInstance().getCar(rs.getInt("ID_CAR")));
        return toReturn;
    }

    public List<Schedule> getSchedules() throws SQLException {
        Connection conn = getConnection();
        List<Schedule> toReturn = new LinkedList<Schedule>();
        ResultSet rs = executeQuery(conn,"SELECT * FROM "+ NAME_TABLE );
        while (rs.next()) {
            toReturn.add(fillSchedule(rs));
        }
        rs.close();
        closeConnection(conn);
        return toReturn;
    }    
}
