/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.carwash.dao;

import br.com.carwash.Util.Util;
import br.com.carwash.objects.Car;
import br.com.carwash.objects.CarType;
import br.com.carwash.objects.Client;
import java.sql.SQLException;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author alexandrebarros
 */
public class CarDaoTest {
    
    private static Car car;
    
    public CarDaoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws SQLException {
        System.out.println("BeforeClass#CarDaoTest");
        
        Car carInsert = new Car();
        carInsert.setClient(new Client());
        carInsert.getClient().setId(1);
        carInsert.setPlaque("NUS2349");
        carInsert.setType(CarType.SUV);
        carInsert.setMark("Fiat");
        carInsert.setModel("Uno Vivace");
        carInsert.setYear(2011);
        
        Integer idCar = CarDao.getInstance().addCar(carInsert);
        Car carBD = CarDao.getInstance().getCar(idCar);
        car = carBD;
        
        assertEquals(idCar, carBD.getId());        
    }
    
    @AfterClass
    public static void tearDownClass() throws SQLException {
        System.out.println("AfterClass#CarDaoTest");
        
        CarDao.getInstance().removeCar(car.getId());
        Car carBD = CarDao.getInstance().getCar(car.getId());
        assertNull(carBD);        
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getInstance method, of class CarDao.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        CarDao expResult = CarDao.getInstance();
        assertEquals(expResult, CarDao.getInstance());
    }

    /**
     * Test of updateCar method, of class CarDao.
     */
    @Test
    public void testUpdateCar() throws Exception {
        System.out.println("updateCar");
        String newMark = "New Mark of Car Test";
        
        Car carUpdate = CarDao.getInstance().getCar(car.getId());
        carUpdate.setMark(newMark);
        CarDao.getInstance().updateCar(carUpdate);
        
        Car carBD = CarDao.getInstance().getCar(car.getId());
        assertEquals(newMark, carBD.getMark());
    }

    /**
     * Test of getCar method, of class CarDao.
     */
    @Test
    public void testGetCar() throws Exception {
        System.out.println("getCar");
        Car carBD = CarDao.getInstance().getCar(car.getId());
        assertNotNull(carBD);
    }

    /**
     * Test of getAllCars method, of class CarDao.
     */
    @Test
    public void testGetAllCars() throws Exception {
        System.out.println("getAllCars");
        List<Car> listCar = CarDao.getInstance().getAllCars();
        assertFalse(Util.isEmpty(listCar));
    }
}