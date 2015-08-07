/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.core.industry;
import es.bitsonfire.PDMinisDatabase;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import es.bitsonfire.PDMinisDatabase;
import org.apache.log4j.Logger;
import org.perfectday.logicengine.core.configuration.Configuration;

/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public abstract class IndexFactory {
    
    protected Map<String,Object> database;
    protected static final String NONE = "NONE";
    protected static Logger logger = Logger.getLogger(IndexFactory.class);
    
    public IndexFactory(InputStream is,boolean indexProperties)  {
        try {
            //Solo por obligar las referencias
            System.out.println("Index factory:"+is);
            PDMinisDatabase pDMinisDatabase = new PDMinisDatabase();
            
            Properties index = new Properties();
            database = new HashMap<String, Object>();
            index.load(is);
            Set set = index.keySet();
            if(indexProperties)
                loadDataBaseByProperties(index, set);
            else
                loadDataBaseByClass(index, set);
        } catch (ClassNotFoundException ex) {
            logger.error("No se carga la clase",ex);
            System.exit(0);
        } catch (FileNotFoundException ex) {
            logger.error("No se carga la clase",ex);
            System.exit(0);
        }catch (IOException ex) {
            logger.error("No se carga la clase",ex);
            System.exit(0);
        } 
    }
    
    public abstract Object create(String key) throws Exception;
    
    public Set<String> getIndex(){
        return this.database.keySet();
    }

    private void loadDataBaseByClass(Properties index, Set set) throws ClassNotFoundException {
        for (Object object : set) {
            String clazz = index.getProperty((String)object);
            this.database.put((String)object, Class.forName(clazz));
        }        
    }

    private void loadDataBaseByProperties(Properties index, Set set) {
        String path = Configuration.DATA_MINI_PATH;
        for (Object object : set) {
            if (object instanceof String) {
                String name = (String) object;
                String url = index.getProperty(name);
                url = path + File.separator + url;
                Properties p = new Properties();
                try {
                    logger.debug("Properties to load:"+url);
                    p.load(PDMinisDatabase.class.getResourceAsStream(url));
                    database.put(name, p);
                    logger.info("Nuevo elemento cargado[" + name + "]");
                } catch (Exception ex) {
                    logger.error("No se carga[" + name + "]");
                }
            }
        }
    }
    

}
