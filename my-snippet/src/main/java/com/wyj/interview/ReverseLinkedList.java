package com.wyj.interview;

import org.junit.Test;

/*
* 反转链表
* https://www.nowcoder.com/practice/75e878df47f24fdc9dc3e400ec6058ca?tpId=188&tqId=38029&rp=1&ru=%2Factivity%2Foj&qru=%2Fta%2Fjob-code-high-week%2Fquestion-ranking&tab=answerKey*/
public class ReverseLinkedList {
    public ListNode reverseList(ListNode head) {
        if (head==null)return null;

        ListNode pre = null;//当前节点的上一个节点
        ListNode next = null;//当前节点的下一个节点

        while (head!=null){
            //next记录下个要遍历的元素，防止head被赋值后断链
            next = head.next;
            //这个元素的下一个元素指向上一个
            head.next =pre;
            //让pre 、head都下移一位
            pre=head;
            head=next;

        }
        return pre;

 }

    public class ListNode {
        int val;
        ListNode next = null;

        ListNode(int val) {
            this.val = val;
        }
    }

    @Test
    public void  test(){
        //
        ListNode head =new ListNode(0);
        ListNode curr=head;
        for (int i=1;i<10;i++){
            ListNode node = new ListNode(i);
            curr.next=node;
            curr=node;
        }
        head = printList(head);

        System.out.println("反转结果：");

        ListNode newHead = reverseList(head);
        printList(newHead);


    }


    private ListNode printList(ListNode head) {
        ListNode curr = head;//防止破坏原链表
        while (curr!=null){
            System.out.print(curr.val+" ");
            curr = curr.next;
        }
        return head;
    }
}
