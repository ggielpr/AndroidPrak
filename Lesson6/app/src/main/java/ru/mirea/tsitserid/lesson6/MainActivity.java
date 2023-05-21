package ru.mirea.tsitserid.lesson6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import ru.mirea.tsitserid.lesson6.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences sharedPref = MainActivity.this.getSharedPreferences("TsitserID_settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("GROUP", String.valueOf(binding.editTextTextPersonName.getText()));
                editor.putString("NUMBER", String.valueOf(binding.editTextTextPersonName2.getText()));
                editor.putString("FILM", String.valueOf(binding.editTextTextPersonName3.getText()));
                editor.apply();
            }
        });

        binding.editTextTextPersonName.setText(sharedPref.getString("GROUP", "BSBO-03-20"));
        binding.editTextTextPersonName2.setText(sharedPref.getString("NUMBER", "28"));
        binding.editTextTextPersonName3.setText(sharedPref.getString("FILM", "Шаг вперед"));
    }
}