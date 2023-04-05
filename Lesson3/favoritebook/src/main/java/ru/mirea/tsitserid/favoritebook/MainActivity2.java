package ru.mirea.tsitserid.favoritebook;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity2 extends AppCompatActivity {
    private TextView textViewBook;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Bundle extras = getIntent().getExtras();
        String bookName = extras.getString(MainActivity.KEY);
        textViewBook = findViewById(R.id.textViewBook);
        textViewBook.setText("Любимая книга разработчика – " + bookName);
        editText = findViewById(R.id.editTextBook);
    }

    public void choosebook(View view) {
        Intent data = new Intent();
        data.putExtra(MainActivity.USER_MESSAGE, editText.getText().toString());
        setResult(Activity.RESULT_OK, data);
        finish();
    }
}