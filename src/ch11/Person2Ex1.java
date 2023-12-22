package ch11;

public class Person2Ex1 {
    public static void main(String[] args) {
        Person2 p = new Person2("David", 10);
        int hashcode1 = p.hashCode();
        int hashcode2 = p.hashCode();

        System.out.println("hashcode1:"+hashcode1+", hashcode2:"+hashcode2);

        p.age = 20;
        int hashcode3 = p.hashCode();

        System.out.println("hashcode3:"+hashcode3);

        Person2 p1 = new Person2("David", 10);
        Person2 p2 = new Person2("David", 10);

        boolean b = p1.equals(p2);

        int hashcode4 = p1.hashCode();
        int hashcode5 = p2.hashCode();

        System.out.println("hashcode4:"+hashcode4+", hashcode5:"+hashcode5);
    }
}
