package com.example.androidhomework8;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    EditText etEmail, etPhone, etDOB;
    TextView tvEmail, tvPhone, tvDOB;
    Boolean bEmail, bPhone, bDOB;

    //Regex  Pattern for Email Address  
    private static Pattern checkEmail = Pattern.compile(
            "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"
    );
    //Regex Pattern for Phone Number
    private static Pattern checkPhone = Pattern.compile(
            "^(?:(?:\\+?1\\s*(?:[.-]\\s*)?)?(?:\\(\\s*([2-9]1[02-9]|[2-9][02-8]1|[2-9][02-8][02-9])\\s*\\)|([2-9]1[02-9]|[2-9][02-8]1|[2-9][02-8][02-9]))\\s*(?:[.-]\\s*)?)?([2-9]1[02-9]|[2-9][02-9]1|[2-9][02-9]{2})\\s*(?:[.-]\\s*)?([0-9]{4})(?:\\s*(?:#|x\\.?|ext\\.?|extension)\\s*(\\d+))?$"
    );
    //Regex Pattern for Date of Birth
    private  static Pattern checkDOB = Pattern.compile(
            "^(19|20)\\d\\d[- /.](0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])$"
    );

    //This abstract class is used for listening to an EditText field every time
    //a user inputs or changes something in the field
    public abstract class TextChangedListener<T> implements TextWatcher {
        private T target;

        public TextChangedListener(T target) {
            this.target = target;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            this.onTextChanged(target, s);
        }

        public abstract void onTextChanged(T target, Editable s);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Handle Activity Elements ID
        Button btnCheckRegex = findViewById(R.id.btnCheckRegex);
        etEmail = findViewById(R.id.edtTxtEmailAddress);
        etPhone = findViewById(R.id.edtTxtPhoneNumber);
        etDOB = findViewById(R.id.edtTxtDateOfBirth);

        tvEmail = findViewById(R.id.txtViewEmailMessage);
        tvPhone = findViewById(R.id.txtViewPhoneMessage);
        tvDOB = findViewById(R.id.txtViewDateOfBirthMessage);

        //Add a text listener for email address EditText
        etEmail.addTextChangedListener(new TextChangedListener<EditText>(etEmail) {
            @Override
            public void onTextChanged(EditText target, Editable s) {
                if (!ValidateEmailPhoneDOB(etEmail.getText().toString(), 1))
                {
                    tvEmail.setText("* Invalid Email Address");
                    bEmail = false;
                }else{
                    tvEmail.setText("");
                    bEmail = true;
                }
            }
        });
        //Add a text listener for Phone Number EditText
        etPhone.addTextChangedListener(new TextChangedListener<EditText>(etPhone) {
            @Override
            public void onTextChanged(EditText target, Editable s) {
                if (!ValidateEmailPhoneDOB(etPhone.getText().toString(), 2))
                {
                    tvPhone.setText("* Invalid Phone Number");
                    bPhone = false;
                }else{
                    tvPhone.setText("");
                    bPhone = true;
                }
            }
        });

        //Add a text listener for Date of Birth EditText
        etDOB.addTextChangedListener(new TextChangedListener<EditText>(etDOB) {
            @Override
            public void onTextChanged(EditText target, Editable s) {
                if (!ValidateEmailPhoneDOB(etDOB.getText().toString(), 3))
                {
                    tvDOB.setText("* Invalid Date of Birth");
                    bDOB = false;
                }else{
                    tvDOB.setText("");
                    bDOB = true;
                }
            }
        });
        
        //Button For Checking if all fields are filled
        btnCheckRegex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (etEmail.getText().toString().isEmpty() || etPhone.getText().toString().isEmpty() || etDOB.getText().toString().isEmpty())
                {
                    Toast.makeText(MainActivity.this, "All Fields Are Required!", Toast.LENGTH_SHORT).show();
                }
                else if (!bEmail || !bPhone || !bDOB){
                    Toast.makeText(MainActivity.this, "Please Double Check Your Inputs", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "All Fields Passed The Validation", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    //This method is used for calling the Regex pattern for specific field
    //It receives to parameters, one is for EditText string and another is an int type
    //for switch purposes. If an EditText passes a designated Regex pattern
    //It returns true, otherwise returns false
    public static boolean ValidateEmailPhoneDOB(String s, int type)
    {
        Matcher match = null;
        switch (type)
        {
            case 1://calls email regex pattern
                match = checkEmail.matcher(s);
                break;
            case 2://calls phone regex pattern
                match = checkPhone.matcher(s);
                break;
            case 3://calls DOB regex pattern
                match = checkDOB.matcher(s);
                break;        }

        if (match.matches())
        {
            return true;
        }
        return false;
    }
}
