
package com.example.budgetapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class EntryActivity extends Activity {
    EditText editExpense, editIncome, editDesc1, editDesc2;
    Button btnEnterData, btnBack, btnShow, btnDatePick;
    Calendar selectedDate = Calendar.getInstance();
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        editExpense = findViewById(R.id.editExpense);
        editIncome = findViewById(R.id.editIncome);
        editDesc1 = findViewById(R.id.editDesc1);
        editDesc2 = findViewById(R.id.editDesc2);
        btnEnterData = findViewById(R.id.btnEnterData);
        btnBack = findViewById(R.id.btnBack);
        btnShow = findViewById(R.id.btnShow);
        btnDatePick = findViewById(R.id.btnDatePick);

        btnDatePick.setOnClickListener(v -> {
            DatePickerDialog dpd = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
                selectedDate.set(year, month, dayOfMonth);
                btnDatePick.setText(dayOfMonth + "/" + (month + 1));
            }, selectedDate.get(Calendar.YEAR), selectedDate.get(Calendar.MONTH), selectedDate.get(Calendar.DAY_OF_MONTH));
            dpd.show();
        });

        btnEnterData.setOnClickListener(v -> saveToExcel());

        btnBack.setOnClickListener(v -> finish());

        btnShow.setOnClickListener(v -> {
            if (file != null && file.exists()) {
                Toast.makeText(this, "الموقع: " + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void saveToExcel() {
        try {
            String month = String.valueOf(selectedDate.get(Calendar.MONTH) + 1);
            String fileName = "budget_" + month + ".xlsx";
            file = new File(getExternalFilesDir(null), fileName);
            Workbook workbook;
            Sheet sheet;

            if (file.exists()) {
                workbook = WorkbookFactory.create(file);
                sheet = workbook.getSheetAt(0);
            } else {
                workbook = new XSSFWorkbook();
                sheet = workbook.createSheet("الشهر " + month);
                Row header = sheet.createRow(0);
                header.createCell(0).setCellValue("التاريخ");
                header.createCell(1).setCellValue("المصروفات");
                header.createCell(2).setCellValue("الإيرادات");
                header.createCell(3).setCellValue("الوصف");
            }

            int lastRow = sheet.getLastRowNum() + 1;
            Row row = sheet.createRow(lastRow);
            row.createCell(0).setCellValue(selectedDate.getTime().toString());
            row.createCell(1).setCellValue(editExpense.getText().toString());
            row.createCell(2).setCellValue(editIncome.getText().toString());
            row.createCell(3).setCellValue(editDesc1.getText().toString() + " " + editDesc2.getText().toString());

            FileOutputStream fos = new FileOutputStream(file);
            workbook.write(fos);
            fos.close();
            workbook.close();

            Toast.makeText(this, "تم حفظ البيانات", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(this, "خطأ: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
