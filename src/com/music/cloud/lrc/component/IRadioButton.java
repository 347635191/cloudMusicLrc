package com.music.cloud.lrc.component;

import javax.swing.*;
import java.awt.*;

public class IRadioButton extends JRadioButton {
    public IRadioButton(String text, int x, int y, int w, int h, boolean isDefault) {
        super(text);
        setFont(new Font("黑体", Font.PLAIN, 16));
        setSelected(isDefault);
        setBorderPainted(false);
        setBackground(Color.WHITE);
        setBounds(x, y, w, h);
    }
}
