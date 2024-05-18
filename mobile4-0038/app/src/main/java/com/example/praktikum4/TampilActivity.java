package com.example.praktikum4;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class TampilActivity extends AppCompatActivity {

    private DbHelper dbHelper;
    private TableLayout tbMhs;
    private TableRow tr;
    private TextView coll, col2, col3;
    private Button btnTutup, btnEdit, btnHapus;
    private ArrayList<Mahasiswa> arrListMhs = new ArrayList<>();
    private String stb, nama;
    private int angkatan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil);

        dbHelper = new DbHelper(this);
        tbMhs = findViewById(R.id.tb_mahasiswa);
        btnTutup = findViewById(R.id.btn_tutup);
        btnEdit = findViewById(R.id.btn_edit);
        btnHapus = findViewById(R.id.btn_hapus);

        tampilTabelMhs();

        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.hapusData(dbHelper.getWritableDatabase(), stb);
                tampilTabelMhs();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("stb", stb);
                intent.putExtra("nama", nama);
                intent.putExtra("angkatan", angkatan);
                dbHelper.close();
                setResult(1, intent);
                finish();
            }
        });

        btnTutup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void tampilTabelMhs() {
        tbMhs.removeAllViews();
        arrListMhs = dbHelper.getArrListMhs(dbHelper.getWritableDatabase());

        tr = new TableRow(this);
        coll = new TextView(this);
        col2 = new TextView(this);
        col3 = new TextView(this);

        coll.setText("Stambuk");
        col2.setText("Nama Mahasiswa");
        col3.setText("Angkatan");

        coll.setWidth(200);
        col2.setWidth(300);
        col3.setWidth(150);

        tr.addView(coll);
        tr.addView(col2);
        tr.addView(col3);
        tbMhs.addView(tr);

        for (final Mahasiswa mhs : arrListMhs) {
            tr = new TableRow(this);
            coll = new TextView(this);
            col2 = new TextView(this);
            col3 = new TextView(this);

            coll.setText(mhs.getStb());
            col2.setText(mhs.getNama());
            col3.setText(String.valueOf(mhs.getAngkatan()));

            coll.setWidth(200);
            col2.setWidth(300);
            col3.setWidth(150);

            tr.addView(coll);
            tr.addView(col2);
            tr.addView(col3);
            tbMhs.addView(tr);

            tr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int i = 0; i < tbMhs.getChildCount(); i++) {
                        if (tbMhs.getChildAt(i) == view) {
                            tbMhs.getChildAt(i).setBackgroundColor(Color.LTGRAY);
                            stb = mhs.getStb();
                            nama = mhs.getNama();
                            angkatan = mhs.getAngkatan();
                        } else {
                            tbMhs.getChildAt(i).setBackgroundColor(Color.WHITE);
                        }
                    }
                }
            });
        }
    }
}
