/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package paygate.objects;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author gachanja
 */
@Entity
@Table(name = "siteuser")
@NamedQueries({
    @NamedQuery(name = "Siteuser.findAll", query = "SELECT s FROM Siteuser s"),
    @NamedQuery(name = "Siteuser.findById", query = "SELECT s FROM Siteuser s WHERE s.id = :id"),
    @NamedQuery(name = "Siteuser.findByFirstname", query = "SELECT s FROM Siteuser s WHERE s.firstname = :firstname"),
    @NamedQuery(name = "Siteuser.findByLastname", query = "SELECT s FROM Siteuser s WHERE s.lastname = :lastname"),
    @NamedQuery(name = "Siteuser.findByUsername", query = "SELECT s FROM Siteuser s WHERE s.username = :username"),
    @NamedQuery(name = "Siteuser.findByPassword", query = "SELECT s FROM Siteuser s WHERE s.password = :password"),
    @NamedQuery(name = "Siteuser.findByLastaccess", query = "SELECT s FROM Siteuser s WHERE s.lastaccess = :lastaccess"),
    @NamedQuery(name = "Siteuser.findByCreated", query = "SELECT s FROM Siteuser s WHERE s.created = :created"),
    @NamedQuery(name = "Siteuser.findByModified", query = "SELECT s FROM Siteuser s WHERE s.modified = :modified")})
public class Siteuser implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "firstname")
    private String firstname;
    @Column(name = "lastname")
    private String lastname;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "lastaccess")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastaccess;
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Column(name = "modified")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modified;
    @JoinColumn(name = "usergroupid", referencedColumnName = "id")
    @ManyToOne
    private Usergroup usergroup;
    @JoinColumn(name = "merchantid", referencedColumnName = "id")
    @ManyToOne
    private Merchant merchant;

    public Siteuser() {
    }

    public Siteuser(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getLastaccess() {
        return lastaccess;
    }

    public void setLastaccess(Date lastaccess) {
        this.lastaccess = lastaccess;
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

    public Usergroup getUsergroup() {
        return usergroup;
    }

    public void setUsergroup(Usergroup usergroup) {
        this.usergroup = usergroup;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
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
        if (!(object instanceof Siteuser)) {
            return false;
        }
        Siteuser other = (Siteuser) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "paygate.objects.Siteuser[id=" + id + "]";
    }

}
