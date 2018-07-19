package com.hr.musicktv.utils;


import java.util.Random;

/*
 * lv   2018/7/13
 */
public class ColorUtils {
    private static Random random = new Random();
    private static int []ranColor ={
            0x70B9D3EE,0x70A2CD5A,0x7097FFFF,0x708B8B00,
            0x7087CEEB,0x807CCD7C,0x7079CDCD,0x70698B22,
            0x704F94CD,0x704876FF,0x6027408B,0x7000FA9A,
            0x70008B8B,0x7000BFFF,0x70006400,0x70E0FFFF,
            0x70EE6AA7,0x70EE30A7,0x70FFFF00,0x70CAFF70,
    };

    public static int getColor() {
        return ranColor[random.nextInt(ranColor.length)];
    }
}
