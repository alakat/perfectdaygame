package org.perfectday.logicengine.model.activationstack;

import org.perfectday.logicengine.model.minis.Mini; 

// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.8642A37C-EBDF-C165-0E8C-F823DF00444A]
// </editor-fold> 
public class InstanceColaboration extends CarringOut {

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.AAEC000B-DA8D-CDB0-E80C-55946735E80F]
    // </editor-fold> 
    private Mini ejecutor;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.37A09477-F24C-2459-F3E8-A587758945ED]
    // </editor-fold> 
    private Mini receptor;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.B10DC603-B47D-56E8-96B8-78AD92B26A25]
    // </editor-fold> 
    public InstanceColaboration () {
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.456D38C4-6C4D-84B0-5D27-CEBD18AD60AD]
    // </editor-fold> 
    public Mini getEjecutor () {
        return ejecutor;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.8E29B469-AB84-0D62-20DC-1D922DB4B88F]
    // </editor-fold> 
    public void setEjecutor (Mini val) {
        this.ejecutor = val;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.CC293A5C-BEDE-FC26-7E71-546651DB0590]
    // </editor-fold> 
    public Mini getReceptor () {
        return receptor;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.C1AFED55-1609-1073-BAF1-5E9F9588ED1C]
    // </editor-fold> 
    public void setReceptor (Mini val) {
        this.receptor = val;
    }

}

