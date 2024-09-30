package com.nkm.mypracticespring.test;

import org.bson.types.ObjectId;

public class MainTest {

    public static void main(String[] args) {
        ObjectId objectId = new ObjectId();
        System.out.println("Generated ObjectId: " + objectId.toHexString());
    }

}
