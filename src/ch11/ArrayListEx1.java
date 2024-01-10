package ch11;

import java.util.*;
public class ArrayListEx1 {
    public static void main(String[] args) {
        // 기본길이(용량, capacity)가 10인 ArrayList를 생성
        ArrayList list1 = new ArrayList(10);
        // ArrayList에는 객체만 저장가능
        // autoboxing에 의해 기본형이 참조형으로 자동 반환
        list1.add(5);
        //list1.add(new Integer(5));
        list1.add(new Integer(4));
        list1.add(new Integer(2));
        list1.add(new Integer(0));
        list1.add(new Integer(1));
        list1.add(new Integer(3));

        ArrayList list2 = new ArrayList(list1.subList(1,4));
        print(list1, list2);

        Collections.sort(list1);
        Collections.sort(list2);
        print(list1, list2);

        System.out.println("list1.containsAll(list2):"
                + list1.containsAll(list2));

        list2.add("B");
        list2.add("C");
        list2.add(3,"A");
        print(list1, list2);

        list2.set(3, "AA");
        print(list1, list2);

        System.out.println("list1.retainAll(list2):"
                + list1.retainAll(list2));
        print(list1, list2);

        //for(int i = list2.size()-1; i >= 0; i--) { /* 정상적인 경우 */
        for(int i = 0; i < list2.size(); i++) { /* 버그 존재함 */
            if(list1.contains(list2.get(i)))
                list2.remove(i);
        }
        print(list1, list2);
    }

    static void print(ArrayList list1, ArrayList list2) {
        System.out.println("list1:"+list1);
        System.out.println("list2:"+list2);
        System.out.println();
    }
}
