package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 开始界面窗口类
 */
public class StartFrame extends JFrame
{
    // 仅供测试时使用
    public static void main(String[] args)
    {
        // 更换界面风格
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        JFrame startFrame = new StartFrame();
    }

    public static final int DEFAULT_WIDTH = 600;
    public static final int DEFAULT_HEIGHT = 400;
    private JLabel content;
    private Font buttonFont;
    private JButton instructionButton;
    private JButton startButton;

    public StartFrame()
    {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);

        setTitle("程序代码相似度比较系统");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        buttonFont = new Font("仿宋", Font.PLAIN, 18);

        content = new JLabel("欢迎使用本系统！");
        content.setFont(new Font("宋体", Font.BOLD, 50));
        content.setBounds(100, 140, 440, 50);

        instructionButton = new JButton("使用说明");
        instructionButton.setBounds(150, 300, 100, 50);
        instructionButton.setFont(buttonFont);
        instructionButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //dispose();
                new InstructionFrame();
            }
        });

        startButton = new JButton("开始使用");
        startButton.setBounds(350, 300, 100, 50);
        startButton.setFont(buttonFont);
        startButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                dispose();
                new MainFrame();
            }
        });

        add(content);
        add(instructionButton);
        add(startButton);

        setVisible(true);
    }
}
