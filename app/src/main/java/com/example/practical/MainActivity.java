package com.example.practical;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private DbHelper dbHelper = new DbHelper(MainActivity.this);

    private EditText name;
    private EditText designation;
    private EditText salary;
    private Button addBtn;
    private Button updateBtn;
    private Button deleteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView)findViewById(R.id.lvEmployee);
        name = (EditText)findViewById(R.id.name);
        designation = (EditText)findViewById(R.id.designation);
        salary = (EditText)findViewById(R.id.salary);

        List<Employee> listEmployee = dbHelper.getAllEmployee();

        ArrayAdapter<Employee> arrayAdapter
                = new ArrayAdapter<Employee>(this, android.R.layout.activity_list_item , listEmployee);

        listView.setAdapter(arrayAdapter);
    }


    @Override
    public void onClick(View v) {
        if (v==addBtn){
            onAdd();
        } else if (v==updateBtn){
            onUpdate();
        } else if (v==deleteBtn) {
            onDelete();
        }
    }

    private void onDelete() {
        Employee employee = new Employee();
        employee.setName(name.getText().toString());
        employee.setDesignation(designation.getText().toString());
        employee.setSalary(Integer.parseInt(salary.getText().toString()));
        dbHelper.deleteEmployee(employee);
        finish();
    }

    private void onUpdate() {
        Employee employee = new Employee();
        employee.setName(name.getText().toString());
        employee.setDesignation(designation.getText().toString());
        employee.setSalary(Integer.parseInt(salary.getText().toString()));
        dbHelper.updateEmployee(employee);
        finish();
    }

    private void onAdd() {
        Employee employee = new Employee();
        employee.setName(name.getText().toString());
        employee.setDesignation(designation.getText().toString());
        employee.setSalary(Integer.parseInt(salary.getText().toString()));
        dbHelper.addEmployee(employee);
        finish();
    }
}
