package kr.co.scp.util;

import lombok.extern.slf4j.Slf4j;

/**
 * 삼천리 암호화 유틸리티
 * @author dhj
 */
@Slf4j
public class SLCEncryptUtil {

    /**
     * 암호화 유틸리티 (삼천리)
     * @param plainText
     * @return
     */
    public static String EncryptStr(String plainText) {

        String encriptStr = "";
        if (plainText.length() < 1) {
            return null;
        }

        try {

            char[] chs = plainText.toCharArray();
            int i = 0;
            int value;
            int quote, remainder;

            for (char ch : chs) {

                value = (int) ch;
                value &= 0xff;

                if ((i++ % 2) == 0) {

                    quote = ((value * 15) / 256) + 1;

                    remainder = ((value * 15) % 256) + 1;

                } else {

                    remainder = ((value * 15) / 256) + 1;

                    quote = ((value * 15) % 256) + 1;

                }


                if (quote < 16) {

                    encriptStr += String.format("0%X", quote);

                } else {

                    encriptStr += String.format("%X", quote);

                }


                if (remainder < 16) {

                    encriptStr += String.format("0%X", remainder);

                } else {

                    encriptStr += String.format("%X", remainder);

                }

            }

        } catch (Exception ex) {
            log.warn("EncryptStr: {}", ex.getMessage());
        }

        return encriptStr;

    }

    public static void main(String[] args) {
        System.out.println("pwd : " + SLCEncryptUtil.EncryptStr("1234"));
    }
}
