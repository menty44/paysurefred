/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

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
 * @author Joseph
 */
@Entity
@Table(name = "responsecode")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Responsecode.findAll", query = "SELECT r FROM Responsecode r"),
    @NamedQuery(name = "Responsecode.findById", query = "SELECT r FROM Responsecode r WHERE r.id = :id"),
    @NamedQuery(name = "Responsecode.findByCode", query = "SELECT r FROM Responsecode r WHERE r.code = :code"),
    @NamedQuery(name = "Responsecode.findByCodeCard", query = "SELECT r FROM Responsecode r WHERE r.code = :code and r.cardtype = :cardtype"),
    @NamedQuery(name = "Responsecode.findByDescription", query = "SELECT r FROM Responsecode r WHERE r.description = :description"),
    @NamedQuery(name = "Responsecode.findByCreated", query = "SELECT r FROM Responsecode r WHERE r.created = :created"),
    @NamedQuery(name = "Responsecode.findByModified", query = "SELECT r FROM Responsecode r WHERE r.modified = :modified"),
    @NamedQuery(name = "Responsecode.findByCardtype", query = "SELECT r FROM Responsecode r WHERE r.cardtype = :cardtype")})
public class Responsecode implements Serializable {
    @Column(name =     "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Column(name =     "modified")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modified;
    @OneToMany(mappedBy = "responsecode")
    private List<Token> tokenList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Column(name = "cardtype")
    private Integer cardtype;
    @OneToMany(mappedBy = "responsecodeid")
    private List<Transaction> transactionList;

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

    public Integer getCardtype() {
        return cardtype;
    }

    public void setCardtype(Integer cardtype) {
        this.cardtype = cardtype;
    }

    @XmlTransient
    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
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
        return "objects.Responsecode[ id=" + id + " ]";
    }

    @XmlTransient
    public List<Token> getTokenList() {
        return tokenList;
    }

    public void setTokenList(List<Token> tokenList) {
        this.tokenList = tokenList;
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
