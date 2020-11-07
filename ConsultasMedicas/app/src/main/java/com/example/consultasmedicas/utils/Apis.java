package com.example.consultasmedicas.utils;

import com.example.consultasmedicas.utils.Allergy.AllergyController;
import com.example.consultasmedicas.utils.Allergy.AllergyService;
import com.example.consultasmedicas.utils.Login.LoginService;
import com.example.consultasmedicas.utils.Patient.PatientService;

public class Apis {

    public static final String URL_001 = "http://10.0.2.2:8080";

    public static AllergyService getAllergyService(){
        return AllergyController.getAllergy(URL_001).create(AllergyService.class);
    }
    public static LoginService loginUserService(){
        return RetrofitController.buildRetrofit(URL_001).create(LoginService.class);
    }

    public static PatientService patientService(){
        return RetrofitController.buildRetrofit(URL_001).create(PatientService.class);
    }

    public static PatientService signUpPatientService(){
        return RetrofitController.buildRetrofit(URL_001).create(PatientService.class);
    }

}
