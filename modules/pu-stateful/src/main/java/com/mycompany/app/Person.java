package com.mycompany.app;

import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceIndex;

/**
 * Example POJO for storing in GigaSpaces
 */
public class Person {
    
    private String id;
    private String name;
    private Integer age;
    private String city;
    
    // Default constructor required by GigaSpaces
    public Person() {}
    
    public Person(String id, String name, Integer age, String city) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.city = city;
    }
    
    @SpaceId // Marks this field as the space ID (primary key)
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    @SpaceIndex // Creates an index for faster queries
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Integer getAge() {
        return age;
    }
    
    public void setAge(Integer age) {
        this.age = age;
    }
    
    @SpaceIndex // Index for city-based queries
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    @Override
    public String toString() {
        return "Person{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", city='" + city + '\'' +
                '}';
    }
}