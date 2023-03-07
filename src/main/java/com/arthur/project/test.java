package com.arthur.project;

import java.util.Stack;

public class test {
    public boolean isValid (String s) {
        String [] strs = s.split("");
        if (strs.length<1 | strs.length%2 != 0 ){
            return false;
        }
        String [][] cp ={
                {"(",")"},
                {"{","}"},
                {"[","]"}
        };
        Stack<String> stack = new Stack<String>();
        for (String str : strs) {
            if(str.equals(cp[0][0]) | str.equals(cp[1][0]) |str.equals(cp[2][0])){
                stack.push(str);
            }else {
                if (stack.empty()){
                    return false;
                }
                String temp = stack.pop();
                int flag = 0;
                for(String [] cpTemp : cp){
                    if (cpTemp[0].equals(temp) & cpTemp[1].equals(str)){
                        flag ++ ;
                    }
                }
                if (flag != 1){
                    return false;
                }
            }
        }
        if (stack.empty()){
            return true;
        }else {
            return false;
        }
    }

    public static void main(String[] args) {
        test test = new test();
        String s = new String("(){}");
        System.out.println(test.isValid(s));
    }
}
