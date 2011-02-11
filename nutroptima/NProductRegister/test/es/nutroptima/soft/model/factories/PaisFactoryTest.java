/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.nutroptima.soft.model.factories;

import es.nutroptima.soft.model.Pais;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Sergio Álvarez López <salvarez@nutroptima.es>
 */
public class PaisFactoryTest {

    public PaisFactoryTest() {
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
     * Test of getPais method, of class PaisFactory.
     */
    @Test
    public void testGetPais() throws Exception {
        System.out.println("getPais");
        int id = 0;
        PaisFactory instance = new PaisFactory();
        Pais expResult = null;
        Pais result = instance.getPais(id);
        System.out.println(":"+result.getNombre());
      //  assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

}