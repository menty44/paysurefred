/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paygate.objects;

import java.io.Serializable;
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
@Table(name = "cardtype")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cardtype.findAll", query = "SELECT c FROM Cardtype c"),
    @NamedQuery(name = "Cardtype.findById", query = "SELECT c FROM Cardtype c WHERE c.id = :id"),
    @NamedQuery(name = "Cardtype.findByDescription", query = "SELECT c FROM Cardtype c WHERE c.description = :description")})
public class Cardtype implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "description")
    private String description;
    @OneToMany(mappedBy = "cardtype")
    private List<Responsecode> responsecodeList;

    public Cardtype() {
    }

    public Cardtype(Integer id) {
        this.id = id;
    }

    public Cardtype(Integer id, String description) {
        this.id = id;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlTransient
    public List<Responsecode> getResponsecodeList() {
        return responsecodeList;
    }

    public void setResponsecodeList(List<Responsecode> responsecodeList) {
        this.responsecodeList = responsecodeList;
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
        if (!(object instanceof Cardtype)) {
            return false;
        }
        Cardtype other = (Cardtype) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "paygate.objects.Cardtype[ id=" + id + " ]";
    }
    
}
