package com.github.MitI_7

import com.intellij.openapi.components.ApplicationComponent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.options.Configurable
import javax.swing.JComponent



class LiveTemplateSelector extends Configurable with ApplicationComponent {
  val settings: Settings = ServiceManager.getService[Settings](classOf[Settings])
  var settingsForm: SettingsForm = new SettingsForm(settings)

  override def initComponent() {}

  override def disposeComponent() {}

  override def getComponentName: String = {
    "LiveTemplateSelector"
  }

  /*
  * Configurable
  */
  override def getDisplayName: String = {
    "LiveTemplateSelector"
  }

  override def createComponent(): JComponent = {
    if (settingsForm == null) {
      this.settingsForm = new SettingsForm(settings)
    }
    this.settingsForm.panel
  }

  override def apply() {
    if (settingsForm == null) {
      this.settingsForm = new SettingsForm(settings)
    }
    settings.activeGroupList = settingsForm.getDstGroupList
  }

  // Cancel button
  override def reset() {
  }

  override def isModified: Boolean = {
    if (this.settingsForm == null) {
      this.settingsForm = new SettingsForm(this.settings)
    }
    val activeGroupList = settings.activeGroupList

    if (activeGroupList.size != settingsForm.getDstGroupList.size) {
      return true
    }
    settingsForm.getDstGroupList.forEach({ s =>
      if (activeGroupList.contains(s)) {
        return true
      }
    })
    false
  }

  // user closes the form
  override def disposeUIResources() {
    this.settingsForm = null
  }

  override def getHelpTopic: String = {
    "plugins.LiveTemplateSelector"
  }
}