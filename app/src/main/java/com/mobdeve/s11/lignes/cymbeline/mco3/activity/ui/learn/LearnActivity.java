package com.mobdeve.s11.lignes.cymbeline.mco3.activity.ui.learn;

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
import com.mobdeve.s11.lignes.cymbeline.mco3.activity.MainActivity;
import com.mobdeve.s11.lignes.cymbeline.mco3.activity.RegisterActivity;
import com.mobdeve.s11.lignes.cymbeline.mco3.activity.ui.learn.body.BodyActivity;
import com.mobdeve.s11.lignes.cymbeline.mco3.activity.ui.learn.detail.DetailActivity;
import com.mobdeve.s11.lignes.cymbeline.mco3.activity.ui.learn.essential.EssentialActivity;
import com.mobdeve.s11.lignes.cymbeline.mco3.activity.ui.learn.face.FaceActivity;
import com.mobdeve.s11.lignes.cymbeline.mco3.activity.ui.learn.style.StyleActivity;
import com.mobdeve.s11.lignes.cymbeline.mco3.databinding.ActivityLearnBinding;

public class LearnActivity extends AppCompatActivity {
    ActivityLearnBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_learn);
        TextView textView = findViewById(R.id.textView);
        textView.setText("What are we learning today");
        binding.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LearnActivity.this, FaceActivity.class);
                startActivity(intent);
            }
        });

        binding.cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LearnActivity.this, BodyActivity.class);
                startActivity(intent);
            }
        });
        binding.cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LearnActivity.this, StyleActivity.class);
                startActivity(intent);
            }
        });
        binding.cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LearnActivity.this, EssentialActivity.class);
                startActivity(intent);
            }
        });

    }
}