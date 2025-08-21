package com.app.tictacto;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

public class MainActivity extends AppCompatActivity {

    private EditText editTextPlayer1;
    private EditText editTextPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        setContentView(R.layout.activity_main);

        EdgeToEdge.enable(this);
        View rootView = findViewById(android.R.id.content);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        // Adjust for cutouts (API 28+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            Window window = getWindow();
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            window.setAttributes(lp);
        }

        // Theme-aware status bar configuration
        boolean isDarkTheme = (getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES;

        Window window = getWindow();
        if (isDarkTheme) {
            window.setStatusBarColor(Color.BLACK);  // dark background
        } else {
            window.setStatusBarColor(Color.WHITE);  // light background
        }
        WindowInsetsControllerCompat controller = WindowCompat.getInsetsController(window, window.getDecorView());
        controller.setAppearanceLightStatusBars(!isDarkTheme);  // black icons for light theme, white for dark

        // Apply insets only to toolbar
        applyDisplayCutouts(rootView);

        editTextPlayer1 = findViewById(R.id.edit_text_player1);
        editTextPlayer2 = findViewById(R.id.edit_text_player2);
        Button buttonStart = findViewById(R.id.button_start);

        buttonStart.setOnClickListener(v -> startGame());
    }

    private void applyDisplayCutouts(View topInsetTarget) {
        ViewCompat.setOnApplyWindowInsetsListener(topInsetTarget, (v, insets) -> {
            Insets statusBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, statusBars.top, 0, 0); // Only top padding for toolbar
            return insets;
        });
    }

    private void startGame() {
        String player1Name = editTextPlayer1.getText().toString().trim();
        String player2Name = editTextPlayer2.getText().toString().trim();

        if (player1Name.isEmpty()) {
            player1Name = "Player 1";
        }
        if (player2Name.isEmpty()) {
            player2Name = "Player 2";
        }

        // Open Best.java instead of GameActivity.java
        Intent intent = new Intent(this, Best.class);
        intent.putExtra("PLAYER1_NAME", player1Name);
        intent.putExtra("PLAYER2_NAME", player2Name);
        startActivity(intent);
    }
}
