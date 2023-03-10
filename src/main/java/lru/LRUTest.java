package lru; /**
 * lru.LRU  最近最久未使用算法
 * 每次访问的数据都会放在栈顶，当访问的数据不在内存中，且栈内数据存储满了，我们就要选择移除栈底的元素，
 * 因为在栈底部的数据访问的频率是比较低的。所以要将其淘汰。
 */


public class LRUTest {
        // 用链表实现一个LRU缓存 (自己定义节点类，存储的数据类型为int)。
        public static void main(String args[]){
            ListLRU lru = new ListLRU(6);
            lru.addNode(5);
            lru.addNode(4);
            lru.addNode(3);
            lru.addNode(2);
            lru.addNode(1);
            lru.printLRU();
            lru.addNode(6);
            lru.printLRU();
            lru.addNode(3);
            lru.printLRU();
            lru.addNode(7);
            lru.printLRU();
        }
    }

