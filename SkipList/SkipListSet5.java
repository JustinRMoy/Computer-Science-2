package com.company;

/*Justin R. Moy, COP 3503*/

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class  SkipListSet<T extends Comparable<T>> implements SortedSet<T> {

    private int numNodes;
    private int maxHeight;          //int variables
    private boolean extraHeight = false;

    private int nodeHeight(){
        int h = 1;
        Random rand = new Random();
        int num;
        boolean go = true;

        while(go && h < maxHeight){
            num = rand.nextInt();
            if((num % 2) ==0){
                h++;
            }else{
                go = false;
            }
        }

        return h;

    }

    private class SkipNode{
        T key;
        int height;
        ArrayList<SkipNode> next;
        SkipNode prev;

        private SkipNode(T key){
            this.key = key;
            height = nodeHeight();
            next = new ArrayList<SkipNode>(Collections.nCopies(height, null));
            prev = null;
        }

        public void changeHeight() {
            height = nodeHeight();
        }

        public int getHeight(){
            return height;
        }

    }

    private SkipNode head;
    private SkipNode tail;              //node variables
    private ArrayList<SkipNode> trace; // will be used to keep track of the path taken when searching a val in the list
    private SkipNode trail; //this will keep track of what iterator is pointing to

    private SkipNode createNode(T key){
        return new SkipNode(key);
    }

    private void deleteNode(SkipNode node){

        node.next = null;
        node = null;

    }


    public SkipListSet(){

        head = createNode(null);
        maxHeight = 1;
        numNodes = 0;
        trail = head;

    }

    public  SkipListSet(Collection o){
        try {
            if(o == null) throw new Exception("NullPointerException");

            Iterator it = o.iterator();
            head = createNode(null);
            maxHeight = 1;
            numNodes = 0;
            for (int i = 0; i < o.size(); i++) {
                add((T) it.next());
            }
            trail = head;
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private boolean goingDown = false;
    private int powerOf2 = 1;

    private boolean isBalanced(){ //more efficient balance check function
        if(goingDown){
            if(numNodes == 0) return true;
            if(numNodes < (powerOf2 >> 1)+ 1){
                powerOf2 = powerOf2 >> 1;
                return false;
            }
        }else {

            if (numNodes == 1)
                return true;


            if (numNodes > powerOf2) {
                powerOf2 = powerOf2 << 1;
                return false;
            }
        }
        return true;
    }

    private void changeHeadheight(){
        while(head.next.size() > maxHeight)
            head.next.remove(head.next.size()-1);
        while(head.next.size() < maxHeight)
            head.next.add(null);
    }

    private boolean noBalAdd(T t){
        int s = maxHeight-1;
        SkipNode tracker = head; //used to iterate through list items
        trace = new ArrayList<SkipNode>(Collections.nCopies(maxHeight, null)); //use trace to keep track of the path the search is taking

        while (s >= 0){
            while (tracker.next.get(s) != null && tracker.next.get(s).key.compareTo(t) < 0){  //keep moving right until null or val is >= to t
                tracker = tracker.next.get(s);
            }

            if(tracker.next.get(s) != null) {
                if (tracker.next.get(s).key.compareTo(t) == 0)               //if the value is in the list then no need to add it
                    return false;
            }

            trace.set(s, tracker);
            s--; //go down one layer
        }

        SkipNode newNode = createNode(t);
        numNodes++;

        if(trace.get(0).next.get(0) == null)  //use to keep track of last value in list
            tail = newNode;

        for(int i = 0; i < newNode.next.size(); i++){
            newNode.next.set(i, trace.get(i).next.get(i)); /*have new node's pointers point to what the prev. pointers (that are
                                                of equal or less height) are pointing to*/
            trace.get(i).next.set(i, newNode);
        }

        return true;
    }


    @Override
    public boolean add(T t) {

        boolean go = false;
        try {
            if(t == null) throw new Exception("NullPointerException");

            go = noBalAdd(t);

            if ((!isBalanced() || numNodes == 2 ) && go) { /*Based on how it checks powers of two it won't rebalance when
             * there are 2 nodes but I don't want it to rebalance if it fails
             * to add*/
                maxHeight++;
                changeHeadheight();
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        return go;
    }

    private boolean noBalRemove(Object o){
        trace = new ArrayList<SkipNode>(Collections.nCopies(maxHeight, null));
        T O = (T)o;

        int s = maxHeight - 1;
        SkipNode tracker  = head;

        while(s >= 0){
            while (tracker.next.get(s) != null && tracker.next.get(s).key.compareTo(O) < 0){
                tracker = tracker.next.get(s);
            }


            trace.set(s, tracker);
            s--;

        }

        if(tracker.next.get(0) != null) {
            if (!tracker.next.get(0).key.equals(o)){
                return false;
            }
        }else {

            return false;
        }



        if(tracker.next.get(0).next.get(0) == null)
            tail = tracker;

        if(tracker.next.get(0) == trail){
            trail = tracker;
        }

        tracker = tracker.next.get(0);


        for(int i = 0; i < tracker.next.size(); i++){

            if(i >= head.next.size()) continue;

            trace.get(i).next.set(i, tracker.next.get(i));
        }

        deleteNode(tracker);
        numNodes--;

        return true;
    }

    @Override
    public boolean remove(Object o) {

        boolean go = false;
        try {
            if(o == null) throw new Exception("NullPointerException");

            go = noBalRemove(o);


            goingDown = true;
            if ((!isBalanced() || numNodes == 2) && go) {
                maxHeight--;
                changeHeadheight();
            }
            goingDown = false;
        }catch (Exception e){
            e.printStackTrace();
        }

        return go;
    }


    @SuppressWarnings("unchecked")
    @Override
    public boolean contains(Object o){  //simple search function

        try {
            if(o == null) throw new Exception("NullPointerException");

            SkipNode tracker = head;
            int i = maxHeight - 1;
            T O = (T) o;


            while (i >= 0) {
                while (tracker.next.get(i) != null && tracker.next.get(i).key.compareTo(O) < 0) {
                    tracker = tracker.next.get(i);
                }

                if (tracker.next.get(i) != null) {
                    if (tracker.next.get(i).key.equals(o))
                        return true;
                }
                i--;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public T first() {
        try{
            if(head.next.get(0) == null)
                throw new Exception("NoSuchElementException");
        }catch (Exception e){
            e.printStackTrace();
        }
        return head.next.get(0).key;  //head is a node of its own so use next[0] pointer to get the first val in list
        //next[0] points to first val in list
    }

    @Override
    public T last() {
        try{
            if(head.next.get(0) == null)
                throw  new Exception("NoSuchElementException");
        }catch (Exception e){
            e.printStackTrace();
        }
        return tail.key;                //tail is pointing to last val so return the key
    }

    @Override
    public int size() {     //size == numNodes
        return numNodes;
    }

    @Override
    public boolean isEmpty() {
        return head.next.get(0) == null;
    }


    @Override
    public Object[] toArray() {

        SkipNode tracker = head;
        Object[] ret = new Object[numNodes];
        int i = 0;

        while(tracker.next.get(0) != null){
            tracker = tracker.next.get(0);
            ret[i] = tracker.key;
            i++;
        }

        return ret;
    }



    public void reBalance(){
        T target;

        SkipNode tracker;
        SkipNode iterator = head;
        int s;
        int oldHeight;
        int newHeight;

        while(iterator.next.get(0) != null) {
            target = iterator.next.get(0).key;
            tracker = head;
            trace = new ArrayList<>(Collections.nCopies(head.next.size(), null));
            s = maxHeight-1;

            while (s >= 0) {
                while (tracker.next.get(s) != null && tracker.next.get(s).key.compareTo(target) < 0) {  //keep moving right until null or val is >= to t
                    tracker = tracker.next.get(s);
                }

                trace.set(s, tracker);
                s--; //go down one layer
            }

            tracker = tracker.next.get(0);
            oldHeight = tracker.getHeight();

            tracker.changeHeight();
            newHeight = tracker.getHeight();

            if(oldHeight < newHeight){
                for(int i = oldHeight; i < newHeight; i++){
                    tracker.next.add(trace.get(i).next.get(i));
                    trace.get(i).next.set(i, tracker);
                }
            }else if(oldHeight > newHeight){
                for(int i = oldHeight-1; i > newHeight-1; i--){

                    if(i < maxHeight)
                        trace.get(i).next.set(i, tracker.next.get(i));
                    tracker.next.remove(i);
                }
            }

            iterator = iterator.next.get(0);
        }


    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        for(Object e: collection){
            if(!contains(e)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> collection) {
        for(Object e: collection){

            add((T)e);
        }
        return true;
    }


    @Override
    public boolean removeAll(Collection<?> collection) {
        try {
            if(collection == null) throw new Exception("NullPointerException");
            for (Object e : collection) {
                remove(e);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void clear() {

        while(head.next.get(0) != null)
            remove(head.next.get(0).key);
    }

    @Override
    public Comparator<? super T> comparator() {
        return null; //return null to use natural ordering and force user to only use values that extend comparable
    }

    private class SkipListSetIterator implements Iterator{


        @Override
        public boolean hasNext() {
            if(trail.next.get(0) != null) return true;
            return false;
        }

        @Override
        public Object next() {
            if(trail.next.get(0) == null){
                try {
                    throw new Exception("NoSuchElementException");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.exit(1);
            }
            trail = trail.next.get(0);
            return trail.key;

        }

        @Override
        public void remove() {
            trace = new ArrayList<SkipNode>(Collections.nCopies(maxHeight, null)); /*maxheight and head.next.size()
            *are equivalent*/

            int s = head.next.size()-1;
            SkipNode tracker  = head;

            while(s >= 0){
                if(trail.key == null) return;
                while (tracker.next.get(s) != null && tracker.next.get(s).key.compareTo(trail.key) < 0){
                    tracker = tracker.next.get(s);

                }


                trace.set(s, tracker);
                s--;

            }

            if(tracker.next.get(0) != null) {
                if (tracker.next.get(0) != trail){
                    return;
                }
            }else {
                return;
            }

            if(tracker.next.get(0).next.get(0) == null)
                tail = tracker;

            trail = tracker;
            tracker = tracker.next.get(0);




            for(int i = 0; i < tracker.next.size(); i++){

                if(i >= head.next.size()) continue;


                trace.get(i).next.set(i, tracker.next.get(i)) ;
            }

            deleteNode(tracker);
            numNodes--;


            goingDown = true;
            if(!isBalanced() || numNodes == 2){
                maxHeight--;
                changeHeadheight();
            }
            goingDown = false;

        }
    }

    @Override
    public Iterator<T> iterator() {

        return (Iterator<T>) new SkipListSetIterator();
    }

    @Override
    public <T1> T1[] toArray(T1[] t1s) {
        Iterator it = iterator();

        if(t1s.length < numNodes)
            t1s = (T1[]) Array.newInstance(t1s.getClass().getComponentType(), numNodes);
        else if(t1s.length > numNodes)
            t1s[numNodes] = null;

        for (int i = 0; i < numNodes; i++){
            t1s[i] = (T1)it.next();
        }
        trail = head;
        return t1s;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {

        Iterator it = iterator();
        Object temp;
        boolean delete;

        while(it.hasNext()){
            temp = it.next();
            delete = true;
            for(Object e: collection){
                if(temp == e){
                    delete = false;
                    break;
                }
            }
            if(delete) it.remove();
        }
        trail = head;

        return true;
    }

    @Override
    public  boolean equals(Object o){

        if (o == null) return false;
        if(o == this) return true;

        if(o.getClass() == this.getClass() && this.hashCode() == o.hashCode())
            return  true;


            return false;
    }

    @Override
    public int hashCode(){

        int totalHashCode = 0;

        SkipNode tracker = head;

        while(tracker.next.get(0) != null) {
            tracker = tracker.next.get(0);
            totalHashCode += tracker.key.hashCode();
        }

        return totalHashCode;
    }

    @Override
    public SortedSet<T> subSet(T t, T e1) {
        /*SortedSet<T> ret = new SkipListSet<T>();
        SkipNode tracker = head;

        while(tracker.next.get(0) != null && tracker.next.get(0).key.compareTo(t) < 0)
            tracker = tracker.next.get(0);

        if(tracker.next.get(0) != null)
            while(tracker.next.get(0) != null && tracker.next.get(0).key.compareTo(e1) < 0){
                tracker = tracker.next.get(0);
                ret.add(tracker.key);
            }

        return ret;*/
        try {
            throw new Exception("UnsupportedOperationException");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(1);

        return null;
    }

    @Override
    public SortedSet<T> headSet(T t) {
        /*SortedSet<T> ret = new SkipListSet<T>();
        SkipNode tracker = head;

        while(tracker.next.get(0) != null && tracker.next.get(0).key.compareTo(t) < 0){
            tracker = tracker.next.get(0);
            ret.add(tracker.key);
        }

        return ret;*/

        try {
            throw new Exception("UnsupportedOperationException");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(1);

        return null;
    }

    @Override
    public SortedSet<T> tailSet(T t) {
        /*SortedSet<T> ret = new SkipListSet<T>();
        SkipNode tracker = head;

        while(tracker.next.get(0) != null && tracker.next.get(0).key.compareTo(t) < 0)
            tracker = tracker.next.get(0);

        if(tracker.next.get(0) != null)
            while(tracker.next.get(0) != null && tracker.next.get(0).key.compareTo(t) >= 0){
                tracker = tracker.next.get(0);
                ret.add(tracker.key);
            }

        return ret;*/

        try {
            throw new Exception("UnsupportedOperationException");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(1);

        return null;
    }

}
