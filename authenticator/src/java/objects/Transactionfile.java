/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author gachanja
 */
@Entity
@Table(name = "transactionfile")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Transactionfile.findAll", query = "SELECT t FROM Transactionfile t"),
    @NamedQuery(name = "Transactionfile.findById", query = "SELECT t FROM Transactionfile t WHERE t.id = :id"),
    @NamedQuery(name = "Transactionfile.findByFiledate", query = "SELECT t FROM Transactionfile t WHERE t.filedate = :filedate"),
    @NamedQuery(name = "Transactionfile.findByTransactions", query = "SELECT t FROM Transactionfile t WHERE t.transactions = :transactions"),
    @NamedQuery(name = "Transactionfile.findByMerchant", query = "SELECT t FROM Transactionfile t WHERE t.merchant = :merchant"),
    @NamedQuery(name = "Transactionfile.findByKenswitch", query = "SELECT t FROM Transactionfile t WHERE t.kenswitch = :kenswitch"),
    @NamedQuery(name = "Transactionfile.findByChase", query = "SELECT t FROM Transactionfile t WHERE t.chase = :chase"),
    @NamedQuery(name = "Transactionfile.findByPaysure", query = "SELECT t FROM Transactionfile t WHERE t.paysure = :paysure"),
    @NamedQuery(name = "Transactionfile.findByFilename", query = "SELECT t FROM Transactionfile t WHERE t.filename = :filename"),
    @NamedQuery(name = "Transactionfile.findByCreated", query = "SELECT t FROM Transactionfile t WHERE t.created = :created"),
    @NamedQuery(name = "Transactionfile.findByModified", query = "SELECT t FROM Transactionfile t WHERE t.modified = :modified")})
public class Transactionfile implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Column(name = "filedate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date filedate;
    @Column(name = "transactions")
    private Integer transactions;
    @Column(name = "merchant")
    private BigInteger merchant;
    @Column(name = "kenswitch")
    private BigInteger kenswitch;
    @Column(name = "chase")
    private BigInteger chase;
    @Column(name = "paysure")
    private BigInteger paysure;
    @Size(max = 100)
    @Column(name = "filename")
    private String filename;
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Column(name = "modified")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modified;

    public Transactionfile() {
    }

    public Transactionfile(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFiledate() {
        return filedate;
    }

    public void setFiledate(Date filedate) {
        this.filedate = filedate;
    }

    public Integer getTransactions() {
        return transactions;
    }

    public void setTransactions(Integer transactions) {
        this.transactions = transactions;
    }

    public BigInteger getMerchant() {
        return merchant;
    }

    public void setMerchant(BigInteger merchant) {
        this.merchant = merchant;
    }

    public BigInteger getKenswitch() {
        return kenswitch;
    }

    public void setKenswitch(BigInteger kenswitch) {
        this.kenswitch = kenswitch;
    }

    public BigInteger getChase() {
        return chase;
    }

    public void setChase(BigInteger chase) {
        this.chase = chase;
    }

    public BigInteger getPaysure() {
        return paysure;
    }

    public void setPaysure(BigInteger paysure) {
        this.paysure = paysure;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transactionfile)) {
            return false;
        }
        Transactionfile other = (Transactionfile) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "objects.Transactionfile[ id=" + id + " ]";
    }
    
}
