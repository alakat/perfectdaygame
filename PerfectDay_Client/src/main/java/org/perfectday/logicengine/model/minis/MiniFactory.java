/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.model.minis;

import es.bitsonfire.PDMinisDatabase;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;
import java.util.StringTokenizer;
import org.apache.log4j.Logger;
import org.perfectday.logicengine.core.configuration.Configuration;
import org.perfectday.logicengine.model.minis.action.CombatActionFactory;
import org.perfectday.logicengine.model.minis.support.factory.SupportFactory;

/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class MiniFactory {
    
    private Random rand;
    private CombatActionFactory  combatActionFactory;
    private static MiniFactory instance;
    private org.perfectday.logicengine.core.industry.MiniFactory baseMiniFactory = 
            org.perfectday.logicengine.core.industry.MiniFactory.getInstance();
    private Properties miniBaseAttributes;
    
    public static final int CAPTAIN_POINT = 9;
    public static final int CENTURIONT_POITN = 7;
    public static final int HERO_POINT = 13;
    public static final int LEGENDARY_POINT = 17;
    public static final int SARGENT_POINT = 5;
    public static final int SIMPLE_SOLDIER_POINT = 3;
    
    private static final double SOLDIER_PRIMARY_ATTRIBUTE = 0.2;
    private static final double SOLDIER_SECONDARY_ATTRIBUTE = 0.15;
    private static final double SOLDIER_TERTIARY_ATTRIBUTE = (0.65)/6;
    private static final double ARCHER_PRIMARY_ATTRIBUTE=0.20;
    private static final double ARCHER_SECONDAY_ATTRIBUTE=0.20;
    private static final double ARCHER_TERTIARY_ATTRIBUTE=(0.6/6);
    private static final double GOLIATH_PRIMARY_ATTRIBUTE=0.2;
    private static final double GOLIATH_SECONDAY_ATTRIBUTE=0.2;
    private static final double GOLIATH_TERTIARY_ATTRIBUTE=(0.60/6);
    private static final double MAGE_PRIMARY_ATTRIBUTE=0.25;    
    private static final double MAGE_SECONDARY_ATTRIBUTE=0.15;
    private static final double MAGE_TERTIARY_ATTRIBUTE=(0.55/6);
    private static final double LANCER_PRIMARY_ATTRIBUTE=0.2;
    private static final double LANCER_SECONDAY_ATTRIBUTE=0.15;
    private static final double LANCER_TERTIARY_ATTRIBUTE=(0.65/6);
    //TODO: priest y asasain
    private static final double PRIEST_PRIMARY_ATTRIBUTE=0.25;    
    private static final double PRIEST_TERTIARY_ATTRIBUTE=(0.75/4);
    
    public static final double SOLDIER_COST=10;
    public static final double LANCER_COST=11;
    public static final double ARCHER_COST=18;
    public static final double MAGIC_COST=20;
    public static final double GOLIATH_COST=15;
    
    public static final double SIMPLE_SOLDIER=1;
    public static final double SARGET_SOLDIER=1.2;
    public static final double CENTURION_SOLDIER=1.6;
    public static final double CAPITAIN_SOLDIER=1.8;
    public static final double HERO_SOLDIER=2.0;
    public static final double LENGENDARY_HERO=2.5;
    public static final String[] NAMES={"Alakat",
        "Maria",
        "Zalzul",
        "Lobo",
        "Inmortal",
        "Vitorico",
        "La Rake",
        "Fader",
        "Sensy",
        "Ana",
        "Angela",
        "Gustavo","Naga cut","Alfa radical",
        "Chema","Demonio Rojo","Oso","Marques del Zapillo",
        "Rincewind","Rojo",
        "Rondador","Odin","Ruben","Sting","Ulises",
        "Fergu","Rafika","Laswin"};
    private static final double ASSASAIN_COST = 13.0;
    

    public MiniFactory() {
        this.rand = new Random(System.currentTimeMillis());
        this.combatActionFactory = CombatActionFactory.getInstance();
        this.miniBaseAttributes = new Properties();
        try{
            this.miniBaseAttributes.load(Configuration.getInstance().getBasicAttribute());
                    
        }catch(IOException ex){
            org.apache.log4j.Logger.getLogger(MiniFactory.class).error(
                    "No se pueden cargar los attributos iniciales.",ex);
            
        }
    }

    /**
     * Calcula el precio de un mini basado en el Nombre del mini
     * @param mini
     * @param level
     * @return
     */
    public double getMiniCost(String mini, MiniLevel level){
        double cost = baseMiniFactory.getMiniCost(mini);
         switch(level){
            case SIMPLE_SOLDIER: cost=cost*SIMPLE_SOLDIER; break;
            case SARGENT: cost=cost*SARGET_SOLDIER; break;
            case CENTURION: cost=cost*CENTURION_SOLDIER; break;
            case CAPTAIN: cost=cost*CAPITAIN_SOLDIER; break;
            case HERO: cost=cost*HERO_SOLDIER; break;
            case LEGENDARY_HERO: cost=cost*LENGENDARY_HERO; break;        
        }
        return cost;
    }
    
    
    /**
     * Calcula el precio de un mini basado en el Nombre del mini
     * @param mini
     * @param level
     * @return
     */
    public double getMiniCost( MiniLevel level){
        double cost = 1;
         switch(level){
            case SIMPLE_SOLDIER: cost=cost*SIMPLE_SOLDIER; break;
            case SARGENT: cost=cost*SARGET_SOLDIER; break;
            case CENTURION: cost=cost*CENTURION_SOLDIER; break;
            case CAPTAIN: cost=cost*CAPITAIN_SOLDIER; break;
            case HERO: cost=cost*HERO_SOLDIER; break;
            case LEGENDARY_HERO: cost=cost*LENGENDARY_HERO; break;        
        }
        return cost;
    }
    
    
    /**
     * Calcula el precio de un mini basado en el tipo 
     * DEPRECATED
     * @param type
     * @param level
     * @return
     */
    public double getMiniCost(MiniType type,MiniLevel level){
        double cost=0;
        switch(type){
            case ARCHER: cost=ARCHER_COST; break;
            case GOLIATH: cost=GOLIATH_COST; break;
            case LANCER: cost=LANCER_COST;break;
            case MAGICAN: cost=MAGIC_COST;break;
            case SOLDIER: cost=SOLDIER_COST; break;
            case ASSASAIN: cost = ASSASAIN_COST;break;
            default: cost=10.0;
        }
        
        switch(level){
            case SIMPLE_SOLDIER: cost=cost*SIMPLE_SOLDIER; break;
            case SARGENT: cost=cost*SARGET_SOLDIER; break;
            case CENTURION: cost=cost*CENTURION_SOLDIER; break;
            case CAPTAIN: cost=cost*CAPITAIN_SOLDIER; break;
            case HERO: cost=cost*HERO_SOLDIER; break;
            case LEGENDARY_HERO: cost=cost*LENGENDARY_HERO; break;        
        }
        return cost;
    }
    
    
    public static MiniFactory getInstance() {
        if (instance==null)
            instance=new MiniFactory();
        return instance;
    }    
    
    public Mini createMiniBase(MiniType type, MiniLevel level){
        String cad = "";
        switch(type){
            case SOLDIER: cad = this.miniBaseAttributes.getProperty("SimpleSoldier"); break;
            case LANCER: cad = this.miniBaseAttributes.getProperty("Lancer"); break;
            case GOLIATH: cad = this.miniBaseAttributes.getProperty("Goliath"); break;
            case MAGICAN: cad = this.miniBaseAttributes.getProperty("Magican"); break;
            case ARCHER: cad = this.miniBaseAttributes.getProperty("Archer"); break;
            case ASSASAIN: cad =this.miniBaseAttributes.getProperty("Assasain");break;
            default: break;
        }
        StringTokenizer stok = new StringTokenizer(cad,",");
        int atk = Integer.parseInt(stok.nextToken());
        int dfs =Integer.parseInt(stok.nextToken());
        int str = Integer.parseInt(stok.nextToken());
        int res = Integer.parseInt(stok.nextToken());
        int vit = Integer.parseInt(stok.nextToken());
        int ini =Integer.parseInt(stok.nextToken());
        int afm = Integer.parseInt(stok.nextToken());
        int mvt = Integer.parseInt(stok.nextToken());
        int pName=this.rand.nextInt(NAMES.length);
        return new MiniModificable(atk, dfs, str, res, vit,(double) ini,(double) afm, mvt, level, type, null, null, null, false, null,NAMES[pName]);
    }
    
    
    public Mini createMini(String miniName,MiniLevel level) throws Exception{
        try {
            Mini mini = (Mini) baseMiniFactory.create(miniName);
            int pName = this.rand.nextInt(NAMES.length);
            mini.setName(NAMES[pName]);
            mini.setMiniLevel(level);
            int point = this.getPointForLevel(mini);
            switch (mini.getMiniType()) {
                case ARCHER:
                    generateArcher(point, mini);
                    break;
                case GOLIATH:
                    generateGoliath(point, mini);
                    break;
                case LANCER:
                    generateLancer(point, mini);
                    break;
                case MAGICAN:
                    generateMage(point, mini);
                    break;
                case SOLDIER:
                    generateSoldier(point, mini);
                    break;
                case ASSASAIN:
                    generateSoldier(point, mini);
                    break;
                default:
                    break;
            }
            return mini;
        } catch (Exception ex) {
          Logger.getLogger(MiniFactory.class).error("Error al crear el mini:"+miniName,ex);
          throw ex;
        }
    }
    
    /**
     * Crea un nuevo Mini basado en el tipo
     * DEPRECATED
     * @param type
     * @param level
     * @return
     */
    public Mini createMini(MiniType type,MiniLevel level){
        int pName=this.rand.nextInt(NAMES.length);
        Mini mini = createMiniBase(type, level);
        int point = this.getPointForLevel(mini);
        switch(type){
            case ARCHER: completArcher(point, mini); break;
            case GOLIATH: completGoliath(point,mini);break;
            case LANCER: completLancer(point, mini); break;
            case MAGICAN: completMagican(point,mini); break;
            case SOLDIER: completSoldier(point,mini); break;
            case ASSASAIN: completAssasain(point,mini);break;
            default: break;
        }
        return mini;
    }

    private void completAssasain(int point, Mini mini) {
        mini.setPrimaryAction(this.combatActionFactory.createParalizedDager());
        mini.setSecondaryAction(this.combatActionFactory.createPoisonedDager());
        mini.setConterAtack(true);
        mini.setConterAtackAction(this.combatActionFactory.createSimpleBladeAtack());
        //TODO Cambiar a las propias del asesino
        generateSoldier(point, mini);
       
    }

    private void completArcher(int point, Mini mini) {
        mini.setPrimaryAction(this.combatActionFactory.createSimpleBowAtack());
        mini.setSecondaryAction(combatActionFactory.createKnifSimpleAtack());
        generateArcher(point, mini);
    }

    private void completGoliath(int point, Mini mini) {
        mini.setPrimaryAction(this.combatActionFactory.createSimpleAxeAtack());
        mini.setSecondaryAction(this.combatActionFactory.createSimpleBladeAtack());
        generateGoliath(point, mini);
    }

    private void completLancer(int point, Mini mini) {
        mini.setPrimaryAction(this.combatActionFactory.createSimpleSpearAtack());
        mini.setSecondaryAction(this.combatActionFactory.createKnifSimpleAtack());
        mini.setConterAtack(true);
        mini.setConterAtackAction(this.combatActionFactory.createKnifSimpleAtack());
        generateLancer(point, mini);
        mini.setSupport(SupportFactory.getInstance().createShieldSupport(mini));
    }

    private void completMagican(int point, Mini mini) {
        mini.setPrimaryAction(this.combatActionFactory.createFireBallAction());
        mini.setSecondaryAction(this.combatActionFactory.createKnifSimpleAtack());
        generateMage(point, mini);
        mini.setSupport(SupportFactory.getInstance().createFireMagicanSupport(mini));
    }

    private void completSoldier(int point, Mini mini) {
        mini.setPrimaryAction(this.combatActionFactory.createSimpleBladeAtack());
        mini.setSecondaryAction(this.combatActionFactory.createSimpleBladeAtack());
        mini.setConterAtack(true);
        mini.setConterAtackAction(this.combatActionFactory.createSimpleBladeAtack());
        generateSoldier(point, mini);
        mini.setSupport(SupportFactory.getInstance().createBladeSupport(mini));
    }
   

    
    private int getPointForLevel(Mini next) {
        int points=0;
        switch(next.getMiniLevel()){
            case SIMPLE_SOLDIER: points=SIMPLE_SOLDIER_POINT; break;
            case SARGENT: points=SARGENT_POINT; break;
            case CENTURION: points=CENTURIONT_POITN; break;
            case CAPTAIN: points = CAPTAIN_POINT; break;
            case HERO: points = HERO_POINT; break;
            case LEGENDARY_HERO: points=LEGENDARY_POINT; break;        
        }
        return points;
    }

    private boolean isInRange(double init, double fin, double option) {
        return (init<=option) && (option<fin);
    }
    
    private void setAttributes(Mini next, int points) {
        
        switch(next.getMiniType()){
        case SOLDIER: generateSoldier(points,next);break;
        case ARCHER:generateArcher(points,next);break;
        case GOLIATH:generateGoliath(points,next);break;
        case MAGICAN: generateMage(points,next);break;
        case LANCER: generateLancer(points,next);break;       
        }
    }
    
    private void generateGoliath(int points, Mini next) {
        for(int i=0;i<points;i++){
            double option  = this.rand.nextDouble();
            double init=0;
            double fin = GOLIATH_PRIMARY_ATTRIBUTE;
            if((option>=init) && (option<fin)){
                next.setDefense(next.getDefense()+1);
            }
            init+=GOLIATH_PRIMARY_ATTRIBUTE;
            fin +=GOLIATH_SECONDAY_ATTRIBUTE;
            if((option>=init) && (option<fin)){
                next.setVitality(next.getVitality()+1);
            }
            init+=GOLIATH_SECONDAY_ATTRIBUTE;
            fin +=GOLIATH_TERTIARY_ATTRIBUTE;
            if((option>=init) && (option<fin)){
                next.setAtak(next.getAtak()+1);
            }
            init+=GOLIATH_TERTIARY_ATTRIBUTE;
            fin +=GOLIATH_TERTIARY_ATTRIBUTE;
            if((option>=init) && (option<fin)){
                next.setIniciative(next.getIniciative()+1);
            }
            init+=GOLIATH_TERTIARY_ATTRIBUTE;
            fin +=GOLIATH_TERTIARY_ATTRIBUTE;
            if((option>=init) && (option<fin)){
                next.setMoviment(next.getMoviment()+1);
            }
            init+=GOLIATH_TERTIARY_ATTRIBUTE;
            fin +=GOLIATH_TERTIARY_ATTRIBUTE;
            if((option>=init) && (option<fin)){
                next.setStrength(next.getStrength()+1);
            }
            init+=GOLIATH_TERTIARY_ATTRIBUTE;
            fin +=GOLIATH_TERTIARY_ATTRIBUTE;
            if((option>=init) && (option<fin)){
                next.setResistance(next.getResistance()+1);
            }
            init+=GOLIATH_TERTIARY_ATTRIBUTE;
            fin +=GOLIATH_TERTIARY_ATTRIBUTE;
            if((option>=init) && (option<fin)){
                next.setMagicAfinity(next.getMagicAfinity()+1);
            }
        }
    }
    
    private void generateSoldier(int points, Mini next) {
        double init=0;
        double fin=0;
        for(int i=0;i<points;i++){
            double option = rand.nextDouble();
            fin +=SOLDIER_PRIMARY_ATTRIBUTE;
            if(isInRange(init,fin,option))
                next.setAtak(next.getAtak()+1);
            init=fin;
            fin +=SOLDIER_SECONDARY_ATTRIBUTE;
            if(isInRange(init, fin, option))
                next.setDefense(next.getDefense()+1);
            init=fin;
            fin +=SOLDIER_TERTIARY_ATTRIBUTE;
            if(isInRange(init, fin, option))
                next.setStrength(next.getStrength()+1);
            init=fin;
            fin +=SOLDIER_TERTIARY_ATTRIBUTE;
            if(isInRange(init, fin, option))
                next.setResistance(next.getResistance()+1);
            init=fin;
            fin +=SOLDIER_TERTIARY_ATTRIBUTE;
            if(isInRange(init, fin, option))
                next.setVitality(next.getVitality()+1);
            init=fin;
            fin +=SOLDIER_TERTIARY_ATTRIBUTE;
            if(isInRange(init, fin, option))
                next.setIniciative(next.getIniciative()+1);
            init=fin;
            fin +=SOLDIER_TERTIARY_ATTRIBUTE;
            if(isInRange(init, fin, option))
                next.setMagicAfinity(next.getMagicAfinity()+1);
            init=fin;
            fin +=SOLDIER_TERTIARY_ATTRIBUTE;
            if(isInRange(init, fin, option))
                next.setMoviment(next.getMoviment()+1);
            init=0;
            fin=0;
        }
    }
    
    private void generateMage(int points, Mini next) {
        for(int i=0;i<points;i++){
            double option  = rand.nextDouble();
            double init=0;
            double fin = MAGE_PRIMARY_ATTRIBUTE;
            if((option>=init) && (option<fin)){
                next.setMagicAfinity(next.getMagicAfinity()+1);
            }
            init+=MAGE_PRIMARY_ATTRIBUTE;
            fin +=MAGE_SECONDARY_ATTRIBUTE;
            if((option>=init) && (option<fin)){
                next.setAtak(next.getAtak()+1);
            }
            init+=MAGE_SECONDARY_ATTRIBUTE;
            fin +=MAGE_TERTIARY_ATTRIBUTE;
            if((option>=init) && (option<fin)){
                next.setDefense(next.getDefense()+1);
            }
            init+=MAGE_TERTIARY_ATTRIBUTE;
            fin +=MAGE_TERTIARY_ATTRIBUTE;
            if((option>=init) && (option<fin)){
                next.setVitality(next.getVitality()+1);
            }
            init+=MAGE_TERTIARY_ATTRIBUTE;
            fin +=MAGE_TERTIARY_ATTRIBUTE;
            if((option>=init) && (option<fin)){
                next.setMoviment(next.getMoviment()+1);
            }
            init+=MAGE_TERTIARY_ATTRIBUTE;
            fin +=MAGE_TERTIARY_ATTRIBUTE;
            if((option>=init) && (option<fin)){
                next.setIniciative(next.getIniciative()+1);
            }
            init+=MAGE_TERTIARY_ATTRIBUTE;
            fin +=MAGE_TERTIARY_ATTRIBUTE;
            if((option>=init) && (option<fin)){
                next.setResistance(next.getResistance()+1);
            }
            init+=MAGE_TERTIARY_ATTRIBUTE;
            fin +=MAGE_TERTIARY_ATTRIBUTE;
            if((option>=init) && (option<fin)){
                next.setStrength(next.getStrength()+1);
            }
        }
    }
    
    private void generateArcher(int points, Mini next) {
        for(int i=0;i<points;i++){
            double option  = rand.nextDouble();
            double init=0;
            double fin = ARCHER_PRIMARY_ATTRIBUTE;
            if((option>=init) && (option<fin)){
                next.setAtak(next.getAtak()+1);
            }
            init+=ARCHER_PRIMARY_ATTRIBUTE;
            fin +=ARCHER_SECONDAY_ATTRIBUTE;
            if((option>=init) && (option<fin)){
                next.setMoviment(next.getMoviment()+1);
            }
            init+=ARCHER_SECONDAY_ATTRIBUTE;
            fin +=ARCHER_TERTIARY_ATTRIBUTE;
            if((option>=init) && (option<fin)){
                next.setIniciative(next.getIniciative()+1);
            }
            init+=ARCHER_TERTIARY_ATTRIBUTE;
            fin +=ARCHER_TERTIARY_ATTRIBUTE;
            if((option>=init) && (option<fin)){
                next.setDefense(next.getDefense()+1);
            }
            init+=ARCHER_TERTIARY_ATTRIBUTE;
            fin +=ARCHER_TERTIARY_ATTRIBUTE;
            if((option>=init) && (option<fin)){
                next.setVitality(next.getVitality()+1);
            }
            init+=ARCHER_TERTIARY_ATTRIBUTE;
            fin +=ARCHER_TERTIARY_ATTRIBUTE;
            if((option>=init) && (option<fin)){
                next.setStrength(next.getStrength()+1);
            }
            init+=ARCHER_TERTIARY_ATTRIBUTE;
            fin +=ARCHER_TERTIARY_ATTRIBUTE;
            if((option>=init) && (option<fin)){
                next.setResistance(next.getResistance()+1);
            }
            init+=ARCHER_TERTIARY_ATTRIBUTE;
            fin +=ARCHER_TERTIARY_ATTRIBUTE;
            if((option>=init) && (option<fin)){
                next.setMagicAfinity(next.getMagicAfinity()+1);
            }
        }
    }
    
    private void generateLancer(int points, Mini next) {
        for(int i=0;i<points;i++){
            double option  = rand.nextDouble();
            double init=0;
            double fin = LANCER_PRIMARY_ATTRIBUTE;
            if((option>=init) && (option<fin)){
                next.setDefense(next.getDefense()+1);
            }
            init+=LANCER_PRIMARY_ATTRIBUTE;
            fin +=LANCER_SECONDAY_ATTRIBUTE;
            if((option>=init) && (option<fin)){
                next.setAtak(next.getAtak()+1);
            }
            init+=LANCER_SECONDAY_ATTRIBUTE;
            fin +=LANCER_TERTIARY_ATTRIBUTE;
            if((option>=init) && (option<fin)){
                next.setMoviment(next.getMoviment()+1);
            }
            init+=LANCER_TERTIARY_ATTRIBUTE;
            fin +=LANCER_TERTIARY_ATTRIBUTE;
            if((option>=init) && (option<fin)){
                next.setIniciative(next.getIniciative()+1);
            }
            init+=LANCER_TERTIARY_ATTRIBUTE;
            fin +=LANCER_TERTIARY_ATTRIBUTE;
            if((option>=init) && (option<fin)){
                next.setVitality(next.getVitality()+1);
            }
            init+=LANCER_TERTIARY_ATTRIBUTE;
            fin +=LANCER_TERTIARY_ATTRIBUTE;
            if((option>=init) && (option<fin)){
                next.setMagicAfinity(next.getMagicAfinity()+1);
            }
            init+=LANCER_TERTIARY_ATTRIBUTE;
            fin +=LANCER_TERTIARY_ATTRIBUTE;
            if((option>=init) && (option<fin)){
                next.setStrength(next.getStrength()+1);
            }
            init+=LANCER_TERTIARY_ATTRIBUTE;
            fin +=LANCER_TERTIARY_ATTRIBUTE;
            if((option>=init) && (option<fin)){
                next.setResistance(next.getResistance()+1);
            }
        }
    }
}
