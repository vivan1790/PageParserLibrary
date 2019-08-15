package com.sample.parserlibrary;

public class TagAttribute {

    private String attributeName;
    private String attributeValue;

    public TagAttribute(String attrName, String attrValue) {
        this.attributeName = attrName;
        this.attributeValue = attrValue;
    }

    public String getAttributeName() {
        return this.attributeName;
    }

    public void setAttributeName(String attrName) {
        this.attributeName = attrName;
    }

    public String getAttributeValue() {
        return this.attributeValue;
    }

    public void setAttributeValue(String attrValue) {
        this.attributeValue = attrValue;
    }

    @Override
    public String toString() {
        return "{" + attributeName + " = " + attributeValue + "}";
    }
}
