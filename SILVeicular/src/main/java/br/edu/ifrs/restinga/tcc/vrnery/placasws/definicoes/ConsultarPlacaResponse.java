
package br.edu.ifrs.restinga.tcc.vrnery.placasws.definicoes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de consultarPlacaResponse complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="consultarPlacaResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://definicoes.placasws.vrnery.tcc.restinga.ifrs.edu.br/}placasWSResponse" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "consultarPlacaResponse", propOrder = {
    "_return"
})
public class ConsultarPlacaResponse {

    @XmlElement(name = "return")
    protected PlacasWSResponse _return;

    /**
     * Obtém o valor da propriedade return.
     * 
     * @return
     *     possible object is
     *     {@link PlacasWSResponse }
     *     
     */
    public PlacasWSResponse getReturn() {
        return _return;
    }

    /**
     * Define o valor da propriedade return.
     * 
     * @param value
     *     allowed object is
     *     {@link PlacasWSResponse }
     *     
     */
    public void setReturn(PlacasWSResponse value) {
        this._return = value;
    }

}
