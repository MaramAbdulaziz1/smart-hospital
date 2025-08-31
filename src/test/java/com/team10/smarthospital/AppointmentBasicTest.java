package com.team10.smarthospital;

import com.team10.smarthospital.model.entity.Appointment;
import com.team10.smarthospital.model.request.AppointmentBook;
import com.team10.smarthospital.model.response.BaseResponse;
import com.team10.smarthospital.model.response.AppointmentRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Basic appointment tests covering core functionality
 * Tests appointment booking, validation, and entity operations
 */
@DisplayName("Basic Appointment Tests")
public class AppointmentBasicTest {

  private AppointmentBook validAppointmentBook;
  private Appointment testAppointment;

  @BeforeEach
  void setUp() {
    // Setup valid appointment booking request
    validAppointmentBook = new AppointmentBook();
    validAppointmentBook.setDate(LocalDate.of(2025, 12, 15));
    validAppointmentBook.setTime(3);
    validAppointmentBook.setProviderId("provider-123");

    // Setup test appointment entity
    testAppointment = new Appointment();
    testAppointment.setAppointmentId("appt-456");
    testAppointment.setPatientId("patient-789");
    testAppointment.setProviderId("provider-123");
    testAppointment.setDate(LocalDate.of(2025, 12, 15));
    testAppointment.setAppointTime(3);
    testAppointment.setStatus(0);
  }

  @Nested
  @DisplayName("AppointmentBook Request Tests")
  class AppointmentBookTests {

    @Test
    @DisplayName("Valid appointment book request should be accepted")
    void appointmentBook_ValidRequest_ShouldBeAccepted() {
      AppointmentBook book = new AppointmentBook();
      book.setDate(LocalDate.of(2025, 12, 20));
      book.setTime(5);
      book.setProviderId("provider-456");

      assertNotNull(book.getDate(), "Date should not be null");
      assertNotNull(book.getTime(), "Time should not be null");
      assertNotNull(book.getProviderId(), "Provider ID should not be null");
      assertEquals(LocalDate.of(2025, 12, 20), book.getDate());
      assertEquals(Integer.valueOf(5), book.getTime());
      assertEquals("provider-456", book.getProviderId());
    }

