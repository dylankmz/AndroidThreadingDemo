package be.kmz.demothreading;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Message;
import android.widget.Button;
import android.widget.ProgressBar;

import be.kmz.demothreading.threading.ProgressHandler;
import be.kmz.demothreading.threading.ProgressTask;

public class MainActivity extends AppCompatActivity {

    private ProgressBar mProgressBar;
    private Button btnStart, btnAsync;
    private ProgressHandler handler;
    private boolean isRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = findViewById(R.id.progressBar);
        btnStart = findViewById(R.id.btn_start_handler);
        btnStart.setOnClickListener((view) -> {
            if (!isRunning) {
                startThread();
            }
        });
        handler = new ProgressHandler(mProgressBar);
        btnAsync = findViewById(R.id.btn_async);
        btnAsync.setOnClickListener((view) -> {
            ProgressTask mProgressTask = new ProgressTask(mProgressBar);
            mProgressTask.execute();
        });
    }

    private void startThread() {
        Thread bgThread = new Thread((Runnable)() -> {
            isRunning = true;
            for (int i = 1; i <= 100; i++) {
                Message msg = new Message();
                msg.arg1 = i;
                handler.sendMessage(msg);
            }
            isRunning = false;
        });
        bgThread.start();
    }
}