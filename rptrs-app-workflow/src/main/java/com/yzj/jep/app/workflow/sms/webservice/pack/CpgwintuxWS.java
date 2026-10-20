
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
 *         &lt;element name="inbuf" type="{urn:pack.SMS_typedef.salt11}fml32_cpgwintuxWS_In"/>
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
@XmlRootElement(name = "cpgwintuxWS")
public class CpgwintuxWS {

    @XmlElement(required = true)
    protected Fml32CpgwintuxWSIn inbuf;

    /**
     *
     * @return
     *     possible object is
     *     {@link Fml32CpgwintuxWSIn }
     *     
     */
    public Fml32CpgwintuxWSIn getInbuf() {
        return inbuf;
    }

    /**
     * ����inbuf���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Fml32CpgwintuxWSIn }
     *     
     */
    public void setInbuf(Fml32CpgwintuxWSIn value) {
        this.inbuf = value;
    }

}
