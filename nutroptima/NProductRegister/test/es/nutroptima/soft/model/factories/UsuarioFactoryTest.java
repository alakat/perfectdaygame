/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.nutroptima.soft.model.factories;

import es.nutroptima.soft.model.Usuario;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        Usuario u = UsuarioFactory.getInstance().makeUsuario("celia", "celia");
        Logger.getLogger(UsuarioFactoryTest.class.getName()).log(Level.INFO, "celia id:"+u.getId());
        assertNotNull("Usuario no encontrado("+u.getId()+")", u);
        assertEquals("No es el mismo usuario", "celia", u.getLogin());
        assertEquals("No es el mismo usuario", 1, u.getId());

    }

}