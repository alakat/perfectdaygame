package org.perfectday.logicengine.model.minis;


import java.io.Serializable;
import org.perfectday.logicengine.model.ReferenceObject;
import org.perfectday.logicengine.model.minis.action.ActionMini;
import org.perfectday.logicengine.model.minis.action.combat.CombatActionMini;
import org.perfectday.logicengine.model.minis.support.Support;
import org.perfectday.logicengine.model.state.State;
/**
 * Mini
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class Mini extends ReferenceObject {

    private int atak;
    private int defense;
    private int strength;
    private int resistance;
    private int vitality;
    private double damage;
    private double iniciative;
    private double magicAfinity;
    private int moviment;
    private MiniLevel miniLevel;
    private MiniType miniType;
    private ActionMini primaryAction;
    private ActionMini secondaryAction;
    private Support support;
    private boolean conterAtack;
    private CombatActionMini conterAtackAction;
    private String name;
    private String abstractName;
    private int pointMagic;

    public Mini() {
    }
    
    
    
    
    public Mini(String name) {
        this.abstractName = name;
    }

    
    public Mini(int atak, 
            int defense, 
            int strength,
            int resistance,
            int vitality,
            double iniciative,
            double magicAfinity,
            int moviment,
            MiniLevel miniLevel, 
            MiniType miniType,
            ActionMini primaryAction, 
            ActionMini secondaryAction,
            Support support,
            boolean conterAtack,
            CombatActionMini aconterAtack,String name) {
        this.atak = atak;
        this.defense = defense;
        this.strength = strength;
        this.resistance = resistance;
        this.vitality = vitality;
        this.iniciative = iniciative;
        this.magicAfinity = magicAfinity;
        this.moviment = moviment;
        this.miniLevel = miniLevel;
        this.miniType = miniType;
        this.primaryAction = primaryAction;
        this.secondaryAction = secondaryAction;
        this.support = support;
        this.conterAtack = conterAtack;
        this.conterAtackAction = aconterAtack;
        this.damage = 0;
        
        this.name=name;
    }

    public Mini(long id,
            int atak, 
            int defense, 
            int strength, 
            int resistance,
            int vitality,
            double iniciative,
            double magicAfinity,
            int moviment,
            MiniLevel miniLevel,
            MiniType miniType,
            ActionMini primaryAction,
            ActionMini secondaryAction,
            Support support,
            boolean conterAtack,
            CombatActionMini conterAtackAction) {
        super(id);
        this.atak = atak;
        this.defense = defense;
        this.strength = strength;
        this.resistance = resistance;
        this.vitality = vitality;
        this.iniciative = iniciative;
        this.magicAfinity = magicAfinity;
        this.moviment = moviment;
        this.miniLevel = miniLevel;
        this.miniType = miniType;
        this.primaryAction = primaryAction;
        this.secondaryAction = secondaryAction;
        this.support = support;
        this.conterAtack = conterAtack;
        this.conterAtackAction = conterAtackAction;
        this.damage=0;
    }

    private Mini(long id) {
        super(id);
    }
    
    

    public int getAtak() {
        return atak;
    }

    public void setAtak(int atak) {
        this.atak = atak;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public double getIniciative() {
        return iniciative;
    }

    public void setIniciative(double iniciative) {
        this.iniciative = iniciative;
    }

    public double getMagicAfinity() {
        return magicAfinity;
    }

    public void setMagicAfinity(double magicAfinity) {
        this.magicAfinity = magicAfinity;
        this.pointMagic= (int) (this.magicAfinity * 100);
    }

    public MiniLevel getMiniLevel() {
        return miniLevel;
    }

    public void setMiniLevel(MiniLevel miniLevel) {
        this.miniLevel = miniLevel;
    }

    public MiniType getMiniType() {
        return miniType;
    }

    public void setMiniType(MiniType miniType) {
        this.miniType = miniType;
    }

    public int getMoviment() {
        return moviment;
    }

    public void setMoviment(int moviment) {
        this.moviment = moviment;
    }

    public ActionMini getPrimaryAction() {
        return primaryAction;
    }

    public void setPrimaryAction(ActionMini primaryAction) {
        this.primaryAction = primaryAction;
    }

    public int getResistance() {
        return resistance;
    }

    public void setResistance(int resistance) {
        this.resistance = resistance;
    }

    public ActionMini getSecondaryAction() {
        return secondaryAction;
    }

    public void setSecondaryAction(ActionMini secondaryAction) {
        this.secondaryAction = secondaryAction;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public Support getSupport() {
        return support;
    }

    public void setSupport(Support support) {
        this.support = support;
    }

    public int getVitality() {
        return vitality;
    }

    public void setVitality(int vitality) {
        this.vitality = vitality;
    }

    public boolean isConterAtack() {
        return conterAtack;
    }

    public void setConterAtack(boolean conterAtack) {
        this.conterAtack = conterAtack;
    }

    public CombatActionMini getConterAtackAction() {
        return conterAtackAction;
    }

    public void setConterAtackAction(CombatActionMini conterAtackAction) {
        this.conterAtackAction = conterAtackAction;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
    
    public boolean isAlive(){
        return this.vitality>this.damage;
    }

    @Override
    public String toString() {
        return this.name+":: "+this.miniLevel+" / "+ this.abstractName+"["+this.atak+","+this.defense+","+this.strength+","+this.resistance+",]";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void addState(State state){
        throw new  UnsupportedOperationException("No mini modificable");
    }
    public void removeState(State state) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public String getAbstractName() {
        return abstractName;
    }

    public void setAbstractName(String abstractName) {
        this.abstractName = abstractName;
    }

    public int getPointMagic() {
        return pointMagic;
    }

    public void setPointMagic(int pointMagic) {
        this.pointMagic = pointMagic;
    }
    
    
    /**
     * Metodo para enviar la mini expresión de un mini.
     * @return
     */
    public Mini generateSimpleMini(){
        Mini m = new Mini(this.getId());
        return m;        
    }

   
    
    
    
}

