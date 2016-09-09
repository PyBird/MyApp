package com.example.feng.myapp.test;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.feng.myapp.R;
import com.example.feng.myapp.base.BaseActivity;
import com.example.feng.myapp.utils.MyToastUtils;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.Iterator;
import java.util.Set;

public class TestBluetoothActivity extends BaseActivity {

    @ViewInject(R.id.bt_find) private Button bt_find;
    @ViewInject(R.id.bt_see) private Button bt_see;

    private BluetoothAdapter mBluetoothAdapter;
    private int REQUEST_ENABLE_BT=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_bluetooth);

        x.view().inject(this);

        initBluetooth();
        initEvent();

    }

    private void initBluetooth(){
        // 初始化 Bluetooth adapter, 通过蓝牙管理器得到一个参考蓝牙适配器(API必须在以上android4.3或以上和版本)
        BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            mBluetoothAdapter = bluetoothManager.getAdapter();
        }

        // 检查设备上是否支持蓝牙
        if (mBluetoothAdapter == null) {
            MyToastUtils.showToastLong(TestBluetoothActivity.this,"没有支持蓝牙");
            finish();
            return;
        }

        // 为了确保设备上蓝牙能使用, 如果当前蓝牙设备没启用,弹出对话框向用户要求授予权限来启用
//        if (!mBluetoothAdapter.isEnabled()) {
//            if (!mBluetoothAdapter.isEnabled()) {
//                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
//            }
//        }
    }

    private void initEvent(){

        bt_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //得到BluetoothAdapter对象
                BluetoothAdapter blueAdapter = BluetoothAdapter.getDefaultAdapter();

                //判断BluetoothAdapter对象是否为空，如果为空，则表明本机没有蓝牙设备
                if(blueAdapter != null){

                    if(!blueAdapter.isEnabled())
                    {
                        MyToastUtils.showToastLong(TestBluetoothActivity.this,"请打开蓝牙");
                        //如果蓝牙设备不可用的话,创建一个intent对象,该对象用于启动一个Activity,提示用户启动蓝牙适配器
                        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivity(intent);
                    }

                    //得到所有已经配对的蓝牙适配器对象
                    Set<BluetoothDevice> devices = blueAdapter.getBondedDevices();
                    if(devices.size()>0)
                    {
                        MyToastUtils.showToastLong(TestBluetoothActivity.this,"有可用蓝牙设备");
                        //用迭代
                       for(Iterator iterator = devices.iterator(); iterator.hasNext();)
                       {
                           //得到BluetoothDevice对象,也就是说得到配对的蓝牙适配器
                            BluetoothDevice device = (BluetoothDevice)iterator.next();
                            //得到远程蓝牙设备的地址
                            Log.e("mytag",device.getAddress());

                       }
                    }
                }
                else{
                    MyToastUtils.showToastLong(TestBluetoothActivity.this,"没有可用蓝牙设备");
                }
            }
        });


        bt_see.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
