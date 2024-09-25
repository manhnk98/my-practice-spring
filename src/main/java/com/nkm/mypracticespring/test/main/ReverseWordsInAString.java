package com.nkm.mypracticespring.test.main;

public class ReverseWordsInAString {

    public static void main(String[] args) {
        String input = "a good   example";
        String result = reverseWords(input);
        System.out.println("Result: " + result);
    }

    public static String reverseWords(String s) {
        s = s.trim();
        String[] input = s.split(" ");
        StringBuilder sb = new StringBuilder();

        for (int i = input.length - 1; i >= 0; i--) {
            if (!input[i].isBlank()) {
                sb.append(input[i]).append(" ");
            }
        }

        return sb.toString().trim();
    }

}
