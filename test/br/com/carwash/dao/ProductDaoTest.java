/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.carwash.dao;

import br.com.carwash.Util.Util;
import br.com.carwash.objects.Product;
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
public class ProductDaoTest {
    
    private static Product product;
    
    public ProductDaoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws SQLException {
        System.out.println("BeforeClass#ProductDaoTest");
        Product productInsert = new Product();
        productInsert.setName("Product Name Test");
        productInsert.setPrice(12.3F);
        productInsert.setStock(10);
        
        Integer idProduct = ProductDao.getInstance().addProduct(productInsert);
        Product productBD = ProductDao.getInstance().getProduct(idProduct);
        product = productBD;
        
        assertEquals(idProduct, productBD.getId());
        assertEquals(productInsert.getName(), productBD.getName());
        assertEquals(productInsert.getPrice(), productBD.getPrice(), 0);
        assertEquals(productInsert.getStock(), productBD.getStock());        
    }
    
    @AfterClass
    public static void tearDownClass() {
        System.out.println("BeforeClass#ProductDaoTest");
        
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getInstance method, of class ProductDao.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        ProductDao expResult = ProductDao.getInstance();
        assertEquals(expResult, ProductDao.getInstance());
    }

    /**
     * Test of updateProduct method, of class ProductDao.
     */
    @Test
    public void testUpdateProduct() throws Exception {
        System.out.println("updateProduct");
        String newName = "New Name Product Test";
        
        Product productUpdate = ProductDao.getInstance().getProduct(product.getId());
        productUpdate.setName(newName);
        ProductDao.getInstance().updateProduct(productUpdate);
        
        Product productBD = ProductDao.getInstance().getProduct(product.getId());
        assertEquals(newName, productBD.getName());
    }

    /**
     * Test of getProduct method, of class ProductDao.
     */
    @Test
    public void testGetProduct() throws Exception {
        System.out.println("getProduct");
        Product prod = ProductDao.getInstance().getProduct(product.getId());
        assertEquals(product.getId(), prod.getId());
    }

    /**
     * Test of getAllProducts method, of class ProductDao.
     */
    @Test
    public void testGetAllProducts() throws Exception {
        System.out.println("getAllProducts");
        List<Product> result = ProductDao.getInstance().getAllProducts();
        assertFalse(Util.isEmpty(result));
    }
    
    /**
     * Test of removeProduct method, of class ProductDao.
     */
    @Test
    public void testRemoveProduct() throws Exception {
        System.out.println("removeProduct");
        ProductDao.getInstance().removeProduct(product.getId());
        Product prod = ProductDao.getInstance().getProduct(product.getId());
        
        assertNull(prod);
    }    
}