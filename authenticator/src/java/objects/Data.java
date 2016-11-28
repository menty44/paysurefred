/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author gachanja
 */
@Entity
@Table(name = "data")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Data.findAll", query = "SELECT d FROM Data d"),
    @NamedQuery(name = "Data.findById", query = "SELECT d FROM Data d WHERE d.id = :id"),
    @NamedQuery(name = "Data.findByItemid", query = "SELECT d FROM Data d WHERE d.itemid = :itemid"),
    @NamedQuery(name = "Data.findByItemname", query = "SELECT d FROM Data d WHERE d.itemname = :itemname"),
    @NamedQuery(name = "Data.findByItemprice", query = "SELECT d FROM Data d WHERE d.itemprice = :itemprice"),
    @NamedQuery(name = "Data.findByManufacturerid", query = "SELECT d FROM Data d WHERE d.manufacturerid = :manufacturerid"),
    @NamedQuery(name = "Data.findByManufacturername", query = "SELECT d FROM Data d WHERE d.manufacturername = :manufacturername")})
public class Data implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Column(name = "itemid")
    private Integer itemid;
    @Size(max = 100)
    @Column(name = "itemname")
    private String itemname;
    @Column(name = "itemprice")
    private Integer itemprice;
    @Column(name = "manufacturerid")
    private Integer manufacturerid;
    @Size(max = 100)
    @Column(name = "manufacturername")
    private String manufacturername;
    @JoinColumn(name = "purchaseid", referencedColumnName = "id")
    @ManyToOne
    private Purchase purchaseid;

    public Data() {
    }

    public Data(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getItemid() {
        return itemid;
    }

    public void setItemid(Integer itemid) {
        this.itemid = itemid;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public Integer getItemprice() {
        return itemprice;
    }

    public void setItemprice(Integer itemprice) {
        this.itemprice = itemprice;
    }

    public Integer getManufacturerid() {
        return manufacturerid;
    }

    public void setManufacturerid(Integer manufacturerid) {
        this.manufacturerid = manufacturerid;
    }

    public String getManufacturername() {
        return manufacturername;
    }

    public void setManufacturername(String manufacturername) {
        this.manufacturername = manufacturername;
    }

    public Purchase getPurchaseid() {
        return purchaseid;
    }

    public void setPurchaseid(Purchase purchaseid) {
        this.purchaseid = purchaseid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Data)) {
            return false;
        }
        Data other = (Data) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "objects.Data[ id=" + id + " ]";
    }
    
}
