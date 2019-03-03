package com.example.chapter4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_share_write).setOnClickListener(this);
        findViewById(R.id.btn_share_read).setOnClickListener(this);
        findViewById(R.id.btn_login_share).setOnClickListener(this);
        findViewById(R.id.btn_sqlite_create).setOnClickListener(this);
        findViewById(R.id.btn_sqlite_write).setOnClickListener(this);
        findViewById(R.id.btn_sqlite_read).setOnClickListener(this);
        findViewById(R.id.btn_login_sqlite).setOnClickListener(this);
        findViewById(R.id.btn_file_basic).setOnClickListener(this);
        findViewById(R.id.btn_file_path).setOnClickListener(this);
        findViewById(R.id.btn_text_write).setOnClickListener(this);
        findViewById(R.id.btn_text_read).setOnClickListener(this);
        findViewById(R.id.btn_image_write).setOnClickListener(this);
        findViewById(R.id.btn_image_read).setOnClickListener(this);
        findViewById(R.id.btn_app_life).setOnClickListener(this);
        findViewById(R.id.btn_app_write).setOnClickListener(this);
        findViewById(R.id.btn_app_read).setOnClickListener(this);
        findViewById(R.id.btn_content_provider).setOnClickListener(this);
        findViewById(R.id.btn_content_resolver).setOnClickListener(this);
        findViewById(R.id.btn_content_observer).setOnClickListener(this);
        findViewById(R.id.btn_menu_option).setOnClickListener(this);
        findViewById(R.id.btn_menu_context).setOnClickListener(this);
        findViewById(R.id.btn_shopping_cart).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_content_provider) {
            Intent intent = new Intent(this, ContentProviderActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_content_resolver) {
            Intent intent = new Intent(this, ContentResolverActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_content_observer) {
            Intent intent = new Intent(this, ContentObserverActivity.class);
            startActivity(intent);
        }

    }
}
