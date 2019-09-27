package io.qepl.impetus;

import java.util.Locale;
import java.util.Random;

import android.util.Log;
import android.widget.EditText;
import android.content.Context;
import android.app.Activity;

public class PromptQueue {

    private Integer[] valueQueue = {1, 2, 3, 4};
    private Context context;
    private Random PRNG = new Random();

    PromptQueue(Context context) {
        this.context = context;

        for (int i = 0; i < 4 ; i++) {
            this.valueQueue[i] = randomDigit();
        }

        this.updatePrompt();
    }

    private void updatePrompt() {
        Log.e("HELLO", "updatePrompt: ");
        EditText prompt0 = ((Activity)context).findViewById(R.id.textPrompt0);
        prompt0.setText(String.format(Locale.ENGLISH, "%d",valueQueue[0]));

        EditText prompt1 = ((Activity)context).findViewById(R.id.textPrompt1);
        prompt1.setText(String.format(Locale.ENGLISH, "%d",valueQueue[1]));

        EditText prompt2 = ((Activity)context).findViewById(R.id.textPrompt2);
        prompt2.setText(String.format(Locale.ENGLISH, "%d",valueQueue[2]));

        EditText prompt3 = ((Activity)context).findViewById(R.id.textPrompt3);
        prompt3.setText(String.format(Locale.ENGLISH, "%d",valueQueue[3]));
    }

    public void next() {

        for (int i = 0; i < 3 ; i++) {
            this.valueQueue[i] = this.valueQueue[i + 1];
        }

        this.valueQueue[3] = randomDigit();

        this.updatePrompt();
    }

    private Integer randomDigit() {
        return PRNG.nextInt(9 + 1);
    }
}
