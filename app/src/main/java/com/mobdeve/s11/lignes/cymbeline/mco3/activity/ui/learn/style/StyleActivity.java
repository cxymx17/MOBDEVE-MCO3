package com.mobdeve.s11.lignes.cymbeline.mco3.activity.ui.learn.style;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.mobdeve.s11.lignes.cymbeline.mco3.R;
import com.mobdeve.s11.lignes.cymbeline.mco3.activity.ui.learn.detail.DetailActivity;
import com.mobdeve.s11.lignes.cymbeline.mco3.databinding.ActivityStyleBinding;
import com.mobdeve.s11.lignes.cymbeline.mco3.navbar.BottomNavbarHelper;

/**
 * Activity class for displaying style-related learning categories.
 * This activity allows users to navigate to specific detail activities for each category.
 */
public class StyleActivity extends AppCompatActivity {
    ActivityStyleBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_style);
        TextView textView = findViewById(R.id.textView);
        textView.setText("Style");
        binding.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StyleActivity.this, DetailActivity.class);
                intent.putExtra("mode", "ustyle");
                startActivity(intent);
            }
        });
        binding.cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StyleActivity.this, DetailActivity.class);
                intent.putExtra("mode", "tops");

                startActivity(intent);
            }
        });
        binding.cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StyleActivity.this, DetailActivity.class);
                intent.putExtra("mode", "bottoms");

                startActivity(intent);
            }
        });
        binding.cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StyleActivity.this, DetailActivity.class);
                intent.putExtra("mode", "shoes");

                startActivity(intent);
            }
        });
        // Set up bottom navigation bar
        BottomNavbarHelper.setProfileIconClickListener(this);
    }
}