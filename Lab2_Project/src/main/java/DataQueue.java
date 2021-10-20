import javax.swing.*;
import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.LinkedList;

public class DataQueue<T> {
    private LinkedList<T> elements = new LinkedList<>();
    private int limit = 10;
    private volatile boolean dispatchWork = false;
    public synchronized void setLimit(int limit){
       this.limit = limit;
    }

    public synchronized boolean signal() throws InterruptedException{
        while(dispatchWork){
           wait();
        }
        if(!dispatchWork){
            notifyAll();
        }
        return dispatchWork;
    }

    public Iterator<T> iterator() {
        return elements.iterator();
    }


    public int size() {
        return elements.size();
    }

    public synchronized void offer(T t) throws InterruptedException {
        while(elements.size() == limit){
            wait();
        }
        if(elements.size() == 0){
            notifyAll();
        }
        elements.add(t);
    }

    public synchronized T poll() throws InterruptedException{
        while(elements.size() == 0){
            wait();
        }
        if(elements.size() == limit){
            notifyAll();
        }
        T item = this.elements.remove(0);
        return item;
    }
    public T peek() {
        return elements.getFirst();
    }
}
