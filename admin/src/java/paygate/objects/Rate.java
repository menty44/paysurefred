/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paygate.objects;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author gachanja
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

    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Column(name = "modified")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modified;
    private static final long serialVersionUID = 1L;
    @SequenceGenerator(name = "rateGenerator", catalog = "paygate", schema = "public", sequenceName = "rate_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "rateGenerator")
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Column(name = "merchantrate")
    private BigInteger merchantrate;
    @Column(name = "paysurerate")
    private BigDecimal paysurerate;
    @OneToMany(mappedBy = "rateid")
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

    public BigInteger getMerchantrate() {
        return merchantrate;
    }

    public void setMerchantrate(BigInteger merchantrate) {
        this.merchantrate = merchantrate;
    }

    public BigDecimal getPaysurerate() {
        return paysurerate;
    }

    public void setPaysurerate(BigDecimal paysurerate) {
        this.paysurerate = paysurerate;
    }

    @XmlTransient
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
        return "paygate.objects.Rate[ id=" + id + " ]";
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
}
   /*  public void GetTwoDecimal() {
        DecimalFormat df = new DecimalFormat("0.00");

        DecimalFormat myFormatter = new DecimalFormat(paysurerate);
        String output = myFormatter.format(value);
        System.out.println(value + " " + paysurerate + " " + output);
    }
}
       
         * DecimalFormat myFormatter = new DecimalFormat(paysurerate); String
         * output = myFormatter.format(value); System.out.println(value + " " +
         * paysurerate + " " + output);
         */
