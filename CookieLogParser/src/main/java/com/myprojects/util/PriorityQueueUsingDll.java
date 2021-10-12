package com.myprojects.util;

public class PriorityQueueUsingDll {

    public Node getHead() {
        return head;
    }

    // head of DLL
    private Node head = null;

    /**
     * Inserts newNode at head if newNode.count>=head.count
     * @param newNode
     */
    public void push(Node newNode) {
        if (head == null) {
            head = newNode;
            return;
        } else if (newNode.getCount() > head.getCount()) {
            head = newNode;
        } else if (newNode.getCount() == head.getCount()) {
            newNode.setNext(head);
            head.setPrevious(newNode);
            head = newNode;
        }

    }
}

