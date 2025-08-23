package com.team10.smarthospital.controllers.api;

import com.team10.smarthospital.model.request.AppointmentBook;
import com.team10.smarthospital.model.response.AppointmentRecord;
import com.team10.smarthospital.model.response.BaseResponse;
import com.team10.smarthospital.service.AppointmentSearchService;
import com.team10.smarthospital.service.AppointmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired private AppointmentService appointmentService;

    @Autowired private AppointmentSearchService appointmentSearchService;

    @GetMapping("/patient/upcoming")
    @PreAuthorize("hasRole('PATIENT')")
    public BaseResponse<List<AppointmentRecord>> getPatientUpcoming(
            @AuthenticationPrincipal UserDetails userDetails) {
        return appointmentService.getPatientUpcoming(userDetails.getUsername());
    }

    @GetMapping("/patient/me")
    @PreAuthorize("hasRole('PATIENT')")
    public BaseResponse<List<AppointmentRecord>> getAppointmentPatient(
            @AuthenticationPrincipal UserDetails userDetails) {
        return appointmentService.getAppointmentPatient(userDetails.getUsername());
    }

    @GetMapping("/doctor/me")
    @PreAuthorize("hasRole('DOCTOR')")
    public BaseResponse<List<AppointmentRecord>> getAppointmentDoctor(
            @AuthenticationPrincipal UserDetails userDetails) {
        return appointmentService.getAppointmentDoctor(userDetails.getUsername());
    }

    @GetMapping("/nurse/me")
    @PreAuthorize("hasRole('NURSE')")
    public BaseResponse<List<AppointmentRecord>> getAppointmentNurse(
            @AuthenticationPrincipal UserDetails userDetails) {
        return appointmentService.getAppointmentNurse(userDetails.getUsername());
    }

    @PostMapping("/book")
    @PreAuthorize("hasRole('PATIENT')")
    public BaseResponse<String> bookAppointment(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody AppointmentBook appointmentBook) {
        return appointmentService.book(userDetails.getUsername(), appointmentBook);
    }

    @PostMapping("/cancel")
    @PreAuthorize("hasRole('PATIENT')")
    public BaseResponse<Void> cancelAppointment(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam("appointmentId") String appointmentId) {
        return appointmentService.cancelAppointment(userDetails.getUsername(), appointmentId);
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('DOCTOR')")
    public BaseResponse<List<AppointmentRecord>> searchAppointment(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam("search") String search) {
        return appointmentSearchService.searchAppointment(userDetails.getUsername(), search);
    }
}
