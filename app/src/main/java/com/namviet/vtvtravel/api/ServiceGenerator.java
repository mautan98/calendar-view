package com.namviet.vtvtravel.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

//    public static final String API_BASE_URL = "http://cdn1.travel.onex.vn/";
    public static final String API_BASE_URL = "http://cdn.vtvtravel.vn/";

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass){
        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }

}










//package com.namviet.vtvtravel.api;
//
//        import android.annotation.SuppressLint;
//
//        import com.namviet.vtvtravel.app.MyApplication;
//        import com.namviet.vtvtravel.model.Account;
//
//        import java.io.IOException;
//        import java.security.KeyManagementException;
//        import java.security.NoSuchAlgorithmException;
//        import java.security.cert.CertificateException;
//        import java.util.concurrent.TimeUnit;
//
//        import javax.net.ssl.SSLContext;
//        import javax.net.ssl.SSLSocketFactory;
//        import javax.net.ssl.TrustManager;
//        import javax.net.ssl.X509TrustManager;
//
//        import okhttp3.Interceptor;
//        import okhttp3.OkHttpClient;
//        import okhttp3.Request;
//        import okhttp3.Response;
//        import retrofit2.Retrofit;
//        import retrofit2.converter.gson.GsonConverterFactory;
//
//public class ServiceGenerator {
//
//    private static OkHttpClient okHttpClient = null;
//
//    //    public static final String API_BASE_URL = "http://cdn1.travel.onex.vn/";
//    public static final String API_BASE_URL = "http://cdn.vtvtravel.vn/";
//
//    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
//
//    private static Retrofit.Builder builder =
//            new Retrofit.Builder()
//                    .baseUrl(API_BASE_URL)
//                    .addConverterFactory(GsonConverterFactory.create());
//
//    public static <S> S createService(Class<S> serviceClass){
//        Retrofit retrofit = builder.client(unitHttpClient(okHttpClient)).build();
//        return retrofit.create(serviceClass);
//    }
//
//
//
//    private static OkHttpClient unitHttpClient(OkHttpClient httpClient) {
//
//        if (httpClient == null) {
//            final Account mInfo = MyApplication.getInstance().getAccount();
//            if (null != mInfo && null != mInfo.getToken() && 0 < mInfo.getToken().length()) {
//
//                // Install the all-trusting trust manager
//                final SSLContext sslContext;
//                try {
//
//                    // Create a trust manager that does not validate certificate chains
//                    final TrustManager[] trustAllCerts = new TrustManager[]{
//                            new X509TrustManager() {
//                                @Override
//                                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
//                                }
//
//                                @Override
//                                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
//                                }
//
//                                @Override
//                                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
//                                    return new java.security.cert.X509Certificate[]{};
//                                }
//                            }
//                    };
//
//                    sslContext = SSLContext.getInstance("SSL");
//                    sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
//                    // Create an ssl socket factory with our all-trusting manager
//                    final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
//
//
//                    httpClient = new OkHttpClient.Builder()
//                            .addInterceptor(
//                                    new Interceptor() {
//                                        @Override
//                                        public Response intercept(Chain chain) throws IOException {
//                                            Request request = chain.request().newBuilder()
//                                                    .addHeader("Content-Type", "Application/JSON")
//                                                    .addHeader(WSConfig.KeyParam.HEADER_TOKEN, mInfo.getToken()).build();
//                                            return chain.proceed(request);
//                                        }
//                                    })
//                            .addInterceptor(new LoggingInterceptor())
//                            .sslSocketFactory(sslSocketFactory)
//                            .hostnameVerifier((hostname, session) -> true)
//                            .connectTimeout(1, TimeUnit.MINUTES)
//                            .readTimeout(30, TimeUnit.SECONDS)
//                            .writeTimeout(15, TimeUnit.SECONDS)
//                            .build();
//                } catch (NoSuchAlgorithmException | KeyManagementException e) {
//                    e.printStackTrace();
//                }
//
//
//            } else {
//                //API base param
//                final SSLContext sslContext;
//                try {
//
//                    // Create a trust manager that does not validate certificate chains
//                    final TrustManager[] trustAllCerts = new TrustManager[]{
//                            new X509TrustManager() {
//                                @Override
//                                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
//                                }
//
//                                @Override
//                                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
//                                }
//
//                                @Override
//                                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
//                                    return new java.security.cert.X509Certificate[]{};
//                                }
//                            }
//                    };
//
//                    sslContext = SSLContext.getInstance("SSL");
//                    sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
//                    // Create an ssl socket factory with our all-trusting manager
//                    final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
//
//                    httpClient = new OkHttpClient.Builder()
//                            .addInterceptor(
//                                    new Interceptor() {
//                                        @SuppressLint("NewApi")
//                                        @Override
//                                        public Response intercept(Chain chain) throws IOException {
////                                        String userStr = "Test mã hóa RSA";
////                                        byte[] encodeStr = null;
////                                        try {
////
////                                            encodeStr = RSA.encryptByPublicKey(userStr.getBytes(), RSA.PUBLIC_KEY);
////                                            L.e("btEncode " + RSA.encryptBASE64(encodeStr));
////
////                                            L.e("Decode " + new String(RSA.decryptByPrivateKey(encodeStr, RSA.PRIVATE_KEY)));
////                                        } catch (Exception e) {
////                                            L.e("btEncode " + e.toString());
////                                        }
//                                            Request request = null;
//                                            try {
//                                                request = chain.request().newBuilder()
////                                                    .addHeader("Content-Type", "Application/JSON")
//                                                        .addHeader("Content-Type", "Application/JSON").build();
////                                                    .addHeader("nvtravel-signature", RSA.encryptBASE64(encodeStr)).build();
//                                            } catch (Exception e) {
//                                                e.printStackTrace();
//                                            }
//                                            return chain.proceed(request);
//                                        }
//                                    })
//                            .addInterceptor(new LoggingInterceptor())
//                            .sslSocketFactory(sslSocketFactory)
//                            .hostnameVerifier((hostname, session) -> true)
//                            .build();
//
//                } catch (NoSuchAlgorithmException | KeyManagementException e) {
//                    e.printStackTrace();
//                }
//
//
//
//            }
//        }
//
//
//        return httpClient;
//    }
//
//}

