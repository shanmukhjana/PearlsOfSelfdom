package com.sves.pearlsofselfdom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.sves.pearlsofselfdom.apicall.APIInterface;
import com.sves.pearlsofselfdom.apicall.CampusModal;
import com.sves.pearlsofselfdom.apicall.DepartmentModal;
import com.sves.pearlsofselfdom.apicall.LoginResponse;
import com.sves.pearlsofselfdom.apicall.RegisterPostModal;
import com.sves.pearlsofselfdom.apicall.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterPage extends AppCompatActivity {

    Spinner sp_college_reg, sp_college_dep;
    EditText etStudentName, etHallTicketNumber, etStudentMail, etStudentPhoneNumber, etStudentClass, etPassword, etPasswordRe;
    Button btn_Register;
    LinearLayout llDepartment, llRegFields;
    List<CampusModal> campusModalList;
    List<DepartmentModal> depModalList;
    ProgressBar progressBar;
    APIInterface apiInterface;
    String spinnerItems, campus_id = "", dep_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        apiInterface = RetrofitClient.getInstance().getMyApi();
        init();


    }

    private void init() {
        sp_college_reg = findViewById(R.id.sp_college_reg);
        sp_college_dep = findViewById(R.id.sp_college_dep);
        etStudentName = findViewById(R.id.etStudentName);
        etHallTicketNumber = findViewById(R.id.etHallTicketNumber);
        etStudentMail = findViewById(R.id.etStudentMail);
        etStudentPhoneNumber = findViewById(R.id.etStudentPhoneNumber);
        etStudentClass = findViewById(R.id.etStudentClass);
        etPassword = findViewById(R.id.etPassword);
        etPasswordRe = findViewById(R.id.etPasswordRe);
        btn_Register = findViewById(R.id.btn_Register);
        llDepartment = findViewById(R.id.llDepartment);
        llRegFields = findViewById(R.id.llRegFields);
        progressBar = findViewById(R.id.id_progressbar);
        campusModalList = new ArrayList<CampusModal>();
        depModalList = new ArrayList<>();
        progressBar.setVisibility(View.VISIBLE);
        getCampus();
    }

    public void registerSubmit(View view) {
        String studentName = etStudentName.getText().toString().trim();
        String hallTicketNumber = etHallTicketNumber.getText().toString().trim();
        String studentMail = etStudentMail.getText().toString().trim();
        String studentPhoneNumber = etStudentPhoneNumber.getText().toString().trim();
        String studentClass = etStudentClass.getText().toString().trim();
        String pwd = etPassword.getText().toString().trim();
        String passwordRe = etPasswordRe.getText().toString().trim();
        String clgId = campus_id;
        String depId = dep_id;

        if (studentName.equalsIgnoreCase("")
                || hallTicketNumber.equalsIgnoreCase("")
                || studentMail.equalsIgnoreCase("")
                || studentPhoneNumber.equalsIgnoreCase("")
                || studentClass.equalsIgnoreCase("")
                || pwd.equalsIgnoreCase("")
                || passwordRe.equalsIgnoreCase("")) {
            Toast.makeText(RegisterPage.this, "Enter All Details", Toast.LENGTH_SHORT).show();
        } else if (!pwd.equals(passwordRe))
            Toast.makeText(RegisterPage.this, "Password Mis-Match", Toast.LENGTH_SHORT).show();
        else {
            Toast.makeText(RegisterPage.this, "enter", Toast.LENGTH_SHORT).show();
            String deviceId = getDeviceId();
            registerMethod(clgId, depId, studentName, hallTicketNumber, studentMail, studentPhoneNumber,
                    passwordRe, studentClass, deviceId);
        }
    }

    public void registerMethod(String campus_id, String department_id, String student_name,
                               String student_hallticket, String student_mail, String student_number,
                               String password, String classss, String device_id) {
        progressBar.setVisibility(View.VISIBLE);
        RegisterPostModal response = new RegisterPostModal(campus_id, department_id, student_name,
                student_hallticket, student_mail, student_number, password, classss, device_id);
        Call<RegisterPostModal> call = apiInterface.register(response);
        call.enqueue(new Callback<RegisterPostModal>() {
            @Override
            public void onResponse(Call<RegisterPostModal> call, Response<RegisterPostModal> response) {
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    RegisterPostModal registerPostModal = response.body();
                    Toast.makeText(RegisterPage.this, "" + registerPostModal.getStudent_name(), Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(RegisterPage.this, MainActivity.class));
                }
            }

            @Override
            public void onFailure(Call<RegisterPostModal> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    public void getCampus() {
        Call<List<CampusModal>> call = apiInterface.campusModal();
        call.enqueue(new Callback<List<CampusModal>>() {
            @Override
            public void onResponse(Call<List<CampusModal>> call, Response<List<CampusModal>> response) {
                List<CampusModal> campusModals = response.body();
                for (CampusModal campusModal : campusModals) {
                    spinnerItems = campusModal.getCampus_name();
                    String name = campusModal.getCampus_name();
                    String campus_id = campusModal.getCampus_id();
                    campusModal = new CampusModal(campus_id, name);
                    campusModalList.add(campusModal);
                    progressBar.setVisibility(View.GONE);
                }
                CampusModal cm = new CampusModal("0", "Select Campus");
                campusModalList.add(0, cm);
                ArrayAdapter arrayAdapter = new ArrayAdapter(RegisterPage.this,
                        android.R.layout.simple_dropdown_item_1line, campusModalList);
                sp_college_reg.setAdapter(arrayAdapter);
            }

            @Override
            public void onFailure(Call<List<CampusModal>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);

            }
        });
        sp_college_reg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CampusModal user = (CampusModal) parent.getSelectedItem();
                campus_id = user.getCampus_id();
                if (position != 0) {
                    llDepartment.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                    depModalList.clear();
                    getDepartment(campus_id);
                } else {
                    llRegFields.setVisibility(View.GONE);
                    llDepartment.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                llDepartment.setVisibility(View.GONE);
            }
        });
    }

    public void getDepartment(String campus_id) {
        progressBar.setVisibility(View.VISIBLE);

        Call<List<DepartmentModal>> depCall = apiInterface.depModal(campus_id);
        depCall.enqueue(new Callback<List<DepartmentModal>>() {
            @Override
            public void onResponse(Call<List<DepartmentModal>> call, Response<List<DepartmentModal>> response) {
                List<DepartmentModal> departmentModalList = response.body();
                if (departmentModalList != null) {
                    for (DepartmentModal departmentModal : departmentModalList) {
                        String department_name = departmentModal.getDepartment_name();
                        String department_id = departmentModal.getDepartment_id();
                        String campus_id = departmentModal.getCampus_id();
                        departmentModal = new DepartmentModal(department_id, campus_id, department_name);
                        depModalList.add(departmentModal);
                        progressBar.setVisibility(View.GONE);
                    }
                    DepartmentModal dm = new DepartmentModal("0", "0",
                            "Select Department");
                    depModalList.add(0, dm);
                    ArrayAdapter arrayAdapter = new ArrayAdapter(RegisterPage.this,
                            android.R.layout.simple_dropdown_item_1line, depModalList);
                    sp_college_dep.setAdapter(arrayAdapter);

                } else {
                    Toast.makeText(RegisterPage.this, "Error while getting Department. Please Try Again", Toast.LENGTH_SHORT).show();
                    llDepartment.setVisibility(View.GONE);
                    llRegFields.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<DepartmentModal>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
        sp_college_dep.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                DepartmentModal department = (DepartmentModal) adapterView.getSelectedItem();
                dep_id = department.getDepartment_id();
                if (i != 0) {
                    llRegFields.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                } else
                    llRegFields.setVisibility(View.GONE);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public String getDeviceId() {
        String android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.d("Android", "Android ID : " + android_id);
        return android_id;
    }
}


