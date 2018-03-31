/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.convertdrawing.capture.ui;

import java.awt.Choice;
import java.awt.GridBagConstraints;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author aswin.vijayakumar
 */
public class ComboControl extends JComponent {
    
    public JTextField newP;
    public Choice sF;
    public JTextField newS;
    public JTextField text;
    
    public ComboControl(JTextField text, Choice select, JTextField textSuffix) throws Exception
    {
        try {
            this.newP = text;
            this.sF = select;
            this.newS = textSuffix;
        } catch(Exception exception) {
            throw exception;
        }
    }
    
    public ComboControl(JTextField text) throws Exception
    {
        try {
            this.text = text;
        } catch(Exception exception) {
            throw exception;
        }
    }
    
    public static ComboControl addComponents(ComboControl control)
    {
        GridBagConstraints gc = new GridBagConstraints();
        
        gc.anchor = GridBagConstraints.LINE_START;
        control.add(control.newP, gc);
        control.add(control.sF, gc);
        control.add(control.newS, gc);
        return control;
    }
    
    public static ComboControl addComponent(ComboControl control)
    {
        GridBagConstraints gc = new GridBagConstraints();
        gc.anchor = GridBagConstraints.LINE_START;
        control.add(control.text, gc);
        return control;
    }
    
    /**
     * 
     * @return String
     */
    public String getText()
    {
        String[] text = new String[10];
        text[text.length - 1] = newP.getText() + " " + sF.getSelectedItem() + " " + newS.getText();
        return String.join(" ", text);
    }
}
