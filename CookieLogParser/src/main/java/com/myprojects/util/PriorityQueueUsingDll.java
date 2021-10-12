package com.myprojects.util;

public class PriorityQueueUsingDll {

    public Node getHead() {
        return head;
    }

    // head of DLL
    private Node head = null;

    public void push(Node newNode) {
        if (head == null) {
            head = newNode;
            return;
        }

        Node temp = head, parent = null;
        while (temp != null && temp.getCount() >= newNode.getCount()) {
            parent = temp;
            temp = temp.getNext();
        }
        // Case 1 : All the nodes are having counts less than 'count'
        if (parent == null) {
            // insert the new node at the beginning of linked list
            newNode.setNext(head);
            head.setPrevious(newNode);
            head = newNode;
        }
        // Case 2 : All the nodes are having count greater than 'count'
        else if (temp == null) {
            // insert the node at the end of the linked list
            parent.setNext(newNode);
            newNode.setPrevious(parent);
        }
        // Case 3 : Some nodes have count higher than 'count' and
        // some have count lower than 'count'
        else {
            // insert the new node before the first node having count
            // less than 'count'
            parent.setNext(newNode);
            newNode.setPrevious(parent);
            newNode.setNext(temp);
            temp.setPrevious(newNode);
        }

    }

    public String peek() {
        // if head is not null, return the element at first position
        if (head != null) {
            return head.getCookieId();
        }
        return null;
    }

    public String pop() {
        // if head is not null, delete the element at first position and return its value
        if (head != null) {
            String curr = head.getCookieId();
            head = head.getNext();
            if (head != null) {
                head.setPrevious(null);
            }
            return curr;
        }
        return null;
    }

    public void removeNode(Node node) {
        /**
         * Remove an existing node from the linked list.
         */
        Node prev = node.getPrevious();
        Node next = node.getNext();
        if(prev == null && next == null)
        {
            head = null;
        }
        else if (prev == null && next!=null) {
            next.setPrevious(null);
            head = next;
        } else if (next == null && prev!=null) {
            prev.setNext(null);
        } else {
            prev.setNext(next);
            next.setPrevious(prev);
        }
    }
}

