/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paygate.objects;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
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
@Table(name = "merchant")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Merchant.findAll", query = "SELECT m FROM Merchant m"),
    @NamedQuery(name = "Merchant.findById", query = "SELECT m FROM Merchant m WHERE m.id = :id"),
    @NamedQuery(name = "Merchant.findByMerchantidentifier", query = "SELECT m FROM Merchant m WHERE m.merchantidentifier = :merchantidentifier"),
    @NamedQuery(name = "Merchant.findByTerminalid", query = "SELECT m FROM Merchant m WHERE m.terminalid = :terminalid"),
    @NamedQuery(name = "Merchant.findByUsername", query = "SELECT m FROM Merchant m WHERE m.username = :username"),
    @NamedQuery(name = "Merchant.findByPassword", query = "SELECT m FROM Merchant m WHERE m.password = :password"),
    @NamedQuery(name = "Merchant.findByToken", query = "SELECT m FROM Merchant m WHERE m.token = :token"),
    @NamedQuery(name = "Merchant.findByIpaddress", query = "SELECT m FROM Merchant m WHERE m.ipaddress = :ipaddress"),
    @NamedQuery(name = "Merchant.findByUrl", query = "SELECT m FROM Merchant m WHERE m.url = :url"),
    @NamedQuery(name = "Merchant.findByReturnurl", query = "SELECT m FROM Merchant m WHERE m.returnurl = :returnurl"),
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
    @NamedQuery(name = "Merchant.findByModified", query = "SELECT m FROM Merchant m WHERE m.modified = :modified"),
    @NamedQuery(name = "Merchant.findByEft", query = "SELECT m FROM Merchant m WHERE m.eft = :eft"),
    @NamedQuery(name = "Merchant.findByLiveid", query = "SELECT m FROM Merchant m WHERE m.liveid = :liveid"),
    @NamedQuery(name = "Merchant.findByLiveidusd", query = "SELECT m FROM Merchant m WHERE m.liveidusd = :liveidusd"),
    @NamedQuery(name = "Merchant.findByUsd", query = "SELECT m FROM Merchant m WHERE m.usd = :usd")})
public class Merchant implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Size(max = 15)
    @Column(name = "merchantidentifier")
    private String merchantidentifier;
    @Size(max = 15)
    @Column(name = "terminalid")
    private String terminalid;
    @Size(max = 20)
    @Column(name = "username")
    private String username;
    @Size(max = 50)
    @Column(name = "password")
    private String password;
    @Size(max = 30)
    @Column(name = "token")
    private String token;
    @Size(max = 15)
    @Column(name = "ipaddress")
    private String ipaddress;
    @Size(max = 100)
    @Column(name = "url")
    private String url;
    @Size(max = 100)
    @Column(name = "returnurl")
    private String returnurl;
    @Size(max = 2147483647)
    @Column(name = "key")
    private String key;
    @Size(max = 100)
    @Column(name = "name")
    private String name;
    @Size(max = 100)
    @Column(name = "street")
    private String street;
    @Size(max = 10)
    @Column(name = "postcode")
    private String postcode;
    @Size(max = 100)
    @Column(name = "city")
    private String city;
    @Size(max = 100)
    @Column(name = "state")
    private String state;
    @Size(max = 10)
    @Column(name = "bankcode")
    private String bankcode;
    @Size(max = 10)
    @Column(name = "branchcode")
    private String branchcode;
    @Size(max = 10)
    @Column(name = "bankaccount")
    private String bankaccount;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 100)
    @Column(name = "email")
    private String email;
    @Size(max = 100)
    @Column(name = "contactperson")
    private String contactperson;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Size(max = 100)
    @Column(name = "phone")
    private String phone;
    @Size(max = 100)
    @Column(name = "mobile")
    private String mobile;
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Column(name = "modified")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modified;
    @Column(name = "eft")
    private BigInteger eft;
    @Column(name = "liveid")
    private String liveid;
    @Column(name = "liveidusd")
    private String liveidusd;
    @Column(name = "usd")
    private Boolean usd;
    @OneToMany(mappedBy = "merchantid")
    private Collection<Transaction> transactionCollection;
    @JoinColumn(name = "statusid", referencedColumnName = "code")
    @ManyToOne
    private Status statusid;
    @JoinColumn(name = "rateid", referencedColumnName = "id")
    @ManyToOne
    private Rate rateid;
    @JoinColumn(name = "countryid", referencedColumnName = "id")
    @ManyToOne
    private Country countryid;
    @JoinColumn(name = "categoryid", referencedColumnName = "id")
    @ManyToOne
    private Category categoryid;
    @OneToMany(mappedBy = "merchantid")
    private Collection<Siteuser> siteuserCollection;
    @OneToMany(mappedBy = "merchantid")
    private Collection<Purchase> purchaseCollection;

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

    public String getMerchantidentifier() {
        return merchantidentifier;
    }

    public void setMerchantidentifier(String merchantidentifier) {
        this.merchantidentifier = merchantidentifier;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getReturnurl() {
        return returnurl;
    }

    public void setReturnurl(String returnurl) {
        this.returnurl = returnurl;
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

    public BigInteger getEft() {
        return eft;
    }    
    
    public void setEft(BigInteger eft) {
        this.eft = eft;
    }

    public String getLiveid() {
        return liveid;
    }

    public void setLiveid(String liveid) {
        this.liveid = liveid;
    }

    public String getLiveidusd() {
        return liveidusd;
    }

    public void setLiveidusd(String liveidusd) {
        this.liveidusd = liveidusd;
    }

    public Boolean getUsd() {
        return usd;
    }

    public void setUsd(Boolean usd) {
        this.usd = usd;
    }
        
    @XmlTransient
    public Collection<Transaction> getTransactionCollection() {
        return transactionCollection;
    }

    public void setTransactionCollection(Collection<Transaction> transactionCollection) {
        this.transactionCollection = transactionCollection;
    }

    public Status getStatusid() {
        return statusid;
    }

    public void setStatusid(Status statusid) {
        this.statusid = statusid;
    }

    public Rate getRateid() {
        return rateid;
    }

    public void setRateid(Rate rateid) {
        this.rateid = rateid;
    }

    public Country getCountryid() {
        return countryid;
    }

    public void setCountryid(Country countryid) {
        this.countryid = countryid;
    }

    public Category getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(Category categoryid) {
        this.categoryid = categoryid;
    }

    @XmlTransient
    public Collection<Siteuser> getSiteuserCollection() {
        return siteuserCollection;
    }

    public void setSiteuserCollection(Collection<Siteuser> siteuserCollection) {
        this.siteuserCollection = siteuserCollection;
    }

    @XmlTransient
    public Collection<Purchase> getPurchaseCollection() {
        return purchaseCollection;
    }

    public void setPurchaseCollection(Collection<Purchase> purchaseCollection) {
        this.purchaseCollection = purchaseCollection;
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
        return "paygate.objects.Merchant[ id=" + id + " ]";
    }
    
}
