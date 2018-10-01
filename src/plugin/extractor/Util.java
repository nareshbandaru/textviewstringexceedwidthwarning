package plugin.extractor;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;

class Util {
  static Document createEmptyDocument() throws ParserConfigurationException {
    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
    return documentBuilder.newDocument();
  }

  static void assertPathIsDirectory(File path) {
    if (!path.isDirectory()) {
      throw new IllegalArgumentException(String.format("'%s' is no directory", path));
    }
  }
}
