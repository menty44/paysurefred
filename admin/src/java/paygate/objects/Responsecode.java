/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paygate.objects;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author gachanja
 */
@Entity
@Table(name = "responsecode")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Responsecode.findAll", query = "SELECT r FROM Responsecode r"),
    @NamedQuery(name = "Responsecode.findById", query = "SELECT r FROM Responsecode r WHERE r.id = :id"),
    @NamedQuery(name = "Responsecode.findByCode", query = "SELECT r FROM Responsecode r WHERE r.code = :code"),
    @NamedQuery(name = "Responsecode.findByDescription", query = "SELECT r FROM Responsecode r WHERE r.description = :description"),
    @NamedQuery(name = "Responsecode.findByCreated", query = "SELECT r FROM Responsecode r WHERE r.created = :created"),
    @NamedQuery(name = "Responsecode.findByModified", query = "SELECT r FROM Responsecode r WHERE r.modified = :modified")})
public class Responsecode implements Serializable {
    @Column(name =     "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Column(name =     "modified")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modified;
    private static final long serialVersionUID = 1L;
    @SequenceGenerator(name = "responsecodeGenerator", catalog = "paygate", schema = "public", sequenceName = "responsecode_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "responsecodeGenerator")
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Size(max = 3)
    @Column(name = "code")
    private String code;
    @Size(max = 100)
    @Column(name = "description")
    private String description;
    @OneToMany(mappedBy = "responsecodeid")
    private Collection<Transaction> transactionCollection;
    @JoinColumn(name = "cardtype", referencedColumnName = "id")
    @ManyToOne
    private Cardtype cardtype;
    
    public Responsecode() {
    }

    public Responsecode(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlTransient
    public Collection<Transaction> getTransactionCollection() {
        return transactionCollection;
    }

    public void setTransactionCollection(Collection<Transaction> transactionCollection) {
        this.transactionCollection = transactionCollection;
    }

    public Cardtype getCardtype() {
        return cardtype;
    }

    public void setCardtype(Cardtype cardtype) {
        this.cardtype = cardtype;
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
        if (!(object instanceof Responsecode)) {
            return false;
        }
        Responsecode other = (Responsecode) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "paygate.objects.Responsecode[ id=" + id + " ]";
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
