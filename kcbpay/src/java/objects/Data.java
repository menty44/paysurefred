/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Joseph
 */
@Entity
@Table(name = "data")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Data.findAll", query = "SELECT d FROM Data d"),
    @NamedQuery(name = "Data.findById", query = "SELECT d FROM Data d WHERE d.id = :id"),
    @NamedQuery(name = "Data.findByItemname", query = "SELECT d FROM Data d WHERE d.itemname = :itemname"),
    @NamedQuery(name = "Data.findByItemprice", query = "SELECT d FROM Data d WHERE d.itemprice = :itemprice"),
    @NamedQuery(name = "Data.findByQuantity", query = "SELECT d FROM Data d WHERE d.quantity = :quantity"),
    @NamedQuery(name = "Data.findBySubtotal", query = "SELECT d FROM Data d WHERE d.subtotal = :subtotal")})
public class Data implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Size(max = 100)
    @Column(name = "itemname")
    private String itemname;
    @Column(name = "itemprice")
    private BigInteger itemprice;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "subtotal")
    private BigInteger subtotal;
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

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public BigInteger getItemprice() {
        return itemprice;
    }

    public void setItemprice(BigInteger itemprice) {
        this.itemprice = itemprice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigInteger getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigInteger subtotal) {
        this.subtotal = subtotal;
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
