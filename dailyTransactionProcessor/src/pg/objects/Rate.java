/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pg.objects;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
@Table(name = "rate")
@NamedQueries({
    @NamedQuery(name = "Rate.findAll", query = "SELECT r FROM Rate r"),
    @NamedQuery(name = "Rate.findById", query = "SELECT r FROM Rate r WHERE r.id = :id"),
    @NamedQuery(name = "Rate.findByMerchantrate", query = "SELECT r FROM Rate r WHERE r.merchantrate = :merchantrate"),
    @NamedQuery(name = "Rate.findByPaysurerate", query = "SELECT r FROM Rate r WHERE r.paysurerate = :paysurerate"),
    @NamedQuery(name = "Rate.findByCreated", query = "SELECT r FROM Rate r WHERE r.created = :created"),
    @NamedQuery(name = "Rate.findByModified", query = "SELECT r FROM Rate r WHERE r.modified = :modified")})
public class Rate implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "merchantrate")
    private BigDecimal merchantrate;
    @Column(name = "paysurerate")
    private BigDecimal paysurerate;
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Column(name = "modified")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modified;
    @OneToMany(mappedBy = "rate")
    private Collection<Merchant> merchantCollection;

    public Rate() {
    }

    public Rate(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getMerchantrate() {
        return merchantrate;
    }

    public void setMerchantrate(BigDecimal merchantrate) {
        this.merchantrate = merchantrate;
    }

    public BigDecimal getPaysurerate() {
        return paysurerate;
    }

    public void setPaysurerate(BigDecimal paysurerate) {
        this.paysurerate = paysurerate;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rate)) {
            return false;
        }
        Rate other = (Rate) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pg.objects.Rate[id=" + id + "]";
    }

}
