package com.mobdeve.s11.lignes.cymbeline.mco3.activity.ui.learn.face;

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
import com.mobdeve.s11.lignes.cymbeline.mco3.activity.ui.learn.LearnActivity;
import com.mobdeve.s11.lignes.cymbeline.mco3.activity.ui.learn.body.BodyActivity;
import com.mobdeve.s11.lignes.cymbeline.mco3.activity.ui.learn.detail.DetailActivity;
import com.mobdeve.s11.lignes.cymbeline.mco3.activity.ui.learn.essential.EssentialActivity;
import com.mobdeve.s11.lignes.cymbeline.mco3.activity.ui.learn.style.StyleActivity;
import com.mobdeve.s11.lignes.cymbeline.mco3.databinding.ActivityFaceBinding;
import com.mobdeve.s11.lignes.cymbeline.mco3.databinding.ActivityLearnBinding;

public class FaceActivity extends AppCompatActivity {
    ActivityFaceBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_face);
        TextView textView = findViewById(R.id.textView);
        textView.setText("Face");
        binding.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FaceActivity.this, DetailActivity.class);
                intent.putExtra("mode", "eyes");
                startActivity(intent);
            }
        });

        binding.cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FaceActivity.this, DetailActivity.class);
                intent.putExtra("mode", "jaws");

                startActivity(intent);
            }
        });
        binding.cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FaceActivity.this, DetailActivity.class);
                intent.putExtra("mode", "skines");

                startActivity(intent);
            }
        });
        binding.cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FaceActivity.this, DetailActivity.class);
                intent.putExtra("mode", "lips");

                startActivity(intent);
            }
        });

    }
}