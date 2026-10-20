
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
 * &lt;complexType name="fml32_cpgwouttux_authWS_Out">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FMsgType" type="{http://www.w3.org/2001/XMLSchema}short" maxOccurs="50" minOccurs="0"/>
 *         &lt;element name="FMsgId" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="50" minOccurs="0"/>
 *         &lt;element name="FSrcGWId" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="50" minOccurs="0"/>
 *         &lt;element name="FDestGWId" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="50" minOccurs="0"/>
 *         &lt;element name="FSrcTermId" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="50" minOccurs="0"/>
 *         &lt;element name="FSrcTermType" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="50" minOccurs="0"/>
 *         &lt;element name="FMsgLevel" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="50" minOccurs="0"/>
 *         &lt;element name="FMTMsgId" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="50" minOccurs="0"/>
 *         &lt;element name="FDestTermId" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="50" minOccurs="0"/>
 *         &lt;element name="FStat" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="50" minOccurs="0"/>
 *         &lt;element name="FStatDesc" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="50" minOccurs="0"/>
 *         &lt;element name="FSubmitTime" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="50" minOccurs="0"/>
 *         &lt;element name="FDoneTime" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="50" minOccurs="0"/>
 *         &lt;element name="FMsgFmt" type="{http://www.w3.org/2001/XMLSchema}short" maxOccurs="50" minOccurs="0"/>
 *         &lt;element name="FMsgContent" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="50" minOccurs="0"/>
 *         &lt;element name="FLinkId" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="50" minOccurs="0"/>
 *         &lt;element name="FResult" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="50" minOccurs="0"/>
 *         &lt;element name="FMsgContentType" type="{http://www.w3.org/2001/XMLSchema}short" maxOccurs="50" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fml32_cpgwouttux_authWS_Out", propOrder = {
    "fMsgType",
    "fMsgId",
    "fSrcGWId",
    "fDestGWId",
    "fSrcTermId",
    "fSrcTermType",
    "fMsgLevel",
    "fmtMsgId",
    "fDestTermId",
    "fStat",
    "fStatDesc",
    "fSubmitTime",
    "fDoneTime",
    "fMsgFmt",
    "fMsgContent",
    "fLinkId",
    "fResult",
    "fMsgContentType"
})
public class Fml32CpgwouttuxAuthWSOut {

    @XmlElement(name = "FMsgType", type = Short.class)
    protected List<Short> fMsgType;
    @XmlElement(name = "FMsgId")
    protected List<String> fMsgId;
    @XmlElement(name = "FSrcGWId")
    protected List<String> fSrcGWId;
    @XmlElement(name = "FDestGWId")
    protected List<String> fDestGWId;
    @XmlElement(name = "FSrcTermId")
    protected List<String> fSrcTermId;
    @XmlElement(name = "FSrcTermType")
    protected List<String> fSrcTermType;
    @XmlElement(name = "FMsgLevel")
    protected List<String> fMsgLevel;
    @XmlElement(name = "FMTMsgId")
    protected List<String> fmtMsgId;
    @XmlElement(name = "FDestTermId")
    protected List<String> fDestTermId;
    @XmlElement(name = "FStat")
    protected List<String> fStat;
    @XmlElement(name = "FStatDesc")
    protected List<String> fStatDesc;
    @XmlElement(name = "FSubmitTime")
    protected List<String> fSubmitTime;
    @XmlElement(name = "FDoneTime")
    protected List<String> fDoneTime;
    @XmlElement(name = "FMsgFmt", type = Short.class)
    protected List<Short> fMsgFmt;
    @XmlElement(name = "FMsgContent")
    protected List<String> fMsgContent;
    @XmlElement(name = "FLinkId")
    protected List<String> fLinkId;
    @XmlElement(name = "FResult")
    protected List<String> fResult;
    @XmlElement(name = "FMsgContentType", type = Short.class)
    protected List<Short> fMsgContentType;

