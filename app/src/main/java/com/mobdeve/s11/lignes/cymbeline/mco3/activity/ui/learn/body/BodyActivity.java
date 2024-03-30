package com.mobdeve.s11.lignes.cymbeline.mco3.activity.ui.learn.body;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;

import com.mobdeve.s11.lignes.cymbeline.mco3.R;
import com.mobdeve.s11.lignes.cymbeline.mco3.activity.ui.learn.detail.DetailActivity;
import com.mobdeve.s11.lignes.cymbeline.mco3.activity.ui.learn.face.FaceActivity;
import com.mobdeve.s11.lignes.cymbeline.mco3.databinding.ActivityBodyBinding;
import com.mobdeve.s11.lignes.cymbeline.mco3.databinding.ActivityFaceBinding;
import com.mobdeve.s11.lignes.cymbeline.mco3.navbar.BottomNavbarHelper;

public class BodyActivity extends AppCompatActivity {
    ActivityBodyBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_body);
        TextView textView = findViewById(R.id.textView);
        textView.setText("Body");
        binding.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BodyActivity.this, DetailActivity.class);
                intent.putExtra("mode", "muscles");
                startActivity(intent);
            }
        });
        binding.cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BodyActivity.this, DetailActivity.class);
                intent.putExtra("mode", "fat");

                startActivity(intent);
            }
        });
        binding.cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BodyActivity.this, DetailActivity.class);
                intent.putExtra("mode", "athe");

                startActivity(intent);
            }
        });
        binding.cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BodyActivity.this, DetailActivity.class);
                intent.putExtra("mode", "diet");

                startActivity(intent);
            }
        });

        // Set up bottom navigation bar
        BottomNavbarHelper.setProfileIconClickListener(this);
    }
}