package com.simple.common.utils;

import org.springframework.util.AntPathMatcher;

import java.util.List;

/**
 * description: AntPathMatcherExt <br>
 * date: 2020/9/29 13:39 <br>
 * author: ws <br>
 * version: 1.0 <br>
 */
public class AntPathMatcherExt extends AntPathMatcher {

    public boolean pathMatch(String[] paths, String pattern) {
        for (String path : paths) {
            if (super.match(path, pattern)) {
                return true;
            }
        }
        return false;
    }

    public boolean pathMatch(List<String> paths, String pattern) {
        if (paths == null || paths.isEmpty()) {
            return false;
        }
        for (String path : paths) {
            if (super.match(path, pattern)) {
                return true;
            }
        }
        return false;
    }
}
