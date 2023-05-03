package com.music.cloud.lrc.component;

import javax.swing.*;
import java.awt.*;

public class ILabel extends JLabel {
    public ILabel(String text, int size, Color color, int x, int y, int w, int h) {
        super(text);
        setFont(new Font("黑体", Font.PLAIN, size));
        setForeground(color);
        setBounds(x, y, w, h);
    }
}
