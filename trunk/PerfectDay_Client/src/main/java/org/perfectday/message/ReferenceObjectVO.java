/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.message;

import java.io.Serializable;

/**
 *
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class ReferenceObjectVO implements Serializable {
    private Long id;

    public ReferenceObjectVO(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    

}
