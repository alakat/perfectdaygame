/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.dashboard.threads;

import org.perfectday.threads.PerfectDayThreadGroup;

/**
 * Este ThreadGroup es lanzado con el Dashboard y sirve de puente de enlace
 * el nucleo y el DashBoard
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class DashBoardThreadGroup extends PerfectDayThreadGroup{

    private static final DashBoardThreadGroup instance = new DashBoardThreadGroup("Dashboard");

    private DashBoardThreadGroup(String name) {
        super(name);
    }

    public static DashBoardThreadGroup getInstance() {
        return instance;
    }
}
