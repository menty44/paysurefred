/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paygate.objects;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
@Table(name = "batchheader")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Batchheader.findAll", query = "SELECT b FROM Batchheader b order by b.id desc"),
    @NamedQuery(name = "Batchheader.findall", query = "SELECT b FROM Batchheader b where b.merchantid= :mid"),
    //@NamedQuery(name = "Batchheader.findByUser", query = "SELECT b FROM Batchheader b"),
    @NamedQuery(name = "Batchheader.findById", query = "SELECT b FROM Batchheader b WHERE b.id = :id"),
    @NamedQuery(name = "Batchheader.findByBatchname", query = "SELECT b FROM Batchheader b WHERE b.batchname = :batchname"),
    @NamedQuery(name = "Batchheader.findByDate", query = "SELECT b FROM Batchheader b WHERE b.date = :date"),
    @NamedQuery(name = "Batchheader.findByStatus", query = "SELECT b FROM Batchheader b WHERE b.status = :status"),
    @NamedQuery(name = "Batchheader.findByRequests", query = "SELECT b FROM Batchheader b WHERE b.requests = :requests"),
    @NamedQuery(name = "Batchheader.findByMerchantid", query = "SELECT b FROM Batchheader b WHERE b.merchantid = :merchantid order by b.id desc"),
    @NamedQuery(name = "Batchheader.findByDescription", query = "SELECT b FROM Batchheader b WHERE b.description = :description")})
public class Batchheader implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    // @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "batchname")
    private String batchname;
    //@Basic(optional = false)
    //@NotNull
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    // @Size(max = 2147483647)
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "status")
    private String status;
    @Basic(optional = false)
    @NotNull
    @Column(name = "requests")
    private int requests;
    @Column(name = "description")
    private String description;
    @Column(name = "currency")
    private String currency;
    //@OneToMany(mappedBy = "description")
    //private Collection<Batchitem> batchitemCollection;
    @JoinColumn(name = "merchantid", referencedColumnName = "id")
    @ManyToOne
    private Merchant merchantid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "batchheaderId")
    private List<Batchitem> batchitemList;    
    @Column(name = "emailstatus")
    private Boolean emailstatus;

    public Batchheader() {
    }

    public Batchheader(Integer id) {
        this.id = id;
    }

    public Batchheader(Integer id, String batchname, Date date, String status, int requests) {
        this.id = id;
        this.batchname = batchname;
        this.date = date;
        this.status = status;
        this.requests = requests;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBatchname() {
        return batchname;
    }

    public void setBatchname(String batchname) {
        this.batchname = batchname;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getRequests() {
        return requests;
    }

    public void setRequests(int requests) {
        this.requests = requests;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public Merchant getMerchantid() {
        return merchantid;
    }

    public void setMerchantid(Merchant merchantid) {
        this.merchantid = merchantid;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
        
    /*@XmlTransient
    public Collection<Batchitem> getBatchitemCollection() {
        return batchitemCollection;
    }

    public void setBatchitemCollection(Collection<Batchitem> batchitemCollection) {
        this.batchitemCollection = batchitemCollection;
    }*/

    @XmlTransient
    public List<Batchitem> getBatchitemList() {
        return batchitemList;
    }

    public void setBatchitemList(List<Batchitem> batchitemList) {
        this.batchitemList = batchitemList;
    }
    
    public Boolean getEmailstatus() {
        return emailstatus;
    }

    public void setEmailstatus(Boolean emailstatus) {
        this.emailstatus = emailstatus;
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
        if (!(object instanceof Batchheader)) {
            return false;
        }
        Batchheader other = (Batchheader) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "paygate.objects.Batchheader[ id=" + id + " ]";
    }
}