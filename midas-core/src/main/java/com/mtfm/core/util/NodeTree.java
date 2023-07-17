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
package com.mtfm.core.util;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * 节点树构建
 * 不是线程安全的
 * <pre>
 *     public class Message implements Linkable {
 *
 *     }
 * </pre>
 * @param <T> 指定需要构建树的类
 * @since 1.0.0
 */
public class NodeTree<T extends Linkable<T>> implements Tree<T>, Serializable {

    private T element;

    public NodeTree() {
        this(null);
    }

    public NodeTree(T element) {
        this.element = element;
    }

    @Override
    public NodeTree<T> find(Predicate<T> function) {
        if (function.test(element)) {
            return new NodeTree<>(element);
        }
        return new NodeTree<>(find(element.getNodes(), function));
    }

    @Override
    public List<T> toArray() {
        List<T> list = new ArrayList<>();
        if (element == null) {
            return list;
        }
        list.add(element);
        loopAll(element.getNodes(), list);
        return list;
    }

    private void loopAll(List<T> items, List<T> root) {
        if (CollectionUtils.isEmpty(items)) {
            return ;
        }
        for (T item : items) {
            root.add(item);
            loopAll(item.getNodes(), root);
        }
    }

    private T find(List<T> elements, Predicate<T> function) {
        for (T element : elements) {
            if (function.test(element)) {
                return element;
            } else {
                if (CollectionUtils.isEmpty(element.getNodes())) {
                    continue;
                }
                return find(element.getNodes(), function);
            }
        }
        return null;
    }

    public static <T extends Linkable<T>> NodeTree<T> build(List<T> list) {
        T root = findParent(list);
        if (root == null || root.getKey() == null) {
            throw new IllegalArgumentException("未找到根节点");
        }
        return build(list, root);
    }

    public static <T extends Linkable<T>> NodeTree<T> build(List<T> list, String parent) {
        T root = null;
        if (parent == null) {
            root = findParent(list);
        } else {
            for (T element : list) {
                if (element.getParent().equals(parent)) {
                    root = element;
                    break;
                }
            }
        }
        if (root == null || root.getKey() == null) {
            throw new IllegalArgumentException("could not find root node");
        }
        build(list, root);
        return new NodeTree<>(root);
    }

    public static <T extends Linkable<T>> NodeTree<T> build(List<T> list, T root) {
        if (root == null) {
            root = findParent(list);
            if (root == null || root.getKey() == null) {
                throw new IllegalArgumentException("could not find root node");
            }
        }
        List<T> collection = root.getNodes();
        if (collection == null) {
            collection = new ArrayList<>();
        }
        root.setHeight(0);
        deepSearch(list, collection, root.getKey(), 1);
        root.setNodes(collection);
        return new NodeTree<>(root);
    }

    private static <T extends Linkable<T>> void deepSearch(List<T> elements,
                                                           List<T> children,
                                                           String parent,
                                                           int height) {
        if (CollectionUtils.isEmpty(elements)) {
            return ;
        }

        for (T element : elements) {
            if (parent.equals(element.getParent())) {
                List<T> collection = element.getNodes();
                if (collection == null) {
                    collection = new ArrayList<>();
                }
                element.setHeight(height);
                deepSearch(elements, collection, element.getKey(), height + 1);
                element.setNodes(collection);
                children.add(element);
            }
        }
    }

    private static <T extends Linkable<T>> T findParent(List<T> list) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        T root = null;
        for (T element : list) {
            if (Objects.isNull(element)) {
                continue;
            }
            root = element;
            if (StringUtils.hasText(element.getKey()) && element.getKey().equals(element.getParent())) {
                return element;
            }
            for (T item : list) {
                if (!element.getKey().equals(item.getKey()) && element.getParent().equals(item.getKey())) {
                    root = null;
                   break;
                }
            }
        }
        return root;
    }

    public T getElement() {
        return element;
    }

    public void setElement(T element) {
        this.element = element;
    }

}
