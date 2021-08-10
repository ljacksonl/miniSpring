package lru;

/**
 * @author Lu
 */


public class ListLRU {

    //带头结点的单链表，所有结点值不同
    private int lenLimit;


    //结点值-1表示这是头结点
    static Node headNode = new Node(-1);

    public ListLRU(int lenLimit) {
        this.lenLimit = lenLimit;
    }

    // 返回链表长度
    static public int length() {
        int length = 0;
        Node scan = headNode;
        while (scan.next != null) {
            scan = scan.next;
            length++;

        }
        return length;
    }

    // 在链表头部添加节点 x


    public void addNode(int value) {
        //先创建一个待加入的新结点
        Node newone = new Node(value);
        //p为单链表
        Node p = headNode;

        //1.遍历链表查看数据是否已经存在
        while (p.value != newone.value && p.next != null) {
            p = p.next;
        }
        //出循环两种可能，遍历结束没有找到，或者是找到了
        /**
         * 如果数据不存在
         * a.缓存未满 直接在表头添加
         * b.缓存已满 删除表尾节点 在表头添加
         */
        //遍历完了没有找到
        if (p.next == null) {
            int len = ListLRU.length();
            if (len < lenLimit) {
                //不需要删除，直接在头部添加
                newone.next = headNode.next;
                headNode.next = newone;

            } else {
                //先删除最后一个结点
                deleteNode(p.value);
                //再在列表头部添加
                newone.next = headNode.next;
                headNode.next = newone;
            }
        } else {
            //2.如果数据已经存在 删除当前节点，在表头添加
            deleteNode(p.value);
            newone.next = headNode.next;
            headNode.next = newone;
        }

    }

    // 删除链表中的 x 节点（x 一定存在）
    public boolean deleteNode(int value) {
        Node p = headNode;
        Node pre = null;
        while (p.value != value && p.next != null) {
            pre = p;
            p = p.next;
        }
        //跳出循环有两种可能：找到要删除的结点了，或者到达末尾了
        //说明是找到了
        if (p.value == value) {
            pre.next = p.next;
            return true;

        } else { //到了末尾还没有，返回异常
            return false;
        }
    }

    //测试输出链表值
    public void printLRU() {
        Node p = headNode;
        if (p.next == null) {
            System.out.println("LRU链表当前无结点");
        }
        while (p.next != null) {
            p = p.next;
            System.out.print(p.value + "  ");
        }
        System.out.println();
    }
}
