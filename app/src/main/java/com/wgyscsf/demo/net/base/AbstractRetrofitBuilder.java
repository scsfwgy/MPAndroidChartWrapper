package com.wgyscsf.demo.net.base;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.GsonUtils;

import org.jetbrains.annotations.NotNull;

import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * ============================================================
 * 作 者 :    wgyscsf@163.com
 * 更新时间 ：2019/06/20 14:58
 * 描 述 ：抽象一个Retrofit对象,创建Retrofit直接继承这个抽象类即可
 * ============================================================
 */
public abstract class AbstractRetrofitBuilder {
    private Retrofit mRetrofit;

    public Retrofit getRetrofit() {
        if (mRetrofit == null) {
            synchronized (AbstractRetrofitBuilder.class) {
                if (mRetrofit == null) {
                    mRetrofit = createRetrofit();
                }
            }
        }
        return mRetrofit;
    }

    @NotNull
    protected Retrofit createRetrofit() {
        return createBuilder().build();
    }

    @NonNull
    protected Retrofit.Builder createBuilder() {
        return new Retrofit.Builder().baseUrl(apiBaseUrl())
                .addConverterFactory(GsonConverterFactory.create(GsonUtils.getGson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(okHttpClient());

    }

    protected OkHttpClient okHttpClient() {
        return DefOkHttpClient.getInstance().getOkHttpClient();
    }

    protected abstract String apiBaseUrl();

    public <T> T create(@NonNull final Class<T> service) {
        return getRetrofit().create(service);
    }
}
