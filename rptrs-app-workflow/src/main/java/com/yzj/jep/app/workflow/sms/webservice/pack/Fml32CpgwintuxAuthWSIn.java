
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
 * &lt;complexType name="fml32_cpgwintux_authWS_In">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FCPServiceCode" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="50"/>
 *         &lt;element name="FCPCorpId" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="50"/>
 *         &lt;element name="FPassWd" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="50"/>
 *         &lt;element name="FMsgType" type="{http://www.w3.org/2001/XMLSchema}short" maxOccurs="50"/>
 *         &lt;element name="FSrcGWId" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="50" minOccurs="0"/>
 *         &lt;element name="FDestGWId" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="50" minOccurs="0"/>
 *         &lt;element name="FSrcTermId" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="50" minOccurs="0"/>
 *         &lt;element name="FChargeTermId" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="50" minOccurs="0"/>
 *         &lt;element name="FChargeTermType" type="{http://www.w3.org/2001/XMLSchema}short" maxOccurs="50" minOccurs="0"/>
 *         &lt;element name="FDestTermCount" type="{http://www.w3.org/2001/XMLSchema}short" maxOccurs="50"/>
 *         &lt;element name="FDestTermId" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="50"/>
 *         &lt;element name="FDestTermType" type="{http://www.w3.org/2001/XMLSchema}short" maxOccurs="50"/>
 *         &lt;element name="FFeeType" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="50" minOccurs="0"/>
 *         &lt;element name="FFeeCode" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="50" minOccurs="0"/>
 *         &lt;element name="FGivenValue" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="50" minOccurs="0"/>
 *         &lt;element name="FMTF" type="{http://www.w3.org/2001/XMLSchema}short" maxOccurs="50" minOccurs="0"/>
 *         &lt;element name="FMsgLevel" type="{http://www.w3.org/2001/XMLSchema}short" maxOccurs="50" minOccurs="0"/>
 *         &lt;element name="FValidTime" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="50" minOccurs="0"/>
 *         &lt;element name="FAtTime" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="50" minOccurs="0"/>
 *         &lt;element name="FTPpid" type="{http://www.w3.org/2001/XMLSchema}short" maxOccurs="50" minOccurs="0"/>
 *         &lt;element name="FTPudhi" type="{http://www.w3.org/2001/XMLSchema}short" maxOccurs="50" minOccurs="0"/>
 *         &lt;element name="FMsgFmt" type="{http://www.w3.org/2001/XMLSchema}short" maxOccurs="50"/>
 *         &lt;element name="FMsgContentType" type="{http://www.w3.org/2001/XMLSchema}short" maxOccurs="50"/>
 *         &lt;element name="FMsgContent" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="50"/>
 *         &lt;element name="FLinkId" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="50" minOccurs="0"/>
 *         &lt;element name="FSignature" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="50" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fml32_cpgwintux_authWS_In", propOrder = {
    "fcpServiceCode",
    "fcpCorpId",
    "fPassWd",
    "fMsgType",
    "fSrcGWId",
    "fDestGWId",
    "fSrcTermId",
    "fChargeTermId",
    "fChargeTermType",
    "fDestTermCount",
    "fDestTermId",
    "fDestTermType",
    "fFeeType",
    "fFeeCode",
    "fGivenValue",
    "fmtf",
    "fMsgLevel",
    "fValidTime",
    "fAtTime",
    "ftPpid",
    "ftPudhi",
    "fMsgFmt",
    "fMsgContentType",
    "fMsgContent",
    "fLinkId",
    "fSignature"
})
public class Fml32CpgwintuxAuthWSIn {

    @XmlElement(name = "FCPServiceCode", required = true)
    protected List<String> fcpServiceCode;
    @XmlElement(name = "FCPCorpId", required = true)
    protected List<String> fcpCorpId;
    @XmlElement(name = "FPassWd", required = true)
    protected List<String> fPassWd;
    @XmlElement(name = "FMsgType", type = Short.class)
    protected List<Short> fMsgType;
    @XmlElement(name = "FSrcGWId")
    protected List<String> fSrcGWId;
    @XmlElement(name = "FDestGWId")
    protected List<String> fDestGWId;
    @XmlElement(name = "FSrcTermId")
    protected List<String> fSrcTermId;
    @XmlElement(name = "FChargeTermId")
    protected List<String> fChargeTermId;
    @XmlElement(name = "FChargeTermType", type = Short.class)
    protected List<Short> fChargeTermType;
    @XmlElement(name = "FDestTermCount", type = Short.class)
    protected List<Short> fDestTermCount;
    @XmlElement(name = "FDestTermId", required = true)
    protected List<String> fDestTermId;
    @XmlElement(name = "FDestTermType", type = Short.class)
    protected List<Short> fDestTermType;
    @XmlElement(name = "FFeeType")
    protected List<String> fFeeType;
    @XmlElement(name = "FFeeCode")
    protected List<String> fFeeCode;
    @XmlElement(name = "FGivenValue")
    protected List<String> fGivenValue;
    @XmlElement(name = "FMTF", type = Short.class)
    protected List<Short> fmtf;
    @XmlElement(name = "FMsgLevel", type = Short.class)
    protected List<Short> fMsgLevel;
    @XmlElement(name = "FValidTime")
    protected List<String> fValidTime;
    @XmlElement(name = "FAtTime")
    protected List<String> fAtTime;
    @XmlElement(name = "FTPpid", type = Short.class)
    protected List<Short> ftPpid;
    @XmlElement(name = "FTPudhi", type = Short.class)
    protected List<Short> ftPudhi;
    @XmlElement(name = "FMsgFmt", type = Short.class)
    protected List<Short> fMsgFmt;
    @XmlElement(name = "FMsgContentType", type = Short.class)
    protected List<Short> fMsgContentType;
    @XmlElement(name = "FMsgContent", required = true)
    protected List<String> fMsgContent;
    @XmlElement(name = "FLinkId")
    protected List<String> fLinkId;
    @XmlElement(name = "FSignature")
    protected List<String> fSignature;

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
     * Gets the value of the fcpCorpId property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fcpCorpId property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFCPCorpId().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getFCPCorpId() {
        if (fcpCorpId == null) {
            fcpCorpId = new ArrayList<String>();
        }
        return this.fcpCorpId;
    }

