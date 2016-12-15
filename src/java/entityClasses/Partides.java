/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityClasses;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author tonix
 */
@Entity
@Table(name = "partides")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Partides.findAll", query = "SELECT p FROM Partides p"),
    @NamedQuery(name = "Partides.findByCoun", query = "SELECT p FROM Partides p WHERE p.coun = :coun"),
    @NamedQuery(name = "Partides.findByPunts", query = "SELECT p FROM Partides p WHERE p.punts = :punts"),
    @NamedQuery(name = "Partides.findByFecha", query = "SELECT p FROM Partides p WHERE p.fecha = :fecha")})
public class Partides implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "coun")
    private Integer coun;
    @Basic(optional = false)
    @Column(name = "punts")
    private float punts;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @JoinColumn(name = "u_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuari uId;

    public Partides() {
    }

    public Partides(Integer coun) {
        this.coun = coun;
    }

    public Partides(Integer coun, float punts, Date fecha) {
        this.coun = coun;
        this.punts = punts;
        this.fecha = fecha;
    }

    public Integer getCoun() {
        return coun;
    }

    public void setCoun(Integer coun) {
        this.coun = coun;
    }

    public float getPunts() {
        return punts;
    }

    public void setPunts(float punts) {
        this.punts = punts;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Usuari getUId() {
        return uId;
    }

    public void setUId(Usuari uId) {
        this.uId = uId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (coun != null ? coun.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Partides)) {
            return false;
        }
        Partides other = (Partides) object;
        if ((this.coun == null && other.coun != null) || (this.coun != null && !this.coun.equals(other.coun))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityClasses.Partides[ coun=" + coun + " ]";
    }
    
}
