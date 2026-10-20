
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
 *         &lt;element name="inbuf" type="{urn:pack.SMS_typedef.salt11}fml32_cpgwouttuxWS_In"/>
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
    "inbuf"
})
@XmlRootElement(name = "cpgwouttuxWS")
public class CpgwouttuxWS {

    @XmlElement(required = true)
    protected Fml32CpgwouttuxWSIn inbuf;

    /**
     * ��ȡinbuf���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Fml32CpgwouttuxWSIn }
     *     
     */
    public Fml32CpgwouttuxWSIn getInbuf() {
        return inbuf;
    }

    /**
     * ����inbuf���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Fml32CpgwouttuxWSIn }
     *     
     */
    public void setInbuf(Fml32CpgwouttuxWSIn value) {
        this.inbuf = value;
    }

}
