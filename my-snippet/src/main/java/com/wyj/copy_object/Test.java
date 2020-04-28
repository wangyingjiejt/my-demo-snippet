package com.wyj.copy_object;

public class Test {

    public static void main(String[] args) {

        Address address= new Address("北京","长安街");
        User user = new User("wyj",28,address);
        System.out.println("user = " + user.toString());
        User copyUser = null;
        try {
            copyUser = (User)user.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        copyUser.getAddress().setCity("上海");
        System.out.println("copyUser = " + copyUser.toString());
        System.out.println("second print user = " + user.toString());
    }
}
