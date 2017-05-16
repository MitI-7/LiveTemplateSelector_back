package com.github.MitI_7;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.util.xmlb.XmlSerializerUtil;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import java.util.ArrayList;
import java.util.List;



@State(name = "LiveTemplateSelector",  storages = {@Storage(id = "LiveTemplateSelector", file = "LiveTemplateSelectorSetting.xml")})
public class Settings implements PersistentStateComponent<Settings> {

    public List<String> activeGroupList = new ArrayList<>();

    @Override
    public Settings getState() {
        return this;
    }

    @Override
    public void loadState(Settings state) {
        XmlSerializerUtil.copyBean(state, this);
    }
}