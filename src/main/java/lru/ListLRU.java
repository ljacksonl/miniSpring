package lru;

public class ListLRU {
    //带头结点的单链表，根据题意，所有结点值不同
    private int lenLimit;
    static Node headNode = new Node(-1);//结点值-1表示这是头结点
    public ListLRU(int lenLimit){
        this.lenLimit = lenLimit;
    }

    static public int length(){
        int length = 0;
        Node scan = headNode;
        while(scan.next!=null){
            scan=scan.next;
            length++;

        }
        return  length;
    }

    //添加的逻辑：
    public void addNode(int value){
        Node newone = new Node(value);//先创建一个待加入的新结点
        Node p = headNode;

        while(p.value != newone.value&&p.next!=null){
            p=p.next;
        }
        //出循环两种可能，遍历结束没有找到，或者是找到了
        if(p.next == null){//遍历完了没有找到
            int len=ListLRU.length();
            if(len<lenLimit){ //不需要删除，直接在头部添加
                newone.next = headNode.next;
                headNode.next = newone ;

            }else{

                //先删除最后一个结点
                deleteNode(p.value);
                //再在列表头部添加
                newone.next = headNode.next;
                headNode.next = newone;
            }
        }else{
            //找到了这个结点，删除，在头部添加
            deleteNode(p.value);
            newone.next = headNode.next;
            headNode.next = newone;
        }

    }


    public boolean deleteNode(int value){
        Node p = headNode;
        Node pre = null;
        while(p.value!=value&&p.next!=null){
            pre = p;
            p = p.next;
        }
        //跳出循环有两种可能：找到要删除的结点了，或者到达末尾了
        if(p.value == value){ //说明是找到了
            pre.next = p.next;
            return true;

        }else{ //到了末尾还没有，返回异常
            return false;
        }
    }
    public void printLRU(){
        Node p = headNode;
        if(p.next == null) {
            System.out.println("LRU链表当前无结点");
        }
        while(p.next!= null){
            p = p.next;
            System.out.print(p.value+"  ");
        }
        System.out.println();
    }
}
