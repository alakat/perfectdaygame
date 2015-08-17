/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.perfectday.oneplayer.battleloaders;

import java.util.Set;
import junit.framework.TestCase;
import org.perfectday.oneplayer.battleloaders.model.Quest;

/**
 *
 * @author Miguel (alakat@gmail.com)
 */
public class LoaderOfQuestTest extends TestCase {

    public LoaderOfQuestTest(String testName) {
        super(testName);
    }

    /**
     * Test of getInstance method, of class LoaderOfQuest.
     */
    public void testGetInstance() {
        System.out.println("getInstance");
        LoaderOfQuest result = LoaderOfQuest.getInstance();
    }

    /**
     * Test of getQuest method, of class LoaderOfQuest.
     */
    public void testGetQuest_0args() {
        try {
            System.out.println("getQuest");

            Set<String> result = LoaderOfQuest.getInstance().getQuest();
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getLocalizedMessage());
        }

    }

    /**
     * Test of getQuest method, of class LoaderOfQuest.
     */
    public void testGetQuest_String() throws Exception {
        try {
            System.out.println("getQuest");

            Quest result = LoaderOfQuest.getInstance().getQuest("tutorial");
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getLocalizedMessage());
        }

    }

}
