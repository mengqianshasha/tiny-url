package edu.northeastern.tinyurl.util;

public class ShortUrlGenerator {
    private static final int RADIX = 62;
    private static final String CODEC = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static String base62Encoding(long param) {
        StringBuffer sb = new StringBuffer();
        while(param > 0) {
            sb.append(CODEC.charAt((int)param % RADIX));
            param /= RADIX;
        }

        return sb.toString();
    }
}
