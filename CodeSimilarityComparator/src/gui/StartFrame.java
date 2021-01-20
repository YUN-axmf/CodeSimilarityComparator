package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * ��ʼ���洰����
 */
public class StartFrame extends JFrame
{
    // ��������ʱʹ��
    public static void main(String[] args)
    {
        // ����������
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

        setTitle("����������ƶȱȽ�ϵͳ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        buttonFont = new Font("����", Font.PLAIN, 18);

        content = new JLabel("��ӭʹ�ñ�ϵͳ��");
        content.setFont(new Font("����", Font.BOLD, 50));
        content.setBounds(100, 140, 440, 50);

        instructionButton = new JButton("ʹ��˵��");
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

        startButton = new JButton("��ʼʹ��");
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
