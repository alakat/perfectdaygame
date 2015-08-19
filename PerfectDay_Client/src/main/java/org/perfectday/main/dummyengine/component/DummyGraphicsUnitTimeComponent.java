/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.perfectday.main.dummyengine.component;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import javax.swing.JPanel;
import org.apache.log4j.Logger;
import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.logicengine.model.unittime.LongUnitTime;
import org.perfectday.logicengine.model.unittime.factories.LongUnitTimeFactory;

/**
 *
 * @author Miguel (alakat@gmail.com)
 */
public class DummyGraphicsUnitTimeComponent extends JPanel {

    private static final Logger logger = Logger.getLogger(DummyGraphicsUnitTimeComponent.class);
    private static final int MAX_WIDTH = 220;
    private static final int MAX_HEIGHT = 80;

    /**
     * Tiempo gastado en %
     */
    private double timeSpendPercent;

    /**
     *
     */
    private LongUnitTime selectedActionTimeSpend;
    /**
     * Tiempo m
     */
    private LongUnitTime maxTime;

    /**
     *
     */
    private LongUnitTime unitTime;

    public DummyGraphicsUnitTimeComponent() {
        this.setBackground(new Color(0, 0, 0, 0));//Transparente

    }

    /**
     * Nuevo turno
     *
     * @param mini
     */
    public void setActiveMini(Mini mini) {
        this.timeSpendPercent = 0.0;

        newActivationMini(mini);

    }

    /**
     * Prepara el componente para un nuevo turno
     *
     * @param mini
     */
    public void newActivationMini(Mini mini) {
        this.unitTime = LongUnitTimeFactory.getInstance().doNothing(mini);
        this.maxTime = LongUnitTimeFactory.getInstance().doNothing(mini);
        this.maxTime.plus(LongUnitTimeFactory.getInstance().doMovementAction(mini));
        this.maxTime.plus(LongUnitTimeFactory.getInstance().doCombat(mini));

        reCalcTimePercent();
        this.selectedActionTimeSpend = LongUnitTimeFactory.getInstance().zero(mini);
        this.updateUI();
    }

    /**
     * No mini selected
     */
    public void noMiniSelected() {
        this.unitTime = LongUnitTimeFactory.getInstance().zero(null);
        this.selectedActionTimeSpend = LongUnitTimeFactory.getInstance().zero(null);
        this.maxTime = LongUnitTimeFactory.getInstance().one((Mini) null);
        this.reCalcTimePercent();
        this.updateUI();
    }

    /**
     * Recalculo el % de tiempo usado
     */
    private void reCalcTimePercent() {
        if (unitTime != null && maxTime != null && maxTime.getValue() != 0) {
            this.timeSpendPercent = ((double) this.unitTime.getValue())
                    / ((double) this.maxTime.getValue());
        } else {
            this.timeSpendPercent = 0;
        }
    }

    /**
     * Modifica el componente de acuerdo a la nueva acci?n adquirida
     *
     * @param time
     */
    public void selectedAction(LongUnitTime time) {
        this.selectedActionTimeSpend = time;
        this.reCalcTimePercent();
        this.updateUI();
    }

    /**
     * Asgina el nuevo tiempo del mini
     *
     * @param longUnitTime
     */
    public void confirmMiniMovement(LongUnitTime longUnitTime) {
        this.unitTime = longUnitTime;
        this.reCalcTimePercent();
        this.updateUI();
    }

    /**
     *
     * @param longUnitTime
     */
    public void confirmMiniAporEllos(LongUnitTime longUnitTime) {
        this.unitTime = longUnitTime;
        this.updateUI();
    }

