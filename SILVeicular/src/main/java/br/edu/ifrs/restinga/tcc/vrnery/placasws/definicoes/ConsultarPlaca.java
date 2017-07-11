
package br.edu.ifrs.restinga.tcc.vrnery.placasws.definicoes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de consultarPlaca complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="consultarPlaca">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="arg0" type="{http://definicoes.placasws.vrnery.tcc.restinga.ifrs.edu.br/}placasWSRequest" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "consultarPlaca", propOrder = {
    "arg0"
})
public class ConsultarPlaca {

    protected PlacasWSRequest arg0;

    /**
     * Obtém o valor da propriedade arg0.
     * 
     * @return
     *     possible object is
     *     {@link PlacasWSRequest }
     *     
     */
    public PlacasWSRequest getArg0() {
        return arg0;
    }

    /**
     * Define o valor da propriedade arg0.
     * 
     * @param value
     *     allowed object is
     *     {@link PlacasWSRequest }
     *     
     */
    public void setArg0(PlacasWSRequest value) {
        this.arg0 = value;
    }

}
