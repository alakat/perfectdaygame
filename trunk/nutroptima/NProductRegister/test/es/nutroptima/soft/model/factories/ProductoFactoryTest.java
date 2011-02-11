/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.nutroptima.soft.model.factories;

import es.nutroptima.soft.model.Producto;
import es.nutroptima.soft.model.Usuario;
import es.nutroptima.soft.model.exception.LoginException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
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
        Usuario a= UsuarioFactory.getInstance().makeUsuario("celia", "celia");
        List l = ProductoFactory.getInstance().getProductosByUsuario(a);
        assertEquals("No ha encontrado valores correctos", 1, l.size());

    }


    @Test
    public void testNextID(){
        try {
            int x = ProductoFactory.getInstance().getNextProductID();
            Logger.getLogger(ProductoFactory.class.getName()).log(Level.INFO, "NextID:" + x);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProductoFactoryTest.class.getName()).log(Level.SEVERE, null, ex);
            fail("EX:"+ex);
        } catch (SQLException ex) {
            Logger.getLogger(ProductoFactoryTest.class.getName()).log(Level.SEVERE, null, ex);
            fail("EX:"+ex);
        }
    }

    @Test
    public void saveNewProduce() throws SQLException, ClassNotFoundException, LoginException{
        Usuario u = UsuarioFactory.getInstance().makeUsuario("betty", "betty");
        Producto p = new Producto(u);
        p.setTitulo("Netbeans");
        p.setKilocalorias(1200);
        p.setHidratosCarbono(123.2);
        p.setGrasas(1222);
        p.setProteinas(2);
        p.setCategoria(CategoriasFactory.getInstance().getCategorias().get(1));
        p.setPais(PaisFactory.getInstance().getPais(0));
        p.save();
    }

    @Test
    public void updateNewProduce() throws SQLException, ClassNotFoundException, LoginException{
        Usuario u = UsuarioFactory.getInstance().makeUsuario("betty", "betty");
        Producto p = u.getProductos().get(u.getProductos().size()-1);
        p.setTitulo("NETBEANS MOD");
        p.setKilocalorias(-1200);
        p.setHidratosCarbono(-123.2);
        p.setGrasas(-1222);
        p.setProteinas(-2);
        p.setCategoria(CategoriasFactory.getInstance().getCategorias().get(2));
        p.save();
    }


//    @Test
//    public void delete() throws ClassNotFoundException, SQLException, LoginException{
//        Usuario u = UsuarioFactory.getInstance().makeUsuario("betty", "betty");
//        Producto p = u.getProductos().get(u.getProductos().size()-1);
//        p.setTitulo("NO DEBERIA EXISTIR");
//        p.save();
//        p.delete();
//    }


    @Test
    public void getProductosItems() throws SQLException, ClassNotFoundException, LoginException{
        Usuario u = UsuarioFactory.getInstance().makeUsuario("betty", "betty");
        List<Producto> prs = u.getProductos();
        for (Producto p : prs) {
            if(p.getId()==1){
                Logger.getLogger(ProductoFactoryTest.class.getName()).log(Level.INFO, "Items"+p.getItems());
                assertEquals("Cantidad de items", 2, p.getItems().size());
            }
        }

    }

}