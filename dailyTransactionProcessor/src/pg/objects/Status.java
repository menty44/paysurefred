/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pg.objects;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author gachanja
 */
@Entity
@Table(name = "status")
@NamedQueries({
    @NamedQuery(name = "Status.findAll", query = "SELECT s FROM Status s"),
    @NamedQuery(name = "Status.findById", query = "SELECT s FROM Status s WHERE s.id = :id"),
    @NamedQuery(name = "Status.findByCode", query = "SELECT s FROM Status s WHERE s.code = :code"),
    @NamedQuery(name = "Status.findByDescription", query = "SELECT s FROM Status s WHERE s.description = :description"),
    @NamedQuery(name = "Status.findByCreated", query = "SELECT s FROM Status s WHERE s.created = :created"),
    @NamedQuery(name = "Status.findByModified", query = "SELECT s FROM Status s WHERE s.modified = :modified")})
public class Status implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "code")
    private Integer code;
    @Column(name = "description")
    private String description;
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Column(name = "modified")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modified;
    @OneToMany(mappedBy = "status")
    private Collection<Merchant> merchantCollection;
    @OneToMany(mappedBy = "status")
    private Collection<Transaction> transactionCollection;
    @OneToMany(mappedBy = "status")
    private Collection<Country> countryCollection;

    public Status() {
    }

    public Status(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public Collection<Merchant> getMerchantCollection() {
        return merchantCollection;
    }

    public void setMerchantCollection(Collection<Merchant> merchantCollection) {
        this.merchantCollection = merchantCollection;
    }

    public Collection<Transaction> getTransactionCollection() {
        return transactionCollection;
    }

    public void setTransactionCollection(Collection<Transaction> transactionCollection) {
        this.transactionCollection = transactionCollection;
    }

    public Collection<Country> getCountryCollection() {
        return countryCollection;
    }

    public void setCountryCollection(Collection<Country> countryCollection) {
        this.countryCollection = countryCollection;
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
        if (!(object instanceof Status)) {
            return false;
        }
        Status other = (Status) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pg.objects.Status[id=" + id + "]";
    }

}
