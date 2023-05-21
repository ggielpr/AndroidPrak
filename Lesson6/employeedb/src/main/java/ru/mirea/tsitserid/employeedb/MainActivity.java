package ru.mirea.tsitserid.employeedb;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import ru.mirea.tsitserid.employeedb.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AppDatabase db = App.getInstance().getDatabase();
        EmployeeDao employeeDao = db.employeeDao();

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.editTextTextPersonName.getText().length() != 0) {
                    Employee employee;
                    employee = employeeDao.getById(Long.parseLong(String.valueOf(binding.editTextTextPersonName.getText())));
                    binding.editTextTextPersonName2.setText(employee.getName());
                    binding.editTextTextPersonName3.setText(employee.getSalary());
                } else {
                    Toast.makeText(getApplicationContext(), "Введите id", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.editTextTextPersonName.getText().length() != 0 & binding.editTextTextPersonName2.getText().length() != 0 & binding.editTextTextPersonName3.getText().length() != 0) {
                    try {
                        Employee employee = new Employee();
                        employee.id = Long.parseLong(String.valueOf(binding.editTextTextPersonName.getText()));
                        employee.name = String.valueOf(binding.editTextTextPersonName2.getText());
                        employee.salary = Integer.parseInt(String.valueOf(binding.editTextTextPersonName3.getText()));
                        employeeDao.insert(employee);
                    }
                    catch (SQLiteConstraintException e) {
                        Toast.makeText(getApplicationContext(), "id уже используется", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Вы не заполнили все поля", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}