    /**
     * Gets the value of the fPassWd property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fPassWd property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFPassWd().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getFPassWd() {
        if (fPassWd == null) {
            fPassWd = new ArrayList<String>();
        }
        return this.fPassWd;
    }

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
     * Gets the value of the fChargeTermId property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fChargeTermId property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFChargeTermId().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getFChargeTermId() {
        if (fChargeTermId == null) {
            fChargeTermId = new ArrayList<String>();
        }
        return this.fChargeTermId;
    }

    /**
     * Gets the value of the fChargeTermType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fChargeTermType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFChargeTermType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Short }
     * 
     * 
     */
    public List<Short> getFChargeTermType() {
        if (fChargeTermType == null) {
            fChargeTermType = new ArrayList<Short>();
        }
        return this.fChargeTermType;
    }

    /**
     * Gets the value of the fDestTermCount property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fDestTermCount property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFDestTermCount().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Short }
     * 
     * 
     */
    public List<Short> getFDestTermCount() {
        if (fDestTermCount == null) {
            fDestTermCount = new ArrayList<Short>();
        }
        return this.fDestTermCount;
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
     * Gets the value of the fDestTermType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fDestTermType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFDestTermType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Short }
     * 
     * 
     */
    public List<Short> getFDestTermType() {
        if (fDestTermType == null) {
            fDestTermType = new ArrayList<Short>();
        }
        return this.fDestTermType;
    }

    /**
     * Gets the value of the fFeeType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fFeeType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFFeeType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getFFeeType() {
        if (fFeeType == null) {
            fFeeType = new ArrayList<String>();
        }
        return this.fFeeType;
    }

    /**
     * Gets the value of the fFeeCode property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fFeeCode property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFFeeCode().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getFFeeCode() {
        if (fFeeCode == null) {
            fFeeCode = new ArrayList<String>();
        }
        return this.fFeeCode;
    }

    /**
     * Gets the value of the fGivenValue property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fGivenValue property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFGivenValue().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getFGivenValue() {
        if (fGivenValue == null) {
            fGivenValue = new ArrayList<String>();
        }
        return this.fGivenValue;
    }

    /**
     * Gets the value of the fmtf property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fmtf property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFMTF().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Short }
     * 
     * 
     */
    public List<Short> getFMTF() {
        if (fmtf == null) {
            fmtf = new ArrayList<Short>();
        }
        return this.fmtf;
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
     * {@link Short }
     * 
     * 
     */
    public List<Short> getFMsgLevel() {
        if (fMsgLevel == null) {
            fMsgLevel = new ArrayList<Short>();
        }
        return this.fMsgLevel;
    }

    /**
     * Gets the value of the fValidTime property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fValidTime property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFValidTime().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getFValidTime() {
        if (fValidTime == null) {
            fValidTime = new ArrayList<String>();
        }
        return this.fValidTime;
    }

    /**
     * Gets the value of the fAtTime property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fAtTime property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFAtTime().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getFAtTime() {
        if (fAtTime == null) {
            fAtTime = new ArrayList<String>();
        }
        return this.fAtTime;
    }

    /**
     * Gets the value of the ftPpid property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ftPpid property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFTPpid().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Short }
     * 
     * 
     */
    public List<Short> getFTPpid() {
        if (ftPpid == null) {
            ftPpid = new ArrayList<Short>();
        }
        return this.ftPpid;
    }

    /**
     * Gets the value of the ftPudhi property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ftPudhi property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFTPudhi().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Short }
     * 
     * 
     */
    public List<Short> getFTPudhi() {
        if (ftPudhi == null) {
            ftPudhi = new ArrayList<Short>();
        }
        return this.ftPudhi;
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
     * Gets the value of the fSignature property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fSignature property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFSignature().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getFSignature() {
        if (fSignature == null) {
            fSignature = new ArrayList<String>();
        }
        return this.fSignature;
    }

}
