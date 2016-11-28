/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

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
@Table(name = "email_delivery")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EmailDelivery.findAll", query = "SELECT e FROM EmailDelivery e"),
    @NamedQuery(name = "EmailDelivery.findById", query = "SELECT e FROM EmailDelivery e WHERE e.id = :id"),
    @NamedQuery(name = "EmailDelivery.findByStatus", query = "SELECT e FROM EmailDelivery e WHERE e.status = :status"),
    @NamedQuery(name = "EmailDelivery.findByDescription", query = "SELECT e FROM EmailDelivery e WHERE e.description = :description")})
public class EmailDelivery implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Column(name = "status")
    private Integer status;
    @Size(max = 2147483647)
    @Column(name = "description")
    private String description;
    @OneToMany(mappedBy = "deliverystatus")
    private List<Batchitem> batchitemList;

    public EmailDelivery() {
    }

    public EmailDelivery(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        if (!(object instanceof EmailDelivery)) {
            return false;
        }
        EmailDelivery other = (EmailDelivery) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "objects.EmailDelivery[ id=" + id + " ]";
    }
    
}
