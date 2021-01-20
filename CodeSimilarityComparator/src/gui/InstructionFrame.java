package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 使用说明窗口类
 */
public class InstructionFrame extends JFrame
{
    public static final int DEFAULT_WIDTH = 400;
    public static final int DEFAULT_HEIGHT = 400;
    public static final String INSTRUCTION = " 对于两个Java语言的源程序代\n 码，用哈希表" +
            "的方法分别统计\n 两个程序中使用Java语言关键\n 字和用户标识符的情况，并最\n 终按定量的" +
            "计算结果，得出两\n 份程序的相似性";
    private JButton returnButton;
    private JTextArea instructionText;
    private Font buttonFont;

    public InstructionFrame()
    {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLocationRelativeTo(null);                // 居中显示
        setLayout(null);
        setResizable(false);
        setTitle("使用说明");

        buttonFont = new Font("仿宋", Font.PLAIN, 18);

        instructionText = new JTextArea();
        instructionText.setBounds(10, 10, 375, 300);
        instructionText.setEditable(false);
        instructionText.setFont(new Font("楷体", Font.BOLD,24));
        instructionText.setText("\n\n");
        instructionText.append(INSTRUCTION);
        add(instructionText);

        returnButton = new JButton("返回");
        returnButton.setBounds(150,320,100, 40);
        returnButton.setFont(buttonFont);
        returnButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                dispose();
                //new StartFrame();
            }
        });
        add(returnButton);

        setVisible(true);
    }
}