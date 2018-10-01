package plugin.extractor;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.util.List;

class ReferenceReplacer {
  void replaceHardCodedValues(Document document, List<StringOccurrence> stringOccurrences)
      throws XPathExpressionException {
    for (StringOccurrence stringOccurrence : stringOccurrences) {
      Element n = findElementByAndroidId(document, stringOccurrence.getId());
      if (n == null) continue;
      n.setAttribute("android:" + stringOccurrence.getAttribute(), stringOccurrence.getValue());
    }
  }

  private Element findElementByAndroidId(Document document, String id)
      throws XPathExpressionException {
    String expression = String.format("//*[@*[name()='android:id'] = '%s']", "@+id/" + id);
    XPathExpression expr = XPathFactory.newInstance().newXPath().compile(expression);

    return (Element) expr.evaluate(document, XPathConstants.NODE);
  }
}
