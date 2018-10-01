package plugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;
import com.intellij.openapi.project.Project;
import groovy.swing.impl.DefaultAction;
import org.jetbrains.annotations.NotNull;

public class ExtractStringAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        System.out.println("Action Triggered!!");
        final Editor editor = anActionEvent.getRequiredData(CommonDataKeys.EDITOR);
        EditorActionHandler actionHandler = new StringExtractionHandler();
        actionHandler.execute(editor, editor.getCaretModel().getCurrentCaret(), anActionEvent.getDataContext());
    }

    @Override
    public void update(@NotNull final AnActionEvent anActionEvent) {
        final Project project = anActionEvent.getData(CommonDataKeys.PROJECT);
        final Editor editor = anActionEvent.getData(CommonDataKeys.EDITOR);
        anActionEvent.getPresentation().setVisible((project != null && editor != null && editor.getCaretModel().getCaretCount() > 0));
    }
}
