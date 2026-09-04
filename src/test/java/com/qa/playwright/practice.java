package com.qa.playwright;

public class practice {
    public static void main(String[] args) {
        String input = "a2d3n4";
        for (int i = 0 ; i < input.length() ; i++) {
            char c = input.charAt(i);
            if(Character.isAlphabetic(c)) {
                System.out.print(input.charAt(i));
            }
        }
    }
}
