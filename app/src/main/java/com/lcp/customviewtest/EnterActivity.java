package com.lcp.customviewtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import custom.calendar.CalendarActivity;

public class EnterActivity extends AppCompatActivity {

    private final String TAG = EnterActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);
        test2();
    }

    public void clickCalendar(View view) {
        startActivity(new Intent(this, CalendarActivity.class));
    }

    public void clickOther(View view) {
        startActivity(new Intent(this, CalendarActivity.class));
    }

    private void test() {
        int size = 9;
        int[] a = new int[size];
        for (int i = 1; i <= size; i++)
            a[i - 1] = i;
        int[] b = new int[size];

        for (int i = 0; i < size / 2; i++) {
            b[i * 2] = a[size - 1 - i];
            b[i * 2 + 1] = a[i];
        }
        if (size % 2 != 0)
            b[size - 1] = a[size / 2];
        StringBuilder builder = new StringBuilder();
        StringBuilder builder2 = new StringBuilder();
        for (int i = 0; i < size; i++) {
            builder.append(a[i] + ",");
            builder2.append(b[i] + ",");
        }
        Log.i(TAG, builder.toString() + "----" + builder2.toString());
    }

    //所有的数都在1-100之间，所以方法是设置100个桶，桶的数值表示第i个桶有几个
    private int[] sort(int[] data, int dataLength) {
        //整个数组最大值为100，设为101方便后面计算
        int N = 101;
        //初始化每个桶，桶的值就是该下标所对应的个数
        int[] bottle = new int[N];
        for (int i = 0; i < N; i++) {
            bottle[i] = 0;
        }
        //计算每个下标的个数
        for (int i = 0; i < dataLength; i++) {
            int key = data[i];
            ++bottle[key];
        }

        int minValue = 0;
        int maxValue = 100;
        for (int i = 0; i < dataLength / 2; i++) {
            //计算最小的数
            for (int n = minValue; n <= maxValue;n++) {
                minValue = n;
                if (bottle[minValue] > 0) {
                    bottle[minValue]--;
                    break;
                }
            }
            //计算最大的数
            for (int m = maxValue; m >= minValue;m--) {
                maxValue = m;
                if (bottle[maxValue] > 0) {
                    bottle[maxValue]--;
                    break;
                }
            }
            //偶数位为剩余数值的最大值，奇数位为剩余数值的最小值
            data[i * 2] = maxValue;
            data[i * 2 + 1] = minValue;
        }
        if (dataLength % 2 != 0) {
            //当为奇数个数时，计算最后一个数值
            for (int index = minValue; index <= maxValue; index++) {
                if (bottle[index] > 0) {
                    data[dataLength - 1] = index;
                }
            }
        }
        return data;

    }

    private void test2() {
        int[] data = {1, 1, 2, 3, 5, 7, 8, 9, 10};
        int size = data.length;

        data = sort(data, size);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            builder.append(data[i] + ",");
        }

        Log.i(TAG, builder.substring(0, builder.length() - 1));
    }
}
