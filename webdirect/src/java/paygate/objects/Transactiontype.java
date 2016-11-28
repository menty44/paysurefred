/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paygate.objects;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author paysure
 */
@Entity
@Table(name = "transactiontype")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Transactiontype.findAll", query = "SELECT t FROM Transactiontype t"),
    @NamedQuery(name = "Transactiontype.findById", query = "SELECT t FROM Transactiontype t WHERE t.id = :id"),
    @NamedQuery(name = "Transactiontype.findByValue", query = "SELECT t FROM Transactiontype t WHERE t.value = :value"),
    @NamedQuery(name = "Transactiontype.findByDescription", query = "SELECT t FROM Transactiontype t WHERE t.description = :description")})
public class Transactiontype implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Size(max = 20)
    @Column(name = "value")
    private String value;
    @Size(max = 20)
    @Column(name = "description")
    private String description;
    @OneToMany(mappedBy = "transactiontype")
    private List<Purchase> purchaseList;

    public Transactiontype() {
    }

    public Transactiontype(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlTransient
    public List<Purchase> getPurchaseList() {
        return purchaseList;
    }

    public void setPurchaseList(List<Purchase> purchaseList) {
        this.purchaseList = purchaseList;
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
        if (!(object instanceof Transactiontype)) {
            return false;
        }
        Transactiontype other = (Transactiontype) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "service.Transactiontype[ id=" + id + " ]";
    }
    
}
