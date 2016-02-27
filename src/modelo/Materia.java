/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
@Table(name = "materia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Materia.findAll", query = "SELECT m FROM Materia m"),
    @NamedQuery(name = "Materia.findByNumeromateria", query = "SELECT m FROM Materia m WHERE m.numeromateria = :numeromateria"),
    @NamedQuery(name = "Materia.findByNombremateria", query = "SELECT m FROM Materia m WHERE m.nombremateria = :nombremateria"),
    @NamedQuery(name = "Materia.findByCreditosmateria", query = "SELECT m FROM Materia m WHERE m.creditosmateria = :creditosmateria")})
public class Materia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "numeromateria")
    private Integer numeromateria;
    @Column(name = "nombremateria")
    private String nombremateria;
    @Column(name = "creditosmateria")
    private Integer creditosmateria;
    @ManyToMany(mappedBy = "materiaList")
    private List<Docente> docenteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "materia")
    private List<Matricula> matriculaList;
    @JoinColumn(name = "numerocarrera", referencedColumnName = "numerocarrera")
    @ManyToOne
    private Carrera numerocarrera;

    public Materia() {
    }

    public Materia(Integer numeromateria) {
        this.numeromateria = numeromateria;
    }

    public Integer getNumeromateria() {
        return numeromateria;
    }

    public void setNumeromateria(Integer numeromateria) {
        this.numeromateria = numeromateria;
    }

    public String getNombremateria() {
        return nombremateria;
    }

    public void setNombremateria(String nombremateria) {
        this.nombremateria = nombremateria;
    }

    public Integer getCreditosmateria() {
        return creditosmateria;
    }

    public void setCreditosmateria(Integer creditosmateria) {
        this.creditosmateria = creditosmateria;
    }

    @XmlTransient
    public List<Docente> getDocenteList() {
        return docenteList;
    }

    public void setDocenteList(List<Docente> docenteList) {
        this.docenteList = docenteList;
    }

    @XmlTransient
    public List<Matricula> getMatriculaList() {
        return matriculaList;
    }

    public void setMatriculaList(List<Matricula> matriculaList) {
        this.matriculaList = matriculaList;
    }

    public Carrera getNumerocarrera() {
        return numerocarrera;
    }

    public void setNumerocarrera(Carrera numerocarrera) {
        this.numerocarrera = numerocarrera;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numeromateria != null ? numeromateria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Materia)) {
            return false;
        }
        Materia other = (Materia) object;
        if ((this.numeromateria == null && other.numeromateria != null) || (this.numeromateria != null && !this.numeromateria.equals(other.numeromateria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Materia[ numeromateria=" + numeromateria + " ]";
    }
    
}
