/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paygate.objects;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Joseph
 */
@Entity
@Table(name = "helb")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Helb.findAll", query = "SELECT h FROM Helb h"),
    @NamedQuery(name = "Helb.findById", query = "SELECT h FROM Helb h WHERE h.id = :id"),
    @NamedQuery(name = "Helb.findByName", query = "SELECT h FROM Helb h WHERE h.name = :name"),
    @NamedQuery(name = "Helb.findByEmail", query = "SELECT h FROM Helb h WHERE h.email = :email"),
   @NamedQuery(name = "Helb.findByIdnum", query = "SELECT h FROM Helb h WHERE h.idnum = :idnum"),
    @NamedQuery(name = "Helb.findByMobilenum", query = "SELECT h FROM Helb h WHERE h.mobilenum = :mobilenum")})
public class Helb implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    //@NotNull
    @Column(name = "id")
    private Integer id;
    @Size(max = 2147483647)
    @Column(name = "name")
    private String name;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 2147483647)
    @Column(name = "email")
    private String email;
    @Size(max = 2147483647)
    @Column(name = "idnum")
    private String idnum;
    @Size(max = 2147483647)
    @Column(name = "mobilenum")
    private String mobilenum;

    public Helb() {
    }

    public Helb(Integer id) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdnum() {
        return idnum;
    }

    public void setIdnum(String idnum) {
        this.idnum = idnum;
    }

    public String getMobilenum() {
        return mobilenum;
    }

    public void setMobilenum(String mobilenum) {
        this.mobilenum = mobilenum;
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
        if (!(object instanceof Helb)) {
            return false;
        }
        Helb other = (Helb) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "objects.Helb[ id=" + id + " ]";
    }
    
}
