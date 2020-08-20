package com.namviet.vtvtravel.encode;

import android.util.Base64;

import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

public class RSA {
    public static final String KEY_ALGORITHM = "RSA";
    public static final String SIGNATURE_ALGORITHM = "SHA256withRSA";

    public static final String PUBLIC_KEY = "MIICITANBgkqhkiG9w0BAQEFAAOCAg4AMIICCQKCAgB5NAngnynpzTcKfxHDLqZx\n" +
            "ooAGOFZDW+NBDwA6IkgstbePPWHs07lLYHlCbGF6BsX3bBLhun2eOhlVAplQNd0/\n" +
            "FeoL1Oy96RRZ73FVl9ryWkp9m0zqfXgKyk6c375wY6p1ZZZ8EYCs06pVQw+5BHwl\n" +
            "F3J4NvosLp4ULqB525u+SxraAkco4+8qe9J0rbaKVzbJi2L4rG2oZM01s0F1JYKM\n" +
            "ZH+Kn6fThQPoF4wznCymZca3iBFgc31yTXjQMTK3x5py+x23R0fDqeh+8KzUQ83W\n" +
            "hwEMkyr+4vxciU9K7LCF+uuppfwrA4DUrsgPUogm0EWOw6kYTrW9E7p6diOFjB42\n" +
            "sB/tiAUFVbNVOSzar6pqYp9L1dKQilNTsMcHN0MuyoHux1DUreHLIidFNbwnqV4C\n" +
            "htbg6+kHP6fqbD+BX1sa1yPx0eQtkL8wBCwTS7vLBKDhSPFt0TzPnd6YNeRzl2ob\n" +
            "5DMZP6e0wvL7mXBaZdrcNT4SyohAQjRc0urfU1FG8XOTuU68D48gxpR/jvf66lIo\n" +
            "/TNJrZw5kyKfIkSftMQs9a2awRKMuvvuYOH45UYhW0uYDrvKTclBu07nAR2ioxaL\n" +
            "r36wu+ZvZLL5G4JnH9H3cqeNXUDqHY/lSFldTyRKfXikYZ9QhNwcsozdMagXwRCM\n" +
            "aAtA2TYt5T1pw9fX1uO7ywIDAQAB";
    public static final String PRIVATE_KEY = "MIIJJgIBAAKCAgB5NAngnynpzTcKfxHDLqZxooAGOFZDW+NBDwA6IkgstbePPWHs\n" +
            "07lLYHlCbGF6BsX3bBLhun2eOhlVAplQNd0/FeoL1Oy96RRZ73FVl9ryWkp9m0zq\n" +
            "fXgKyk6c375wY6p1ZZZ8EYCs06pVQw+5BHwlF3J4NvosLp4ULqB525u+SxraAkco\n" +
            "4+8qe9J0rbaKVzbJi2L4rG2oZM01s0F1JYKMZH+Kn6fThQPoF4wznCymZca3iBFg\n" +
            "c31yTXjQMTK3x5py+x23R0fDqeh+8KzUQ83WhwEMkyr+4vxciU9K7LCF+uuppfwr\n" +
            "A4DUrsgPUogm0EWOw6kYTrW9E7p6diOFjB42sB/tiAUFVbNVOSzar6pqYp9L1dKQ\n" +
            "ilNTsMcHN0MuyoHux1DUreHLIidFNbwnqV4Chtbg6+kHP6fqbD+BX1sa1yPx0eQt\n" +
            "kL8wBCwTS7vLBKDhSPFt0TzPnd6YNeRzl2ob5DMZP6e0wvL7mXBaZdrcNT4SyohA\n" +
            "QjRc0urfU1FG8XOTuU68D48gxpR/jvf66lIo/TNJrZw5kyKfIkSftMQs9a2awRKM\n" +
            "uvvuYOH45UYhW0uYDrvKTclBu07nAR2ioxaLr36wu+ZvZLL5G4JnH9H3cqeNXUDq\n" +
            "HY/lSFldTyRKfXikYZ9QhNwcsozdMagXwRCMaAtA2TYt5T1pw9fX1uO7ywIDAQAB\n" +
            "AoICAFkWOehjtSAH2dwaOEkZwfemdKmdwTIzmR9Z0lXomtpaGvjrKk4z9W+FErwT\n" +
            "A7b4wYwyYNKQDANAOxx0E5KUdPIIvoZB1F1ms7HDQK+kNaeLXFNk0i1NWe28c/n0\n" +
            "r3rWlxVBPVImX0kpL3sKFsVsmX9984KPDmVpQoIdH5FSLPC7LZpusDVLDKFScAeZ\n" +
            "I6IdpsSPcojL3LI4nWlKP+PGVWWx/Nk8rtPXYl626DOLKHqKNnzyQ6t8WAmrDhEh\n" +
            "CGFzEoBPObFccFBOU/jyGGbRC7wScV/30FOXLd0qhCDTRpDENXH18hr/sTMi4SP6\n" +
            "Ct9UmLvt7x1RxV2b4aZfrY5cxltxn/f5+tFC8rvTN/MWGzcz1iQYTH294GW3FeaY\n" +
            "FVcZ672G7VNuScHhnq72Rv6KUwvINBBKLOkwjlCmEc/7OBku5H6lQkq2IiFLZFya\n" +
            "Dg8rQWfLvNwaeXcUs0SyKkMhURjMpo8V1SCSpPf9HUsw+L/ncoSv+i6H4TkXBO2e\n" +
            "WW6CxjdlNUIMBjB7BkD0ocLi7z3Jaei79O2ypqzXzupHY1cRwFSDNy5gUCQt+rbR\n" +
            "ptc34gHPU0u7fJJX6+KZYoBi8ZhxB+MpBuF9adssmwfjH81SdUpK11V5T3RgH735\n" +
            "NQlejIwH62cMHJ0HOdBoX52u03+bqNGnU7lOIkUSQxHa54/JAoIBAQDE1CNz5cAI\n" +
            "OY/JepuEoZvdQu/W+ScoBIQZe9wZw+6q5BxnU4ZAVw9LGetvcpPoT2Ea8PYCgRgh\n" +
            "BFt8nbex4dhgJmHMv30wGYYRXiHTDZe2Yg8Y/Gv2JCWKm4RoAXQEy79nfTZes5yr\n" +
            "x5hYHY0jT6wUtYuYnCQkwf5yPblboklLVF6rkGulJjz39YM8BNoeOKGlNYqtNPtN\n" +
            "AwAkFi9Q+1Sls5ZlZ/fOG9iYz9LjrYg1MQmz95cNafXftl7GzIxsilZt78G58YGg\n" +
            "z4EOZoCrY6xW30MySwZbGQqH67ca4MZDD8Odord9bdmLd9MaPS/0yUpziVUVXJP/\n" +
            "K0T1h2EiVFJfAoIBAQCdo8xHbwyZZwzAIYSfKGN5Ah8Gv6hpLbseCtQjy4Q3LRMK\n" +
            "18pEIDSn12E90w/KYXyfjLJkTkYxk6VcXLddMaQAA21D5WZiA4ZH3lwF3rWF0gIn\n" +
            "g6mdH0Gu/6l+H4MIMBjz/YU8paQJLH1gAIG1gVCDEhiRW+f6/U77BGAgeKg5YU0x\n" +
            "20rNWhubcIAsxyNYL8gUjDlyMnDy72q1DnjxwWcCRyjSlT8ggd5nouc28vx7VbvK\n" +
            "eXwXf2FYu5le4Rj9efeKvGL9VT/huiFjBpus0B5WsHNdRh/1LhNQqRh5hADy33tR\n" +
            "BfZAYw8Oc2YHlJ+KgESqJA0QddzJWUq4qaY7bUYVAoIBAFUa6M1nDPZBdVOHqRDb\n" +
            "E9rC45NPHcgm07t1G8EkP+EySrm+mrDdM+akKXGB2EQ9dl5AzA5fJUC5cplaVKAc\n" +
            "aAOC9Qyjy4xtrNN7oSJxoSSiF6cVFdVS4cyh28lgv3qHYpZrxIOEI/z+i3emJ22a\n" +
            "OGyn5tkwwkwGeW+KEsh1Tj29GEYUZwb8lqYcSQc9jdY9ZF9b3D9yRiqRXpE76m+Y\n" +
            "jIwtL51fK/25epjwW15InTlqn6hqKK0LIN3/K9HHhfw0gJR+c4Rj0UnoDwRb+8yf\n" +
            "h1f0p0hqui/PjwcgO4bqo69BdLKHNdGNGuA8ClIxHLg6BPAZ9GkNI4zUd7e2NqCy\n" +
            "Vv8CggEAHwKFwNebhuG6pGDzkcuEJd3A9LbnDims0XAX4yK505DOWiew5k3fjdTU\n" +
            "kDDgvlMHDOvEe+7Z6A6/TLgAahjFxirmZ6DMzyMaWzDmsVDqLHTvb4564ymu5hns\n" +
            "4Z6ZTBcKEZUhjJiY+CUEVMpEucvCZsY8hvupWrpRuxdVQY36qa72XIRi5pp/K8ip\n" +
            "riVOUp6zoTckZc5+YYuvVgaOzB/bf5s38JTjYiWp9oEZMroB6NEoq9/SBV1NbQMa\n" +
            "XIuzOsmvZF9CqcsHUgQnZS97mDcogK0dRI/y4Ww66HcbFWxeM2i6/WlK08eowpL4\n" +
            "0yztZXVuDHojB8XntMQX6tOUm8QXMQKCAQA9ugWCFS7HF9LvJLrqZJUs4/0aTw34\n" +
            "hLZnZpk87BEg11+5V14y60qOG1IQ6Z65cspDQk8zjGC2GHc6jejiKbqFvpfTpmxW\n" +
            "jt7tlDZDK3l0uNjPfRugQqVUJAIZiKUpTodlfN815v4SUZZuoOzHFGX6iR84ouPm\n" +
            "Liqm8QclA9DyZnBWAUX2q+F0JQnkknIhLW0PoH8BHP8a/7Nc2R+2wqJd1b1rBH3K\n" +
            "Z7J9TtgcQgI2MCKj/OdCIQ9ErTkcZQsY+FRZTPOhc6qoCo707l3LFQSqO1CbNzoC\n" +
            "SU5j8Rv5odjx+A/s1kpbSJzD0d+PLiYtr4pOnLR3jkxHa/IjVF+O9yfR";

