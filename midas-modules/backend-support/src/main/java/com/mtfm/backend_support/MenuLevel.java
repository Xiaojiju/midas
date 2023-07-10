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
package com.mtfm.backend_support;

import com.baomidou.mybatisplus.annotation.IEnum;
/**
 * @author 一块小饼干
 * @since 1.0.0
 * 菜单级别
 */
public enum MenuLevel implements IEnum<Integer> {

    // 菜单
    MENU(0),

    // 页面
    PAGE(1),

    // 组件
    COMPONENT(2),

    // 按钮
    BUTTON(3);

    private final int level;

    MenuLevel(int level) {
        this.level = level;
    }

    @Override
    public Integer getValue() {
        return this.level;
    }

    public static MenuLevel selectLevel(int level) {
        final MenuLevel[] menuLevels = MenuLevel.values();
        for (MenuLevel menuLevel : menuLevels) {
            if (menuLevel.level == level) {
                return menuLevel;
            }
        }
        return null;
    }
}
