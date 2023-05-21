package ru.mirea.tsitserid.employeedb;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Employee {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public String name;
    public int salary;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSalary() {
        return String.valueOf(salary);
    }
}