    public static byte[] decryptBASE64(String key) throws Exception {
        return Base64.decode(key, Base64.DEFAULT);
    }

    public static String encryptBASE64(byte[] key) throws Exception {
        return Base64.encodeToString(key, Base64.DEFAULT);
    }


    public static String sign(byte[] data, String privateKey) throws Exception {

        byte[] keyBytes = decryptBASE64(privateKey);

        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);

        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);

        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(priKey);
        signature.update(data);

        return encryptBASE64(signature.sign());
    }


    public static boolean verify(byte[] data, String publicKey, String sign)
            throws Exception {

        byte[] keyBytes = decryptBASE64(publicKey);

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);

        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

        PublicKey pubKey = keyFactory.generatePublic(keySpec);

        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(pubKey);
        signature.update(data);

        return signature.verify(decryptBASE64(sign));
    }


    public static byte[] decryptByPrivateKey(byte[] data, String key)
            throws Exception {

        byte[] keyBytes = decryptBASE64(key);

        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        return cipher.doFinal(data);
    }


    public static byte[] decryptByPublicKey(byte[] data, String key)
            throws Exception {

        byte[] keyBytes = decryptBASE64(key);

        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);

        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);

        return cipher.doFinal(data);
    }


    public static byte[] encryptByPublicKey(byte[] data, String key)
            throws Exception {

        byte[] keyBytes = decryptBASE64(key);

        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);

        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        return cipher.doFinal(data);
    }


    public static byte[] encryptByPrivateKey(byte[] data, String key)
            throws Exception {

        byte[] keyBytes = decryptBASE64(key);

        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);

        return cipher.doFinal(data);
    }

    public static String getPrivateKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);

        return encryptBASE64(key.getEncoded());
    }

    public static String getPublicKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);

        return encryptBASE64(key.getEncoded());
    }


    public static Map<String, Object> initKey() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator
                .getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);

        KeyPair keyPair = keyPairGen.generateKeyPair();

        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        Map<String, Object> keyMap = new HashMap<String, Object>(2);

        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    public static PublicKey getPublicKey(String MODULUS, String EXPONENT) throws Exception {
        byte[] modulusBytes = Base64.decode(MODULUS, 0);
        byte[] exponentBytes = Base64.decode(EXPONENT, 0);

        BigInteger modulus = new BigInteger(1, (modulusBytes));
        BigInteger exponent = new BigInteger(1, (exponentBytes));

        RSAPublicKeySpec spec = new RSAPublicKeySpec(modulus, exponent);
        KeyFactory kf = KeyFactory.getInstance(RSA.KEY_ALGORITHM);
        return kf.generatePublic(spec);
    }

    public static byte[] encrypt(Key publicKey, String s) throws Exception {
        byte[] byteData = s.getBytes();
        Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedData = cipher.doFinal(byteData);


        return encryptedData;
    }
}
