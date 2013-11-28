/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.carwash.dao;

import br.com.carwash.objects.Car;
import br.com.carwash.objects.CarType;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author alexandrebarros
 */
public class CarDao extends GenericDao{

    private static final long serialVersionUID = 1L;
    
    private static CarDao instance;
    
    public static final String NAME_TABLE = GenericDao.SCHEMA_BASE +".\"T_CARS\"";
    
    private CarDao(){}
        
    public static CarDao getInstance(){
        if(instance == null)
            instance = new CarDao();
        return instance;
    }
    
   public int addCar(Car car) throws SQLException {
        Connection conn = getConnection();
        car.setId(getNextId(conn,"ID",NAME_TABLE));
        String query = "INSERT INTO "+ NAME_TABLE +" (\"ID\",\"ID_CLIENT\",\"PLAQUE\",\"TYPE\",\"MARK\",\"MODEL\",\"YEAR\") VALUES (?,?,?,?,?,?,?)";
        executeCommand(conn,query, car.getId(), car.getClient().getId(), car.getPlaque(), car.getType().toString(), car.getMark(), car.getModel(),car.getYear());
        closeConnection(conn);
        return car.getId();
    }

    public void removeCar(int idCar) throws SQLException {
        Connection conn = getConnection();
        executeCommand(conn,"DELETE FROM "+ NAME_TABLE +" WHERE \"ID\" = ?", idCar);
        closeConnection(conn);
    }

    public void updateCar(Car car) throws SQLException {
        Connection conn = getConnection();
        String query = "UPDATE "+ NAME_TABLE +" SET \"ID_CLIENT\" = ?,\"PLAQUE\"=?,\"TYPE\"=?,\"MARK\"=?,\"MODEL\"=?,\"YEAR\"=? WHERE \"ID\" = ?";
        executeCommand(conn,query, car.getClient().getId(), car.getPlaque(), car.getType().toString(), car.getMark(), car.getModel(),car.getYear(),car.getId());
        closeConnection(conn);
    }

    public Car getCar(int idCar) throws SQLException {
        Connection conn = getConnection();
        ResultSet rs = executeQuery(conn,"SELECT * FROM "+ NAME_TABLE +" WHERE \"ID\" = ?", idCar);
        Car car = null;
        while (rs.next()) {
            car = fillCar(rs);
        }
        rs.close();
        return car;
    }

    public List<Car> getAllCars() throws SQLException {
        Connection conn = getConnection();
        ResultSet rs = executeQuery(conn,"SELECT * FROM "+ NAME_TABLE );
        List<Car> toReturn = new LinkedList<Car>();
        while (rs.next()) {
            toReturn.add(fillCar(rs));
        }
        rs.close();
        return toReturn;

    }

    public static Car fillCar(ResultSet rs) throws SQLException {
        
        Car toReturn = new Car();
        toReturn.setId(rs.getInt("ID"));
        toReturn.setClient(ClientDao.getInstance().getClient(rs.getInt("ID_CLIENT")));
        toReturn.setPlaque(rs.getString("PLAQUE"));
        toReturn.setType(CarType.valueOf(rs.getString("TYPE")));
        toReturn.setMark(rs.getString("MARK"));
        toReturn.setModel(rs.getString("MODEL"));
        toReturn.setYear(rs.getInt("YEAR"));
        return toReturn;
    }    
}
