/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pg.objects;

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
 * @author Joseph
 */
@Entity
@Table(name = "usergroup")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usergroup.findAll", query = "SELECT u FROM Usergroup u"),
    @NamedQuery(name = "Usergroup.findById", query = "SELECT u FROM Usergroup u WHERE u.id = :id"),
    @NamedQuery(name = "Usergroup.findByName", query = "SELECT u FROM Usergroup u WHERE u.name = :name"),
    @NamedQuery(name = "Usergroup.findByDescription", query = "SELECT u FROM Usergroup u WHERE u.description = :description"),
    @NamedQuery(name = "Usergroup.findByCreated", query = "SELECT u FROM Usergroup u WHERE u.created = :created"),
    @NamedQuery(name = "Usergroup.findByModified", query = "SELECT u FROM Usergroup u WHERE u.modified = :modified")})
public class Usergroup implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Size(max = 20)
    @Column(name = "name")
    private String name;
    @Size(max = 20)
    @Column(name = "description")
    private String description;
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Column(name = "modified")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modified;
    @OneToMany(mappedBy = "usergroupid")
    private Collection<Siteuser> siteuserCollection;

    public Usergroup() {
    }

    public Usergroup(Integer id) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
    public Collection<Siteuser> getSiteuserCollection() {
        return siteuserCollection;
    }

    public void setSiteuserCollection(Collection<Siteuser> siteuserCollection) {
        this.siteuserCollection = siteuserCollection;
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
        if (!(object instanceof Usergroup)) {
            return false;
        }
        Usergroup other = (Usergroup) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pg.objects.Usergroup[ id=" + id + " ]";
    }
    
}
