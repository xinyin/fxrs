
package com.yzj.jep.app.workflow.sms.webservice.pack;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 *
 *
 * <pre>
 * &lt;complexType name="fml32_cpgwouttux_authWS_In">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FCPCorpId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="FPassWd" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fml32_cpgwouttux_authWS_In", propOrder = {
    "fcpCorpId",
    "fPassWd"
})
public class Fml32CpgwouttuxAuthWSIn {

    @XmlElement(name = "FCPCorpId", required = true)
    protected String fcpCorpId;
    @XmlElement(name = "FPassWd", required = true)
    protected String fPassWd;

    /**
     * ��ȡfcpCorpId���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFCPCorpId() {
        return fcpCorpId;
    }

    /**
     * ����fcpCorpId���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFCPCorpId(String value) {
        this.fcpCorpId = value;
    }

    /**
     * ��ȡfPassWd���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFPassWd() {
        return fPassWd;
    }

    /**
     * ����fPassWd���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFPassWd(String value) {
        this.fPassWd = value;
    }

}
