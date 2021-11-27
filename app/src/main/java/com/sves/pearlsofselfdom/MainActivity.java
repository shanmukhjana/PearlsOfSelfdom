package com.sves.pearlsofselfdom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.sves.pearlsofselfdom.apicall.APIInterface;
import com.sves.pearlsofselfdom.apicall.CampusModal;
import com.sves.pearlsofselfdom.apicall.LoginResponse;
import com.sves.pearlsofselfdom.apicall.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Spinner sp_college;
    EditText etHallTicketNumber, etPassword;
    Button btn_login;
    String spinnerItems;
    List<CampusModal> campusModalList;
    ProgressBar progressBar;
    APIInterface apiInterface;
    String campus_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        apiInterface = RetrofitClient.getInstance().getMyApi();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (checkInternet()) {
            progressBar.setVisibility(View.VISIBLE);
            apiCall();
        } else {
            //  progressBar.setVisibility(View.VISIBLE);
            Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
        }
    }

    private void init() {
        sp_college = findViewById(R.id.sp_college_reg);
        etHallTicketNumber = findViewById(R.id.etHallTicketNumber);
        etPassword = findViewById(R.id.etPassword);
        btn_login = findViewById(R.id.btn_Register);
        campusModalList = new ArrayList<CampusModal>();
        progressBar = findViewById(R.id.id_progressbar);
      //  progressBar=new ProgressBar(this);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void apiCall() {

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
                ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this,
                        android.R.layout.simple_dropdown_item_1line, campusModalList);
                sp_college.setAdapter(arrayAdapter);
            }

            @Override
            public void onFailure(Call<List<CampusModal>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
        sp_college.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CampusModal user = (CampusModal) parent.getSelectedItem();
                campus_id = user.getCampus_id();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    private boolean checkInternet() {
        ConnectivityManager cM = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        assert cM != null;
        NetworkInfo activeNetworkInfo = cM.getActiveNetworkInfo();
        if (activeNetworkInfo != null) {
            return true;
        }
        return false;
    }

    public void register(View view) {
        startActivity(new Intent(this, RegisterPage.class));
    }

    public void login(View view) {
        String hallTicketNumber = etHallTicketNumber.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (hallTicketNumber.equalsIgnoreCase(""))
            Toast.makeText(MainActivity.this, "Please enter your hallticket number", Toast.LENGTH_SHORT).show();
        else if (password.equalsIgnoreCase(""))
            Toast.makeText(MainActivity.this, "Please enter your Password number", Toast.LENGTH_SHORT).show();
        else if (campus_id!=null && campus_id.equalsIgnoreCase("0")) {
            Toast.makeText(MainActivity.this, "Select your campus", Toast.LENGTH_SHORT).show();
        } else
            loginValidation(hallTicketNumber, password, campus_id);
    }

    public void loginValidation(String hallTicketNumber, String password, String campusId) {
        LoginResponse response = new LoginResponse(campusId, hallTicketNumber, password);
        Call<List<LoginResponse>> call = apiInterface.createPost(response);
        call.enqueue(new Callback<List<LoginResponse>>() {
            @Override
            public void onResponse(Call<List<LoginResponse>> call, Response<List<LoginResponse>> response) {
                if (response.isSuccessful()) {
                    List<LoginResponse> loginResponses = response.body();
                    Log.d("shannu", "res---------" + loginResponses.size());
                    for (LoginResponse loginResponse : loginResponses) {
                        String name = loginResponse.getStudent_name();
                        finish();
                        startActivity(new Intent(MainActivity.this, UserHome.class));
                        Log.d("shannu", "name---------" + name);
                        Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                    }

                } else {
                    Toast.makeText(MainActivity.this, "Invalid Login Credentials", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<LoginResponse>> call, Throwable t) {
                Log.d("shannu", "res fail---------" + t.toString());

            }
        });
    }
}