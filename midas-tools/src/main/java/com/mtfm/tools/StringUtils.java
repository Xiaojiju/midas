package com.mtfm.tools;

import net.sourceforge.pinyin4j.PinyinHelper;

import java.util.Arrays;

public final class StringUtils {

    private static final String SPACE = " ";

    private static final String SPACE_TRIM = "";

    /**
     * 获取中文语句的第一个字符的首字母
     * @param source 中文字符串
     * @return 首字母
     */
    public static String chineseFirstLetter(String source) {
        return chineseEachFirstLetter(String.valueOf(source.charAt(0)));
    }

    /**
     * 获取中文每个字的首字母
     * 注意：该方法仅仅只能提取中文的首字母，如果中英混合，则英文是不处理，直接进行合并
     * @param source 中文字符串
     * @return 首字母
     */
    public static String chineseEachFirstLetter(String source) {
        StringBuilder letters = new StringBuilder();
        for (int i = 0; i < source.length(); i++) {
            char each = source.charAt(i);
            String[] hanyuPinyinStringArray = PinyinHelper.toHanyuPinyinStringArray(each);
            if (hanyuPinyinStringArray != null && hanyuPinyinStringArray.length > 0) {
                letters.append(hanyuPinyinStringArray[0].charAt(0));
            } else {
                letters.append(each);
            }
        }
        String replace = letters.toString().trim().replace(SPACE, SPACE_TRIM);
        return replace.toUpperCase();
    }

    /**
     * 获取中文的拼音
     * @param source 字符串
     * @param eachUpperCase 每段拼音首字母都大写
     * @return 拼音
     */
    public static String chineseLetters(String source, boolean eachUpperCase) {
        StringBuilder letters = new StringBuilder();
        for (int i = 0; i < source.length(); i++) {
            char each = source.charAt(i);
            String[] hanyuPinyinStringArray = PinyinHelper.toHanyuPinyinStringArray(each);
            if (hanyuPinyinStringArray != null && hanyuPinyinStringArray.length > 0) {
                String pinyin = Arrays.toString(hanyuPinyinStringArray);
                if (eachUpperCase) {
                    pinyin = toFirstLetterUpperCase(pinyin);
                }
                letters.append(pinyin);
            } else {
                letters.append(each);
            }
        }
        String replace = letters.toString().trim().replace(" ", "");
        return replace.toUpperCase();
    }

    /**
     * 将字符串首字母大学
     * 只处理在ASCII上的编码，其他语言例如中文等都不进行处理。
     * @param source 英文字符
     * @return 首字母大写后的字符
     */
    public static String toFirstLetterUpperCase(String source) {
        if (!hasText(source)) {
            return source;
        }
        char[] chars = source.toCharArray();
        boolean flag = true;
        for (int i = 0; i < chars.length; i++) {
            char element = chars[i];
            if (element == 32) {
                flag = true;
                continue;
            }
            if (flag) {
                if (element <= 122 && element >= 97) {
                    element += (-32);
                    chars[i] = element;
                    flag = false;
                }
            }
        }
        return String.valueOf(chars);
    }

    public static boolean hasText(String source) {
        return source != null && source.length() > 0 && containsText(source);
    }

    private static boolean containsText(CharSequence str) {
        int strLen = str.length();

        for(int i = 0; i < strLen; ++i) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }

        return false;
    }
}
