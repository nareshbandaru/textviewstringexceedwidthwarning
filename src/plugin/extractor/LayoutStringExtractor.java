package plugin.extractor;

import org.apache.commons.lang3.StringUtils;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class LayoutStringExtractor {
  private final AndroidProjectFactory factory;

  public LayoutStringExtractor(AndroidProjectFactory factory) {
    this.factory = factory;
  }

  public void extract(String projectPath)
      throws TransformerException, IOException, ParserConfigurationException, SAXException,
      XPathExpressionException {
    AndroidProject project = factory.create(new File(projectPath));
    List<Flavor> flavors = project.readFlavors();
    for (Flavor flavor : flavors) {
      handleFlavor(flavor);
    }
  }

  private void handleFlavor(Flavor flavor)
      throws TransformerException, IOException, ParserConfigurationException, SAXException,
      XPathExpressionException {
    StringValues stringValues = flavor.readStringValues();

    List<Layout> layouts = flavor.readLayouts();
    for (Layout layout : layouts) {
      handleLayout(layout, stringValues);
    }

    if(stringValues.hasChanged()) flavor.writeStringValues(stringValues);
  }

  private void handleLayout(Layout layout, StringValues stringValues)
      throws TransformerException, IOException, ParserConfigurationException, SAXException,
      XPathExpressionException {
    List<StringOccurrence> stringOccurrences = layout.readStrings();
    for (StringOccurrence string : stringOccurrences) {
      handleString(string, layout, stringValues);
    }

    layout.writeStrings(stringOccurrences);
  }

  private void handleString(StringOccurrence occurrence, Layout layout, StringValues stringValues) {
    String value = null;
    if (occurrence.hasHardCodedValue()) {
      //Read the text value
      value = occurrence.getValue();
    } else {
      //Read the text from strings.xml
      value = stringValues.getValues().get(extractIdFromTextValue(occurrence.getValue()));
    }

    if(!StringUtils.isEmpty(value)){
   //   boolean truncated = occurrence.isStringTruncated(value);
    //  String message = truncated ? " will be truncated" : " will not be truncated";
    //  System.out.println(value + " of length " + value.length() + message);
       // Font font = new Font("Helvetica",Font.PLAIN,12);
      Font font = new Font(occurrence.getTextviewFont(),occurrence.getTextviewStyle(),occurrence.getTextviewSize());
      Canvas c = new Canvas();
      FontMetrics fontMetrics = c.getFontMetrics(font);
      int textWidthForTheGivenFont = fontMetrics.stringWidth(value);
      System.out.println("textWidthForTheGivenFont = " + textWidthForTheGivenFont);

      boolean shouldStringBeTruncated = textWidthForTheGivenFont > occurrence.getTextviewWidth() ? true : false;
      String awtMessage = shouldStringBeTruncated ? " will be truncated" : " will not be truncated";
      System.out.println(value + " of length " + value.length() + awtMessage);




    }
  }

  private String extractIdFromTextValue (String text){
    return text.substring(8);
  }
}
