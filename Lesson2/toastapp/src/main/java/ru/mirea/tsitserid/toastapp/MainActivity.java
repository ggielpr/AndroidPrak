package ru.mirea.tsitserid.toastapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private Integer len;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editTextPersonName);
    }

    public void onClickNewToast(View view){
        len = editText.getText().length();
        Toast toast = Toast.makeText(getApplicationContext(),
                "СТУДЕНТ No " + len +" ГРУППА " + len +" Количество символов - " + len,
                Toast.LENGTH_SHORT);
        toast.show();
    }
}