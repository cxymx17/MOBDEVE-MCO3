package com.mobdeve.s11.lignes.cymbeline.mco3.activity.ui.learn.detail;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mobdeve.s11.lignes.cymbeline.mco3.R;
import com.mobdeve.s11.lignes.cymbeline.mco3.databinding.ActivityDetailBinding;
import com.mobdeve.s11.lignes.cymbeline.mco3.navbar.BottomNavbarHelper;

public class DetailActivity extends AppCompatActivity {

    private ActivityDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        TextView textView = findViewById(R.id.textView);
        Intent intent = getIntent();
        String mode = intent.getStringExtra("mode");
        WebView webView = findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // Enable JavaScript
        webView.setWebViewClient(new WebViewClient());
        if (mode.equals("eyes")) {
            textView.setText("Eyes and eyebrows");
            binding.txtdis.setText("Eyes and eyebrows are crucial for grooming as they frame the face and contribute significantly to overall facial expression. \n" + "\n" + "Well-groomed eyebrows can enhance symmetry and attractiveness, while cared-for eyes convey vitality and personality. \n" + "\n" + "They serve as focal points, influencing how others perceive one's appearance and demeanor, making them essential for both men and women seeking to improve their looks.");
            webView.loadUrl("https://www.youtube.com/watch?v=gbCy6N_Xvv4");
        } else if (mode.equals("Style")) {
            textView.setText("Eyes and eyebrows");
            binding.txtdis.setText("Developing a unique style allows individuals to express their personality and individuality, making them more memorable and attractive.\n" + "\n" + "Embracing one's personal style fosters confidence and self-assurance, which are inherently attractive qualities.\n" + "\n" + "A distinct personal style communicates authenticity and originality, drawing positive attention and admiration from others.\n");
            webView.loadUrl("https://www.youtube.com/watch?v=CUv2Df3Zcf8");
        } else if (mode.equals("jaws")) {
            textView.setText("jaw and cheekbones");
            binding.txtdis.setText("Jaw and cheekbones play a crucial role in facial symmetry and definition, enhancing attractiveness for both men and women.\n" + "\n" + "Grooming these features can accentuate facial structure, providing a more balanced and aesthetically pleasing appearance.\n" + "\n" + "Sculpted jawlines and high cheekbones are often seen as signs of youthfulness and vitality, adding to overall attractiveness for both men and women.\n" + "\n");
            webView.loadUrl("https://www.youtube.com/watch?v=fmSvbJM17ck");
        } else if (mode.equals("skines")) {
            textView.setText("skin essentials");
            binding.txtdis.setText("Healthy, well-groomed skin creates a positive first impression, reflecting overall hygiene and self-care.\n" + "\n" + "Clear and radiant skin boosts confidence, allowing individuals to feel more comfortable and attractive in their appearance.\n" + "\n" + "Proper skincare can help maintain a youthful complexion, minimizing the appearance of wrinkles and signs of aging for both men and women.\n" + "\n");
            webView.loadUrl("https://www.youtube.com/watch?v=OrElyY7MFVs");
        } else if (mode.equals("lips")) {
            textView.setText("lip and teeth");
            binding.txtdis.setText("Groomed lips and teeth contribute to a confident and inviting smile, enhancing overall facial attractiveness.\n" + "\n" + "Well-maintained lips and teeth are essential for good oral hygiene, signaling cleanliness and health-consciousness.\n" + "\n" + "Aesthetic lips and teeth can positively influence communication, as they draw attention and convey friendliness and approachability.\n" + "\n");
            webView.loadUrl("https://www.youtube.com/watch?v=jajfu5VBFWI");
        } else if (mode.equals("muscles")) {
            textView.setText("Muscles");
            binding.txtdis.setText("Building muscle improves body composition, leading to a more toned and attractive physique for both men and women.\n" + "\n" + "Increased muscle mass can boost self-confidence and self-esteem, enhancing overall attractiveness and presence.\n" + "\n" + "Building muscle promotes better metabolic health, strength, and mobility, contributing to a healthier and more vibrant appearance.");
            webView.loadUrl("https://www.youtube.com/watch?v=04kAfDdqEfg");
        } else if (mode.equals("fat")) {
            textView.setText("Reducing body fat enhances muscle definition, contributing to a more sculpted and toned appearance for both men and women.\n" + "\n" + "Lower body fat levels help achieve a balanced physique, emphasizing natural curves and contours, enhancing overall attractiveness.\n" + "\n" + "Shedding excess body fat can lead to increased confidence and improved self-image, positively impacting how individuals perceive themselves and how others perceive them.\n" + "\n" + "\n");
            binding.txtdis.setText("body fat");
            webView.loadUrl("https://www.youtube.com/watch?v=E3QpXj_QOqQ");
        } else if (mode.equals("athe")) {
            textView.setText("athleticism");
            binding.txtdis.setText("Athleticism exudes energy and vitality, enhancing overall attractiveness and making individuals stand out.\n" + "\n" + "Being athletically groomed signifies strength, agility, and capability, traits that are universally appealing.\n" + "\n" + "An athletic physique often suggests a commitment to physical well-being, which is attractive to both men and women seeking partners who prioritize health and fitness.");
            webView.loadUrl("https://www.youtube.com/watch?v=-HGrBqy77iY");
        } else if (mode.equals("diet")) {
            textView.setText("diet and supplements");
            binding.txtdis.setText(" A balanced diet and appropriate supplements provide essential nutrients that support healthy skin, hair, and nails, enhancing overall appearance.\n" + "\n" + "Proper nutrition fuels energy levels, promoting a vibrant and youthful demeanor, which contributes to attractiveness.\n" + "\n" + "A nutritious diet and targeted supplements can help maintain a healthy weight, reduce signs of aging, and support overall well-being, leading to a more attractive and youthful appearance over time.");
            webView.loadUrl("https://www.youtube.com/watch?v=yRDI30HrjVQ");
        } else if (mode.equals("ustyle")) {
            textView.setText("your style");
            binding.txtdis.setText("Developing a unique style allows individuals to express their personality and individuality, making them more memorable and attractive.\n" + "\n" + "Embracing one's personal style fosters confidence and self-assurance, which are inherently attractive qualities.\n" + "\n" + "A distinct personal style communicates authenticity and originality, drawing positive attention and admiration from others.\n");
            webView.loadUrl("https://www.youtube.com/watch?v=CUv2Df3Zcf8");
        } else if (mode.equals("tops")) {
            textView.setText("tops");
            binding.txtdis.setText("Choosing the right top can make a significant style statement, enhancing overall appearance and showcasing personal taste.\n" + "\n" + "Well-selected tops that fit properly accentuate body proportions, creating a more flattering silhouette for both men and women.\n" + "\n" + "Wearing tops that make individuals feel comfortable and confident enhances their overall demeanor, radiating attractiveness and self-assurance.\n");
            webView.loadUrl("https://www.youtube.com/watch?v=BpcemabDB3E");
        } else if (mode.equals("bottoms")) {
            textView.setText("bottoms");
            binding.txtdis.setText("Selecting bottoms that complement body shape and proportions enhances overall symmetry and attractiveness for both men and women.\n" + "\n" + "Choosing bottoms that can be easily paired with various tops and accessories expands wardrobe options, allowing for more stylish and cohesive looks.\n" + "\n" + "Prioritizing comfort in bottom selection ensures ease of movement and confidence, contributing to a polished and groomed appearance.");
            webView.loadUrl("https://www.youtube.com/watch?v=L0DGWIqfgi4");
        } else if (mode.equals("shoes")) {
            textView.setText("shoes and accessories");
            binding.txtdis.setText("Selecting shoes and accessories that complement the outfit's color scheme and style enhances overall coherence and polish.\n" + "\n" + "Incorporating standout shoes and accessories adds personality and flair to the look, making it more memorable and attractive.\n" + "\n" + "Choosing shoes and accessories that are comfortable and functional ensures both style and practicality, enhancing overall comfort and confidence.");
            webView.loadUrl("https://www.youtube.com/watch?v=IzrXkL-k45Y");
        } else if (mode.equals("essentials")) {

            textView.setText("essentials");
            binding.txtdis.setText("Grooming essentials such as skincare products and grooming tools help maintain hygiene and promote a polished appearance for both men and women.\n" + "\n" + "Proper grooming enhances self-confidence by improving overall appearance and presenting a well-kept image to others.\n" + "\n" + "Attention to grooming suggests professionalism and attention to detail, positively influencing how individuals are perceived in various social and professional settings.\n");
            webView.loadUrl("https://www.youtube.com/watch?v=jWdyJaXvPLQ");
        } else if (mode.equals("tier")) {

            textView.setText("tierlist");
            binding.txtdis.setText("Creating good habits develop character and personal growth that will eventually bear fruit in the long run. + \n + Here is the tier list on what you should focus on and develop.");
            webView.loadUrl("https://www.youtube.com/watch?v=GriR73kSvPY");
        } else if (mode.equals("goals")) {

            textView.setText("essentials");
            binding.txtdis.setText("Setting personal grooming goals provides motivation to improve ones appearance, leading to a more confident and attractive demeanor.\n" + "\n" + "Pursuing individual grooming goals encourages self-improvement, fostering a sense of fulfillment and accomplishment.\n" + "\n" + "Taking control of ones grooming routine allows for a tailored approach that reflects personal preferences and values, enhancing overall satisfaction and attractiveness.");
            webView.loadUrl("https://www.youtube.com/watch?v=XpKvs-apvOs");
        } else if (mode.equals("personality")) {
            textView.setText("personality");
            binding.txtdis.setText("Embracing ones unique personality adds depth and authenticity to their appearance, making them more genuine and attractive.\n" + "\n" + "Developing your own personality style allows for personal expression, setting individuals apart from the crowd and making them more memorable.\n" + "\n" + "Being true to oneself fosters confidence, which radiates outwardly and enhances overall attractiveness, regardless of societal beauty standards.");
            webView.loadUrl("https://www.youtube.com/watch?v=U8ieBTJCrEE");
        }

        // Set up bottom navigation bar
        BottomNavbarHelper.setProfileIconClickListener(this);
    }


}