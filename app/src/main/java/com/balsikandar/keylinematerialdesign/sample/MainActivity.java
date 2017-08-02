package com.balsikandar.keylinematerialdesign.sample;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.balsikandar.android.keylinematerialdesign.KeylineMaterialDesign;
import com.balsikandar.android.keylinematerialdesign.KeylineUtil;

public class MainActivity extends AppCompatActivity {
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        findViewById(R.id.showKeylines).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeylineMaterialDesign.builder(context)
                        .setColor(Color.BLACK)
                        .setAlpha(KeylineUtil.TRANSPARENCY.EIGHTY)
                        .setGridSize(KeylineUtil.GRIDSIZE.EIGHTDP)
                        .setOrientation(KeylineUtil.LINE.BOTH)
                        .drawKeyLines();
            }
        });

        findViewById(R.id.hideKeylines).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //to hide keylines
                KeylineMaterialDesign.builder(context).hideKeyLines();
            }
        });
    }
}
