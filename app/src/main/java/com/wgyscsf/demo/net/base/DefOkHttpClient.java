package com.wgyscsf.demo.net.base;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * ============================================================
 * 作 者 :    wgyscsf@163.com
 * 更新时间 ：2019/06/20 15:02
 * 描 述 ：
 * ============================================================
 */
public class DefOkHttpClient {
    public static final long TIMEOUT_CONN = 10;
    public static final long READ_CONN = 10;
    public static final long WRITE_CONN = 10;
    private static final DefOkHttpClient ourInstance = new DefOkHttpClient();

    private DefOkHttpClient() {

    }

    public static DefOkHttpClient getInstance() {
        return ourInstance;
    }

    /**
     * 临时解决方案
     *
     * @return
     */
    private static SSLSocketFactory getUnsafeSSLSocketFactory() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 构造一个通用的builder
     *
     * @return
     */
    @NotNull
    public OkHttpClient.Builder getDefaultOkHttpClientBuilder() {
        //系统默认的日志系统，打印不没关，但是便于复制。
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(s -> {
            Log.d("OkHttp", s);
        });
        httpLoggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(TIMEOUT_CONN, TimeUnit.SECONDS)
                .readTimeout(READ_CONN, TimeUnit.SECONDS)
                .writeTimeout(WRITE_CONN, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                //给后端传递head参数
                .addInterceptor(new HeaderInterceptor())
                //日志
                .addInterceptor(httpLoggingInterceptor);

        //temp
//        SSLSocketFactory unsafeSSLSocketFactory = getUnsafeSSLSocketFactory();
//        if (unsafeSSLSocketFactory != null) {
//            builder.sslSocketFactory(unsafeSSLSocketFactory)
//                    .hostnameVerifier((hostname, session) -> true);
//        }
        return builder;
    }

    /**
     * ws 注意维持心跳的逻辑
     *
     * @return
     */
    public OkHttpClient getWsOkHttpClient() {
        OkHttpClient.Builder builder = getDefaultOkHttpClientBuilder();
        return builder.build();
    }

    /**
     * IM ws
     *
     * @return
     */
    public OkHttpClient getImWsOkHttpClient() {
        OkHttpClient.Builder builder = getDefaultOkHttpClientBuilder();
        return builder.build();
    }

    /**
     * common api
     *
     * @return
     */
    public OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder builder = getDefaultOkHttpClientBuilder();
        return builder.build();
    }

    /**
     * file http
     *
     * @return
     */
    public OkHttpClient getFileOkHttpClient() {
        OkHttpClient.Builder builder = getDefaultOkHttpClientBuilder();
        builder.connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);
        return builder.build();
    }
}
