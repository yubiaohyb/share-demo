package com.yubiaohyb.sharedemo.algorithm;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.util.*;

/**
 * 人若志趣不远，心不在焉，虽学不成。
 * <p>
 * description  -  function biz name
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @since 2023/8/17 10:49
 */
public class WordContainedPredictor {
    @Data
    static class Pos {
        int i;
        int j;
        Pos(int i, int j) {
            this.i = i;
            this.j = j;
        }
    }

    public static void main(String[] args) {
//        char[][] arr = new char[][]{
//                {'A','B','C','E'},
//                {'S','F','C','S'},
//                {'A','D','E','E'}};
//        String word = "ABCCED";

        char[][] arr = new char[][]{
                {'a','b'},
                {'c','d',}};
        //String word = "abcd";
       // String word = "abdb";
        String word = "abdc";
        Map<Character, List<Pos>> map = new HashMap<>();

        for (int index=0; index<word.length(); index++) {
            char c = word.charAt(index);
            if (map.containsKey(c)) {
                continue;
            }
            map.put(c, new ArrayList<>());
        }
        for (int i=0; i<arr.length; i++) {
            for (int j=0; j<arr[i].length; j++) {
                char c = arr[i][j];
                if (map.containsKey(c)) {
                    map.get(c).add(new Pos(i, j));
                }
            }
        }
        System.out.println(JSON.toJSONString(map));
        //todo pre check
        Stack<Pos> stack = new Stack<>();

        boolean contains = contains(stack, map, word, 0);
        System.out.println(contains);


    }

    static boolean aroundBy(Pos cur, Pos nxt) {
        return ((cur.i == nxt.i) && (cur.j == nxt.j + 1))
                ||((cur.i == nxt.i) && (cur.j == nxt.j - 1))
                ||((cur.i == nxt.i+1) && (cur.j == nxt.j))
                ||((cur.i == nxt.i-1) && (cur.j == nxt.j));
    }

    static boolean contains(Stack<Pos> stack, Map<Character, List<Pos>> map, String word, int index) {
        if (index >= word.length()) {
            System.out.println(JSON.toJSONString(stack));
            return stack.size() == word.length();
        }
        char c = word.charAt(index);
        List<Pos> poses = map.get(c);
        if (poses.isEmpty()) {
            return false;
        }
        for (Pos p : poses ) {

            if (stack.isEmpty() ||
                    (aroundBy(stack.peek(), p) && !stack.contains(p)) ) {
                stack.push(p);
                if (contains(stack, map, word, index+1)) {

                    return true;
                }
                stack.pop();
            }
        }
        System.out.println(JSON.toJSONString(stack));
        System.out.println("index:" + index);
        return false;
    }

}
