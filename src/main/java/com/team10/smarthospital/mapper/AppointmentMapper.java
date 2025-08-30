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

    List<Appointment> getAppointmentsByProviderIdPatientIdDate(
            String providerId, String patientId, LocalDate date);

    List<Appointment> getAppointmentsUpcomingByPatientId(String patientId);

    void updateStatusByDate(LocalDate date, Integer status);

    void updateStatusByPatientId(String patientId, String appointmentId, Integer status);

    void updateStatus(String appointmentId, Integer status);
}
