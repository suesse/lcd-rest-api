package edu.mayo.lcd.rest.cts2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ValueSetsType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="ValueSetsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ValueSet" type="{}ValueSetType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ValueSetsType", propOrder = {
        "valueSet"
})
public class ValueSetsType {

    @XmlElement(name = "ValueSet")
    protected List<ValueSetType> valueSet;

    /**
     * Gets the value of the valueSet property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the valueSet property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getValueSet().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ValueSetType }
     *
     *
     */
    public List<ValueSetType> getValueSet() {
        if (valueSet == null) {
            valueSet = new ArrayList<ValueSetType>();
        }
        return this.valueSet;
    }

}