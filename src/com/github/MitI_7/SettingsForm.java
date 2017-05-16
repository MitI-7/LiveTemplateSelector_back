package com.github.MitI_7;

import com.intellij.codeInsight.template.impl.TemplateGroup;
import com.intellij.codeInsight.template.impl.TemplateSettings;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.util.ArrayList;
import java.util.List;



public class SettingsForm {
    public JPanel panel;
    private JList srcGroupListJList;
    private JList dstGroupListJList;
    private JButton addButton;
    private JButton removeButton;

    public SettingsForm(Settings settings) {

        // 初期値
        List<String> dstList = getDstGroupList(settings.activeGroupList);
        List<String> srcList = getSrcGroupList(dstList);
        {
            DefaultListModel model = new DefaultListModel();
            for (String name : srcList) {
                model.addElement(name);
            }
            srcGroupListJList.setModel(model);
        }

        {
            DefaultListModel model = new DefaultListModel();
            for (String name : dstList) {
                model.addElement(name);
            }
            dstGroupListJList.setModel(model);
        }

        addButton.addActionListener(event -> {
            for (String s :(List<String>)srcGroupListJList.getSelectedValuesList()) {
                ((DefaultListModel) srcGroupListJList.getModel()).removeElement(s);
                ((DefaultListModel) dstGroupListJList.getModel()).addElement(s);
            }
        });

        removeButton.addActionListener(event -> {
            for (String s : (List<String>)dstGroupListJList.getSelectedValuesList()) {
                ((DefaultListModel) dstGroupListJList.getModel()).removeElement(s);
                ((DefaultListModel) srcGroupListJList.getModel()).addElement(s);
            }
        });

        // 枠線
        srcGroupListJList.setBorder(new TitledBorder("Live Templates Group"));
        dstGroupListJList.setBorder(new TitledBorder("Active Group"));
    }


    private List<String> getDstGroupList(List<String> activeList) {
        List<String> ret = new ArrayList<>();
        for (TemplateGroup g : TemplateSettings.getInstance().getTemplateGroups()) {
            String savedGroupName = g.getName();
            if (activeList.contains(savedGroupName)) {
                ret.add(savedGroupName);
            }
        }
        return ret;
    }

    private List<String> getSrcGroupList(List<String> dstList) {
        List<String> ret = new ArrayList<>();
        for (TemplateGroup g : TemplateSettings.getInstance().getTemplateGroups()) {
            String savedGroupName = g.getName();
            if (!dstList.contains(savedGroupName)) {
                ret.add(savedGroupName);
            }
        }
        return ret;
    }

    public List<String> getDstGroupList() {
        List<String> ret = new ArrayList<>();
        for (int i = 0; i < this.dstGroupListJList.getModel().getSize(); ++i) {
            String s = (String)this.dstGroupListJList.getModel().getElementAt(i);
            ret.add(s);
        }
        return ret;
    }
}