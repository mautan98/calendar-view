package com.namviet.vtvtravel.api;


import android.annotation.SuppressLint;

import com.baseapp.utils.L;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.namviet.vtvtravel.app.MyApplication;
import com.namviet.vtvtravel.encode.RSA;
import com.namviet.vtvtravel.encode.RsaExample;
import com.namviet.vtvtravel.model.Account;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class TravelFactory {
    private static OkHttpClient okHttpClient = null;
    public byte[] encodeData = null;
    static Gson gson = new GsonBuilder()
            .setLenient()
            .create();
    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(WSConfig.HOST);

    private static Retrofit.Builder builderAcc =
            new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(WSConfig.HOST_ACC);

    private static Retrofit.Builder builderChat =
            new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(WSConfig.HOST_CHAT);


    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder.client(unitHttpClient(okHttpClient)).build();
        return retrofit.create(serviceClass);
    }

    public static <S> S createServiceAcc(Class<S> serviceClass) {
        Retrofit retrofit = builderAcc.client(unitHttpClient(okHttpClient)).build();
        return retrofit.create(serviceClass);
    }

    public static <S> S createServiceAccNoneToken(Class<S> serviceClass) {
        Retrofit retrofit = builderAcc.client(unitHttpClientNoneToken(okHttpClient)).build();
        return retrofit.create(serviceClass);
    }

    public static <S> S createServiceChat(Class<S> serviceClass) {
        Retrofit retrofit = builderChat.client(unitHttpClient(okHttpClient)).build();
        return retrofit.create(serviceClass);
    }

    private static OkHttpClient unitHttpClient(OkHttpClient httpClient) {

        if (httpClient == null) {
            final Account mInfo = MyApplication.getInstance().getAccount();
            if (null != mInfo && null != mInfo.getToken() && 0 < mInfo.getToken().length()) {

                // Install the all-trusting trust manager
                final SSLContext sslContext;
                try {

                    // Create a trust manager that does not validate certificate chains
                    final TrustManager[] trustAllCerts = new TrustManager[]{
                            new X509TrustManager() {
                                @Override
                                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                                }

                                @Override
                                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                                }

                                @Override
                                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                                    return new java.security.cert.X509Certificate[]{};
                                }
                            }
                    };

                    sslContext = SSLContext.getInstance("SSL");
                    sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
                    // Create an ssl socket factory with our all-trusting manager
                    final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();


                    httpClient = new OkHttpClient.Builder()
                            .addInterceptor(
                                    new Interceptor() {
                                        @Override
                                        public Response intercept(Chain chain) throws IOException {
                                            Request request = chain.request().newBuilder()
                                                    .addHeader("Content-Type", "Application/JSON")
                                                    .addHeader(WSConfig.KeyParam.HEADER_TOKEN, mInfo.getToken()).build();
                                            return chain.proceed(request);
                                        }
                                    })
                            .addInterceptor(new LoggingInterceptor())
                            .sslSocketFactory(sslSocketFactory)
                            .hostnameVerifier((hostname, session) -> true)
                            .connectTimeout(1, TimeUnit.MINUTES)
                            .readTimeout(30, TimeUnit.SECONDS)
                            .writeTimeout(15, TimeUnit.SECONDS)
                            .build();
                } catch (NoSuchAlgorithmException | KeyManagementException e) {
                    e.printStackTrace();
                }


            } else {
                //API base param
                final SSLContext sslContext;
                try {

                    // Create a trust manager that does not validate certificate chains
                    final TrustManager[] trustAllCerts = new TrustManager[]{
                            new X509TrustManager() {
                                @Override
                                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                                }

                                @Override
                                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                                }

                                @Override
                                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                                    return new java.security.cert.X509Certificate[]{};
                                }
                            }
                    };

                    sslContext = SSLContext.getInstance("SSL");
                    sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
                    // Create an ssl socket factory with our all-trusting manager
                    final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

                    httpClient = new OkHttpClient.Builder()
                            .addInterceptor(
                                    new Interceptor() {
                                        @SuppressLint("NewApi")
                                        @Override
                                        public Response intercept(Chain chain) throws IOException {
//                                        String userStr = "Test mã hóa RSA";
//                                        byte[] encodeStr = null;
//                                        try {
//
//                                            encodeStr = RSA.encryptByPublicKey(userStr.getBytes(), RSA.PUBLIC_KEY);
//                                            L.e("btEncode " + RSA.encryptBASE64(encodeStr));
//
//                                            L.e("Decode " + new String(RSA.decryptByPrivateKey(encodeStr, RSA.PRIVATE_KEY)));
//                                        } catch (Exception e) {
//                                            L.e("btEncode " + e.toString());
//                                        }
                                            Request request = null;
                                            try {
                                                request = chain.request().newBuilder()
//                                                    .addHeader("Content-Type", "Application/JSON")
                                                        .addHeader("Content-Type", "Application/JSON").build();
//                                                    .addHeader("nvtravel-signature", RSA.encryptBASE64(encodeStr)).build();
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            return chain.proceed(request);
                                        }
                                    })
                            .addInterceptor(new LoggingInterceptor())
                            .sslSocketFactory(sslSocketFactory)
                            .hostnameVerifier((hostname, session) -> true)
                            .build();

                } catch (NoSuchAlgorithmException | KeyManagementException e) {
                    e.printStackTrace();
                }



            }
        }


        return httpClient;
    }

    private static OkHttpClient unitHttpClientNoneToken(OkHttpClient httpClient) {

        if (httpClient == null) {
            final Account mInfo = MyApplication.getInstance().getAccount();
            if (null != mInfo && null != mInfo.getToken() && 0 < mInfo.getToken().length()) {

                // Install the all-trusting trust manager
                final SSLContext sslContext;
                try {

                    // Create a trust manager that does not validate certificate chains
                    final TrustManager[] trustAllCerts = new TrustManager[]{
                            new X509TrustManager() {
                                @Override
                                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                                }

                                @Override
                                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                                }

                                @Override
                                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                                    return new java.security.cert.X509Certificate[]{};
                                }
                            }
                    };

                    sslContext = SSLContext.getInstance("SSL");
                    sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
                    // Create an ssl socket factory with our all-trusting manager
                    final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();


                    httpClient = new OkHttpClient.Builder()
                            .addInterceptor(
                                    new Interceptor() {
                                        @Override
                                        public Response intercept(Chain chain) throws IOException {
                                            Request request = chain.request().newBuilder()
                                                    .addHeader("Content-Type", "Application/JSON")
                                                    .build();
                                            return chain.proceed(request);
                                        }
                                    })
                            .addInterceptor(new LoggingInterceptor())
                            .sslSocketFactory(sslSocketFactory)
                            .hostnameVerifier((hostname, session) -> true)
                            .build();
                } catch (NoSuchAlgorithmException | KeyManagementException e) {
                    e.printStackTrace();
                }


            } else {
                //API base param
                final SSLContext sslContext;
                try {

                    // Create a trust manager that does not validate certificate chains
                    final TrustManager[] trustAllCerts = new TrustManager[]{
                            new X509TrustManager() {
                                @Override
                                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                                }

                                @Override
                                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                                }

                                @Override
                                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                                    return new java.security.cert.X509Certificate[]{};
                                }
                            }
                    };

                    sslContext = SSLContext.getInstance("SSL");
                    sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
                    // Create an ssl socket factory with our all-trusting manager
                    final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

                    httpClient = new OkHttpClient.Builder()
                            .addInterceptor(
                                    new Interceptor() {
                                        @SuppressLint("NewApi")
                                        @Override
                                        public Response intercept(Chain chain) throws IOException {
//                                        String userStr = "Test mã hóa RSA";
//                                        byte[] encodeStr = null;
//                                        try {
//
//                                            encodeStr = RSA.encryptByPublicKey(userStr.getBytes(), RSA.PUBLIC_KEY);
//                                            L.e("btEncode " + RSA.encryptBASE64(encodeStr));
//
//                                            L.e("Decode " + new String(RSA.decryptByPrivateKey(encodeStr, RSA.PRIVATE_KEY)));
//                                        } catch (Exception e) {
//                                            L.e("btEncode " + e.toString());
//                                        }
                                            Request request = null;
                                            try {
                                                request = chain.request().newBuilder()
//                                                    .addHeader("Content-Type", "Application/JSON")
                                                        .addHeader("Content-Type", "Application/JSON").build();
//                                                    .addHeader("nvtravel-signature", RSA.encryptBASE64(encodeStr)).build();
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            return chain.proceed(request);
                                        }
                                    })
                            .addInterceptor(new LoggingInterceptor())
                            .sslSocketFactory(sslSocketFactory)
                            .hostnameVerifier((hostname, session) -> true)
                            .connectTimeout(1, TimeUnit.MINUTES)
                            .readTimeout(30, TimeUnit.SECONDS)
                            .writeTimeout(15, TimeUnit.SECONDS)
                            .build();

                } catch (NoSuchAlgorithmException | KeyManagementException e) {
                    e.printStackTrace();
                }



            }
        }


        return httpClient;
    }
}
