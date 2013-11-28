/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.carwash.dao;

import br.com.carwash.Util.Util;
import br.com.carwash.objects.Product;
import br.com.carwash.objects.Sell;
import br.com.carwash.objects.SellItem;
import br.com.carwash.objects.User;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.LinkedList;
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
public class SellDaoTest {
    
    private static Sell _sell;
    private static SellItem _sellItem;
    
    public SellDaoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws SQLException {
        System.out.println("BeforeClass#SellDaoTest");
        
        SellItem sellItem = new SellItem();
        sellItem.setProduct(new Product());
        sellItem.getProduct().setId(1);
        sellItem.setQnt(2);
        
        Sell sell = new Sell();
        sell.setUser(new User());
        sell.getUser().setId(1);
        sell.setDataOfSale(new Date(Calendar.getInstance().getTimeInMillis()));
        sell.setItens(new LinkedList<SellItem>());
        sell.getItens().add(sellItem);
        sellItem.setSell(sell);
        
        Integer idSell = SellDao.getInstance().addSell(sell);
        Sell sellBD = SellDao.getInstance().getSell(idSell);
        sell.setId(idSell);
        _sell = sellBD;
        _sellItem = sellBD.getItens().get(0);
        
        assertEquals(sell.getId(), sellBD.getId());
        assertEquals(sell.getUser().getId(), sellBD.getUser().getId());
        assertEquals(sell.getDataOfSale().toString(), sellBD.getDataOfSale().toString());
        assertEquals(sell.getTotal(), sellBD.getTotal(), 0);
        assertFalse(Util.isEmpty(sellBD.getItens()));
        
        assertEquals(_sellItem.getId(), sellItem.getId());
        assertEquals(_sellItem.getProduct().getId(), sellItem.getProduct().getId());
        assertEquals(_sellItem.getQnt(), sellItem.getQnt());
        
    }
    
    @AfterClass
    public static void tearDownClass() throws SQLException {
        System.out.println("AfterClass#SellDaoTest");
        
        SellDao.getInstance().removeSell(_sell.getId());
        Sell sellBD = SellDao.getInstance().getSell(_sell.getId());
        SellItem itemBD = SellDao.getInstance().getSellItem(_sellItem.getId());
        
        assertNull(sellBD);
        assertNull(itemBD);
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getInstance method, of class SellDao.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        SellDao expResult = SellDao.getInstance();
        assertEquals(expResult, SellDao.getInstance());
    }

    /**
     * Test of updateSell method, of class SellDao.
     */
    @Test
    public void testUpdateSell() throws Exception {
        System.out.println("updateSell");
        Integer idUser = 2;
        int qntSellItem = 3;
        
        Sell sell = SellDao.getInstance().getSell(_sell.getId());
        sell.getUser().setId(idUser);
        sell.getItens().get(0).setQnt(qntSellItem);
        
        SellDao.getInstance().updateSell(sell);
        
        assertEquals(idUser, sell.getUser().getId());
        assertEquals(qntSellItem, sell.getItens().get(0).getQnt());
        assertEquals(qntSellItem * _sellItem.getProduct().getPrice(), sell.getItens().get(0).getQnt() * sell.getItens().get(0).getProduct().getPrice(), 0);
        
    }
}