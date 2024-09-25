package com.nkm.mypracticespring.test.main;

public class CanPlaceFlowers {

    public static void main(String[] args) {
        int[] flowerbed = {0, 0, 1, 0, 0};
        int flower = 1;
        boolean result = canPlaceFlowers(flowerbed, flower);
        System.out.println(result);
    }

    public static boolean canPlaceFlowers(int[] flowerbed, int n) {
        Integer lastIndex1 = null;
        int countPlace = 0;
        for (int i = 0; i < flowerbed.length; i++) {
            if (flowerbed[i] == 1) {
                countPlace = lastIndex1 == null ? i / 2 : (i - lastIndex1 - 1 - 1) / 2;
                lastIndex1 = i;
            }

            if (i == flowerbed.length - 1) {
                if (flowerbed[i] != 1) {
                    if (lastIndex1 == null) {
                        countPlace = (flowerbed.length + 1) / 2;
                    } else {
                        countPlace += (i - lastIndex1) / 2;
                    }
                }
            }
        }

        System.out.println("countPlace = " + countPlace);

        return n <= countPlace;
    }


}
