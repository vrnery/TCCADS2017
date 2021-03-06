
package br.edu.ifrs.restinga.tcc.vrnery.placasws.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import br.edu.ifrs.restinga.tcc.vrnery.placasws.definicoes.ObjectFactory;
import br.edu.ifrs.restinga.tcc.vrnery.placasws.definicoes.PlacasWSRequest;
import br.edu.ifrs.restinga.tcc.vrnery.placasws.definicoes.PlacasWSResponse;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "IConsultaPlacasWS", targetNamespace = "http://definicoes.placasws.vrnery.tcc.restinga.ifrs.edu.br/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface IConsultaPlacasWS {


    /**
     * 
     * @param arg0
     * @return
     *     returns br.edu.ifrs.restinga.tcc.vrnery.placasws.definicoes.PlacasWSResponse
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "consultarPlaca", targetNamespace = "http://definicoes.placasws.vrnery.tcc.restinga.ifrs.edu.br/", className = "br.edu.ifrs.restinga.tcc.vrnery.placasws.definicoes.ConsultarPlaca")
    @ResponseWrapper(localName = "consultarPlacaResponse", targetNamespace = "http://definicoes.placasws.vrnery.tcc.restinga.ifrs.edu.br/", className = "br.edu.ifrs.restinga.tcc.vrnery.placasws.definicoes.ConsultarPlacaResponse")
    @Action(input = "http://definicoes.placasws.vrnery.tcc.restinga.ifrs.edu.br/IConsultaPlacasWS/consultarPlacaRequest", output = "http://definicoes.placasws.vrnery.tcc.restinga.ifrs.edu.br/IConsultaPlacasWS/consultarPlacaResponse")
    public PlacasWSResponse consultarPlaca(
        @WebParam(name = "arg0", targetNamespace = "")
        PlacasWSRequest arg0);

}
