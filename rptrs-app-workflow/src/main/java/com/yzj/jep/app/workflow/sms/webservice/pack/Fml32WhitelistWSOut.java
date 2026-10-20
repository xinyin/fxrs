
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
 * &lt;complexType name="fml32_whitelistWS_Out">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FZHType" type="{http://www.w3.org/2001/XMLSchema}short" maxOccurs="50" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fml32_whitelistWS_Out", propOrder = {
    "fzhType"
})
public class Fml32WhitelistWSOut {

    @XmlElement(name = "FZHType", type = Short.class)
    protected List<Short> fzhType;

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

}
