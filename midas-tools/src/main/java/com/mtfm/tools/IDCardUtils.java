/*
 * Copyright 2022 一块小饼干(莫杨)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mtfm.tools;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 主要用于国内身份证
 */
public class IDCardUtils {

    private static final char[] VALIDATE = { '1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2' };
    private static final int[] WEIGHT = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
    private static final int STRICT_BALANCER = 11;

    /**
     * 判定身份证是否合法
     * @param card 身份证号码
     * @return true为合法，反之则错误
     */
    public static boolean isLegal(String card) {
        if (card == null || card.length() != 18) {
            return false;
        }
        char lastOne = String.valueOf(card.charAt(17)).toUpperCase().charAt(0);
        int sum = 0;
        for (int i = 0; i < card.length() - 1; i++) {
            sum = sum + Integer.parseInt(String.valueOf(card.charAt(i))) * WEIGHT[i];
        }
        int mode = sum % STRICT_BALANCER;

        return VALIDATE[mode] == lastOne;
    }

    /**
     * 获取性别
     * @param card 身份证
     * @return -1 为身份证错误 1 为女 0 为男
     */
    public static int getGender(String card) {
        if (!isLegal(card)) {
            return -1;
        }
        int genderCode = Integer.parseInt(card.substring(17, 18));
        if (genderCode % 2 == 0) {
            return 1;
        }
        return 0;
    }
}
