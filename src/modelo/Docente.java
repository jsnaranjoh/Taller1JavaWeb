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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jsnar
 */
@Entity
@Table(name = "docente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Docente.findAll", query = "SELECT d FROM Docente d"),
    @NamedQuery(name = "Docente.findByDocumentodocente", query = "SELECT d FROM Docente d WHERE d.documentodocente = :documentodocente"),
    @NamedQuery(name = "Docente.findByNombredocente", query = "SELECT d FROM Docente d WHERE d.nombredocente = :nombredocente"),
    @NamedQuery(name = "Docente.findByApellidodocente", query = "SELECT d FROM Docente d WHERE d.apellidodocente = :apellidodocente"),
    @NamedQuery(name = "Docente.findByCorreodocente", query = "SELECT d FROM Docente d WHERE d.correodocente = :correodocente"),
    @NamedQuery(name = "Docente.findByTelefonodocente", query = "SELECT d FROM Docente d WHERE d.telefonodocente = :telefonodocente"),
    @NamedQuery(name = "Docente.findByProfesiondocente", query = "SELECT d FROM Docente d WHERE d.profesiondocente = :profesiondocente"),
    @NamedQuery(name = "Docente.findByClavedocente", query = "SELECT d FROM Docente d WHERE d.clavedocente = :clavedocente")})
public class Docente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "documentodocente")
    private Long documentodocente;
    @Column(name = "nombredocente")
    private String nombredocente;
    @Column(name = "apellidodocente")
    private String apellidodocente;
    @Column(name = "correodocente")
    private String correodocente;
    @Column(name = "telefonodocente")
    private String telefonodocente;
    @Column(name = "profesiondocente")
    private String profesiondocente;
    @Column(name = "clavedocente")
    private String clavedocente;
    @JoinTable(name = "dicta", joinColumns = {
        @JoinColumn(name = "documentodocente", referencedColumnName = "documentodocente")}, inverseJoinColumns = {
        @JoinColumn(name = "numeromateria", referencedColumnName = "numeromateria")})
    @ManyToMany
    private List<Materia> materiaList;

    public Docente() {
    }

    public Docente(Long documentodocente) {
        this.documentodocente = documentodocente;
    }

    public Long getDocumentodocente() {
        return documentodocente;
    }

    public void setDocumentodocente(Long documentodocente) {
        this.documentodocente = documentodocente;
    }

    public String getNombredocente() {
        return nombredocente;
    }

    public void setNombredocente(String nombredocente) {
        this.nombredocente = nombredocente;
    }

    public String getApellidodocente() {
        return apellidodocente;
    }

    public void setApellidodocente(String apellidodocente) {
        this.apellidodocente = apellidodocente;
    }

    public String getCorreodocente() {
        return correodocente;
    }

    public void setCorreodocente(String correodocente) {
        this.correodocente = correodocente;
    }

    public String getTelefonodocente() {
        return telefonodocente;
    }

    public void setTelefonodocente(String telefonodocente) {
        this.telefonodocente = telefonodocente;
    }

    public String getProfesiondocente() {
        return profesiondocente;
    }

    public void setProfesiondocente(String profesiondocente) {
        this.profesiondocente = profesiondocente;
    }

    public String getClavedocente() {
        return clavedocente;
    }

    public void setClavedocente(String clavedocente) {
        this.clavedocente = clavedocente;
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
        hash += (documentodocente != null ? documentodocente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Docente)) {
            return false;
        }
        Docente other = (Docente) object;
        if ((this.documentodocente == null && other.documentodocente != null) || (this.documentodocente != null && !this.documentodocente.equals(other.documentodocente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Docente[ documentodocente=" + documentodocente + " ]";
    }
    
}
