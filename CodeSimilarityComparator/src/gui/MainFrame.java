package gui;

import entity.CodeHashMap;
import entity.Node;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * 系统主窗口类
 */
public class MainFrame extends JFrame
{
    public static final int DEFAULT_WIDTH = 600;
    public static final int DEFAULT_HEIGHT = 460;
    private JButton fileButton1;
    private JButton fileButton2;
    private JTextField fileText1;
    private JTextField fileText2;
    private JButton confirmButton;
    private JButton exitButton;
    private JTextArea statistics1;
    private JTextArea statistics2;
    private JTextArea conclusion;
    private File file1;
    private File file2;
    private Font buttonFont1;
    private Font buttonFont2;
    private Font textFont;
    private CodeHashMap map1 = new CodeHashMap();
    private CodeHashMap map2 = new CodeHashMap();
    private CodeHashMap identifierMap1 = new CodeHashMap("identifier");
    private CodeHashMap identifierMap2 = new CodeHashMap("identifier");

    public MainFrame()
    {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLocationRelativeTo(null);                // 居中显示
        setLayout(null);
        setResizable(false);

        setTitle("程序代码相似度比较系统");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        buttonFont1 = new Font("仿宋", Font.PLAIN, 18);
        buttonFont2 = new Font("仿宋", Font.PLAIN, 14);
        textFont = new Font("宋体", Font.PLAIN, 18);

        fileButton1 = new JButton("选择文件1");
        fileButton1.setBounds(10, 10, 100, 30);
        fileButton1.addActionListener(new FileAction());
        fileButton1.setFont(buttonFont2);
        add(fileButton1);

        fileButton2 = new JButton("选择文件2");
        fileButton2.setBounds(10, 50, 100, 30);
        fileButton2.addActionListener(new FileAction());
        fileButton2.setFont(buttonFont2);
        add(fileButton2);

        confirmButton = new JButton("开始比较");
        confirmButton.setBounds(460, 10, 100, 70);
        confirmButton.addActionListener(new CompareAction());
        confirmButton.setFont(buttonFont1);
        add(confirmButton);

        exitButton = new JButton("退出系统");
        exitButton.setBounds(250,375,100, 40);
        exitButton.setFont(buttonFont1);
        exitButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JOptionPane.showMessageDialog(null, "感谢您的使用！");
                System.exit(0);
            }
        });
        add(exitButton);

        fileText1 = new JTextField();
        fileText1.setBounds(130, 10, 300, 30);
        fileText1.setEditable(false);
        fileText1.setFont(textFont);
        add(fileText1);

        fileText2 = new JTextField();
        fileText2.setBounds(130, 50, 300, 30);
        fileText2.setEditable(false);
        fileText2.setFont(textFont);
        add(fileText2);

        statistics1 = new JTextArea();
        statistics1.setEditable(false);
        statistics1.setFont(textFont);
        JScrollPane scrollPane1 = new JScrollPane(statistics1);
        scrollPane1.setBounds(20, 100, 250, 180);
        scrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane1);

        statistics2 = new JTextArea();
        statistics2.setEditable(false);
        statistics2.setFont(textFont);
        JScrollPane scrollPane2 = new JScrollPane(statistics2);
        scrollPane2.setBounds(300, 100, 250, 180);
        add(scrollPane2);
        scrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        conclusion = new JTextArea();
        conclusion.setBounds(20, 290, 540, 80);
        conclusion.setEditable(false);
        conclusion.setFont(textFont);
        add(conclusion);

        setVisible(true);
    }

    public String makeConclusion()
    {
        String result = null;
        double s1 = map1.compareCode(map2);
        double s2 = identifierMap1.compareIIdentifier(identifierMap2);
        if (s1 == 1 && s2 == 1)
            result = "比较的为同一文件";
        else if (s1 == 1 && s2 != 1)
            result = "一定是抄的！";
        else if (s1 > 0.5 || s2 > 0.1)
            result = "两程序高度相似！";
        else if (s1 > 0.2 || s2 > 0.05)
            result = "两程序部分相似！";
        else
            result = "两程序相似度低";
        return result;
    }

    private class FileAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File("C:\\Users\\96215\\Desktop\\"));
            FileNameExtensionFilter filter = new FileNameExtensionFilter("JAVA", "java");
            chooser.setFileFilter(filter);
            int returnVal = chooser.showOpenDialog(MainFrame.this);
            if (!chooser.getSelectedFile().getName().endsWith(".java"))
            {
                JOptionPane.showMessageDialog(MainFrame.this,
                        "请选择JAVA文件！", "错误", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if(returnVal == JFileChooser.APPROVE_OPTION)
            {
                if (e.getActionCommand() == "选择文件1")
                {
                    file1 = chooser.getSelectedFile();
                    map1.wordsStatistics(file1);
                    identifierMap1.identifierStatistics(file1);
                    statistics1.setText("关键字频率如下:\n");
                    for (int i = 0; i < map1.getSt().length; i++)
                    {
                        for (Node x = map1.getSt()[i].getHead(); x != null; x = x.getNext())
                        {
                            if (x.getCount() != 0)
                                statistics1.append("    " + x.getKey() + " : " + x.getCount() + "\n");
                        }
                    }
                    statistics1.append("\n************************\n");
                    statistics1.append("用户标识符频率如下:\n");
                    for (int i = 0; i < identifierMap1.getSt().length; i++)
                    {
                        for (Node x = identifierMap1.getSt()[i].getHead(); x != null; x = x.getNext())
                        {
                            if (x.getCount() != 0)
                                statistics1.append("    " + x.getKey() + " : " + x.getCount() + "\n");
                        }
                    }
                    fileText1.setText(file1.getName());
                }
                else if (e.getActionCommand() == "选择文件2")
                {
                    file2 = chooser.getSelectedFile();
                    map2.wordsStatistics(file2);
                    identifierMap2.identifierStatistics(file2);
                    statistics2.setText("关键字频率如下:\n");
                    for (int i = 0; i < map2.getSt().length; i++)
                    {
                        for (Node x = map2.getSt()[i].getHead(); x != null; x = x.getNext())
                        {
                            if (x.getCount() != 0)
                                statistics2.append("    " + x.getKey() + " : " + x.getCount() + "\n");
                        }
                    }
                    statistics2.append("\n************************\n");
                    statistics2.append("用户标识符频率如下:\n");
                    for (int i = 0; i < identifierMap2.getSt().length; i++)
                    {
                        for (Node x = identifierMap2.getSt()[i].getHead(); x != null; x = x.getNext())
                        {
                            if (x.getCount() != 0)
                                statistics2.append("    " + x.getKey() + " : " + x.getCount() + "\n");
                        }
                    }
                    fileText2.setText(file2.getName());
                }
            }
        }
    }

    private class CompareAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (file1 != null && file2 != null)
            {
                double s1 = map1.compareCode(map2);
                double s2 = identifierMap1.compareIIdentifier(identifierMap2);
                conclusion.setText("两程序的关键字相似度值是: " + String.valueOf(s1) + "\n");
                conclusion.append("两程序的用户标识符相似度值是: " + String.valueOf(s2) + "\n");
                conclusion.append(makeConclusion());
            }
            else
                JOptionPane.showMessageDialog(MainFrame.this,
                        "请选择要比较的文件！", "错误", JOptionPane.WARNING_MESSAGE);
        }
    }
}
