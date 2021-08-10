package lru;

//单链表的节点类
public class Node {
    public int value=-1;
    //单链表 指向后继节点
    //这里定义了一个指向下一个节点的对象，每一个节点都包含一个值和它的下一个节点
    public Node next=null;

//    public Node(int value,Node next){
//        this.value = value;
//        this.next = next;
//    }
    public Node(int value){
        this.value = value;
    }
}
