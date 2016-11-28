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
 * @author Joseph
 */
@Entity
@Table(name = "transactionsource")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Transactionsource.findAll", query = "SELECT t FROM Transactionsource t"),
    @NamedQuery(name = "Transactionsource.findById", query = "SELECT t FROM Transactionsource t WHERE t.id = :id"),
    @NamedQuery(name = "Transactionsource.findByName", query = "SELECT t FROM Transactionsource t WHERE t.name = :name")})
public class Transactionsource implements Serializable {
    @OneToMany(mappedBy = "transactionsourceid")
    private List<Token> tokenList;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Size(max = 20)
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "transactionsourceid")
    private List<Transaction> transactionList;
    @OneToMany(mappedBy = "transactionsourceid")
    private List<Purchase> purchaseList;

    public Transactionsource() {
    }

    public Transactionsource(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
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
        if (!(object instanceof Transactionsource)) {
            return false;
        }
        Transactionsource other = (Transactionsource) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "paygate.objects.Transactionsource[ id=" + id + " ]";
    }

    @XmlTransient
    public List<Token> getTokenList() {
        return tokenList;
    }

    public void setTokenList(List<Token> tokenList) {
        this.tokenList = tokenList;
    }
    
}
