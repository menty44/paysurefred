/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paygate.objects;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author paysure
 */
@Entity
@Table(name = "rate")
@XmlRootElement
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
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Column(name = "merchantrate")
    private BigInteger merchantrate;
    @Column(name = "paysurerate")
    private BigInteger paysurerate;
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Column(name = "modified")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modified;
    @OneToMany(mappedBy = "rateid")
    private List<Merchant> merchantList;

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

    public BigInteger getMerchantrate() {
        return merchantrate;
    }

    public void setMerchantrate(BigInteger merchantrate) {
        this.merchantrate = merchantrate;
    }

    public BigInteger getPaysurerate() {
        return paysurerate;
    }

    public void setPaysurerate(BigInteger paysurerate) {
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

    @XmlTransient
    public List<Merchant> getMerchantList() {
        return merchantList;
    }

    public void setMerchantList(List<Merchant> merchantList) {
        this.merchantList = merchantList;
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
        return "service.Rate[ id=" + id + " ]";
    }
    
}
