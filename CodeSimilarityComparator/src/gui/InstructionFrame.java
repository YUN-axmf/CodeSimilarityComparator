package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * ʹ��˵��������
 */
public class InstructionFrame extends JFrame
{
    public static final int DEFAULT_WIDTH = 400;
    public static final int DEFAULT_HEIGHT = 400;
    public static final String INSTRUCTION = " ��������Java���Ե�Դ�����\n �룬�ù�ϣ��" +
            "�ķ����ֱ�ͳ��\n ����������ʹ��Java���Թؼ�\n �ֺ��û���ʶ�������������\n �հ�������" +
            "���������ó���\n �ݳ����������";
    private JButton returnButton;
    private JTextArea instructionText;
    private Font buttonFont;

    public InstructionFrame()
    {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLocationRelativeTo(null);                // ������ʾ
        setLayout(null);
        setResizable(false);
        setTitle("ʹ��˵��");

        buttonFont = new Font("����", Font.PLAIN, 18);

        instructionText = new JTextArea();
        instructionText.setBounds(10, 10, 375, 300);
        instructionText.setEditable(false);
        instructionText.setFont(new Font("����", Font.BOLD,24));
        instructionText.setText("\n\n");
        instructionText.append(INSTRUCTION);
        add(instructionText);

        returnButton = new JButton("����");
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