package com.nkm.mypracticespring.test.main;

import java.util.ArrayList;
import java.util.List;

public class KidsWithTheGreatestNumberOfCandies {

    public static void main(String[] args) {
        int[] candies = {2, 3, 5, 1, 3};
        int extraCandies = 3;
        List<Boolean> result = kidsWithCandies(candies, extraCandies);
        System.out.println(result);
    }

    public static List<Boolean> kidsWithCandies(int[] candies, int extraCandies) {
        ArrayList<Boolean> result = new ArrayList<>();
        int maxArr = 0;
        for (int j : candies) {
            if (j > maxArr) {
                maxArr = j;
            }
        }

        for (int candy : candies) {
            if ((candy + extraCandies) >= maxArr) {
                result.add(true);
            } else {
                result.add(false);
            }
        }

        return result;
    }

}
