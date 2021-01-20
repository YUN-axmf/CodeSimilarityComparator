package entity;

/**
 * 基于单链表的符号表类
 */
public class SymbolTable
{
    private Node head;

    public Node getHead()
    {
        return head;
    }

    public int getCount(String key)
    {
        for (Node x = head; x != null; x = x.next)
            if (key.equals(x.key))
                return x.count;
        return 0;
    }

    public void put(String key, int count)
    {
        for (Node x = head; x != null; x = x.next)
            if (key.equals(x.key))
            {
                x.count = count;
                return;
            }
        head = new Node(key, count, head);
    }
}
