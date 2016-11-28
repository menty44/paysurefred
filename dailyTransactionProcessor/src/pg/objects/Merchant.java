/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pg.objects;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author gachanja
 */
@Entity
@Table(name = "merchant")
@NamedQueries({
    @NamedQuery(name = "Merchant.findAll", query = "SELECT m FROM Merchant m"),
    @NamedQuery(name = "Merchant.findById", query = "SELECT m FROM Merchant m WHERE m.id = :id"),
    @NamedQuery(name = "Merchant.findByTerminalid", query = "SELECT m FROM Merchant m WHERE m.terminalid = :terminalid"),
    @NamedQuery(name = "Merchant.findByUsername", query = "SELECT m FROM Merchant m WHERE m.username = :username"),
    @NamedQuery(name = "Merchant.findByPassword", query = "SELECT m FROM Merchant m WHERE m.password = :password"),
    @NamedQuery(name = "Merchant.findByKey", query = "SELECT m FROM Merchant m WHERE m.key = :key"),
    @NamedQuery(name = "Merchant.findByName", query = "SELECT m FROM Merchant m WHERE m.name = :name"),
    @NamedQuery(name = "Merchant.findByStreet", query = "SELECT m FROM Merchant m WHERE m.street = :street"),
    @NamedQuery(name = "Merchant.findByPostcode", query = "SELECT m FROM Merchant m WHERE m.postcode = :postcode"),
    @NamedQuery(name = "Merchant.findByCity", query = "SELECT m FROM Merchant m WHERE m.city = :city"),
    @NamedQuery(name = "Merchant.findByState", query = "SELECT m FROM Merchant m WHERE m.state = :state"),
    @NamedQuery(name = "Merchant.findByBankcode", query = "SELECT m FROM Merchant m WHERE m.bankcode = :bankcode"),
    @NamedQuery(name = "Merchant.findByBranchcode", query = "SELECT m FROM Merchant m WHERE m.branchcode = :branchcode"),
    @NamedQuery(name = "Merchant.findByBankaccount", query = "SELECT m FROM Merchant m WHERE m.bankaccount = :bankaccount"),
    @NamedQuery(name = "Merchant.findByEmail", query = "SELECT m FROM Merchant m WHERE m.email = :email"),
    @NamedQuery(name = "Merchant.findByContactperson", query = "SELECT m FROM Merchant m WHERE m.contactperson = :contactperson"),
    @NamedQuery(name = "Merchant.findByPhone", query = "SELECT m FROM Merchant m WHERE m.phone = :phone"),
    @NamedQuery(name = "Merchant.findByMobile", query = "SELECT m FROM Merchant m WHERE m.mobile = :mobile"),
    @NamedQuery(name = "Merchant.findByCreated", query = "SELECT m FROM Merchant m WHERE m.created = :created"),
    @NamedQuery(name = "Merchant.findByModified", query = "SELECT m FROM Merchant m WHERE m.modified = :modified")})
public class Merchant implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "terminalid")
    private String terminalid;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "key")
    private String key;
    @Column(name = "name")
    private String name;
    @Column(name = "street")
    private String street;
    @Column(name = "postcode")
    private String postcode;
    @Column(name = "city")
    private String city;
    @Column(name = "state")
    private String state;
    @Column(name = "bankcode")
    private String bankcode;
    @Column(name = "branchcode")
    private String branchcode;
    @Column(name = "bankaccount")
    private String bankaccount;
    @Column(name = "email")
    private String email;
    @Column(name = "contactperson")
    private String contactperson;
    @Column(name = "phone")
    private String phone;
    @Column(name = "mobile")
    private String mobile;
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Column(name = "modified")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modified;
    @JoinColumn(name = "statusid", referencedColumnName = "code")
    @ManyToOne
    private Status status;
    @JoinColumn(name = "rateid", referencedColumnName = "id")
    @ManyToOne
    private Rate rate;
    @JoinColumn(name = "countryid", referencedColumnName = "id")
    @ManyToOne
    private Country country;
    @OneToMany(mappedBy = "merchant")
    private Collection<Transaction> transactionCollection;
    private BigDecimal eft;

    public BigDecimal getEft() {
        return eft;
    }

    public void setEft(BigDecimal eft) {
        this.eft = eft;
    }

    public Merchant() {
    }

    public Merchant(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTerminalid() {
        return terminalid;
    }

    public void setTerminalid(String terminalid) {
        this.terminalid = terminalid;
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getBankcode() {
        return bankcode;
    }

    public void setBankcode(String bankcode) {
        this.bankcode = bankcode;
    }

    public String getBranchcode() {
        return branchcode;
    }

    public void setBranchcode(String branchcode) {
        this.branchcode = branchcode;
    }

    public String getBankaccount() {
        return bankaccount;
    }

    public void setBankaccount(String bankaccount) {
        this.bankaccount = bankaccount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactperson() {
        return contactperson;
    }

    public void setContactperson(String contactperson) {
        this.contactperson = contactperson;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Rate getRate() {
        return rate;
    }

    public void setRate(Rate rate) {
        this.rate = rate;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Collection<Transaction> getTransactionCollection() {
        return transactionCollection;
    }

    public void setTransactionCollection(Collection<Transaction> transactionCollection) {
        this.transactionCollection = transactionCollection;
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
        if (!(object instanceof Merchant)) {
            return false;
        }
        Merchant other = (Merchant) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pg.objects.Merchant[id=" + id + "]";
    }

}
