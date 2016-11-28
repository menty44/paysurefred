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
@Table(name = "acquirer")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Acquirer.findAll", query = "SELECT a FROM Acquirer a"),
    @NamedQuery(name = "Acquirer.findById", query = "SELECT a FROM Acquirer a WHERE a.id = :id"),
    @NamedQuery(name = "Acquirer.findByName", query = "SELECT a FROM Acquirer a WHERE a.name = :name"),
    @NamedQuery(name = "Acquirer.findByUrl", query = "SELECT a FROM Acquirer a WHERE a.url = :url")})
public class Acquirer implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Size(max = 2147483647)
    @Column(name = "name")
    private String name;
    @Size(max = 2147483647)
    @Column(name = "url")
    private String url;
    @OneToMany(mappedBy = "acquirerid")
    private List<Merchant> merchantList;

    public Acquirer() {
    }

    public Acquirer(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
        if (!(object instanceof Acquirer)) {
            return false;
        }
        Acquirer other = (Acquirer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "objects.Acquirer[ id=" + id + " ]";
    }
    
}
