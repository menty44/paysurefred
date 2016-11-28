/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paygate.objects;

import java.io.Serializable;
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
    @NamedQuery(name = "Batchheader.findAll", query = "SELECT b FROM Batchheader b"),
    @NamedQuery(name = "Batchheader.findById", query = "SELECT b FROM Batchheader b WHERE b.id = :id"),
    @NamedQuery(name = "Batchheader.findByBatchname", query = "SELECT b FROM Batchheader b WHERE b.batchname = :batchname"),
    @NamedQuery(name = "Batchheader.findByDate", query = "SELECT b FROM Batchheader b WHERE b.date = :date"),
    @NamedQuery(name = "Batchheader.findByStatus", query = "SELECT b FROM Batchheader b WHERE b.status = :status"),
    @NamedQuery(name = "Batchheader.findByRequests", query = "SELECT b FROM Batchheader b WHERE b.requests = :requests"),
    @NamedQuery(name = "Batchheader.findByDescription", query = "SELECT b FROM Batchheader b WHERE b.description = :description")})
public class Batchheader implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "batchname")
    private String batchname;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "status")
    private String status;
    @Basic(optional = false)
    @NotNull
    @Column(name = "requests")
    private int requests;
    @Size(max = 2147483647)
    @Column(name = "description")
    private String description;
    @JoinColumn(name = "merchantid", referencedColumnName = "id")
    @ManyToOne
    private Merchant merchantid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "batchheaderId")
    private List<Batchitem> batchitemList;

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

    @XmlTransient
    public List<Batchitem> getBatchitemList() {
        return batchitemList;
    }

    public void setBatchitemList(List<Batchitem> batchitemList) {
        this.batchitemList = batchitemList;
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
