package ru.fazziclay.sharefile.android.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import ru.fazziclay.sharefile.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {
    ActivityHomeBinding binding;
    List<ItemInfo> items = new ArrayList<>();
    Thread updateThread = new Thread(() -> {
        while (true) {
            items = new ArrayList<>();
            runOnUiThread(this::updateList);

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });

    public void updateList() {
        binding.list.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return items.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int i, View convertView, ViewGroup parent) {
                TextView textView = new TextView(HomeActivity.this);
                textView.setText(items.get(i).ip);
                return textView;
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        updateThread.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    static class ItemInfo {
        String ip;
        public ItemInfo(String ip) {
            this.ip = ip;
        }
    }
}