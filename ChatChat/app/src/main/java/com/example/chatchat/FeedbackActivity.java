package com.example.chatchat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import androidx.appcompat.app.AppCompatActivity;

public class FeedbackActivity extends AppCompatActivity {
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_feedback);
    final RatingBar rating = findViewById(R.id.starbar);
    final EditText bug = findViewById(R.id.bugfeedBack);
    final EditText feature = findViewById(R.id.featureSuggestion);
    Button submit = findViewById(R.id.sendFeedbackButton);
    submit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            if (bug.getText().toString().length() == 0 || feature.getText().toString().length() == 0)
//                return;
            String feedback = composeFeedback(rating.getRating(), bug.getText().toString(), feature.getText().toString());
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setData(Uri.parse("mailto:"));
            emailIntent.setType("text/plain");
            emailIntent.putExtra(Intent.EXTRA_EMAIL  , new String[]{"jiang385@purdue.edu"});
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "feedback");
            emailIntent.putExtra(Intent.EXTRA_TEXT   , feedback);
            startActivity(emailIntent);
        }
    });
}
    public String composeFeedback( Float rating, String bugReport, String featureSuggestion){
        String score = "NA";
        if (rating > 0)
            score = rating.toString();
        return "Rating: " + score + "\n" +
                "Bugs: " + bugReport + "\n" +
                "Feature Suggestion: " + featureSuggestion;
    }
}
