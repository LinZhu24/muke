package com.miaosha.miaosha.test;

/**
 * @Author: LinXueRui
 * @Date: 2019/8/20 14:50
 * @Desc:
 */
public class NumberTest {
    public static void main(String[] args) {
        test1();
        String s = printBinary(-35);
        System.out.println(s);
    }

    /**
     *
     *    >>1  表示二进制数向右移动 1位，相当于除以二，但某些情况结果并不会完全的等于   除以二
     *    <<1  表示二进制数向左移动 1位，相当于乘以二，但某些情况结果并不会完全的等于   乘以二
     *    >>>1  表示无符号右移，    注意只有无符号右移，并没有无符号左移
     */
    public static void test0(){
        int a = 8;
        int i1 = a >> 1;
        int i2 = a >> 2;
        int j = a >>> 63;
        System.out.println(a+" 向右移动1位的结果是"+i1);
        System.out.println(a+" 向右移动2位的结果是"+i2);
        System.out.println("j is :"+j);
    }

    /**
     * 按位取反 ~
     * 按位异或 ^
     * 逻辑与 &&     逻辑与  具有短路功能
     * 逻辑或 ||     逻辑或  具有短路功能
     * 按位与 &
     * 按位或 |
     */
    public static void test1(){
        int a = 2;
        int i1 = ~a;
        int i2 = 2^7;
        boolean a1 = false;
        boolean b1 = false;
        System.out.println("对"+a+"取反的结果是："+i1);
        System.out.println("2和7的异或操作的结果是："+i2);
    }

    /**
     * 打印二进制  以 9和 -35举例子
     * （1）
     * 1:  9/2=4,余数为 1
     * 2:  4/2=2,余数为 0
     * 3:  2/2=1,余数为 0
     * 4:  1/2=0,余数为 1
     *
     *
     * （2）
     * 1：35/2= 17   余数是 1
     * 1：17/2= 8    余数是 1
     * 1：8/2= 4     余数是 0
     * 1：4/2= 2     余数是 0
     * 1：2/2= 1     余数是 0
     * 1：1/2= 0     余数是 1
     *
     * @param val
     */
    public static String printBinary(int val){
//        调用了Integer的自带方法，求二进制
//        String string = Integer.toBinaryString(val);
//        System.out.println(string);
//        int shift = 1;
//        int mag = Integer.SIZE - Integer.numberOfLeadingZeros(val);
//        int chars = Math.max(((mag + (shift - 1)) / shift), 1);
//        char[] buf = new char[chars];
//        int offset = 0;
//        int charPos = chars;
//        int radix = 1 << shift;
//        int mask = radix - 1;
//        do {
//            buf[offset + --charPos] = digits[val & mask];
//            val >>>= shift;
//        } while (val != 0 && charPos > 0);
//
//        // Use special constructor which takes over "buf".
//        return new String(buf);

        return "";
    }

    final static char[] digits = {
            '0' , '1' , '2' , '3' , '4' , '5' ,
            '6' , '7' , '8' , '9' , 'a' , 'b' ,
            'c' , 'd' , 'e' , 'f' , 'g' , 'h' ,
            'i' , 'j' , 'k' , 'l' , 'm' , 'n' ,
            'o' , 'p' , 'q' , 'r' , 's' , 't' ,
            'u' , 'v' , 'w' , 'x' , 'y' , 'z'
    };
}
