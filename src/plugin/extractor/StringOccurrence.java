package plugin.extractor;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.awt.*;

class StringOccurrence {
  public static final int STRING_LENGTH = 10;
  private final String id;
  private final String attribute;
  private String value;
  private String textviewWidth;
  private String textviewSize;
  private String textviewFont;
  private String textviewStyle;

 /* public int getTextviewStyle() {
    return getFontType(textviewStyle);
  }

  int getFontType(String textviewStyle){
    switch (textviewStyle){
      case
    }

  }*/

  public int getTextviewStyle() {
    int val = 0;

    switch (textviewStyle){
      case "normal":
        val = 0;
        break;
      case "bold":
        val = 1;
        break;
      case "italic":
        val = 2;
        break;
      default: val = 0;
    }

    return val;
  }


  public int getTextviewSize() {
    return getRemoveStringMethod(textviewSize);
  }




  public int getTextviewWidth() {
    return getRemoveStringMethod(textviewWidth);
  }

  public int getRemoveStringMethod(String dpValue) {
    String id = dpValue.substring(0, dpValue.length() - 2);

    float value = Float.parseFloat(id);

    //float finalValue = (float) (value * 1.6);

    return Math.round(value);

  }

  public String getTextviewFont() {
    return textviewFont;
  }

  StringOccurrence(String id, String attribute, String value, String textviewWidth, String textviewSize,String textviewFont,String textviewStyle) {
    this.id = id;
    this.attribute = attribute;
    this.value = value;
    this.textviewWidth =textviewWidth;
    this.textviewSize = textviewSize;
    this.textviewFont =textviewFont;
    this.textviewStyle = textviewStyle;
  }

  boolean hasHardCodedValue() {
    boolean isReference = value.startsWith("@string/");
    boolean isDataBinding = value.startsWith("@{");

    return !(isReference || isDataBinding);
  }


  String getId() {
    return id;
  }

  String getAttribute() {
    return attribute;
  }

  String getValue() {
    return value;
  }

  boolean isStringTruncated(String str) {
    return str.length() > STRING_LENGTH;
  }

  @Override public boolean equals(Object o) {
    return EqualsBuilder.reflectionEquals(this, o, false);
  }

  @Override public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  @Override public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
