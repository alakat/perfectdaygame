/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.nutroptima.soft.model.factories;

import es.nutroptima.soft.model.MyVItem;
import es.nutroptima.soft.model.Producto;
import es.nutroptima.soft.model.Usuario;
import es.nutroptima.soft.model.exception.LoginException;
import java.sql.SQLException;
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
public class ItemsPersitentTest {

    private static int idProductoTest;


    public ItemsPersitentTest() {
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

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void testSave() throws ClassNotFoundException, SQLException, LoginException{
        Usuario u = UsuarioFactory.getInstance().makeUsuario("betty", "betty");
        Producto p = new Producto(u);
        p.setCategoria(CategoriasFactory.getInstance().getCategorias().get(3));
        p.setGrasas(12);
        p.setProteinas(45);
        p.setHidratosCarbono(35);
        p.setKilocalorias(2400);
        p.setTitulo("Chocolate con leche gueno");
        p.save();
        MyVItem item = new MyVItem(p);
        item.setCantidad(123);
        item.setTitulo(ItemsFactory.getInstance().getMyvTitulos().get(2));
        item.setUnidad(ItemsFactory.getInstance().getUnidadesPeso().get(3));
        item.save();
        idProductoTest = p.getId();
        Logger.getLogger(ItemsPersitentTest.class.getName()).log(Level.INFO, ">>>>>>>>>>>>>>>>>>>>>IDPRODUCTO TEST:"+idProductoTest);
    }



    @Test
    public void testUpdate() throws ClassNotFoundException,  SQLException,   LoginException{
        boolean test = false;
        Usuario u = UsuarioFactory.getInstance().makeUsuario("betty", "betty");
        for (Producto producto : u.getProductos()) {
            Logger.getLogger(ItemsPersitentTest.class.getName()).log(Level.INFO, "Product: "+producto.getId()+"=="+idProductoTest);
            if(producto.getId() == idProductoTest){
                Logger.getLogger(ItemsPersitentTest.class.getName()).log(Level.INFO, "Modificamos y salvamos items");
                producto.getItems().get(0).setCantidad(-900);
                producto.getItems().get(0).save();
                test = true;
            }
        }
        assertTrue("NO ha actualizado", test);
    }


    @Test
    public void testDelete() throws ClassNotFoundException,  SQLException,   LoginException{
        boolean test = false;
        Usuario u = UsuarioFactory.getInstance().makeUsuario("betty", "betty");
        for (Producto producto : u.getProductos()) {
            Logger.getLogger(ItemsPersitentTest.class.getName()).log(Level.INFO, "Product: "+producto.getId()+"=="+idProductoTest);
            if(producto.getId() == idProductoTest){
                Logger.getLogger(ItemsPersitentTest.class.getName()).log(Level.INFO, "Modificamos y salvamos items");
                producto.getItems().get(0).setCantidad(-93929);
                producto.getItems().get(0).save();
                producto.getItems().get(0).delete();
                test = true;
                producto.delete();
            }
        }
        assertTrue("NO ha actualizado", test);
    }

}