    /**
     * Gets the value of the fMsgType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fMsgType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFMsgType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Short }
     * 
     * 
     */
    public List<Short> getFMsgType() {
        if (fMsgType == null) {
            fMsgType = new ArrayList<Short>();
        }
        return this.fMsgType;
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

    /**
     * Gets the value of the fSrcGWId property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fSrcGWId property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFSrcGWId().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getFSrcGWId() {
        if (fSrcGWId == null) {
            fSrcGWId = new ArrayList<String>();
        }
        return this.fSrcGWId;
    }

    /**
     * Gets the value of the fDestGWId property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fDestGWId property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFDestGWId().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getFDestGWId() {
        if (fDestGWId == null) {
            fDestGWId = new ArrayList<String>();
        }
        return this.fDestGWId;
    }

    /**
     * Gets the value of the fSrcTermId property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fSrcTermId property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFSrcTermId().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getFSrcTermId() {
        if (fSrcTermId == null) {
            fSrcTermId = new ArrayList<String>();
        }
        return this.fSrcTermId;
    }

    /**
     * Gets the value of the fSrcTermType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fSrcTermType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFSrcTermType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getFSrcTermType() {
        if (fSrcTermType == null) {
            fSrcTermType = new ArrayList<String>();
        }
        return this.fSrcTermType;
    }

    /**
     * Gets the value of the fMsgLevel property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fMsgLevel property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFMsgLevel().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getFMsgLevel() {
        if (fMsgLevel == null) {
            fMsgLevel = new ArrayList<String>();
        }
        return this.fMsgLevel;
    }

    /**
     * Gets the value of the fmtMsgId property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fmtMsgId property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFMTMsgId().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getFMTMsgId() {
        if (fmtMsgId == null) {
            fmtMsgId = new ArrayList<String>();
        }
        return this.fmtMsgId;
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

    /**
     * Gets the value of the fStat property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fStat property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFStat().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getFStat() {
        if (fStat == null) {
            fStat = new ArrayList<String>();
        }
        return this.fStat;
    }

    /**
     * Gets the value of the fStatDesc property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fStatDesc property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFStatDesc().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getFStatDesc() {
        if (fStatDesc == null) {
            fStatDesc = new ArrayList<String>();
        }
        return this.fStatDesc;
    }

    /**
     * Gets the value of the fSubmitTime property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fSubmitTime property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFSubmitTime().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getFSubmitTime() {
        if (fSubmitTime == null) {
            fSubmitTime = new ArrayList<String>();
        }
        return this.fSubmitTime;
    }

    /**
     * Gets the value of the fDoneTime property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fDoneTime property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFDoneTime().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getFDoneTime() {
        if (fDoneTime == null) {
            fDoneTime = new ArrayList<String>();
        }
        return this.fDoneTime;
    }

    /**
     * Gets the value of the fMsgFmt property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fMsgFmt property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFMsgFmt().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Short }
     * 
     * 
     */
    public List<Short> getFMsgFmt() {
        if (fMsgFmt == null) {
            fMsgFmt = new ArrayList<Short>();
        }
        return this.fMsgFmt;
    }

    /**
     * Gets the value of the fMsgContent property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fMsgContent property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFMsgContent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getFMsgContent() {
        if (fMsgContent == null) {
            fMsgContent = new ArrayList<String>();
        }
        return this.fMsgContent;
    }

    /**
     * Gets the value of the fLinkId property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fLinkId property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFLinkId().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getFLinkId() {
        if (fLinkId == null) {
            fLinkId = new ArrayList<String>();
        }
        return this.fLinkId;
    }

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
     * Gets the value of the fMsgContentType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fMsgContentType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFMsgContentType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Short }
     * 
     * 
     */
    public List<Short> getFMsgContentType() {
        if (fMsgContentType == null) {
            fMsgContentType = new ArrayList<Short>();
        }
        return this.fMsgContentType;
    }

}
