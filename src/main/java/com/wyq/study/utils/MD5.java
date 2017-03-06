package com.wyq.study.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.security.SecureRandom;

/**
 * MD5加密解密工具
 * 模块名称：study
 * 功能说明：<br>
 * 功能描述：<br>
 * 开发人员：wangyiqiang
 * 创建时间： 2017-03-05 下午1:28
 * 系统版本：1.0.0
 **/
public class MD5 {
    private static final String hex_chr = "0123456789abcdef";
    private static final String HEX_NUMS_STR = "0123456789ABCDEF";
    private static final Integer SALT_LENGTH = 12;

    /**
     * 将16进制字符串转换成字节数组
     *
     * @param hex
     * @return
     */
    public static byte[] hexStringToByte(String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] hexChars = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (HEX_NUMS_STR.indexOf(hexChars[pos]) << 4
                    | HEX_NUMS_STR.indexOf(hexChars[pos + 1]));
        }
        return result;
    }


    /**
     * 将指定byte数组转换成16进制字符串
     *
     * @param b
     * @return
     */
    public static String byteToHexString(byte[] b) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            hexString.append(hex.toUpperCase());
        }
        return hexString.toString();
    }

    /**
     * 验证口令是否合法
     *
     * @param password
     * @param passwordInDb
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static boolean validPassword(String password, String passwordInDb)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //将16进制字符串格式口令转换成字节数组
        byte[] pwdInDb = hexStringToByte(passwordInDb);
        //声明盐变量
        byte[] salt = new byte[SALT_LENGTH];
        //将盐从数据库中保存的口令字节数组中提取出来
        System.arraycopy(pwdInDb, 0, salt, 0, SALT_LENGTH);
        //创建消息摘要对象
        MessageDigest md = MessageDigest.getInstance("MD5");
        //将盐数据传入消息摘要对象
        md.update(salt);
        //将口令的数据传给消息摘要对象
        md.update(password.getBytes("UTF-8"));
        //生成输入口令的消息摘要
        byte[] digest = md.digest();
        //声明一个保存数据库中口令消息摘要的变量
        byte[] digestInDb = new byte[pwdInDb.length - SALT_LENGTH];
        //取得数据库中口令的消息摘要
        System.arraycopy(pwdInDb, SALT_LENGTH, digestInDb, 0, digestInDb.length);
        //比较根据输入口令生成的消息摘要和数据库中消息摘要是否相同
        if (Arrays.equals(digest, digestInDb)) {
            //口令正确返回口令匹配消息
            return true;
        } else {
            //口令不正确返回口令不匹配消息
            return false;
        }
    }


    /**
     * 获得加密后的16进制形式口令
     *
     * @param password
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String getEncryptedPwd(String password)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //声明加密后的口令数组变量
        byte[] pwd = null;
        //随机数生成器
        SecureRandom random = new SecureRandom();
        //声明盐数组变量
        byte[] salt = new byte[SALT_LENGTH];
        //将随机数放入盐变量中
        random.nextBytes(salt);

        //声明消息摘要对象
        MessageDigest md = null;
        //创建消息摘要
        md = MessageDigest.getInstance("MD5");
        //将盐数据传入消息摘要对象
        md.update(salt);
        //将口令的数据传给消息摘要对象
        md.update(password.getBytes("UTF-8"));
        //获得消息摘要的字节数组
        byte[] digest = md.digest();

        //因为要在口令的字节数组中存放盐，所以加上盐的字节长度
        pwd = new byte[digest.length + SALT_LENGTH];
        //将盐的字节拷贝到生成的加密口令字节数组的前12个字节，以便在验证口令时取出盐
        System.arraycopy(salt, 0, pwd, 0, SALT_LENGTH);
        //将消息摘要拷贝到加密口令字节数组从第13个字节开始的字节
        System.arraycopy(digest, 0, pwd, SALT_LENGTH, digest.length);
        //将字节数组格式加密后的口令转化为16进制字符串格式的口令
        return byteToHexString(pwd);
    }



    private String rhex(int num) {
        String str = "";

        for(int j = 0; j <= 3; ++j) {
            str = str + this.hex_chr.charAt(num >> j * 8 + 4 & 15) + this.hex_chr.charAt(num >> j * 8 & 15);
        }

        return str;
    }

    private int[] str2blks_MD5(String str) {
        int nblk = (str.length() + 8 >> 6) + 1;
        int[] blks = new int[nblk * 16];
        boolean i = false;

        int var5;
        for(var5 = 0; var5 < nblk * 16; ++var5) {
            blks[var5] = 0;
        }

        for(var5 = 0; var5 < str.length(); ++var5) {
            blks[var5 >> 2] |= str.charAt(var5) << var5 % 4 * 8;
        }

        blks[var5 >> 2] |= 128 << var5 % 4 * 8;
        blks[nblk * 16 - 2] = str.length() * 8;
        return blks;
    }

    private int add(int x, int y) {
        return (x & 2147483647) + (y & 2147483647) ^ x & -2147483648 ^ y & -2147483648;
    }

    private int rol(int num, int cnt) {
        return num << cnt | num >>> 32 - cnt;
    }

    private int cmn(int q, int a, int b, int x, int s, int t) {
        return this.add(this.rol(this.add(this.add(a, q), this.add(x, t)), s), b);
    }

    private int ff(int a, int b, int c, int d, int x, int s, int t) {
        return this.cmn(b & c | ~b & d, a, b, x, s, t);
    }

    private int gg(int a, int b, int c, int d, int x, int s, int t) {
        return this.cmn(b & d | c & ~d, a, b, x, s, t);
    }

    private int hh(int a, int b, int c, int d, int x, int s, int t) {
        return this.cmn(b ^ c ^ d, a, b, x, s, t);
    }

    private int ii(int a, int b, int c, int d, int x, int s, int t) {
        return this.cmn(c ^ (b | ~d), a, b, x, s, t);
    }

    public String calcMD5(String str) {
        int[] rets = this.md5main(str);
        return this.rhex(rets[1]) + this.rhex(rets[3]) + this.rhex(rets[2]) + this.rhex(rets[0]);
    }

    private int[] md5main(String str) {
        int[] x = this.str2blks_MD5(str);
        int a = 1732584193;
        int b = -271733879;
        int c = -1732584194;
        int d = 271733878;

        for(int i = 0; i < x.length; i += 16) {
            int olda = a;
            int oldb = b;
            int oldc = c;
            int oldd = d;
            a = this.ff(a, b, c, d, x[i + 0], 7, -680876936);
            d = this.ff(d, a, b, c, x[i + 1], 12, -389564586);
            c = this.ff(c, d, a, b, x[i + 2], 17, 606105819);
            b = this.ff(b, c, d, a, x[i + 3], 22, -1044525330);
            a = this.ff(a, b, c, d, x[i + 4], 7, -176418897);
            d = this.ff(d, a, b, c, x[i + 5], 12, 1200080426);
            c = this.ff(c, d, a, b, x[i + 6], 17, -1473231341);
            b = this.ff(b, c, d, a, x[i + 7], 22, -45705983);
            a = this.ff(a, b, c, d, x[i + 8], 7, 1770035416);
            d = this.ff(d, a, b, c, x[i + 9], 12, -1958414417);
            c = this.ff(c, d, a, b, x[i + 10], 17, -42063);
            b = this.ff(b, c, d, a, x[i + 11], 22, -1990404162);
            a = this.ff(a, b, c, d, x[i + 12], 7, 1804603682);
            d = this.ff(d, a, b, c, x[i + 13], 12, -40341101);
            c = this.ff(c, d, a, b, x[i + 14], 17, -1502002290);
            b = this.ff(b, c, d, a, x[i + 15], 22, 1236535329);
            a = this.gg(a, b, c, d, x[i + 1], 5, -165796510);
            d = this.gg(d, a, b, c, x[i + 6], 9, -1069501632);
            c = this.gg(c, d, a, b, x[i + 11], 14, 643717713);
            b = this.gg(b, c, d, a, x[i + 0], 20, -373897302);
            a = this.gg(a, b, c, d, x[i + 5], 5, -701558691);
            d = this.gg(d, a, b, c, x[i + 10], 9, 38016083);
            c = this.gg(c, d, a, b, x[i + 15], 14, -660478335);
            b = this.gg(b, c, d, a, x[i + 4], 20, -405537848);
            a = this.gg(a, b, c, d, x[i + 9], 5, 568446438);
            d = this.gg(d, a, b, c, x[i + 14], 9, -1019803690);
            c = this.gg(c, d, a, b, x[i + 3], 14, -187363961);
            b = this.gg(b, c, d, a, x[i + 8], 20, 1163531501);
            a = this.gg(a, b, c, d, x[i + 13], 5, -1444681467);
            d = this.gg(d, a, b, c, x[i + 2], 9, -51403784);
            c = this.gg(c, d, a, b, x[i + 7], 14, 1735328473);
            b = this.gg(b, c, d, a, x[i + 12], 20, -1926607734);
            a = this.hh(a, b, c, d, x[i + 5], 4, -378558);
            d = this.hh(d, a, b, c, x[i + 8], 11, -2022574463);
            c = this.hh(c, d, a, b, x[i + 11], 16, 1839030562);
            b = this.hh(b, c, d, a, x[i + 14], 23, -35309556);
            a = this.hh(a, b, c, d, x[i + 1], 4, -1530992060);
            d = this.hh(d, a, b, c, x[i + 4], 11, 1272893353);
            c = this.hh(c, d, a, b, x[i + 7], 16, -155497632);
            b = this.hh(b, c, d, a, x[i + 10], 23, -1094730640);
            a = this.hh(a, b, c, d, x[i + 13], 4, 681279174);
            d = this.hh(d, a, b, c, x[i + 0], 11, -358537222);
            c = this.hh(c, d, a, b, x[i + 3], 16, -722521979);
            b = this.hh(b, c, d, a, x[i + 6], 23, 76029189);
            a = this.hh(a, b, c, d, x[i + 9], 4, -640364487);
            d = this.hh(d, a, b, c, x[i + 12], 11, -421815835);
            c = this.hh(c, d, a, b, x[i + 15], 16, 530742520);
            b = this.hh(b, c, d, a, x[i + 2], 23, -995338651);
            a = this.ii(a, b, c, d, x[i + 0], 6, -198630844);
            d = this.ii(d, a, b, c, x[i + 7], 10, 1126891415);
            c = this.ii(c, d, a, b, x[i + 14], 15, -1416354905);
            b = this.ii(b, c, d, a, x[i + 5], 21, -57434055);
            a = this.ii(a, b, c, d, x[i + 12], 6, 1700485571);
            d = this.ii(d, a, b, c, x[i + 3], 10, -1894986606);
            c = this.ii(c, d, a, b, x[i + 10], 15, -1051523);
            b = this.ii(b, c, d, a, x[i + 1], 21, -2054922799);
            a = this.ii(a, b, c, d, x[i + 8], 6, 1873313359);
            d = this.ii(d, a, b, c, x[i + 15], 10, -30611744);
            c = this.ii(c, d, a, b, x[i + 6], 15, -1560198380);
            b = this.ii(b, c, d, a, x[i + 13], 21, 1309151649);
            a = this.ii(a, b, c, d, x[i + 4], 6, -145523070);
            d = this.ii(d, a, b, c, x[i + 11], 10, -1120210379);
            c = this.ii(c, d, a, b, x[i + 2], 15, 718787259);
            b = this.ii(b, c, d, a, x[i + 9], 21, -343485551);
            a = this.add(a, olda);
            b = this.add(b, oldb);
            c = this.add(c, oldc);
            d = this.add(d, oldd);
        }

        return new int[]{a, b, c, d};
    }

    public String dvBbs16(String str) {
        int[] rets = this.md5main(str);
        return this.rhex(rets[1]) + this.rhex(rets[2]);
    }

    public String dvBbs32(String str) {
        int[] rets = this.md5main(str);
        return this.rhex(rets[0]) + this.rhex(rets[1]) + this.rhex(rets[2]) + this.rhex(rets[3]);
    }

}
