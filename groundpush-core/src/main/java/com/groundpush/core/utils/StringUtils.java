package com.groundpush.core.utils;

import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * @description: 处理String
 * @author: zhangxinzhong
 * @date: 2019-08-19 下午1:34
 */
@Component
public class StringUtils {

    /**
     * 生成uuid，不带-
     *
     * @return
     */
    public String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 字符串替换.空串,null都返回原串.
     *
     * @param text
     * @param searchString
     * @param replacement
     * @return
     */
    public String replace(String text, String searchString, String replacement) {
        return org.apache.commons.lang3.StringUtils.replace(text, searchString, replacement);
    }

    /**
     * 空格、空串、null都返回true，否则返回false
     *
     * @param s
     * @return
     */
    public boolean isBlank(String s) {
        return org.apache.commons.lang3.StringUtils.isBlank(s);
    }

    /**
     * 去除字符串右边空格
     *
     * @param s
     * @return
     */
    public String rtrim(String s) {
        String str = org.apache.commons.lang3.StringUtils.stripEnd(s, null);
        return str == null ? "" : str;
    }


    /**
     * 空格、空串、null都返回false，否则返回true
     * <p>
     * StringUtils.isNotBlank(null)      = false
     * StringUtils.isNotBlank("")        = false
     * StringUtils.isNotBlank(" ")       = false
     * StringUtils.isNotBlank("bob")     = true
     * StringUtils.isNotBlank("  bob  ") = true
     *
     * @param s
     * @return
     */
    public boolean isNotBlank(String s) {
        return org.apache.commons.lang3.StringUtils.isNotBlank(s);
    }

    /**
     * 把字符串的首字母大写
     *
     * @param str
     * @return
     */
    public String capitalize(String str) {
        return org.springframework.util.StringUtils.capitalize(str);
    }

    /**
     * 把集合转换为逗号分割的字符串
     *
     * @param coll
     * @return
     */
    public String collectionToCommaDelimitedString(Collection<?> coll) {
        return org.springframework.util.StringUtils.collectionToCommaDelimitedString(coll);
    }

    /**
     * 把集合转换为指定字符分割的字符串
     *
     * @param coll
     * @param delim
     * @return
     */
    public String collectionToDelimitedString(Collection<?> coll, String delim) {
        return org.springframework.util.StringUtils.collectionToDelimitedString(coll, delim);
    }

    /**
     * 把逗号分割的字符串分割为字符串数组
     *
     * @param str
     * @return
     */
    public String[] commaDelimitedListToStringArray(String str) {
        return org.springframework.util.StringUtils.commaDelimitedListToStringArray(str);
    }

    /**
     * 把指定字符分割的字符串分割为字符串数组
     *
     * @param str
     * @param delimiter
     * @return
     */
    public String[] delimitedListToStringArray(String str, String delimiter) {
        return org.springframework.util.StringUtils.delimitedListToStringArray(str, delimiter, null);
    }

    /**
     * 转换字符串的编码
     *
     * @param sourceString
     * @param sourceEncoding
     * @param targetEncoding
     * @return
     */
    public String convertEncoding(String sourceString, String sourceEncoding, String targetEncoding) {

        String stringConverted;
        try {
            stringConverted = new String(sourceString.getBytes(sourceEncoding), targetEncoding);
        } catch (UnsupportedEncodingException e) {
            stringConverted = sourceString;
        }

        return stringConverted;
    }

    /**
     * 把下划线命名转换为驼峰命名
     *
     * @param underscoreName
     * @return
     */
    public String underscoreToCamelCase(String underscoreName) {
        String[] words = underscoreName.toLowerCase().split("_");

        if (words.length > 0) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < words.length; i++) {
                String word = words[i];

                if (i == 0) {
                    builder.append(word);
                } else {
                    builder.append(capitalize(word));
                }
            }

            return builder.toString();
        } else {
            return underscoreName;
        }
    }

    /**
     * 字符串转换为16进制字节数组
     *
     * @param input
     * @return
     */
    public byte[] toHexBytes(String input) {
        int n = input.length();

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        for (int i = 0; i < n; i += 2) {
            String tmp = input.substring(i, i + 2);
            Integer x = new Integer(Integer.parseInt(tmp, 16));
            byte b = x.byteValue();
            bos.write(b);
        }

        return bos.toByteArray();
    }

    /**
     * 将字符串转换为16进制格式，类似UE的16进制模式
     *
     * @param str
     * @return
     */
    public List<String> toHexFormat(String str) {
        return toHexFormat(str, "UTF-8");
    }

    /**
     * 将字符串转换为16进制格式，类似UE的16进制模式
     *
     * @param str
     * @param charset
     * @return
     */
    public List<String> toHexFormat(String str, String... charset) {
        String encoding = "UTF-8";

        if (charset.length > 0) {
            encoding = charset[0];
        }

        byte[] bytes;
        try {
            bytes = str.getBytes(encoding);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        return toHexFormat(bytes);
    }

    /**
     * 将字节数组转换为16进制格式，类似UE的16进制模式
     *
     * @param bytes
     * @return
     */
    public List<String> toHexFormat(byte[] bytes) {
        List<String> hexList = new ArrayList<String>();

        int splitLength = 16;
        int splitHexLength = splitLength * 3;

        StringBuilder hexBuf = new StringBuilder();
        StringBuilder strBuf = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            byte b = bytes[i];
            int a = (int) b;
            char c = (char) b;
            String hex = Integer.toHexString(a);

            if (i > 0 && i % splitLength == 0) {
                hexList.add(hexBuf.toString() + "  " + strBuf.toString());
                hexBuf.delete(0, hexBuf.length());
                strBuf.delete(0, strBuf.length());
            }

            int hexLen = hex.length();
            if (hexLen == 1) {
                hexBuf.append("0").append(hex);
                if (c == '\n') {
                }
                strBuf.append(".");
            } else if (hexLen > 2) {
                hexBuf.append(hex.substring(hexLen - 2));
                strBuf.append(".");
            } else {
                hexBuf.append(hex);
                strBuf.append(c);
            }
            hexBuf.append(" ");
        }

        if (hexBuf.length() < splitHexLength) {
            for (int i = hexBuf.length(); i < splitHexLength; i++) {
                hexBuf.append(" ");
            }
        }

        hexList.add(hexBuf.toString() + "  " + strBuf.toString());

        return hexList;
    }


    public String filterString(String value,String expression){
        if(isNotBlank(value)){
              value.replaceAll(expression,"");
        }
        return value;
    }
}
