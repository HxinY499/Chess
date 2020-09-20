/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JTextField;

/**
 *
 * @author lenovo
 */
public class MyFocusListener implements FocusListener {

    String hint;
    JTextField text;

    public MyFocusListener(String info, JTextField jtf) {
        this.hint = info;
        this.text = jtf;
    }

    @Override
    public void focusGained(FocusEvent e) {//获得焦点的时候,清空提示文字
        String temp = text.getText();
        if (temp.equals(hint)) {
            text.setText("");
        }
    }

    @Override
    public void focusLost(FocusEvent e) {//失去焦点的时候,判断如果为空,就显示提示文字
        String temp = text.getText();
        if (temp.equals("")) {
            text.setText(hint);
        }
    }

}
