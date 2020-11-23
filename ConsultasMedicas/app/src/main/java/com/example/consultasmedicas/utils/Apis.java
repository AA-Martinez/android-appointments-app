package com.example.consultasmedicas.utils;

import com.example.consultasmedicas.model.Appointment.Appointment;
import com.example.consultasmedicas.model.Medication.Medication;
import com.example.consultasmedicas.model.Substance.Substance;
import com.example.consultasmedicas.utils.Allergy.AllergyService;
import com.example.consultasmedicas.utils.ApiMedic.ApiMedicService;
import com.example.consultasmedicas.utils.Appointment.AppointmentService;
import com.example.consultasmedicas.utils.Degree.DegreeService;
import com.example.consultasmedicas.utils.Disease.DiseaseService;
import com.example.consultasmedicas.utils.Doctor.DoctorService;
import com.example.consultasmedicas.utils.Login.LoginService;
import com.example.consultasmedicas.utils.Medication.MedicationService;
import com.example.consultasmedicas.utils.Patient.PatientService;
import com.example.consultasmedicas.utils.Substance.SubstanceService;

public class Apis {

    public static final String URL_001 = "http://10.0.2.2:8080";
    public static final String URL_002 = "https://sandbox-authservice.priaid.ch";


    public static LoginService loginUserService(){
        return RetrofitController.buildRetrofit(URL_001).create(LoginService.class);
    }

    public static PatientService patientService(){
        return RetrofitController.buildRetrofit(URL_001).create(PatientService.class);
    }

    public static PatientService signUpPatientService(){
        return RetrofitController.buildRetrofit(URL_001).create(PatientService.class);
    }

    public static AllergyService allergyService(){
        return RetrofitController.buildRetrofit(URL_001).create(AllergyService.class);
    }

    public static DiseaseService diseaseService(){
        return RetrofitController.buildRetrofit(URL_001).create(DiseaseService.class);
    }

    public static MedicationService medicationService(){
        return RetrofitController.buildRetrofit(URL_001).create(MedicationService.class);
    }

    public static SubstanceService substanceService(){
        return RetrofitController.buildRetrofit(URL_001).create(SubstanceService.class);
    }


    public static DoctorService doctorService(){
        return RetrofitController.buildRetrofit(URL_001).create(DoctorService.class);
    }

    public static AppointmentService appointmentService(){
        return RetrofitController.buildRetrofit(URL_001).create(AppointmentService.class);
    }

    public static DegreeService degreeService(){
        return RetrofitController.buildRetrofit(URL_001).create(DegreeService.class);
    }

    public static ApiMedicService apiMedicService(){
        return RetrofitController.buildRetrofit(URL_002).create(ApiMedicService.class);
    }

}
