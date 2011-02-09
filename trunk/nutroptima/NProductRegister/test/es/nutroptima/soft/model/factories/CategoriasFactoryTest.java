/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.nutroptima.soft.model.factories;

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
public class CategoriasFactoryTest {

    public CategoriasFactoryTest() {
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
     * Test of getInstance method, of class CategoriasFactory.
     */
    @Test
    public void testGetInstance() {
    }

    /**
     * Test of getCategorias method, of class CategoriasFactory.
     */
    @Test
    public void testGetCategorias() {
        int c = CategoriasFactory.getInstance().getCategorias().size();
        assertEquals("No estan todas las categorias esperadas::"+c, c, 9);
    }

}