/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paygate.objects;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Joseph
 */
@Entity
@Table(name = "transactionmpesa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Transactionmpesa.findAll", query = "SELECT t FROM Transactionmpesa t"),
    @NamedQuery(name = "Transactionmpesa.findById", query = "SELECT t FROM Transactionmpesa t WHERE t.id = :id"),
    @NamedQuery(name = "Transactionmpesa.findByIpn", query = "SELECT t FROM Transactionmpesa t WHERE t.ipn = :ipn"),
    @NamedQuery(name = "Transactionmpesa.findByOrig", query = "SELECT t FROM Transactionmpesa t WHERE t.orig = :orig"),
    @NamedQuery(name = "Transactionmpesa.findByDest", query = "SELECT t FROM Transactionmpesa t WHERE t.dest = :dest"),
    @NamedQuery(name = "Transactionmpesa.findByTstamp", query = "SELECT t FROM Transactionmpesa t WHERE t.tstamp = :tstamp"),
    @NamedQuery(name = "Transactionmpesa.findByText", query = "SELECT t FROM Transactionmpesa t WHERE t.text = :text"),
    @NamedQuery(name = "Transactionmpesa.findByCustomerid", query = "SELECT t FROM Transactionmpesa t WHERE t.customerid = :customerid"),
    @NamedQuery(name = "Transactionmpesa.findByUsername", query = "SELECT t FROM Transactionmpesa t WHERE t.username = :username"),
    @NamedQuery(name = "Transactionmpesa.findByPass", query = "SELECT t FROM Transactionmpesa t WHERE t.pass = :pass"),
    @NamedQuery(name = "Transactionmpesa.findByRoutemethodid", query = "SELECT t FROM Transactionmpesa t WHERE t.routemethodid = :routemethodid"),
    @NamedQuery(name = "Transactionmpesa.findByRoutemethodname", query = "SELECT t FROM Transactionmpesa t WHERE t.routemethodname = :routemethodname"),
    @NamedQuery(name = "Transactionmpesa.findByMpesacode", query = "SELECT t FROM Transactionmpesa t WHERE t.mpesacode = :mpesacode"),
    @NamedQuery(name = "Transactionmpesa.findByMpesaacc", query = "SELECT t FROM Transactionmpesa t WHERE t.mpesaacc = :mpesaacc"),
    @NamedQuery(name = "Transactionmpesa.findByMpesamsisdn", query = "SELECT t FROM Transactionmpesa t WHERE t.mpesamsisdn = :mpesamsisdn"),
    @NamedQuery(name = "Transactionmpesa.findByMpesatrxdate", query = "SELECT t FROM Transactionmpesa t WHERE t.mpesatrxdate = :mpesatrxdate"),
    @NamedQuery(name = "Transactionmpesa.findByMpesatrxtime", query = "SELECT t FROM Transactionmpesa t WHERE t.mpesatrxtime = :mpesatrxtime"),
    @NamedQuery(name = "Transactionmpesa.findByMpesaamt", query = "SELECT t FROM Transactionmpesa t WHERE t.mpesaamt = :mpesaamt"),
    @NamedQuery(name = "Transactionmpesa.findByMpesasender", query = "SELECT t FROM Transactionmpesa t WHERE t.mpesasender = :mpesasender")})
public class Transactionmpesa implements Serializable {
    @Column(name = "tstamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tstamp;
    @Column(name = "mpesatrxdate")
    @Temporal(TemporalType.DATE)
    private Date mpesatrxdate;
    @Column(name = "mpesatrxtime")
    @Temporal(TemporalType.TIME)
    private Date mpesatrxtime;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "mpesaamt")
    private Double mpesaamt;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Column(name = "ipn")
    private BigInteger ipn;
    @Size(max = 2147483647)
    @Column(name = "orig")
    private String orig;
    @Column(name = "dest")
    private BigInteger dest;
    @Size(max = 2147483647)
    @Column(name = "text")
    private String text;
    @Column(name = "customerid")
    private Integer customerid;
    @Size(max = 2147483647)
    @Column(name = "username")
    private String username;
    @Size(max = 2147483647)
    @Column(name = "pass")
    private String pass;
    @Column(name = "routemethodid")
    private Integer routemethodid;
    @Size(max = 2147483647)
    @Column(name = "routemethodname")
    private String routemethodname;
    @Size(max = 2147483647)
    @Column(name = "mpesacode")
    private String mpesacode;
    @Size(max = 2147483647)
    @Column(name = "mpesaacc")
    private String mpesaacc;
    @Column(name = "mpesamsisdn")
    private BigInteger mpesamsisdn;
    @Size(max = 2147483647)
    @Column(name = "mpesasender")
    private String mpesasender;
    @JoinColumn(name = "merchantid", referencedColumnName = "id")
    @ManyToOne
    private Merchant merchantid;

    public Transactionmpesa() {
    }

    public Transactionmpesa(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigInteger getIpn() {
        return ipn;
    }

    public void setIpn(BigInteger ipn) {
        this.ipn = ipn;
    }

    public String getOrig() {
        return orig;
    }

    public void setOrig(String orig) {
        this.orig = orig;
    }

    public BigInteger getDest() {
        return dest;
    }

    public void setDest(BigInteger dest) {
        this.dest = dest;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getCustomerid() {
        return customerid;
    }

    public void setCustomerid(Integer customerid) {
        this.customerid = customerid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Integer getRoutemethodid() {
        return routemethodid;
    }

    public void setRoutemethodid(Integer routemethodid) {
        this.routemethodid = routemethodid;
    }

    public String getRoutemethodname() {
        return routemethodname;
    }

    public void setRoutemethodname(String routemethodname) {
        this.routemethodname = routemethodname;
    }

    public String getMpesacode() {
        return mpesacode;
    }

    public void setMpesacode(String mpesacode) {
        this.mpesacode = mpesacode;
    }

    public String getMpesaacc() {
        return mpesaacc;
    }

    public void setMpesaacc(String mpesaacc) {
        this.mpesaacc = mpesaacc;
    }

    public BigInteger getMpesamsisdn() {
        return mpesamsisdn;
    }

    public void setMpesamsisdn(BigInteger mpesamsisdn) {
        this.mpesamsisdn = mpesamsisdn;
    }

    public String getMpesasender() {
        return mpesasender;
    }

    public void setMpesasender(String mpesasender) {
        this.mpesasender = mpesasender;
    }

    public Merchant getMerchantid() {
        return merchantid;
    }

    public void setMerchantid(Merchant merchantid) {
        this.merchantid = merchantid;
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
        if (!(object instanceof Transactionmpesa)) {
            return false;
        }
        Transactionmpesa other = (Transactionmpesa) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "paygate.objects.Transactionmpesa[ id=" + id + " ]";
    }

    public Date getTstamp() {
        return tstamp;
    }

    public void setTstamp(Date tstamp) {
        this.tstamp = tstamp;
    }

    public Date getMpesatrxdate() {
        return mpesatrxdate;
    }

    public void setMpesatrxdate(Date mpesatrxdate) {
        this.mpesatrxdate = mpesatrxdate;
    }

    public Date getMpesatrxtime() {
        return mpesatrxtime;
    }

    public void setMpesatrxtime(Date mpesatrxtime) {
        this.mpesatrxtime = mpesatrxtime;
    }

    public Double getMpesaamt() {
        return mpesaamt;
    }

    public void setMpesaamt(Double mpesaamt) {
        this.mpesaamt = mpesaamt;
    }
    
}
