package plugin.extractor;

import org.w3c.dom.*;

import java.util.ArrayList;
import java.util.List;

class LayoutParser {
    private boolean ignoreMissingId = true;

    void setIgnoreMissingId(boolean ignoreMissingId) {
        this.ignoreMissingId = ignoreMissingId;
    }

    List<StringOccurrence> parse(Document document) {
        List<StringOccurrence> stringOccurrences = new ArrayList<>();
        processNodes(stringOccurrences, document.getChildNodes());

        return stringOccurrences;
    }

    private void processNodes(List<StringOccurrence> stringOccurrences, NodeList nodes)
            throws IllegalStateException {
        for (int i = 0; i < nodes.getLength(); i++) {
            //  System.out.println("naresh");
            processNode(stringOccurrences, nodes.item(i));
        }
    }

    private void processNode(List<StringOccurrence> stringOccurrences, Node node) {
        if (node.hasChildNodes())
            processNodes(stringOccurrences, node.getChildNodes());

        if (!node.hasAttributes()) return;
        Element eElement = (Element) node;
        if (!(eElement.getTagName().equals("TextView"))) {
            return;
        }

        System.out.println("naresh" + eElement.getTagName());


        NamedNodeMap attributes = node.getAttributes();
        Node idAttribute = attributes.getNamedItem("android:id");
        Node textAttribute = attributes.getNamedItem("android:text");
        Node hintAttribute = attributes.getNamedItem("android:hint");
        Node widthAttribute = attributes.getNamedItem("android:layout_width");
        Node sizeAttribute = attributes.getNamedItem("android:textSize");
        Node fontFamilyAttribute = attributes.getNamedItem("android:fontFamily");
        Node styleAttribute = attributes.getNamedItem("android:textStyle");

        /*String textviewWidth = null, textviewSize = null, textviewFont = null, textviewStyle = null;
        if (widthAttribute != null) {
            textviewWidth = eElement.getAttribute("android:layout_width");
        }
        if (sizeAttribute != null) {
            textviewSize = eElement.getAttribute("android:textSize");
        }
        if (fontFamilyAttribute != null) {
            textviewFont = eElement.getAttribute("android:fontFamily");
        }
        if (styleAttribute != null) {
            textviewStyle = eElement.getAttribute("android:textStyle");
        }
        System.out.println("naresh" + textviewWidth + "" + textAttribute.getNodeValue());*/

//    if (!ignoreMissingId) validateNode(idAttribute, textAttribute, hintAttribute);

        if (idAttribute == null || (textAttribute == null && hintAttribute == null) || widthAttribute == null) return;

        /*
        setting default value for font size, font-family and font-style
        */
        String fontSize = (sizeAttribute == null) ? "14sp" : sizeAttribute.getNodeValue();
        String fontFamily = (fontFamilyAttribute == null) ? "sans-serif" : fontFamilyAttribute.getNodeValue();
        String fontStyle = (styleAttribute == null) ? "normal" : styleAttribute.getNodeValue();

        String id = stripIdPrefix(idAttribute.getNodeValue());
        if (textAttribute != null) {
            if (!isDataBinding(textAttribute.getNodeValue())) {
                stringOccurrences.add(
                        new StringOccurrence(id, "text", textAttribute.getNodeValue(),widthAttribute.getNodeValue(),fontSize,fontFamily,fontStyle));
            }
        }
        //  if (hintAttribute != null) {
        // if (!isDataBinding(hintAttribute.getNodeValue())) {
      /*  stringOccurrences.add(
           new StringOccurrence(id, "hint", hintAttribute.getNodeValue(),textviewWidth));*/
        // }
        //   }
    }

    private void validateNode(Node idAttribute, Node textAttribute, Node hintAttribute) {
        if (idAttribute == null || idAttribute.getNodeValue() == null) {
            if (textAttribute != null) {
                throw new IllegalStateException(
                        String.format("No id specified for %s", textAttribute.getNodeValue()));
            } else {
                throw new IllegalStateException(
                        String.format("No id specified for %s", hintAttribute.getNodeValue()));
            }

        }
    }

    private String stripIdPrefix(String id) {
        if (id.startsWith("@+id/")) return id.substring(5);

        return id;
    }

    private boolean isDataBinding(String value) {
        boolean oneWayDataBinding = value.startsWith("@{");
        boolean twoWayDataBinding = value.startsWith("@={");

        return oneWayDataBinding || twoWayDataBinding;
    }
}
