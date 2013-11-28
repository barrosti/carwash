/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.carwash.dao;

import br.com.carwash.Util.Util;
import br.com.carwash.objects.User;
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
public class UserDaoTest {
    
    private static User user;
    
    public UserDaoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws SQLException {
        System.out.println("BeforeClass#UserDaoTest");
        User usr = new User();
        usr.setName("Test User");
        usr.setLogin("TestLogin");
        usr.setPassword("123");
        
        Integer idUser = UserDao.getInstance().addUser(usr);
        User userBD = UserDao.getInstance().getUser(idUser);
        user = userBD;
        assertEquals(idUser, userBD.getId());
        assertEquals(usr.getName(), userBD.getName());
        assertEquals(usr.getLogin(), userBD.getLogin());
        assertEquals(usr.getPassword(), userBD.getPassword());
    }
    
    @AfterClass
    public static void tearDownClass() throws SQLException {
        System.out.println("AfterClass#UserDaoTest");
        UserDao.getInstance().removeUser(user.getId());
        User userBD = UserDao.getInstance().getUser(user.getId());
        assertNull(userBD);        
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getInstance method, of class UserDao.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        UserDao expResult = UserDao.getInstance();
        assertEquals(expResult, UserDao.getInstance());
    }

    /**
     * Test of getUser method, of class UserDao.
     */
    @Test
    public void testGetUser() throws Exception {
        System.out.println("getUser");
        
        User result = UserDao.getInstance().getUser(user.getId());
        assertEquals(user.getId(), result.getId());
    }

    /**
     * Test of isValidLogin method, of class UserDao.
     */
    @Test
    public void testIsValidLogin() throws Exception {
        System.out.println("isValidLogin");
        User userBD = UserDao.getInstance().getUser(user.getId());
        String user = userBD.getLogin();
        String password = userBD.getPassword();
        boolean result = UserDao.getInstance().isValidLogin(user, password);
        assertTrue(result);
    }

    /**
     * Test of updateUser method, of class UserDao.
     */
    @Test
    public void testUpdateUser() throws Exception {
        System.out.println("updateUser");
        String newName = "Test User Name Changed";
        User userBD = UserDao.getInstance().getUser(user.getId());
        userBD.setName(newName);
        UserDao.getInstance().updateUser(userBD);
        userBD = UserDao.getInstance().getUser(user.getId());
        assertEquals(newName, userBD.getName());
    }

    /**
     * Test of getAllUsers method, of class UserDao.
     */
    @Test
    public void testGetAllUsers() throws Exception {
        System.out.println("getAllUsers");
        List result = UserDao.getInstance().getAllUsers();
        assertNotNull(result);
        assertFalse(Util.isEmpty(result));
    }
}