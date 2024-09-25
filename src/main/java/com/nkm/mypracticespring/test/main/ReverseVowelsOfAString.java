package com.nkm.mypracticespring.test.main;

import java.util.*;

public class ReverseVowelsOfAString {

    public static void main(String[] args) {
        String input = "IceCreAm";
        String result = reverseVowels(input);
        System.out.println("Result: " + result);
    }

    public static String reverseVowels(String s) {
        List<Character> listVowels = List.of('a', 'e', 'i', 'o', 'u');
        char[] arrInput = s.toCharArray();
        List<Character> vowel = new ArrayList<>();
        int length = arrInput.length;
        for (char c : arrInput) {
            if (listVowels.contains(Character.toLowerCase(c))) {
                vowel.add(c);
            }
        }

        if (vowel.isEmpty()) {
            return s;
        }

        int indexList = vowel.size() - 1;
        for (int i = 0; i < length; i++) {
            if (listVowels.contains(Character.toLowerCase(arrInput[i]))) {
                arrInput[i] = vowel.get(indexList);
                indexList--;
            }
        }

        return new String(arrInput);
    }

}
