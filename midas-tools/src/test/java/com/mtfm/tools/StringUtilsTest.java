package com.mtfm.tools;

import org.junit.jupiter.api.Test;

public class StringUtilsTest {

    @Test
    public void chineseFirstLetterTest() {
        String chinese = "我的中国心"; // 全中文
        String chineseFirstLetter = StringUtils.chineseFirstLetter(chinese);
        assert "W".equals(chineseFirstLetter);
    }

    @Test
    public void mixChineseFirstLetterTest() {
        String mix = "hello,我的中国"; // 中英混合
        String mixChineseFirstLetter = StringUtils.chineseFirstLetter(mix);
        assert "H".equals(mixChineseFirstLetter);
    }

    @Test
    public void chineseEachFirstLetterTest() {
        String chinese = "我的中国心"; // 全中文
        String result = StringUtils.chineseEachFirstLetter(chinese);
        assert "WDZGX".equals(result);
    }

    @Test
    public void mixChineseEachFirstLetterTest() {
        String chinese = "hello,我的中国"; // 全中文
        String result = StringUtils.chineseEachFirstLetter(chinese);
        assert "HELLO,WDZG".equals(result);
    }

    @Test
    public void toFirstLetterUpperCaseTest() {
        String chinese = " hello, world!我的中国心";
        System.out.println(StringUtils.toFirstLetterUpperCase(chinese));
    }
}
