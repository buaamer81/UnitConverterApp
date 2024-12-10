package com.rami.unitconverterapp;



import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText inputField;
    private Spinner fromUnitSpinner, toUnitSpinner, categorySpinner;
    private TextView resultText;
    private String selectedCategory = "Length";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        inputField = findViewById(R.id.inputField);
        fromUnitSpinner = findViewById(R.id.fromUnitSpinner);
        toUnitSpinner = findViewById(R.id.toUnitSpinner);
        categorySpinner = findViewById(R.id.categorySpinner);
        resultText = findViewById(R.id.resultText);

        setupCategorySpinner();

        // Add listener for input field changes
        inputField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateResult();
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    private void setupCategorySpinner() {
        final String[] categories = {"Length", "Weight", "Temperature"};
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, categories);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        // Category selection listener
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                selectedCategory = categories[position];
                updateUnitSpinners();
                updateResult(); // Recalculate result when category changes
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });
    }

    private void updateUnitSpinners() {
        String[] units = UnitConverter.getUnitsForCategory(selectedCategory);
        ArrayAdapter<String> unitAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, units);
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromUnitSpinner.setAdapter(unitAdapter);
        toUnitSpinner.setAdapter(unitAdapter);

        // Unit selection listeners
        fromUnitSpinner.setOnItemSelectedListener(new UnitChangeListener());
        toUnitSpinner.setOnItemSelectedListener(new UnitChangeListener());
    }

    private void updateResult() {
        String inputValue = inputField.getText().toString().trim();

        // Handle empty input field
        if (inputValue.isEmpty()) {
            resultText.setText("Result");
            resultText.setTextColor(getResources().getColor(android.R.color.black));
            return;
        }

        // Handle negative sign input (e.g., just "-")
        if (inputValue.equals("-")) {
            resultText.setText("Result");
            resultText.setTextColor(getResources().getColor(android.R.color.black));
            return;
        }

        double input;
        try {
            input = Double.parseDouble(inputValue);
        } catch (NumberFormatException e) {
            resultText.setText("Invalid input");
            resultText.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            return;
        }

        // Check for invalid negative values for Length and Weight
        if ((selectedCategory.equals("Length") || selectedCategory.equals("Weight")) && input < 0) {
            resultText.setText("Invalid input");
            resultText.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            return;
        }

        String fromUnit = (String) fromUnitSpinner.getSelectedItem();
        String toUnit = (String) toUnitSpinner.getSelectedItem();

        double result = UnitConverter.convert(selectedCategory, input, fromUnit, toUnit);

        if (Double.isNaN(result)) {
            resultText.setText("Invalid input");
            resultText.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        } else {
            resultText.setText(String.format("%.2f", result));
            resultText.setTextColor(getResources().getColor(android.R.color.black)); // Reset color for valid inputs
        }
    }

    private class UnitChangeListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            updateResult();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) { }
    }
}
