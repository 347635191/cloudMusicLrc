package com.music.cloud.lrc.component;

import com.music.cloud.lrc.view.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class IButton extends JButton {
    private MainFrame mainFrame;

    public IButton(String text, int x, int y, int w, int h) {
        super(text);
        setBounds(x, y, w, h);
        setFont(new Font("黑体", Font.PLAIN, 16));
        setBackground(new Color(255, 255, 255));
        setForeground(new Color(51, 51, 51));
        setFocusPainted(false);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    download();
                }
            }
        });
    }

    private void download() {
        loadMainFrame();
        mainFrame.makeLrc();
    }

    private void loadMainFrame(){
        if(mainFrame == null){
            mainFrame = MainFrame.mainFrame;
        }
    }
}
