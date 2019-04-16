package com.example.z2;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class SocketTestActivity extends AppCompatActivity {


        private static final String TAG = "lzy";
        private Context context;
        private TextView mTextView;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            context = this;
            mTextView = (TextView) findViewById(R.id.tv);

        }

        public void bt(View view) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        Socket socket = new Socket("172.16.86.194", 3000);
                        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"));
                        String line = br.readLine();
                        Log.i(TAG, "读取数据：" + line);
                        br.close();
                        socket.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();

        }

}
