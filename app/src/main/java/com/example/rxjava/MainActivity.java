package com.example.rxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.LogUtils;
import com.example.rxjava.combination.ObservableCombination;
import com.example.rxjava.conversion.Change;
import com.example.rxjava.error.ObservableError;
import com.example.rxjava.filter.ObservableFilter;
import com.example.rxjava.help.ObservableHelper;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private Button textView, change, filter, combination, retry, helper;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text);
        change = findViewById(R.id.change);
        filter = findViewById(R.id.filter);
        combination = findViewById(R.id.combination);
        retry = findViewById(R.id.retry);
        textView.setOnClickListener(this::create);
        change.setOnClickListener(this::change);
        helper = findViewById(R.id.helper);
        helper.setOnClickListener(v -> {
            ObservableHelper.doNext();
        });
        filter.setOnClickListener(v -> {
//            ObservableFilter.filter();
//            ObservableFilter.distince();
            ObservableFilter.debounce();
        });
        combination.setOnClickListener(v -> {
//            ObservableCombination.zip();
//            ObservableCombination.concat();
//            ObservableCombination.merge();
//            ObservableCombination.combineLatest();
            ObservableCombination.startWitch();
        });

        retry.setOnClickListener(v -> {
//            ObservableError.retry();
            ObservableError.retryWhen();
        });
    }


    private void change(View view) {
//        Change.map();
//        Change.flatMap();
//        Change.buffer();
//        Change.window();
        Change.groupBy();
    }

    private void create(View view) {

        Observable.create(a -> a.onNext(77)).subscribe(b -> LogUtils.e("create:" + b));

        Observable.create((ObservableOnSubscribe<Integer>) u -> u.onNext(234)).subscribe(o -> LogUtils.e("create:" + o));

        //repeat--创建一个重复发送特定数据  可以当做计数器或者间歇性网络请求
        Observable.just(9).repeat(2).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                count++;
                LogUtils.e("次数+" + count + " repeat1:" + integer);
            }
        });

//        Observable.just(894).repeat().subscribe(ko -> LogUtils.e("repeat2:" + ko));

        Observable.just("4", "9").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(b -> LogUtils.e(b + "订阅线程:" + Thread.currentThread().getName()));

        Observable.create(a -> {
            a.onNext("85962");
            LogUtils.e("发布线程:" + Thread.currentThread().getName());
        })
                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .observeOn(Schedulers.newThread())
                .subscribe(b -> LogUtils.e(b + "订阅线程:" + Thread.currentThread().getName()));
    }
}