    @Override
    public void paint(Graphics g1) {
        super.paint(g1);
        Graphics g = g1.create();
        LongUnitTime lt;
        try {
            try {
                if (unitTime != null) {
                    lt = (LongUnitTime) this.unitTime.clone();
                } else {
                    lt = LongUnitTimeFactory.getInstance().zero(null);
                }
                if (this.selectedActionTimeSpend != null) {
                    lt.plus(this.selectedActionTimeSpend);
                }
            } catch (CloneNotSupportedException ex) {
                logger.fatal("ERROR EN EL PINTADO DEL COMPONENTE de tiempo de activaci?n", ex);
                return;
            }

            double usedAndPendingTimePercent = 0;
            if (this.maxTime != null && this.maxTime.getValue() != 0) {
                usedAndPendingTimePercent = ((double) lt.getValue())
                        / ((double) this.maxTime.getValue());
            }

            int width = MAX_WIDTH;
            int height = MAX_HEIGHT;

            logger.debug((unitTime != null ? unitTime.getValue() : "null")
                    + "/" + (this.maxTime != null ? this.maxTime.getValue() : "null")
                    + " " + (this.selectedActionTimeSpend != null ? this.selectedActionTimeSpend.getValue() : "null")
                    + " (" + this.timeSpendPercent + "/" + usedAndPendingTimePercent + ")");
            Graphics2D g2d = (Graphics2D) g;
            Polygon pRectanguloDoNothing= this.getRectuaguloDoNothing(width, height);
            Polygon pRectanguloBase = this.getRectuaguloBase(width, height);
            Polygon pTriangulo = this.getTriangulo(width, height);
            Polygon pRectanguloFinal = this.getRectanguloFinal(width, height);
            Polygon pCuadoPrimero = this.getCuadroPrimero(width, height);
            Polygon pCuadoSegundo = this.getCuadroSegundo(width, height);

            g2d.setColor(this.getColorByTimes(this.timeSpendPercent, usedAndPendingTimePercent, -1));
            g2d.fillPolygon(pRectanguloDoNothing);
            
            g2d.setColor(this.getColorByTimes(this.timeSpendPercent, usedAndPendingTimePercent, 0.33));
            g2d.fillPolygon(pRectanguloBase);

            g2d.fillPolygon(pTriangulo);
            g2d.setColor(this.getColorByTimes(this.timeSpendPercent, usedAndPendingTimePercent, 0.66));
            g2d.fillPolygon(pRectanguloFinal);
            g2d.setColor(this.getColorByTimes(this.timeSpendPercent, usedAndPendingTimePercent, 1));
            g2d.fillPolygon(pCuadoPrimero);
            g2d.setColor(this.getColorByTimes(this.timeSpendPercent, usedAndPendingTimePercent, 1.2));
            g2d.fillPolygon(pCuadoSegundo);
            
            g2d.setColor(new Color(0.8f, 0.4f, 0.01f)); //buscar un buen color
            g2d.setFont(new Font(Font.MONOSPACED, Font.BOLD, 18));
            g2d.drawString("+"+((unitTime!=null?unitTime.getValue():0)
                                +(this.selectedActionTimeSpend!=null?this.selectedActionTimeSpend.getValue():0)),
                    (int) (width*0.75), 
                    (int) (0.9*height));

        } catch (Exception ex) {
            logger.debug("Error menor de visualizaci?n", ex);
        }
    }

    
    /**
     * Devuelve el primer rectangulo
     *
     * @param width
     * @param height
     * @return
     */
    private Polygon getRectuaguloDoNothing(int width, int height) {
        Polygon p = new Polygon();
        p.addPoint((int) (0.1 * width), (int) (0.1 * height));
        p.addPoint((int) (0.12 * width), (int) (0.1 * height));
        p.addPoint((int) (0.12 * width), (int) (0.9 * height));
        p.addPoint((int) (0.1 * width), (int) (0.9 * height));
        return p;
    }
    
    /**
     * Devuelve el primer rectangulo
     *
     * @param width
     * @param height
     * @return
     */
    private Polygon getRectuaguloBase(int width, int height) {
        Polygon p = new Polygon();
        p.addPoint((int) (0.12 * width), (int) (0.1 * height));
        p.addPoint((int) (0.28 * width), (int) (0.1 * height));
        p.addPoint((int) (0.28 * width), (int) (0.9 * height));
        p.addPoint((int) (0.12 * width), (int) (0.9 * height));
        return p;
    }

    /**
     * Triangulo de pendiente
     *
     * @param width
     * @param height
     * @return
     */
    private Polygon getTriangulo(int width, int height) {
        Polygon p = new Polygon();

        p.addPoint((int) (0.28 * width), (int) (0.1 * height));
        p.addPoint((int) (0.35 * width), (int) (0.9 * height));
        p.addPoint((int) (0.28 * width), (int) (0.9 * height));
        return p;
    }

    /**
     *
     * @param width
     * @param height
     * @return
     */
    private Polygon getRectanguloFinal(int width, int height) {
        Polygon p = new Polygon();
        p.addPoint((int) (0.33 * width), (int) (0.66 * height));
        p.addPoint((int) (0.60 * width), (int) (0.66 * height));
        p.addPoint((int) (0.60 * width), (int) (0.9 * height));
        p.addPoint((int) (0.33 * width), (int) (0.9 * height));
        return p;
    }

    /**
     *
     * @param width
     * @param height
     * @return
     */
    private Polygon getCuadroPrimero(int width, int height) {
        Polygon p = new Polygon();
        p.addPoint((int) (0.62 * width), (int) (0.66 * height));
        p.addPoint((int) (0.66 * width), (int) (0.66 * height));
        p.addPoint((int) (0.66 * width), (int) (0.9 * height));
        p.addPoint((int) (0.62 * width), (int) (0.9 * height));
        return p;
    }

    /**
     *
     * @param width
     * @param height
     * @return
     */
    private Polygon getCuadroSegundo(int width, int height) {
        Polygon p = new Polygon();
        p.addPoint((int) (0.68 * width), (int) (0.66 * height));
        p.addPoint((int) (0.72 * width), (int) (0.66 * height));
        p.addPoint((int) (0.72 * width), (int) (0.9 * height));
        p.addPoint((int) (0.68 * width), (int) (0.9 * height));
        return p;
    }

    /**
     * Calcula el color con el que deben estar pintados los componentes en
     * funci?n del gasto temporal del mini y del previsto por la acci?n
     * seleccionada.
     *
     * @param timeSpendPercent
     * @param usedAndPendingTimePercent
     * @return
     */
    private Color getColorByTimes(double timeSpendPercent,
            double usedAndPendingTimePercent, double actualTimeComponent) {
        Color noSpendColor = new Color(.8f, .8f, 0.8f, 1);
        Color spendColor = new Color(.0f, 0.9f, 0.1f, 1);
        Color pendingSpendColor = new Color(.4f, .4f, .0f, 1);
        Color color = null;
        if (actualTimeComponent < timeSpendPercent) {
            //Tiempo agotado
            color = spendColor;
        } else if (actualTimeComponent < usedAndPendingTimePercent) {
            //tiempo de acci?n pendiente de confirmar
            color = pendingSpendColor;
        } else {
            //tiempo no agotado
            color = noSpendColor;
        }

        return color;
    }

}