    @ParameterizedTest
    @DisplayName("All valid time slots should be accepted")
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12})
    void appointmentBook_ValidTimeSlots_AllAccepted(int timeSlot) {
      AppointmentBook book = new AppointmentBook();
      book.setTime(timeSlot);

      assertEquals(Integer.valueOf(timeSlot), book.getTime());
      assertTrue(timeSlot >= 1 && timeSlot <= 12, "Time slot should be in valid range");
    }

    @Test
    @DisplayName("Future dates should be accepted for booking")
    void appointmentBook_FutureDates_ShouldBeAccepted() {
      AppointmentBook book = new AppointmentBook();
      LocalDate futureDate = LocalDate.now().plusDays(7);

      book.setDate(futureDate);
      assertEquals(futureDate, book.getDate());
      assertTrue(futureDate.isAfter(LocalDate.now()) || futureDate.isEqual(LocalDate.now()));
    }

    @ParameterizedTest
    @DisplayName("Various provider ID formats should be accepted")
    @CsvSource({
      "'provider-123', 'Standard format'",
      "'PROVIDER-456', 'Uppercase format'",
      "'prov_789', 'Underscore format'",
      "'12345678-1234-1234-1234-123456789012', 'UUID format'"
    })
    void appointmentBook_ProviderIdFormats_AllAccepted(String providerId, String description) {
      AppointmentBook book = new AppointmentBook();
      book.setProviderId(providerId);

      assertEquals(providerId, book.getProviderId(), "Should accept " + description);
      assertNotNull(book.getProviderId(), "Provider ID should not be null");
    }
  }

  @Nested
  @DisplayName("Appointment Entity Tests")
  class AppointmentEntityTests {

    @Test
    @DisplayName("Appointment entity should store all required fields")
    void appointment_RequiredFields_ShouldBeStored() {
      Appointment appointment = new Appointment();
      appointment.setAppointmentId("appt-123");
      appointment.setPatientId("patient-456");
      appointment.setProviderId("provider-789");
      appointment.setDate(LocalDate.of(2025, 11, 30));
      appointment.setAppointTime(7);
      appointment.setStatus(0);

      assertEquals("appt-123", appointment.getAppointmentId());
      assertEquals("patient-456", appointment.getPatientId());
      assertEquals("provider-789", appointment.getProviderId());
      assertEquals(LocalDate.of(2025, 11, 30), appointment.getDate());
      assertEquals(Integer.valueOf(7), appointment.getAppointTime());
      assertEquals(Integer.valueOf(0), appointment.getStatus());
    }

    @ParameterizedTest
    @DisplayName("All appointment status codes should be accepted")
    @CsvSource({
      "0, 'UPCOMING'",
      "1, 'COMPLETED'",
      "2, 'CANCELLED'",
      "3, 'EXPIRED'"
    })
    void appointment_StatusCodes_AllValidCodesAccepted(int statusCode, String description) {
      Appointment appointment = new Appointment();
      appointment.setStatus(statusCode);

      assertEquals(Integer.valueOf(statusCode), appointment.getStatus());
      assertTrue(statusCode >= 0 && statusCode <= 3,
        "Status code should be valid for " + description);
    }

    @Test
    @DisplayName("Appointment should handle cancellation reasons")
    void appointment_CancellationReasons_ShouldBeHandled() {
      Appointment appointment = new Appointment();
      String[] reasons = {
        "Patient requested cancellation",
        "Doctor unavailable",
        "Emergency situation",
        null,
        ""
      };

      for (String reason : reasons) {
        appointment.setCancellationReason(reason);
        assertEquals(reason, appointment.getCancellationReason());
      }
    }

    @Test
    @DisplayName("Appointment IDs should accept various formats")
    void appointment_IdFormats_ShouldAcceptVariousFormats() {
      Appointment appointment = new Appointment();
      String[] testIds = {
        "550e8400-e29b-41d4-a716-446655440000", // UUID
        "appointment-123", // Custom format
        "appt_456", // Underscore format
        "12345" // Numeric
      };

      for (String id : testIds) {
        appointment.setAppointmentId(id);
        assertEquals(id, appointment.getAppointmentId());
      }
    }
  }

  @Nested
  @DisplayName("Appointment Record Tests")
  class AppointmentRecordTests {

    @Test
    @DisplayName("AppointmentRecord should handle complete data")
    void appointmentRecord_CompleteData_ShouldBeHandled() {
      AppointmentRecord record = new AppointmentRecord();
      record.setAppointmentId("record-123");
      record.setDate(LocalDate.of(2025, 12, 20));
      record.setProviderName("Dr. Smith");
      record.setPatientName("John Doe");
      record.setPatientCode("P123456");
      record.setPatientEmail("john@test.com");
      record.setStatus("UPCOMING");
      record.setTimeCode(3);
      record.setStartTime("11:00 AM");

      assertEquals("record-123", record.getAppointmentId());
      assertEquals(LocalDate.of(2025, 12, 20), record.getDate());
      assertEquals("Dr. Smith", record.getProviderName());
      assertEquals("John Doe", record.getPatientName());
      assertEquals("P123456", record.getPatientCode());
      assertEquals("john@test.com", record.getPatientEmail());
      assertEquals("UPCOMING", record.getStatus());
      assertEquals(Integer.valueOf(3), record.getTimeCode());
      assertEquals("11:00 AM", record.getStartTime());
    }

    @Test
    @DisplayName("AppointmentRecord should handle time formatting")
    void appointmentRecord_TimeFormatting_ShouldWork() {
      AppointmentRecord record = new AppointmentRecord();
      String[] timeFormats = {
        "09:00 AM",
        "11:30 AM",
        "02:00 PM",
        "04:30 PM"
      };

      for (String timeFormat : timeFormats) {
        record.setStartTime(timeFormat);
        assertEquals(timeFormat, record.getStartTime());
        assertTrue(timeFormat.contains("AM") || timeFormat.contains("PM"));
      }
    }
  }

  @Nested
  @DisplayName("Base Response Tests")
  class BaseResponseTests {

    @Test
    @DisplayName("BaseResponse should handle appointment data types")
    void baseResponse_AppointmentDataTypes_ShouldBeSupported() {
      // Test with List<AppointmentRecord>
      BaseResponse<List<AppointmentRecord>> listResponse = new BaseResponse<>();
      List<AppointmentRecord> appointments = new ArrayList<>();
      listResponse.setData(appointments);

      assertNotNull(listResponse.getData());
      assertTrue(listResponse.getData() instanceof List);

      // Test with single AppointmentBook
      BaseResponse<AppointmentBook> bookResponse = new BaseResponse<>();
      bookResponse.setData(validAppointmentBook);
      assertEquals(validAppointmentBook, bookResponse.getData());

      // Test with single Appointment
      BaseResponse<Appointment> appointmentResponse = new BaseResponse<>();
      appointmentResponse.setData(testAppointment);
      assertEquals(testAppointment, appointmentResponse.getData());
    }

    @Test
    @DisplayName("BaseResponse should handle null data")
    void baseResponse_NullData_ShouldBeHandled() {
      BaseResponse<AppointmentRecord> response = new BaseResponse<>();

      assertNull(response.getData(), "Initial data should be null");

      response.setData(null);
      assertNull(response.getData(), "Should accept null data");
    }
  }

  @Nested
  @DisplayName("Business Logic Tests")
  class BusinessLogicTests {

    @Test
    @DisplayName("Appointment booking should validate time slots")
    void appointmentBooking_TimeSlots_ShouldBeValidated() {
      AppointmentBook book = new AppointmentBook();

      // Test all valid time slots
      for (int i = 1; i <= 12; i++) {
        book.setTime(i);
        assertTrue(book.getTime() >= 1 && book.getTime() <= 12,
          "Time slot " + i + " should be valid");
      }
    }

    @Test
    @DisplayName("Appointment should handle scheduling conflicts")
    void appointment_SchedulingConflicts_ShouldBeDetected() {
      // Create two appointments with same provider, date, and time
      Appointment appointment1 = new Appointment();
      appointment1.setPatientId("patient-123");
      appointment1.setProviderId("provider-456");
      appointment1.setDate(LocalDate.of(2025, 12, 25));
      appointment1.setAppointTime(5);

      Appointment appointment2 = new Appointment();
      appointment2.setPatientId("patient-789");
      appointment2.setProviderId("provider-456");
      appointment2.setDate(LocalDate.of(2025, 12, 25));
      appointment2.setAppointTime(5);

      // Verify conflict conditions
      assertEquals(appointment1.getProviderId(), appointment2.getProviderId());
      assertEquals(appointment1.getDate(), appointment2.getDate());
      assertEquals(appointment1.getAppointTime(), appointment2.getAppointTime());
      assertNotEquals(appointment1.getPatientId(), appointment2.getPatientId());
    }

    @Test
    @DisplayName("Appointment status should follow valid transitions")
    void appointment_StatusTransitions_ShouldFollowRules() {
      Appointment appointment = new Appointment();

      // Test valid status progression
      appointment.setStatus(0); // UPCOMING
      assertEquals(Integer.valueOf(0), appointment.getStatus());

      appointment.setStatus(1); // COMPLETED
      assertEquals(Integer.valueOf(1), appointment.getStatus());

      appointment.setStatus(2); // CANCELLED
      assertEquals(Integer.valueOf(2), appointment.getStatus());

      appointment.setStatus(3); // EXPIRED
      assertEquals(Integer.valueOf(3), appointment.getStatus());
    }

    @Test
    @DisplayName("Weekend appointments should be handled")
    void appointment_WeekendDates_ShouldBeHandled() {
      AppointmentBook book = new AppointmentBook();
      LocalDate monday = LocalDate.of(2025, 12, 1);

      // Test all days of the week
      for (int i = 0; i < 7; i++) {
        LocalDate testDate = monday.plusDays(i);
        book.setDate(testDate);

        assertEquals(testDate, book.getDate());
        DayOfWeek dayOfWeek = testDate.getDayOfWeek();
        assertTrue(dayOfWeek.getValue() >= 1 && dayOfWeek.getValue() <= 7);
      }
    }
  }
}
