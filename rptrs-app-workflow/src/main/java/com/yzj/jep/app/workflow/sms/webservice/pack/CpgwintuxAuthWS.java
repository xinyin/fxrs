
package com.yzj.jep.app.workflow.sms.webservice.pack;

import javax.xml.bind.annotation.*;


/**
 * <p>anonymous complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="inbuf" type="{urn:pack.SMS_typedef.salt11}fml32_cpgwintux_authWS_In"/>
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
@XmlRootElement(name = "cpgwintux_authWS")
public class CpgwintuxAuthWS {

    @XmlElement(required = true)
    protected Fml32CpgwintuxAuthWSIn inbuf;

    /**
     *
     * @return
     *     possible object is
     *     {@link Fml32CpgwintuxAuthWSIn }
     *     
     */
    public Fml32CpgwintuxAuthWSIn getInbuf() {
        return inbuf;
    }

    /**
     * ����inbuf���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Fml32CpgwintuxAuthWSIn }
     *     
     */
    public void setInbuf(Fml32CpgwintuxAuthWSIn value) {
        this.inbuf = value;
    }

}
