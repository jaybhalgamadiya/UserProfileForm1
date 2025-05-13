package com.example.userprofileform;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText editTextName, editTextEmail;
    private Spinner spinnerCountry;
    private CheckBox checkboxTerm;
    private RadioGroup radioGroupGender;
    private RadioButton radioBtnMale, radioBtnFemale, radioBtnOther;
    private Button btnSave;

    private static final String PREF_NAME = "UserProfilePreferences";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_COUNTRY = "country";
    private static final String KEY_TERMS = "terms";

    private String[] countries= {"Select Country", "India", "USA", "Canada", "UK", "Australia"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        checkboxTerm = findViewById(R.id.checkboxTerm);
        btnSave = findViewById(R.id.btnSave);
        radioBtnMale = findViewById(R.id.radioBtnMale);
        radioBtnFemale = findViewById(R.id.radioBtnFemale);
        radioBtnOther = findViewById(R.id.radioBtnOther);
        spinnerCountry = findViewById(R.id.spinnerCountry);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,countries);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCountry.setAdapter(adapter);

        loadData();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

    }

    private void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //for name and email
        editor.putString(KEY_NAME, editTextName.getText().toString());
        editor.putString(KEY_EMAIL, editTextEmail.getText().toString());
        //for gender
        int selectedGenderId = radioGroupGender.getCheckedRadioButtonId();
        RadioButton selectedGender = findViewById(selectedGenderId);
        editor.putString(KEY_GENDER, selectedGender != null ? selectedGender.getText().toString():"");
        //for country
        editor.putString(KEY_COUNTRY, spinnerCountry.getSelectedItem().toString());
        //for checkbox
        editor.putBoolean(KEY_TERMS, checkboxTerm.isChecked());
        editor.apply();
        Toast.makeText(MainActivity.this, "Data Saved!!",Toast.LENGTH_SHORT).show();
    }

    private void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String name = sharedPreferences.getString(KEY_NAME, "");
        editTextName.setText(name);

        String email = sharedPreferences.getString(KEY_EMAIL, "");
        editTextEmail.setText(email);

        String gender = sharedPreferences.getString(KEY_GENDER, "");
        if("Male".equals(gender)){
            radioGroupGender.check(R.id.radioBtnMale);
        }else if("Female".equals(gender)){
            radioGroupGender.check(R.id.radioBtnFemale);
        }else{
            radioGroupGender.check(R.id.radioBtnOther);
        }

        String country = sharedPreferences.getString(KEY_COUNTRY, "Select Country");
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) spinnerCountry.getAdapter();
        int spinnerPosition = adapter.getPosition(country);
        spinnerCountry.setSelection(spinnerPosition);

        boolean terms = sharedPreferences.getBoolean(KEY_TERMS, false);
        checkboxTerm.setChecked(terms);
    }
}