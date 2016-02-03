package com.wordpressTest;

import java.util.Arrays;
import java.util.List;

/**
 * Created by chijiokea on 26/01/2016.
 */
public class Yello {

    public static int getEvenSum(List<Integer> numbers) {
        int sum = 0;
        for (Integer interge : numbers) {
            if (interge % 2 == 0) {
                sum = sum + interge;
                return sum;
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        List<Integer> myList = Arrays.asList(3, 29, 4, 3, 5);

        System.out.println(getEvenSum(myList));
    }

}
