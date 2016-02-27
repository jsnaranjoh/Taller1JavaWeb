/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author jsnar
 */
@Embeddable
public class MatriculaPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "documentoestudiante")
    private long documentoestudiante;
    @Basic(optional = false)
    @Column(name = "numeromateria")
    private int numeromateria;

    public MatriculaPK() {
    }

    public MatriculaPK(long documentoestudiante, int numeromateria) {
        this.documentoestudiante = documentoestudiante;
        this.numeromateria = numeromateria;
    }

    public long getDocumentoestudiante() {
        return documentoestudiante;
    }

    public void setDocumentoestudiante(long documentoestudiante) {
        this.documentoestudiante = documentoestudiante;
    }

    public int getNumeromateria() {
        return numeromateria;
    }

    public void setNumeromateria(int numeromateria) {
        this.numeromateria = numeromateria;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) documentoestudiante;
        hash += (int) numeromateria;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MatriculaPK)) {
            return false;
        }
        MatriculaPK other = (MatriculaPK) object;
        if (this.documentoestudiante != other.documentoestudiante) {
            return false;
        }
        if (this.numeromateria != other.numeromateria) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.MatriculaPK[ documentoestudiante=" + documentoestudiante + ", numeromateria=" + numeromateria + " ]";
    }
    
}
