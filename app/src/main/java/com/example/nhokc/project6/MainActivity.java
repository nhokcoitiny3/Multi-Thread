package com.example.nhokc.project6;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.nhokc.project6.databinding.ActivityMainBinding;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityMainBinding binding;
    public Handler handler;
    static final int RAMDOM_NUMBER = 1;
    static final int WRITE_NUMBER = 2;
    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        initialize();
        try {
            Thread.sleep(2000);
            onRandomNumber();
            onWriteNumber();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                String text = binding.txtText.getText().toString();
                switch (msg.what){
                    case RAMDOM_NUMBER:
                            binding.txtText.setText(text+"\n"+String.valueOf(msg.arg1));
                    case WRITE_NUMBER:
                        binding.txtText.setText(text+"\n"+String.valueOf(msg.arg1));
                }
                super.handleMessage(msg);
            }
        };
    }

    private void onWriteNumber() {
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                int i =1;
                while (true){
                    try {
                        Thread.sleep(2500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Message msg = new Message();

                    msg.what = WRITE_NUMBER;
                    msg.arg1 = i;
                    handler.sendMessage(msg);
                    i+=2;
                }
            }
        });
        thread2.start();
    }

    private void onRandomNumber() {
        final Random random = new Random();
        final Message  msg = new Message();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                int number = random.nextInt(50)+50;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                msg.what = RAMDOM_NUMBER;
                msg.arg1 = number;
                handler.sendMessage(msg);
            }
        });
        thread1.start();
    }

    private void initialize() {
        binding.btnClear.setOnClickListener(this);
        binding.btnExit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_clear:
                    binding.txtText.setText("");
                break;
                case R.id.btn_exit:
                    finish();
                break;
        }
    }
}
