package io.qepl.impetus;

import io.qepl.impetus.PromptQueue;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.appcompat.app.ActionBar;

public class MainActivity extends AppCompatActivity {

    private PromptQueue promptQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        this.promptQueue = new PromptQueue(this);
    }
}
