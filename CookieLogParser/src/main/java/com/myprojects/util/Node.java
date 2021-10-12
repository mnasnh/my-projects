package com.myprojects.util;

public class Node {
    private String cookieId;
    private int count;
    private Node next;
    private Node previous;

    public Node(String cookieId, int count) {
        this.cookieId = cookieId;
        this.count = count;
        this.next = null;
    }

    public Node() {

    }

    public void setCookieId(String cookieId) {
        this.cookieId = cookieId;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public void setPrevious(Node previous) {
        this.previous = previous;
    }

    public String getCookieId() {
        return cookieId;
    }

    public int getCount() {
        return count;
    }

    public Node getNext() {
        return next;
    }

    public Node getPrevious() {
        return previous;
    }
}
