package step.learning.basics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BasicsDemo {
    public void run() {
        int a = 3;
        byte b = 3;
        String str1 = "111";
        String str2 = "111";
        String str3 = new String("111");

        // same reference
        if (str1 == str2) System.out.println("str1 == str2");
        else System.out.println("str1 != str2");

        // different references
        if (str1 == str3) System.out.println("str1 == str3");
        else System.out.println("str1 != str3");

        // check content
        if (str1.equals(str3)) System.out.println("str1.equals(str3)");
        else System.out.println("!str1.equals(str3)");

        arrDemo();
    }

    private void arrDemo() {
        int[] arr1 = {1, 2, 3, 4, 5};
        int[] arr2 = new int[]{1, 2, 3, 4, 5};
        for (int i = 0; i < arr1.length; i++) {
            System.out.print(arr1[i] + " ");
        }
        System.out.println();

        for (int j : arr2) {
            System.out.print(j + " ");
        }
        System.out.println();

        int[][] arr2d = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        for (int[] arr : arr2d) {
            for (int x : arr) {
                System.out.print(x + " ");
            }
            System.out.println();
        }

        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        for (int i : list) {
            System.out.print(i + " ");
        }
        System.out.println();

        Map<String, String> headers = new HashMap<>();
        headers.put("Host", "localhost");
        headers.put("Connection", "close");
        headers.put("Content-Type", "text/html");
        for (String key : headers.keySet()) {
            System.out.println(String.format("%s: %s", key, headers.get(key)));
        }
    }
}
