/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.carwash.dao;

import br.com.carwash.Util.Util;
import br.com.carwash.objects.Car;
import br.com.carwash.objects.Schedule;
import br.com.carwash.objects.ScheduleType;
import java.sql.SQLException;
import java.util.Calendar;
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
public class ScheduleDaoTest {
    
    private static Schedule schedule;
    
    public ScheduleDaoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws SQLException {
        System.out.println("BeforeClass#ScheduleDaoTest");
        
        Schedule scheduleInsert = new Schedule();
        scheduleInsert.setCar(new Car());
        scheduleInsert.getCar().setId(1);
        scheduleInsert.setDate(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
        scheduleInsert.setType(ScheduleType.OUTWASH);
        scheduleInsert.setTotal(12.3F);
        
        int idSchedule = ScheduleDao.getInstance().addSchedule(scheduleInsert);
        scheduleInsert.setId(idSchedule);
        Schedule scheduleBD = ScheduleDao.getInstance().getSchedule(idSchedule);
        schedule = scheduleBD;
        
        assertEquals(scheduleInsert.getId(), scheduleBD.getId());
        assertEquals(scheduleInsert.getCar().getId(), scheduleBD.getCar().getId());
        assertEquals(scheduleInsert.getDate().toString(), scheduleBD.getDate().toString());
        assertEquals(scheduleInsert.getType(), scheduleBD.getType());
        assertEquals(scheduleInsert.getTotal(), scheduleBD.getTotal(), 0);
        
    }
    
    @AfterClass
    public static void tearDownClass() throws SQLException {
        System.out.println("AfterClass#ScheduleDaoTest");
        
        ScheduleDao.getInstance().removeSchedule(schedule.getId());
        Schedule scheduleBD = ScheduleDao.getInstance().getSchedule(schedule.getId());
        
        assertNull(scheduleBD);
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getInstance method, of class ScheduleDao.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        ScheduleDao expResult = ScheduleDao.getInstance();
        assertEquals(expResult, ScheduleDao.getInstance());
    }

    /**
     * Test of updateSchedule method, of class ScheduleDao.
     */
    @Test
    public void testUpdateSchedule() throws Exception {
        System.out.println("updateSchedule");
        ScheduleType schType = ScheduleType.INTERNALWASH;
        Schedule scheduleBD = ScheduleDao.getInstance().getSchedule(schedule.getId());
        scheduleBD.setType(schType);
        ScheduleDao.getInstance().updateSchedule(scheduleBD);
        scheduleBD = ScheduleDao.getInstance().getSchedule(schedule.getId());        
        assertEquals(schType, scheduleBD.getType());
    }

    /**
     * Test of getSchedule method, of class ScheduleDao.
     */
    @Test
    public void testGetSchedule() throws Exception {
        System.out.println("getSchedule");
        Schedule scheduleBD = ScheduleDao.getInstance().getSchedule(schedule.getId());
        assertNotNull(scheduleBD);
    }

    /**
     * Test of getSchedules method, of class ScheduleDao.
     */
    @Test
    public void testGetSchedules() throws Exception {
        System.out.println("getSchedules");
        List<Schedule> list = ScheduleDao.getInstance().getSchedules();
        
        assertFalse(Util.isEmpty(list));
    }
}