package com.github.MitI_7

import com.intellij.codeInsight.template.TemplateManager
import com.intellij.openapi.actionSystem.{AnAction, AnActionEvent, CommonDataKeys, LangDataKeys}
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.codeInsight.template.impl.TemplateImpl
import com.intellij.codeInsight.template.impl.TemplateManagerImpl
import com.intellij.codeInsight.template.impl.TemplateSettings
import javax.swing.tree.DefaultMutableTreeNode
import java.awt.Point

import scala.collection.JavaConverters._
import scala.collection.mutable



class SelectorAction extends AnAction() {
  
  override def actionPerformed(e: AnActionEvent) {
    val project = e.getProject
    val templateManager = TemplateManager.getInstance(project)
    val templateSetting = TemplateSettings.getInstance()
    val editor = FileEditorManager.getInstance(project).getSelectedTextEditor

    // template選択画面
    val psiFile = e.getData(CommonDataKeys.PSI_FILE)
    val templates: Seq[TemplateImpl] = TemplateManagerImpl.listApplicableTemplateWithInsertingDummyIdentifier(editor, psiFile, false).asScala
    val form = new SelectLiveTemplate(makeModel(templates), templateManager, templateSetting, editor)

    val caretModel = editor.getCaretModel
    val cursorAbsoluteLocation = editor.visualPositionToXY(caretModel.getVisualPosition)
    val editorLocation = editor.getComponent.getLocationOnScreen
    val editorContentLocation = editor.getContentComponent.getLocationOnScreen
    val popupLocation = new Point(editorContentLocation.x + cursorAbsoluteLocation.x - 10,
                                   editorLocation.y + cursorAbsoluteLocation.y - editor.getScrollingModel.getVerticalScrollOffset + 13)

    form.setVisible(true)
    form.setLocation(popupLocation)
  }

    // 表示するLiveTemplateのデータを作成
  def makeModel(templateList: Seq[TemplateImpl]) : DefaultMutableTreeNode  = {
    // Template Group -> Live Templateのリストのmapを作成
    val map = mutable.Map.empty[String, mutable.Seq[String]]
    val activeGroupList = ServiceManager.getService[Settings](classOf[Settings]).activeGroupList
    templateList.foreach({ it =>
      val key = it.getKey
      val groupName = it.getGroupName
      if (activeGroupList.contains(groupName)) {
        map.update(groupName, map.getOrElse(groupName, mutable.Seq.empty[String]) :+ key)
      }
    })

    // Template GroupごとのNodeを作成しrootNodeにいれてく
    val rootNode = new DefaultMutableTreeNode("Live Template Group")
    map.foreach { case(key: String, value: Seq[String]) =>
      val groupName = key
      val templateGroup = new DefaultMutableTreeNode(groupName)
      value.foreach({ it =>
        templateGroup.add(new DefaultMutableTreeNode(it))
      })
      rootNode.add(templateGroup)
    }

    rootNode
  }
}
