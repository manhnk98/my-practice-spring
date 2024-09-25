package com.nkm.mypracticespring.test.main;

import java.util.ArrayList;
import java.util.List;

/**
 * For two strings s and t, we say "t divides s" if and only if s = t + t + t + ... + t + t (i.e., t is concatenated with itself one or more times).
 * Given two strings str1 and str2, return the largest string x such that x divides both str1 and str2.
 * Example
 * Input: str1 = "ABCABC", str2 = "ABC" => Output: "ABC"
 * Input: str1 = "ABABAB", str2 = "ABAB" => Output: "AB"
 * Input: str1 = "LEET", str2 = "CODE" => Output: ""
 */
public class GreatestCommonDivisorOfStrings {

    public static void main(String[] args) {
//        String input1 = "ABABABAB";
//        String input2 = "ABAB"; TTXT

        String input1 = "TTXTTTXTTTXTTTXT";
        String input2 = "TTXTTTXT";

        String result = gcdOfStrings(input1, input2);
        System.out.println("Result: " + result);
    }

    public static String gcdOfStrings(String str1, String str2) {
        if (!str1.concat(str2).equals(str2.concat(str1))) {
            return "";
        }

        List<Character> listRs = new ArrayList<>();
        int i = 0;
        while (i < str1.length() && i < str2.length()) {
            if (!listRs.isEmpty() && str1.charAt(i) == listRs.getFirst()) {
                break;
            } else if (str1.charAt(i) == str2.charAt(i)) {
                listRs.add(str1.charAt(i));
            }
            i++;
        }

        return str1.substring(0, listRs.size());
    }

}
