
package com.yzj.jep.app.workflow.sms.webservice.pack;

import javax.xml.bind.annotation.*;


/**
 *
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="outbuf" type="{urn:pack.SMS_typedef.salt11}fml32_whitelistWS_Out"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "outbuf"
})
@XmlRootElement(name = "whitelistWSResponse")
public class WhitelistWSResponse {

    @XmlElement(required = true)
    protected Fml32WhitelistWSOut outbuf;

    /**
     * ��ȡoutbuf���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Fml32WhitelistWSOut }
     *     
     */
    public Fml32WhitelistWSOut getOutbuf() {
        return outbuf;
    }

    /**
     * ����outbuf���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Fml32WhitelistWSOut }
     *     
     */
    public void setOutbuf(Fml32WhitelistWSOut value) {
        this.outbuf = value;
    }

}
