
package service;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the service package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _LinkResponse_QNAME = new QName("http://service/", "linkResponse");
    private final static QName _PickData_QNAME = new QName("http://service/", "pickData");
    private final static QName _Transactionsource_QNAME = new QName("http://service/", "transactionsource");
    private final static QName _PickDataResponse_QNAME = new QName("http://service/", "pickDataResponse");
    private final static QName _Gender_QNAME = new QName("http://service/", "gender");
    private final static QName _HelloResponse_QNAME = new QName("http://service/", "helloResponse");
    private final static QName _Rate_QNAME = new QName("http://service/", "rate");
    private final static QName _FormData_QNAME = new QName("http://service/", "formData");
    private final static QName _Category_QNAME = new QName("http://service/", "category");
    private final static QName _Hello_QNAME = new QName("http://service/", "hello");
    private final static QName _Country_QNAME = new QName("http://service/", "country");
    private final static QName _TestResponse_QNAME = new QName("http://service/", "testResponse");
    private final static QName _FormDataResponse_QNAME = new QName("http://service/", "formDataResponse");
    private final static QName _Purchase_QNAME = new QName("http://service/", "purchase");
    private final static QName _Transactiontype_QNAME = new QName("http://service/", "transactiontype");
    private final static QName _Link_QNAME = new QName("http://service/", "link");
    private final static QName _Test_QNAME = new QName("http://service/", "test");
    private final static QName _Status_QNAME = new QName("http://service/", "status");
    private final static QName _Merchant_QNAME = new QName("http://service/", "merchant");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: service
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Test }
     * 
     */
    public Test createTest() {
        return new Test();
    }

    /**
     * Create an instance of {@link Status }
     * 
     */
    public Status createStatus() {
        return new Status();
    }

    /**
     * Create an instance of {@link Merchant }
     * 
     */
    public Merchant createMerchant() {
        return new Merchant();
    }

    /**
     * Create an instance of {@link Link }
     * 
     */
    public Link createLink() {
        return new Link();
    }

    /**
     * Create an instance of {@link Transactiontype }
     * 
     */
    public Transactiontype createTransactiontype() {
        return new Transactiontype();
    }

    /**
     * Create an instance of {@link Purchase }
     * 
     */
    public Purchase createPurchase() {
        return new Purchase();
    }

    /**
     * Create an instance of {@link TestResponse }
     * 
     */
    public TestResponse createTestResponse() {
        return new TestResponse();
    }

    /**
     * Create an instance of {@link FormDataResponse }
     * 
     */
    public FormDataResponse createFormDataResponse() {
        return new FormDataResponse();
    }

    /**
     * Create an instance of {@link Country }
     * 
     */
    public Country createCountry() {
        return new Country();
    }

    /**
     * Create an instance of {@link Hello }
     * 
     */
    public Hello createHello() {
        return new Hello();
    }

    /**
     * Create an instance of {@link Category }
     * 
     */
    public Category createCategory() {
        return new Category();
    }

    /**
     * Create an instance of {@link Rate }
     * 
     */
    public Rate createRate() {
        return new Rate();
    }

    /**
     * Create an instance of {@link FormData }
     * 
     */
    public FormData createFormData() {
        return new FormData();
    }

    /**
     * Create an instance of {@link HelloResponse }
     * 
     */
    public HelloResponse createHelloResponse() {
        return new HelloResponse();
    }

    /**
     * Create an instance of {@link Gender }
     * 
     */
    public Gender createGender() {
        return new Gender();
    }

    /**
     * Create an instance of {@link PickDataResponse }
     * 
     */
    public PickDataResponse createPickDataResponse() {
        return new PickDataResponse();
    }

    /**
     * Create an instance of {@link Transactionsource }
     * 
     */
    public Transactionsource createTransactionsource() {
        return new Transactionsource();
    }

    /**
     * Create an instance of {@link PickData }
     * 
     */
    public PickData createPickData() {
        return new PickData();
    }

    /**
     * Create an instance of {@link LinkResponse }
     * 
     */
    public LinkResponse createLinkResponse() {
        return new LinkResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LinkResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service/", name = "linkResponse")
    public JAXBElement<LinkResponse> createLinkResponse(LinkResponse value) {
        return new JAXBElement<LinkResponse>(_LinkResponse_QNAME, LinkResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PickData }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service/", name = "pickData")
    public JAXBElement<PickData> createPickData(PickData value) {
        return new JAXBElement<PickData>(_PickData_QNAME, PickData.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Transactionsource }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service/", name = "transactionsource")
    public JAXBElement<Transactionsource> createTransactionsource(Transactionsource value) {
        return new JAXBElement<Transactionsource>(_Transactionsource_QNAME, Transactionsource.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PickDataResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service/", name = "pickDataResponse")
    public JAXBElement<PickDataResponse> createPickDataResponse(PickDataResponse value) {
        return new JAXBElement<PickDataResponse>(_PickDataResponse_QNAME, PickDataResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Gender }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service/", name = "gender")
    public JAXBElement<Gender> createGender(Gender value) {
        return new JAXBElement<Gender>(_Gender_QNAME, Gender.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HelloResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service/", name = "helloResponse")
    public JAXBElement<HelloResponse> createHelloResponse(HelloResponse value) {
        return new JAXBElement<HelloResponse>(_HelloResponse_QNAME, HelloResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Rate }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service/", name = "rate")
    public JAXBElement<Rate> createRate(Rate value) {
        return new JAXBElement<Rate>(_Rate_QNAME, Rate.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FormData }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service/", name = "formData")
    public JAXBElement<FormData> createFormData(FormData value) {
        return new JAXBElement<FormData>(_FormData_QNAME, FormData.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Category }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service/", name = "category")
    public JAXBElement<Category> createCategory(Category value) {
        return new JAXBElement<Category>(_Category_QNAME, Category.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Hello }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service/", name = "hello")
    public JAXBElement<Hello> createHello(Hello value) {
        return new JAXBElement<Hello>(_Hello_QNAME, Hello.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Country }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service/", name = "country")
    public JAXBElement<Country> createCountry(Country value) {
        return new JAXBElement<Country>(_Country_QNAME, Country.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TestResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service/", name = "testResponse")
    public JAXBElement<TestResponse> createTestResponse(TestResponse value) {
        return new JAXBElement<TestResponse>(_TestResponse_QNAME, TestResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FormDataResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service/", name = "formDataResponse")
    public JAXBElement<FormDataResponse> createFormDataResponse(FormDataResponse value) {
        return new JAXBElement<FormDataResponse>(_FormDataResponse_QNAME, FormDataResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Purchase }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service/", name = "purchase")
    public JAXBElement<Purchase> createPurchase(Purchase value) {
        return new JAXBElement<Purchase>(_Purchase_QNAME, Purchase.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Transactiontype }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service/", name = "transactiontype")
    public JAXBElement<Transactiontype> createTransactiontype(Transactiontype value) {
        return new JAXBElement<Transactiontype>(_Transactiontype_QNAME, Transactiontype.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Link }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service/", name = "link")
    public JAXBElement<Link> createLink(Link value) {
        return new JAXBElement<Link>(_Link_QNAME, Link.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Test }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service/", name = "test")
    public JAXBElement<Test> createTest(Test value) {
        return new JAXBElement<Test>(_Test_QNAME, Test.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Status }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service/", name = "status")
    public JAXBElement<Status> createStatus(Status value) {
        return new JAXBElement<Status>(_Status_QNAME, Status.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Merchant }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service/", name = "merchant")
    public JAXBElement<Merchant> createMerchant(Merchant value) {
        return new JAXBElement<Merchant>(_Merchant_QNAME, Merchant.class, null, value);
    }

}
