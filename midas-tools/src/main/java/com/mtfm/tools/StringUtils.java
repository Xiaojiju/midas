package com.mtfm.tools;

import net.sourceforge.pinyin4j.PinyinHelper;

import java.util.Arrays;

public final class StringUtils {

    private static final String SPACE = " ";
    private static final String SPACE_TRIM = "";

    public static String chineseFirstLetter(String source) {
        return chineseEachFirstLetter(String.valueOf(source.charAt(0)));
    }

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

    public static String toFirstLetterUpperCase(String source) {
        StringBuilder sb = new StringBuilder(source);
        sb.insert(0, " ");
        for (int i = 1; i <sb.length(); i++) {
            if(sb.substring(i-1, i).equals(" ")) {
                String []arr= {sb.substring(i,i+1)};
                arr[0]+=(-32);
                sb.replace(i, i+1, Arrays.toString(arr));
            }
        }
        return sb.toString();
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
