
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
 *         &lt;element name="outbuf" type="{urn:pack.SMS_typedef.salt11}fml32_cpgwouttuxWS_Out"/>
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
@XmlRootElement(name = "cpgwouttuxWSResponse")
public class CpgwouttuxWSResponse {

    @XmlElement(required = true)
    protected Fml32CpgwouttuxWSOut outbuf;

    /**
     * ��ȡoutbuf���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Fml32CpgwouttuxWSOut }
     *     
     */
    public Fml32CpgwouttuxWSOut getOutbuf() {
        return outbuf;
    }

    /**
     * ����outbuf���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Fml32CpgwouttuxWSOut }
     *     
     */
    public void setOutbuf(Fml32CpgwouttuxWSOut value) {
        this.outbuf = value;
    }

}
