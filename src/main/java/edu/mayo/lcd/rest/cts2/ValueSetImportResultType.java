package edu.mayo.lcd.rest.cts2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ValueSetImportResultType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="ValueSetImportResultType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ValueSets" type="{}ValueSetsType"/>
 *         &lt;element name="Messages" type="{}MessagesType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ValueSetImportResultType", propOrder = {
        "status",
        "valueSets",
        "messages"
})
@XmlRootElement(name="ValueSetImportResult", namespace="")
public class ValueSetImportResultType {

    @XmlElement(name = "Status", required = true)
    protected String status;
    @XmlElement(name = "ValueSets", required = true)
    protected ValueSetsType valueSets;
    @XmlElement(name = "Messages", required = true)
    protected MessagesType messages;

    /**
     * Gets the value of the status property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Gets the value of the valueSets property.
     *
     * @return
     *     possible object is
     *     {@link ValueSetsType }
     *
     */
    public ValueSetsType getValueSets() {
        return valueSets;
    }

    /**
     * Sets the value of the valueSets property.
     *
     * @param value
     *     allowed object is
     *     {@link ValueSetsType }
     *
     */
    public void setValueSets(ValueSetsType value) {
        this.valueSets = value;
    }

    /**
     * Gets the value of the messages property.
     *
     * @return
     *     possible object is
     *     {@link MessagesType }
     *
     */
    public MessagesType getMessages() {
        return messages;
    }

    /**
     * Sets the value of the messages property.
     *
     * @param value
     *     allowed object is
     *     {@link MessagesType }
     *
     */
    public void setMessages(MessagesType value) {
        this.messages = value;
    }

}
