package ru.mirea.tsitserid.intentfilter;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickActivity(View view) {
        Uri address = Uri.parse("https://www.mirea.ru/");
        Intent openLinkIntent = new Intent(Intent.ACTION_VIEW, address);
        startActivity(openLinkIntent);
    }

    public void onClickShare(View view){
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "MIREA");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Цицер Изабелла Денисовна");
        startActivity(Intent.createChooser(shareIntent, "МОИ ФИО"));
    }
}