package com.music.cloud.lrc.view;

import com.music.cloud.lrc.component.IButton;
import com.music.cloud.lrc.component.ILabel;
import com.music.cloud.lrc.component.IRadioButton;
import com.music.cloud.lrc.component.IText;
import com.music.cloud.lrc.constant.FileName;
import com.music.cloud.lrc.constant.Language;
import com.music.cloud.lrc.util.LogUtil;
import com.music.cloud.lrc.util.LrcUtil;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;
import java.util.regex.Pattern;

public class MainFrame extends JFrame {
    private final JLabel x = new ILabel("音乐编号", 16, Color.BLACK, 58, 45, 120, 50);

    private final JTextField y = new IText(170, 55, 160, 30);

    private final JRadioButton a = new IRadioButton("主语言", 50, 120, 80, 40, false);

    private final JRadioButton b = new IRadioButton("副语言", 150, 120, 80, 40, false);

    private final JRadioButton c = new IRadioButton("双语", 250, 120, 80, 40, true);

    private final JRadioButton e = new IRadioButton("音乐编号", 100, 190, 100, 40, false);

    private final JRadioButton f = new IRadioButton("歌曲名", 200, 190, 80, 40, true);

    private final ButtonGroup group = new ButtonGroup();

    private final ButtonGroup nameGroup = new ButtonGroup();

    private final JButton button = new IButton("下载", 158, 335, 70, 40);

    private final JLabel label = new ILabel("多个音乐编号请用英文逗号隔开", 14, new Color(62, 125, 62), 148, 16, 280, 40);

    private final JLabel label1 = new ILabel("歌词文件和日志生成在D盘的lrc目录中", 16, new Color(62, 125, 62), 58, 270, 280, 40);

    public volatile static MainFrame mainFrame;

    private MainFrame() throws HeadlessException {
        setTitle("歌词生成");
        setSize(400, 440);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        setFocusable(true);
        getContentPane().setBackground(new Color(255, 255, 255));
        loadComponent();
        try {
            setIconImage(ImageIO.read(Objects.requireNonNull(MainFrame.class.getClassLoader().getResourceAsStream("azurlane.jpg"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void loadComponent() {
        Container contentPane = getContentPane();
        contentPane.add(y);
        contentPane.add(x);
        group.add(a);
        group.add(b);
        group.add(c);
        nameGroup.add(e);
        nameGroup.add(f);
        contentPane.add(a);
        contentPane.add(b);
        contentPane.add(c);
        contentPane.add(e);
        contentPane.add(f);
        contentPane.add(button);
        contentPane.add(label);
        contentPane.add(label1);
    }

    public void makeLrc() {
        disabledComponent();
        String text = y.getText();
        boolean valid = Pattern.matches("^[\\d,]+$", text);
        if (valid) {
            LogUtil.log("start.........................");
            LogUtil.log("本次待处理的音乐编号: " + text);
            String[] textArray = text.split(",");
            for (String id : textArray) {
                if (id.equals("")) {
                    continue;
                }
                Language language = Language.DOUBLE;
                if (a.isSelected()) {
                    language = Language.MAIN;
                }
                if (b.isSelected()) {
                    language = Language.SUB;
                }
                FileName fileName = FileName.MUSIC_NAME;
                if(e.isSelected()){
                    fileName = FileName.MUSIC_ID;
                }
                boolean isContinue = LrcUtil.makeLrc(id, language, fileName);
                if (!isContinue) {
                    JOptionPane.showMessageDialog(null, "请检查您的网络");
                    break;
                }
            }
            LogUtil.log("end...........................\n\n");
        } else {
            JOptionPane.showMessageDialog(null, "音乐编号格式不正确,请检查");
        }
        enabledComponent();
    }

    /**
     * 点击下载之后让按钮和单选，不可再次点击,
     */
    private void disabledComponent() {
        button.setEnabled(false);
        a.setEnabled(false);
        b.setEnabled(false);
        c.setEnabled(false);
        e.setEnabled(false);
        f.setEnabled(false);
    }

    /**
     * 下载完成之后激活失效的组件
     */
    private void enabledComponent() {
        button.setEnabled(true);
        a.setEnabled(true);
        b.setEnabled(true);
        c.setEnabled(true);
        e.setEnabled(true);
        f.setEnabled(true);
    }

    public static void main(String[] args) {
        mainFrame = new MainFrame();
    }
}
