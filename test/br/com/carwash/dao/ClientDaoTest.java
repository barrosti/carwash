/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.carwash.dao;

import br.com.carwash.Util.Util;
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
public class ClientDaoTest {
    
    private static Client clientStatic;
    
    public ClientDaoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws SQLException {
        System.out.println("BeforeClass#ClientDaoTest");
        Client client = new Client();
        client.setName("Name Client Test");
        client.setAddress("Test example Street");
        client.setTelephone("34354353");
        client.setEmail("email@test.com");
        
        Integer idClient = ClientDao.getInstance().addClient(client);
        Client clientBD = ClientDao.getInstance().getClient(idClient);
        clientStatic = clientBD;
        
        assertEquals(idClient, clientBD.getId());
        assertEquals(client.getName(), clientBD.getName());
        assertEquals(client.getAddress(), clientBD.getAddress());
        assertEquals(client.getTelephone(), clientBD.getTelephone());
        assertEquals(client.getEmail(), clientBD.getEmail());
    }
    
    @AfterClass
    public static void tearDownClass() throws SQLException {
        System.out.println("AfterClass#ClientDaoTest");
        ClientDao.getInstance().removeClient(clientStatic.getId());
        Client clientBD = ClientDao.getInstance().getClient(clientStatic.getId());
        assertNull(clientBD);        
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getInstance method, of class ClientDao.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        ClientDao expResult = ClientDao.getInstance();
        assertEquals(expResult, ClientDao.getInstance());
    }

    /**
     * Test of updateClient method, of class ClientDao.
     */
    @Test
    public void testUpdateClient() throws Exception {
        System.out.println("updateClient");
        String newName = "New Name Client";
        
        Client clientUpdate = ClientDao.getInstance().getClient(clientStatic.getId());
        clientUpdate.setName(newName);
        ClientDao.getInstance().updateClient(clientUpdate);
        
        Client clientBD = ClientDao.getInstance().getClient(clientStatic.getId());
        assertEquals(newName, clientBD.getName());
    }

    /**
     * Test of getClient method, of class ClientDao.
     */
    @Test
    public void testGetClient() throws Exception {
        System.out.println("getClient");
        Client clientBD = ClientDao.getInstance().getClient(clientStatic.getId());
        assertEquals(clientStatic.getId(), clientBD.getId());
    }

    /**
     * Test of getAllClients method, of class ClientDao.
     */
    @Test
    public void testGetAllClients() throws Exception {
        System.out.println("getAllClients");
        List<Client> result = ClientDao.getInstance().getAllClients();
        assertFalse(Util.isEmpty(result));
    }

    /**
     * Test of getClientsByName method, of class ClientDao.
     */
    @Test
    public void testGetClientsByName() throws Exception {
        System.out.println("getClientsByName");
        Client clientBD = ClientDao.getInstance().getClient(clientStatic.getId());
        List<Client> listClients = ClientDao.getInstance().getClientsByName(clientBD.getName());
        
        assertFalse(Util.isEmpty(listClients));
    }
}