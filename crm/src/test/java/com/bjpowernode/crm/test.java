package com.bjpowernode.crm;

public class test<T,V> {
    private T obj1;
    private V obj2;

    public test(T obj1, V obj2) {
        this.obj1 = obj1;
        this.obj2 = obj2;
    }

    public test() {
    }
    public void showType(){
        System.out.println(obj1.getClass().getName());
        System.out.println(obj2.getClass().getName());
    }

    public T getObj1() {
        return obj1;
    }

    public void setObj1(T obj1) {
        this.obj1 = obj1;
    }

    public V getObj2() {
        return obj2;
    }

    public void setObj2(V obj2) {
        this.obj2 = obj2;
    }
}
class test1{
    public static void main(String[] args) {
        test<String,Integer> ttttt = new test<>("sda",2);
        ttttt.showType();
    }
}
