package com.wyj.copy_object;

import java.util.Objects;

/**
 * 地址
 * @author wangyj
 * @Date 2020/3/15 5:15 下午
 */
public class Address implements Cloneable{

    private String city;

    private String street;

    public Address(String city, String street) {
        this.city = city;
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

//    @Override
//    protected Object clone()  {
//        Address address=null;
//        try {
//             address=(Address) super.clone();
//        } catch (CloneNotSupportedException e) {
//            address = new Address(this.city,this.street);
//        }
//        return address;
//    }


    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "Address{" +
                "city='" + city + '\'' +
                ", street='" + street + '\'' +
                '}';
    }
}
