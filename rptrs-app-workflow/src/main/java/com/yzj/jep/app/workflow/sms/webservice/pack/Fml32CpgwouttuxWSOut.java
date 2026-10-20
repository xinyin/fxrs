
package com.yzj.jep.app.workflow.sms.webservice.pack;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 *
 *
 * <pre>
 * &lt;complexType name="fml32_cpgwouttuxWS_Out">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FResult" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="50" minOccurs="0"/>
 *         &lt;element name="FMsgId" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="50" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fml32_cpgwouttuxWS_Out", propOrder = {
    "fResult",
    "fMsgId"
})
public class Fml32CpgwouttuxWSOut {

    @XmlElement(name = "FResult")
    protected List<String> fResult;
    @XmlElement(name = "FMsgId")
    protected List<String> fMsgId;

    /**
     * Gets the value of the fResult property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fResult property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFResult().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getFResult() {
        if (fResult == null) {
            fResult = new ArrayList<String>();
        }
        return this.fResult;
    }

    /**
     * Gets the value of the fMsgId property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fMsgId property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFMsgId().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getFMsgId() {
        if (fMsgId == null) {
            fMsgId = new ArrayList<String>();
        }
        return this.fMsgId;
    }

}
