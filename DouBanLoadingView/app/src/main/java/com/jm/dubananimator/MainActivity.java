package com.jm.dubananimator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button mStartButton;
    Button mStopButton;
    DouBanLoadingView mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mView = (DouBanLoadingView) findViewById(R.id.mLoadingView);
        mStartButton = (Button) findViewById(R.id.btn_start_load);
        mStopButton = (Button) findViewById(R.id.btn_stop_load);

        mStartButton.setOnClickListener(this);
        mStopButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_start_load:
                mView.showLoading();

                break;
            case R.id.btn_stop_load:
                mView.stopLoading();
                break;
        }
    }
}