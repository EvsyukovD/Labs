package com.example.demo.LabUtils;

import java.util.Iterator;
import java.util.LinkedList;

public class DataQueue<T> {
    private LinkedList<T> elements = new LinkedList<>();
    private int limit = 10;

    public synchronized void setLimit(int limit) throws InterruptedException {
        while (this.elements.size() == this.limit) {
            wait();
        }
        if (elements.size() == 0) {
            notifyAll();
        }
        this.limit = limit;
    }

    public boolean isConsuming() {
        return elements.size() != limit;
    }

    public int getLimit() {
        return limit;
    }

    public synchronized void clear() {
        elements.clear();
    }

    public Iterator<T> iterator() {
        return elements.iterator();
    }


    public synchronized int size() {
        return elements.size();
    }

    public synchronized void offer(T t) throws InterruptedException {
        while (elements.size() == limit) {
            wait();
        }
        if (elements.size() == 0) {
            notifyAll();
        }
        elements.add(t);
    }

    public synchronized T poll() throws InterruptedException {
        while (elements.size() == 0) {
            wait();
        }
        if (elements.size() == limit) {
            notifyAll();
        }
        T item = this.elements.remove(0);
        return item;
    }

    public T peek() {
        return elements.getFirst();
    }
}
