/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.core.industry;

import java.io.File;
import java.util.Properties;
import org.perfectday.logicengine.core.configuration.Configuration;
import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.logicengine.model.minis.MiniModificable;
import org.perfectday.logicengine.model.minis.MiniType;
import org.perfectday.logicengine.model.minis.action.ActionMini;
import org.perfectday.logicengine.model.minis.action.combat.CombatActionMini;
import org.perfectday.logicengine.model.minis.support.Support;
import org.perfectday.logicengine.model.spells.action.SpellCastAction;

/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class MiniFactory  extends IndexFactory{

    private static final String NAME = "name";
    private static final String ATACK="atack";
    private static final String DEFENSE="defense";
    private static final String STRENGTH="strength";
    private static final String RESISTENCE="resistence";
    private static final String INICIATIVE="iniciative";
    private static final String VITALITY="vitality";
    private static final String MAGIC_AFINITY="magic_afinity";
    private static final String SKILL="skill";
    private static final String TYPE="type";
    private static final String PRIMARY_ATACK="primary_atack";
    private static final String SECONDARY_ATACK="secondary_atack";
    private static final String CONTER_ATACK = "conter_atack";
    private static final String SUPPORT="support";
    private static final String COST="cost";
    private static MiniFactory instance;

    public MiniFactory(File f) {
        super(f,true);
    }

    public static MiniFactory getInstance() {
        if(instance == null )
            instance = new MiniFactory(Configuration.getInstance().getMiniFile());
        return instance;
    }

    @Override
    public Object create(String key) throws Exception {
        key = key.trim();
        Properties p = (Properties) this.database.get(key);
        int atk=Integer.parseInt(p.getProperty(MiniFactory.ATACK).trim());
        int dfs=Integer.parseInt(p.getProperty(MiniFactory.DEFENSE).trim());
        int str=Integer.parseInt(p.getProperty(MiniFactory.STRENGTH).trim());
        int res=Integer.parseInt(p.getProperty(MiniFactory.RESISTENCE).trim());
        double ini=Double.parseDouble(p.getProperty(MiniFactory.INICIATIVE).trim());
        int vit = Integer.parseInt(p.getProperty(MiniFactory.VITALITY).trim());
        double afm=Double.parseDouble(p.getProperty(MiniFactory.MAGIC_AFINITY).trim());
        int skill = Integer.parseInt(p.getProperty(MiniFactory.SKILL).trim());
        String name =p.getProperty(MiniFactory.NAME);
        Mini mini = new MiniModificable(name);
        ActionMini primario;
        ActionMini secundario;
        if(!p.getProperty(MiniFactory.PRIMARY_ATACK).startsWith("spell")){
            primario = generateCombatAction(p.getProperty(MiniFactory.PRIMARY_ATACK));
        }else{
            primario = (ActionMini) SpellFactory.getInstance().create(
                    p.getProperty(MiniFactory.PRIMARY_ATACK).substring(
                     p.getProperty(MiniFactory.PRIMARY_ATACK).indexOf(".")+1,
                     p.getProperty(MiniFactory.PRIMARY_ATACK).length()));
            ((SpellCastAction)primario).setCaster(mini);
        }
         if(!p.getProperty(MiniFactory.SECONDARY_ATACK).startsWith("spell")){
            secundario = generateCombatAction(p.getProperty(MiniFactory.SECONDARY_ATACK));
        }else{
            secundario = (ActionMini) SpellFactory.getInstance().create(
                    p.getProperty(MiniFactory.SECONDARY_ATACK).substring(
                     p.getProperty(MiniFactory.SECONDARY_ATACK).indexOf(".")+1,
                     p.getProperty(MiniFactory.SECONDARY_ATACK).length()));
            ((SpellCastAction)primario).setCaster(mini);
        }
        CombatActionMini conter = generateCombatAction(p.getProperty(MiniFactory.CONTER_ATACK));
        Support support = generateSupport(p.getProperty(MiniFactory.SUPPORT));
        MiniType mt = generateTypeMini(p.getProperty(MiniFactory.TYPE).trim());
        
        mini.setAtak(atk);
        mini.setDefense(dfs);
        mini.setConterAtack(conter!=null);
        mini.setConterAtackAction(conter);
        mini.setIniciative(ini);
        mini.setMiniType(mt);
        mini.setMoviment(skill);
        mini.setPrimaryAction(primario);
        mini.setSecondaryAction(secundario);
        mini.setStrength(str);
        mini.setVitality(vit);
        mini.setSupport(support);
        if(support!=null)
            support.setApoyador(mini);
        mini.setResistance(res);
        mini.setMagicAfinity(afm);
        return mini;
    }

    private CombatActionMini generateCombatAction(String property) throws Exception {
        if(property.equals(IndexFactory.NONE))
            return null;
        return (CombatActionMini) WeaponsFactory.getInstance().create(property);
    }

    private Support generateSupport(String property) throws Exception {
        if(property.equals(IndexFactory.NONE))
            return null;
        return (Support) SupportFactory.getInstance().create(property);
    }

    private MiniType generateTypeMini(String property) throws Exception {
        int opt = Integer.parseInt(property);
        switch(opt){
            case 1: return MiniType.SOLDIER;
            case 2: return MiniType.LANCER;
            case 3: return MiniType.ARCHER;
            case 4: return MiniType.GOLIATH;
            case 5: return MiniType.MAGICAN;
            case 6: return MiniType.ASSASAIN;
            default: throw new Exception("No Tiene un tipo correcto");
        }
    }
    
    public double getMiniCost(String mini){        
        if(!this.database.containsKey(mini))
            logger.error("Se consulta precio de mini desconocido:"+mini);
        return Double.parseDouble(((Properties)database.get(mini)).getProperty(MiniFactory.COST).trim());
    }
    
    
    
    
    
}
