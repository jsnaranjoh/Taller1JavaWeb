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
@Table(name = "estudiante")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Estudiante.findAll", query = "SELECT e FROM Estudiante e"),
    @NamedQuery(name = "Estudiante.findByDocumentoestudiante", query = "SELECT e FROM Estudiante e WHERE e.documentoestudiante = :documentoestudiante"),
    @NamedQuery(name = "Estudiante.findByNombreestudiante", query = "SELECT e FROM Estudiante e WHERE e.nombreestudiante = :nombreestudiante"),
    @NamedQuery(name = "Estudiante.findByApellidoestudiante", query = "SELECT e FROM Estudiante e WHERE e.apellidoestudiante = :apellidoestudiante"),
    @NamedQuery(name = "Estudiante.findByCorreoestudiante", query = "SELECT e FROM Estudiante e WHERE e.correoestudiante = :correoestudiante"),
    @NamedQuery(name = "Estudiante.findBySemestreestudiante", query = "SELECT e FROM Estudiante e WHERE e.semestreestudiante = :semestreestudiante"),
    @NamedQuery(name = "Estudiante.findByClaveestudiante", query = "SELECT e FROM Estudiante e WHERE e.claveestudiante = :claveestudiante")})
public class Estudiante implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "documentoestudiante")
    private Long documentoestudiante;
    @Column(name = "nombreestudiante")
    private String nombreestudiante;
    @Column(name = "apellidoestudiante")
    private String apellidoestudiante;
    @Column(name = "correoestudiante")
    private String correoestudiante;
    @Column(name = "semestreestudiante")
    private Integer semestreestudiante;
    @Column(name = "claveestudiante")
    private String claveestudiante;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estudiante")
    private List<Matricula> matriculaList;

    public Estudiante() {
    }

    public Estudiante(Long documentoestudiante) {
        this.documentoestudiante = documentoestudiante;
    }

    public Long getDocumentoestudiante() {
        return documentoestudiante;
    }

    public void setDocumentoestudiante(Long documentoestudiante) {
        this.documentoestudiante = documentoestudiante;
    }

    public String getNombreestudiante() {
        return nombreestudiante;
    }

    public void setNombreestudiante(String nombreestudiante) {
        this.nombreestudiante = nombreestudiante;
    }

    public String getApellidoestudiante() {
        return apellidoestudiante;
    }

    public void setApellidoestudiante(String apellidoestudiante) {
        this.apellidoestudiante = apellidoestudiante;
    }

    public String getCorreoestudiante() {
        return correoestudiante;
    }

    public void setCorreoestudiante(String correoestudiante) {
        this.correoestudiante = correoestudiante;
    }

    public Integer getSemestreestudiante() {
        return semestreestudiante;
    }

    public void setSemestreestudiante(Integer semestreestudiante) {
        this.semestreestudiante = semestreestudiante;
    }

    public String getClaveestudiante() {
        return claveestudiante;
    }

    public void setClaveestudiante(String claveestudiante) {
        this.claveestudiante = claveestudiante;
    }

    @XmlTransient
    public List<Matricula> getMatriculaList() {
        return matriculaList;
    }

    public void setMatriculaList(List<Matricula> matriculaList) {
        this.matriculaList = matriculaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (documentoestudiante != null ? documentoestudiante.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Estudiante)) {
            return false;
        }
        Estudiante other = (Estudiante) object;
        if ((this.documentoestudiante == null && other.documentoestudiante != null) || (this.documentoestudiante != null && !this.documentoestudiante.equals(other.documentoestudiante))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Estudiante[ documentoestudiante=" + documentoestudiante + " ]";
    }
    
}
