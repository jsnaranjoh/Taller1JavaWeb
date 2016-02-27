/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jsnar
 */
@Entity
@Table(name = "carrera")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Carrera.findAll", query = "SELECT c FROM Carrera c"),
    @NamedQuery(name = "Carrera.findByNumerocarrera", query = "SELECT c FROM Carrera c WHERE c.numerocarrera = :numerocarrera"),
    @NamedQuery(name = "Carrera.findByNombrecarrera", query = "SELECT c FROM Carrera c WHERE c.nombrecarrera = :nombrecarrera")})
public class Carrera implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "numerocarrera")
    private Integer numerocarrera;
    @Column(name = "nombrecarrera")
    private String nombrecarrera;
    @OneToMany(mappedBy = "numerocarrera")
    private List<Materia> materiaList;

    public Carrera() {
    }

    public Carrera(Integer numerocarrera) {
        this.numerocarrera = numerocarrera;
    }

    public Integer getNumerocarrera() {
        return numerocarrera;
    }

    public void setNumerocarrera(Integer numerocarrera) {
        this.numerocarrera = numerocarrera;
    }

    public String getNombrecarrera() {
        return nombrecarrera;
    }

    public void setNombrecarrera(String nombrecarrera) {
        this.nombrecarrera = nombrecarrera;
    }

    @XmlTransient
    public List<Materia> getMateriaList() {
        return materiaList;
    }

    public void setMateriaList(List<Materia> materiaList) {
        this.materiaList = materiaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numerocarrera != null ? numerocarrera.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Carrera)) {
            return false;
        }
        Carrera other = (Carrera) object;
        if ((this.numerocarrera == null && other.numerocarrera != null) || (this.numerocarrera != null && !this.numerocarrera.equals(other.numerocarrera))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return numerocarrera + "\n";
    }
    
}
