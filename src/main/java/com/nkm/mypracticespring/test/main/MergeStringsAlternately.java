package com.nkm.mypracticespring.test.main;

import java.util.Arrays;

/**
 * You are given two strings word1 and word2. Merge the strings by adding letters in alternating order, starting with word1.
 * If a string is longer than the other, append the additional letters onto the end of the merged string.
 * Example
 * Input: "abc", "def" => Output: "adbecf"
 * Input: "abcd", "ef" => Output: "aebfcd"
 */
public class MergeStringsAlternately {

    public static void main(String[] args) {
        String word1 = "abc";
        String word2 = "pqr";
        String result = mergeAlternately(word1, word2);
        System.out.println("Result: " + result);
    }

    public static String mergeAlternately(String word1, String word2) {
        char[] input1 = word1.toCharArray();
        char[] input2 = word2.toCharArray();

        int lengthResult = word1.length() + word2.length();
        int maxLength = Math.max(word1.length(), word2.length());
        char[] result = new char[lengthResult];
        int indexResult = 0;
        for (int i = 0; i < maxLength; i++) {
            if (i <= (input1.length - 1)) {
                result[indexResult] = input1[i];
                indexResult++;
            }
            if (i <= (input2.length - 1)) {
                result[indexResult] = input2[i];
                indexResult++;
            }
        }

        return new String(result);
    }

}
