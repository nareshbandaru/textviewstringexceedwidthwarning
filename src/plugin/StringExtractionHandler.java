package plugin;


import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorWriteActionHandler;
import org.xml.sax.SAXException;
import plugin.extractor.AndroidProjectFactory;
import plugin.extractor.LayoutStringExtractor;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;



public class StringExtractionHandler extends EditorWriteActionHandler {

  private final LayoutStringExtractor layoutStringExtractor;

  public StringExtractionHandler() {
    System.out.println("Inside StringExtractionHandler.......");
    layoutStringExtractor = new LayoutStringExtractor(new AndroidProjectFactory());
  }

  @Override
  public void doExecute(Editor editor, Caret contextCaret, DataContext dataContext) {
//    final Project project = anActionEvent.getData(CommonDataKeys.PROJECT);
//    final Editor editor = anActionEvent.getData(CommonDataKeys.EDITOR);
    System.out.println("Inside extractStringsFromLayouts");
    String projectPath = dataContext.getData(CommonDataKeys.PROJECT).getBasePath() + "/app";
    System.out.println("Inside extractStringsFromLayouts"+projectPath);
        try {
      layoutStringExtractor.extract(projectPath);
    } catch (TransformerException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ParserConfigurationException e) {
      e.printStackTrace();
    } catch (SAXException e) {
      e.printStackTrace();
    } catch (XPathExpressionException e) {
      e.printStackTrace();
    }
  }
}