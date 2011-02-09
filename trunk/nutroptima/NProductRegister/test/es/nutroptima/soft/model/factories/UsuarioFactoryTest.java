/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.nutroptima.soft.model.factories;

import es.nutroptima.soft.model.Usuario;
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
public class UsuarioFactoryTest {

    public UsuarioFactoryTest() {
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
     * Test of makeUsuario method, of class UsuarioFactory.
     */
    @Test
    public void testMakeUsuario() throws Exception {
        Usuario u = UsuarioFactory.getInstance().makeUsuario("betty", "betty");
        assertNotNull("Usuario no encontrado", u);
        assertEquals("No es el mismo usuario", "betty", u.getLogin());

    }

}