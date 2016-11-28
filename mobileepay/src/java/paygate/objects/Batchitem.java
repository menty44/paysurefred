/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paygate.objects;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Joseph
 */
@Entity
@Table(name = "batchitem")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Batchitem.findAll", query = "SELECT b FROM Batchitem b"),
    @NamedQuery(name = "Batchitem.findById", query = "SELECT b FROM Batchitem b WHERE b.id = :id"),
    @NamedQuery(name = "Batchitem.findByClientName", query = "SELECT b FROM Batchitem b WHERE b.clientName = :clientName"),
    @NamedQuery(name = "Batchitem.findByEmail", query = "SELECT b FROM Batchitem b WHERE b.email = :email"),
    @NamedQuery(name = "Batchitem.findByAmount", query = "SELECT b FROM Batchitem b WHERE b.amount = :amount"),
    @NamedQuery(name = "Batchitem.findByDescription", query = "SELECT b FROM Batchitem b WHERE b.description = :description"),
    @NamedQuery(name = "Batchitem.findByCurrency", query = "SELECT b FROM Batchitem b WHERE b.currency = :currency")})
public class Batchitem implements Serializable {
    @JoinColumn(name = "deliverystatus", referencedColumnName = "id")
    @ManyToOne
    private EmailDelivery deliverystatus;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "client_name")
    private String clientName;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 30)
    @Column(name = "email")
    private String email;
    @Column(name = "amount")
    private BigInteger amount;
    @Size(max = 2147483647)
    @Column(name = "description")
    private String description;
    @Size(max = 2147483647)
    @Column(name = "currency")
    private String currency;
    @JoinColumn(name = "batchheader_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Batchheader batchheaderId;

    public Batchitem() {
    }

    public Batchitem(Integer id) {
        this.id = id;
    }

    public Batchitem(Integer id, String clientName) {
        this.id = id;
        this.clientName = clientName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigInteger getAmount() {
        return amount;
    }

    public void setAmount(BigInteger amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Batchheader getBatchheaderId() {
        return batchheaderId;
    }

    public void setBatchheaderId(Batchheader batchheaderId) {
        this.batchheaderId = batchheaderId;
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
        if (!(object instanceof Batchitem)) {
            return false;
        }
        Batchitem other = (Batchitem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "paygate.objects.Batchitem[ id=" + id + " ]";
    }

    public EmailDelivery getDeliverystatus() {
        return deliverystatus;
    }

    public void setDeliverystatus(EmailDelivery deliverystatus) {
        this.deliverystatus = deliverystatus;
    }
    
}
