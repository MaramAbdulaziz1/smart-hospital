package com.team10.smarthospital.mapper;

import com.team10.smarthospital.model.entity.Appointment;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentMapper {

    List<Appointment> getAppointmentsByPatientId(String patientId);

    List<Appointment> getAppointmentsByProviderId(String doctorId);

    Appointment getAppointmentsById(String appointmentId);

    void insertAppointment(Appointment appointment);

    List<Appointment> getPatientYearMonth(String patientId, LocalDate start, LocalDate end);

    List<Appointment> getYearMonth(String providerId, LocalDate start, LocalDate end);

    List<Appointment> getAppointmentsByProviderIdAndSearch(String providerId, String search);

    List<Appointment> getAppointmentsByProviderIdDate(String providerId, LocalDate date);
}
