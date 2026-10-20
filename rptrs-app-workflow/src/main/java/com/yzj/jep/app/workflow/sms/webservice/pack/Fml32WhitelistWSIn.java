
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
 * &lt;complexType name="fml32_whitelistWS_In">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FZHType" type="{http://www.w3.org/2001/XMLSchema}short" maxOccurs="50" minOccurs="0"/>
 *         &lt;element name="FCPServiceCode" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="50" minOccurs="0"/>
 *         &lt;element name="FDestTermId" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="50" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fml32_whitelistWS_In", propOrder = {
    "fzhType",
    "fcpServiceCode",
    "fDestTermId"
})
public class Fml32WhitelistWSIn {

    @XmlElement(name = "FZHType", type = Short.class)
    protected List<Short> fzhType;
    @XmlElement(name = "FCPServiceCode")
    protected List<String> fcpServiceCode;
    @XmlElement(name = "FDestTermId")
    protected List<String> fDestTermId;

    /**
     * Gets the value of the fzhType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fzhType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFZHType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Short }
     * 
     * 
     */
    public List<Short> getFZHType() {
        if (fzhType == null) {
            fzhType = new ArrayList<Short>();
        }
        return this.fzhType;
    }

    /**
     * Gets the value of the fcpServiceCode property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fcpServiceCode property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFCPServiceCode().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getFCPServiceCode() {
        if (fcpServiceCode == null) {
            fcpServiceCode = new ArrayList<String>();
        }
        return this.fcpServiceCode;
    }

    /**
     * Gets the value of the fDestTermId property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fDestTermId property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFDestTermId().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getFDestTermId() {
        if (fDestTermId == null) {
            fDestTermId = new ArrayList<String>();
        }
        return this.fDestTermId;
    }

}
