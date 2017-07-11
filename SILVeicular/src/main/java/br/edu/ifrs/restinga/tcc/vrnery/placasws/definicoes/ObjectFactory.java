
package br.edu.ifrs.restinga.tcc.vrnery.placasws.definicoes;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the br.edu.ifrs.restinga.tcc.vrnery.placasws.definicoes package. 
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

    private final static QName _ConsultarPlacaResponse_QNAME = new QName("http://definicoes.placasws.vrnery.tcc.restinga.ifrs.edu.br/", "consultarPlacaResponse");
    private final static QName _PlacasWSResponse_QNAME = new QName("http://definicoes.placasws.vrnery.tcc.restinga.ifrs.edu.br/", "placasWSResponse");
    private final static QName _PlacasWSRequest_QNAME = new QName("http://definicoes.placasws.vrnery.tcc.restinga.ifrs.edu.br/", "placasWSRequest");
    private final static QName _ConsultarPlaca_QNAME = new QName("http://definicoes.placasws.vrnery.tcc.restinga.ifrs.edu.br/", "consultarPlaca");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: br.edu.ifrs.restinga.tcc.vrnery.placasws.definicoes
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ConsultarPlacaResponse }
     * 
     */
    public ConsultarPlacaResponse createConsultarPlacaResponse() {
        return new ConsultarPlacaResponse();
    }

    /**
     * Create an instance of {@link PlacasWSResponse }
     * 
     */
    public PlacasWSResponse createPlacasWSResponse() {
        return new PlacasWSResponse();
    }

    /**
     * Create an instance of {@link ConsultarPlaca }
     * 
     */
    public ConsultarPlaca createConsultarPlaca() {
        return new ConsultarPlaca();
    }

    /**
     * Create an instance of {@link PlacasWSRequest }
     * 
     */
    public PlacasWSRequest createPlacasWSRequest() {
        return new PlacasWSRequest();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConsultarPlacaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://definicoes.placasws.vrnery.tcc.restinga.ifrs.edu.br/", name = "consultarPlacaResponse")
    public JAXBElement<ConsultarPlacaResponse> createConsultarPlacaResponse(ConsultarPlacaResponse value) {
        return new JAXBElement<ConsultarPlacaResponse>(_ConsultarPlacaResponse_QNAME, ConsultarPlacaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PlacasWSResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://definicoes.placasws.vrnery.tcc.restinga.ifrs.edu.br/", name = "placasWSResponse")
    public JAXBElement<PlacasWSResponse> createPlacasWSResponse(PlacasWSResponse value) {
        return new JAXBElement<PlacasWSResponse>(_PlacasWSResponse_QNAME, PlacasWSResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PlacasWSRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://definicoes.placasws.vrnery.tcc.restinga.ifrs.edu.br/", name = "placasWSRequest")
    public JAXBElement<PlacasWSRequest> createPlacasWSRequest(PlacasWSRequest value) {
        return new JAXBElement<PlacasWSRequest>(_PlacasWSRequest_QNAME, PlacasWSRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConsultarPlaca }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://definicoes.placasws.vrnery.tcc.restinga.ifrs.edu.br/", name = "consultarPlaca")
    public JAXBElement<ConsultarPlaca> createConsultarPlaca(ConsultarPlaca value) {
        return new JAXBElement<ConsultarPlaca>(_ConsultarPlaca_QNAME, ConsultarPlaca.class, null, value);
    }

}
