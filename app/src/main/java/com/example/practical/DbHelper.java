package com.example.practical;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {

    private static final String TAG = "SQLite";


    // Phiên bản
    private static final int DATABASE_VERSION = 1;


    // Tên cơ sở dữ liệu.
    private static final String DATABASE_NAME = "Employee_Manager";


    // Tên bảng: Note.
    private static final String TABLE_EMPLOYEE = "Employee";

    private static final String COLUMN_EMPLOYEE_NAME ="Employee_Name";
    private static final String COLUMN_EMPLOYEE_DESGINATION ="Employee_Designation";
    private static final String COLUMN_EMPLOYEE_SALARY = "Employee_Salary";

    public DbHelper(Context context)  {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Script tạo bảng.
        String script = "CREATE TABLE " + TABLE_EMPLOYEE + "("
                + COLUMN_EMPLOYEE_NAME + " TEXT ," + COLUMN_EMPLOYEE_DESGINATION + " TEXT,"
                + COLUMN_EMPLOYEE_SALARY + " INTERGER" + ")";
        // Chạy lệnh tạo bảng.
        db.execSQL(script);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEE);
        onCreate(db);
    }

    public void addEmployee(Employee employee) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_EMPLOYEE_NAME, employee.getName());
        values.put(COLUMN_EMPLOYEE_DESGINATION, employee.getDesignation());
        values.put(COLUMN_EMPLOYEE_SALARY, employee.getSalary());


        // Trèn một dòng dữ liệu vào bảng.
        db.insert(TABLE_EMPLOYEE, null, values);


        // Đóng kết nối database.
        db.close();
    }

    public List<Employee> getAllEmployee() {


        List<Employee> employeeList = new ArrayList<Employee>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_EMPLOYEE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        // Duyệt trên con trỏ, và thêm vào danh sách.
        if (cursor.moveToFirst()) {
            do {
                Employee employee = new Employee();
                employee.setName(cursor.getString(1));
                employee.setDesignation(cursor.getString(2));
                employee.setSalary(Integer.parseInt(cursor.getString(0)));

                // Thêm vào danh sách.
                employeeList.add(employee);
            } while (cursor.moveToNext());
        }

        // return note list
        return employeeList;
    }


    public int updateEmployee(Employee employee) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_EMPLOYEE_NAME, employee.getName());
        values.put(COLUMN_EMPLOYEE_DESGINATION, employee.getDesignation());
        values.put(COLUMN_EMPLOYEE_SALARY, employee.getSalary());

        // updating row
        return db.update(TABLE_EMPLOYEE, values, COLUMN_EMPLOYEE_NAME + " = ?",
                new String[]{String.valueOf(employee.getName())});
    }

    public void deleteEmployee(Employee employee) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EMPLOYEE, COLUMN_EMPLOYEE_NAME + " = ?",
                new String[] { String.valueOf(employee.getName()) });
        db.close();
    }
}
