package com.yzj.jep.app.sys.util;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;

/**
 * @title: RsaUtil
 * @Author Whx
 * @Date: 2023/5/8 10:20
 * @Version 1.0
 */
public class RsaUtil {

    private static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDC8l9UJ+sb+os5wcAnc1S0dgpdzAtxfsBJwFvbFMy1vPsfwQPmLXKwrX84f8Zy5W2ghS4CQbGVB5+bDl+K8zFhRywsq0DnRw85J8nHOoG5aSk8jfzeIuWK6XZ4/h17akHMKYObjrPs63SbGAnSQ/9DhfEPd/3GPZeITKwDq6MHDQIDAQAB";
    private static final String PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMLyX1Qn6xv6iznBwCdzVLR2Cl3MC3F+wEnAW9sUzLW8+x/BA+YtcrCtfzh/xnLlbaCFLgJBsZUHn5sOX4rzMWFHLCyrQOdHDzknycc6gblpKTyN/N4i5Yrpdnj+HXtqQcwpg5uOs+zrdJsYCdJD/0OF8Q93/cY9l4hMrAOrowcNAgMBAAECgYAIWYc5vIf2Z9HExIataeQr4o5pHA81aj9kKUgF5yspcZGz93lqR3INHTFAw0BFtYvvfyTMVfH8Nxpf8sqm0enAdLVDgtkjRdomSG6XLKJpSir4ARx/eX38+gkegugBoHeGgGx2t+nrsfjZAexNotZJDwj4Vjgf6epoPi145pnypQJBAO6dogtM0Be8eJNQfwjiBVVp91+rZlGEk0ScYapMfz988cKWlyMZ+8rJqWpNSOQak+sUUWh5Ed/zDW7r9N5vnjsCQQDRJkd9sa99oEKrjx1md32rNS1HcZvAdudod+jQE1SWpTWbdeV4CZtoqqwdEGPg26qmn+CzFxHAYimk9U/SI7NXAkAFoA+25nutY5MQHgCwq+tmRRZA2iCSYWa7YlhnFGCXlZF5feVYPX+TG0f0KphCUGIWU6dLqDcEqY0yhTuDluwLAkEAmnQYa5yiqrWFRsjDv34S0jLNI2ukx/eHYUDcL313BGExO4NPWNxPZnZT8IrAF7hhR/fdqqQoudhERMvcjKVMtQJAbxvh6eEixpjDJ1dvUzSyzxC/HavQCyYwU/w02oaYLgfjF0zG+elVZKsQjOPU/dXpQlTcHKALSoFwLoRtEuaQZA==";

    /**
     * 解密密码
     *
     * @param encodePd 前端使用base64加密，公钥加密，私钥解密
     * @return
     */
    public static String decode(String encodePd) {
        RSA rsa = SecureUtil.rsa(PRIVATE_KEY, PUBLIC_KEY);
        byte[] decrypt = rsa.decrypt(encodePd, KeyType.PrivateKey);
        return new String(decrypt);
    }
}
