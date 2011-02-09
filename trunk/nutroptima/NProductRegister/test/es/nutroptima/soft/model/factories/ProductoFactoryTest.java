/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.nutroptima.soft.model.factories;

import es.nutroptima.soft.model.Usuario;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Miguel Angel López Montellano <mlopez@nutroptima.es>
 */
public class ProductoFactoryTest {

    public ProductoFactoryTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

   
    /**
     * Test of getProductosByUsuario method, of class ProductoFactory.
     */
    @Test
    public void testGetProductosByUsuario() throws Exception {
        Usuario a= new Usuario(0, "a", "a");
        List l = ProductoFactory.getInstance().getProductosByUsuario(a);
        assertEquals("No ha encontrado valores correctos", 2, l.size());

    }

}