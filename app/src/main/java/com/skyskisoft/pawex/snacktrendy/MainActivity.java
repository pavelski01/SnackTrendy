package com.skyskisoft.pawex.snacktrendy;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class MainActivity
    extends AppCompatActivity
    implements ItemClickListener {

    ItemsRecyclerViewAdapter adapter;
    ArrayList<String> items;
    BluetoothAdapter bluetoothAdapter;
    String scanBuffer = "";

    private static final UUID DEVICE_UUID =
        UUID.fromString("e070570d-0ce5-4dd5-b676-8a2d5cf1451f");

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        super.dispatchKeyEvent(event);
        boolean consumeFlag = false;
        if
        (
            event.getKeyCode() != KeyEvent.KEYCODE_ENTER &&
            event.getAction() == KeyEvent.ACTION_DOWN
        ) {
            scanBuffer += (char)event.getUnicodeChar();
            consumeFlag = true;
        }
        if
        (
            event.getKeyCode() == KeyEvent.KEYCODE_ENTER &&
            event.getAction() == KeyEvent.ACTION_DOWN
        ) {
            items.add(scanBuffer);
            adapter.notifyItemInserted(items.size() - 1);
            scanBuffer = "";
            consumeFlag = true;
        }
        return consumeFlag;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        Intent intent = getIntent();
        if (intent.getBooleanExtra("crash", false)) {
            Toast.makeText(
                this,
                "Application restarted after crash",
                Toast.LENGTH_SHORT
            ).show();
            intent.putExtra("crash", false);
        }
        items = new ArrayList<>();
        items.add("Pizza");
        items.add("Soup");
        items.add("Burger");
        items.add("Coffee");
        RecyclerView recyclerView = findViewById(R.id.items_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ItemsRecyclerViewAdapter(this, items);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        ItemTouchHelper.SimpleCallback itemTouchCallback =
            new ItemTouchHelper.SimpleCallback(
                0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT
            ) {
                @Override
                public boolean onMove(
                    RecyclerView recyclerView,
                    RecyclerView.ViewHolder viewHolder,
                    RecyclerView.ViewHolder target
                ) {
                    return false;
                }

                @Override
                public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                    int swipedPosition = viewHolder.getAdapterPosition();
                    items.remove(swipedPosition);
                    adapter.notifyItemRemoved(swipedPosition);
                }
            };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 1);
        }
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        BluetoothSocket bs = null;
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                try {
                    bs = device.createInsecureRfcommSocketToServiceRecord(DEVICE_UUID);
                } catch (IOException e) {
                    Log.e("Bluetooth Test", "Socket's create() method failed", e);
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(
            this,
            "You clicked " + adapter.getItem(position) + " on row number " + position,
            Toast.LENGTH_SHORT
        ).show();
    }

    public void onHold(View view) {
        items.add("Added via click");
        adapter.notifyItemInserted(items.size() - 1);
    }

    public void onKiosk(View view) {
        throw new NullPointerException();
    }
}