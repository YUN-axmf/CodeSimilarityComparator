package entity;

import java.io.*;

/**
 * 基于符号表的拉链式哈希表类
 */
public class CodeHashMap
{
    private int listNum;            // 链表数
    private SymbolTable[] st;
    public static final String[] KEYWORDS = {"const", "goto", "public", "protected", "private",
            "class", "interface", "abstract", "implements", "extends", "new", "import", "package",
            "byte", "char", "boolean", "short", "int", "float", "long", "double", "void", "null",
            "true", "false", "if", "else", "while", "for", "switch", "case", "default", "do", "break",
            "continue", "return", "instanceof", "static", "final", "super", "this", "native",
            "strictfp", "synchronized", "transient", "volatile", "try", "finally", "throw",
            "throws", "enum", "assert"};       // java关键字

    public CodeHashMap()
    {
        this(40);
    }

    public CodeHashMap(int listNum)
    {
        this.listNum = listNum;
        st = new SymbolTable[listNum];
        for (int i = 0; i < listNum; i++)
            st[i] = new SymbolTable();
        putKeywords();
    }

    public CodeHashMap(String kind)
    {
        this.listNum = 100;
        st = new SymbolTable[listNum];
        for (int i = 0; i < listNum; i++)
            st[i] = new SymbolTable();
    }

    private int hash(String key)
    {
        return (key.hashCode() & 0x7fffffff) % listNum;
    }

    public int getCount(String key)
    {
        return st[hash(key)].getCount(key);
    }

    public void put(String key, int count)
    {
        st[hash(key)].put(key, count);
    }

    public SymbolTable[] getSt()
    {
        return st;
    }

    public void show()
    {
        for (int i = 0; i < st.length; i++)
        {
            for (Node x = st[i].getHead(); x != null; x = x.next)
            {
                System.out.println(x.key + "-" + x.count);
            }
        }
    }

    private void putKeywords()
    {
        for (String s : KEYWORDS)
            put(s, 0);
    }

    /**
     * 判断哈希表是否包含s
     */
    public boolean contains(String s)
    {
        for (int i = 0; i < st.length; i++)
        {
            for (Node x = st[i].getHead(); x != null; x = x.next)
            {
                if (x.key.equals(s))
                    return true;
            }
        }
        return false;
    }

    /**
     * 统计Java文件中各关键词出现的频率
     */
    public void wordsStatistics(File file)
    {
        initialize();
        BufferedReader reader = null;
        try
        {
            reader = new BufferedReader(new FileReader(file));
            StringBuffer buffer = new StringBuffer();
            String line = null;
            while ((line = reader.readLine()) != null)
                buffer.append(line);
            String[] strings = buffer.toString().split("\\W+");
            for (String s : strings)
            {
                if (isIdentifier(s))
                {
                    int x = getCount(s);
                    put(s, ++x);
                }
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                reader.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * 统计Java文件中各用户标识符出现的频率
     */
    public void identifierStatistics(File file)
    {
        clear();
        BufferedReader reader = null;
        try
        {
            reader = new BufferedReader(new FileReader(file));
            StringBuffer buffer = new StringBuffer();
            String line = null;
            while ((line = reader.readLine()) != null)
                buffer.append(line);
            String[] strings = buffer.toString().split("\\W+");
            for (String s : strings)
            {
                if (!isIdentifier(s) && Character.isAlphabetic(s.toCharArray()[0]))
                {
                    if (!contains(s))
                    {
                        put(s, 1);
                    }
                    else
                    {
                        int x = getCount(s);
                        put(s, ++x);
                    }
                }
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                reader.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * 初始化哈希表 将频率全部置零
     */
    public void initialize()
    {
        for (int i = 0; i < st.length; i++)
        {
            for (Node x = st[i].getHead(); x != null; x = x.next)
            {
                x.count = 0;
            }
        }
    }

    /**
     * 将哈希表置空
     */
    public void clear()
    {
        for (int i = 0; i < st.length; i++)
        {
            st[i] = new SymbolTable();
        }
    }

    /**
     * 比较两哈希表关键字出现频率
     */
    public double compareCode(CodeHashMap map)
    {
        double s = 0;
        for (int i = 0; i < st.length; i++)
        {
            for (Node x = st[i].getHead(); x != null; x = x.next)
            {
                for (Node y = map.st[i].getHead(); y != null; y = y.next)
                    if (x.key.equals(y.key) && (x.count != y.count))
                    {
                        int count1 = x.count;
                        int count2 = y.count;
                        s += (count1 - count2) * (count1 - count2);
                    }
            }
        }
        s = Math.sqrt(s);
        if (s == 0)
            s = 1;
        else
            s = 1 / s;
        return s;
    }

    /**
     * 比较两哈希表用户标识符出现频率
     */
    public double compareIIdentifier(CodeHashMap map)
    {
        double s = 0;
        for (int i = 0; i < this.st.length; i++)
        {
            for (Node x = st[i].getHead(); x != null; x = x.next)
            {
                if (map.contains(x.key))
                {
                    int count = getCount(x.key) - map.getCount(x.key);
                    s += count * count;
                }
                else
                {
                    int count = getCount(x.key);
                    s += count * count;
                }
            }
        }
        for (int i = 0; i < map.st.length; i++)
        {
            for (Node x = map.st[i].getHead(); x != null; x = x.next)
            {
                if (!this.contains(x.key))
                {
                    int count = map.getCount(x.key);
                    s += count * count;
                }
            }
        }
        s = Math.sqrt(s);
        if (s == 0)
            s = 1;
        else
            s = 1 / s;
        return s;
    }

    public int length()
    {
        int count = 0;
        for (int i = 0; i < st.length; i++)
        {
            for (Node x = st[i].getHead(); x != null; x = x.next)
            {
                count++;
            }
        }
        return count;
    }

    /**
     * 判断字符串s是否为Java关键字
     */
    public static boolean isIdentifier(String s)
    {
        for (int i = 0; i < KEYWORDS.length; i++)
        {
            if (s.equals(KEYWORDS[i]))
                return true;
        }
        return false;
    }
}
