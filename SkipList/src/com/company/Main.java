package com.company;
//FIXME THIS IS A COPY OF THE ORIGINAL!!!!!!!!!!!!!!!!!!!!!!
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;

public class Main {
    public static void main(String[] args){
//FIXME THIS IS A COPY OF THE ORIGINAL!!!!!!!!!!!!!!!!!!!!!!
        SkipListSet <Integer> n = new SkipListSet<>();
        SkipListSet<Integer> m = new SkipListSet<>();
        //Iterator it = n.iterator();

       /* for(int i = 20; i >= 0; i--){
            n.add(i);
        }

        System.out.println("Vals:///////////////////////////////////////");
        while(it.hasNext()){
            System.out.print(it.next() + " ");
        }*/

       /* System.out.println();
        SortedSet<Integer> k = n.subSet(5, 16);
        it = k.iterator();
        while(it.hasNext()){
            System.out.print(it.next() + " ");
        }
        System.out.println();*/

        n.add(4);
        n.add(2);
        m.add(4);
        m.add(2);
        ArrayList<Integer> k = new ArrayList<Integer>();
        k.add(1);
        k.add(6);
        //SkipListSet<Integer> n = new SkipListSet(k);
        //boolean m = n.remove(6);
        n.add(5);
        m.add(5);
        Object in[] = n.toArray();
        //n.clear();
        n.remove(7);
        n.addAll(k);
        m.addAll(k);
        //System.out.println("////////////////////////////////////////////////"+ n.equals(m));
        //System.out.println("n.hash: " + n.hashCode() + " m.hash: "+ m.hashCode());
        //SortedSet<Integer> r = n.subSet(2,6);

        Iterator it = n.iterator();
        //Integer[] m = new Integer[n.size()-2];

       // m = n.toArray(m);

        /*System.out.println("Headset is: " );
        while (it.hasNext()){
            System.out.print(it.next() + " ");
        }*/

        /*for(int i = 0; i < m.length; i++){
            System.out.print(m[i] + " ");
        }*/
        //System.out.println("HEAD IS: " + n.first() + " TAIL IS: " + n.last() + " IT HAS : " + n.contains(3) );
        //n.retainAll(k);
       /* System.out.println(n.containsAll(m) + "<--!!!!!!!!!!!!!!!! " + n.contains(1));
        System.out.print("Values in the n List: ");
        while(it.hasNext()){
            System.out.print(it.next() + " ");
        }
        System.out.print("\nValues in the m list:");
        it = m.iterator();
        while (it.hasNext()) {
            System.out.print(it.next() + " ");
        }
        //it.remove();
        //System.out.println(it.next() );
       System.out.println("\nSIZE: " + m.size());
        it.remove();
        System.out.println("SIZE: " + m.size());
        it.remove();
        System.out.println("SIZE: " + m.size());
        it.remove();
        System.out.println("SIZE: " + m.size());
        it.remove();
        System.out.println("SIZE: " + m.size());
        it.remove();
        System.out.println(m.size());*/
       //n.reBalance();
    for(int i = 0; i < 10000000; i++){
        n.add(i);
        /*if((i % 1000000) == 0){
            n.reBalance();
        }*/
        n.m();

    }
    //n.reBalance();
/*System.out.println("\n///////////////////////////////////////////////////////////////////////////\n");
        System.out.println("\n///////////////////////////////////////////////////////////////////////////\n");

        System.out.println("\n///////////////////////////////////////////////////////////////////////////\n");

        System.out.println("\n///////////////////////////////////////////////////////////////////////////\n");System.out.println("\n///////////////////////////////////////////////////////////////////////////\n");
        System.out.println("\n///////////////////////////////////////////////////////////////////////////\n");
        System.out.println("\n///////////////////////////////////////////////////////////////////////////\n");
        System.out.println("\n///////////////////////////////////////////////////////////////////////////\n");
        System.out.println("\n///////////////////////////////////////////////////////////////////////////\n");
        System.out.println("\n///////////////////////////////////////////////////////////////////////////\n");
        System.out.println("\n///////////////////////////////////////////////////////////////////////////\n");
        for(int i = 900; i >= 0; i--){
            n.remove(i);
            if((i % 50) == 0){
                n.reBalance();
                System.out.println("KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK");
            }
            //n.printMax();
        }


        n.reBalance();*/
    }
}
