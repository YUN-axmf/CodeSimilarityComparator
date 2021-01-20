package entity;

public class Node
{
    String key;     // 关键字名称
    int count;      // 关键字计数
    Node next;

    public Node(String key, int count, Node next)
    {
        this.key = key;
        this.count = count;
        this.next = next;
    }

    public String getKey()
    {
        return key;
    }

    public int getCount()
    {
        return count;
    }

    public Node getNext()
    {
        return next;
    }
}