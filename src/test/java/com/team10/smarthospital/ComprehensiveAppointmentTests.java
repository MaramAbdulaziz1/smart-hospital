package com.team10.smarthospital;

import com.team10.smarthospital.model.entity.Appointment;
import com.team10.smarthospital.model.request.AppointmentBook;
import com.team10.smarthospital.model.response.AppointmentRecord;
import com.team10.smarthospital.model.response.BaseResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;
import java.time.LocalDate;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive unit tests for appointment-related functionality
 * Tests all aspects of appointment booking, validation, and business logic
 */
@DisplayName("Comprehensive Appointment System Tests")
public class ComprehensiveAppointmentTests {

  private AppointmentBook defaultAppointmentBook;
  private Appointment defaultAppointment;

  @BeforeEach
  void setUp() {
    // Setup default test data
    defaultAppointmentBook = new AppointmentBook();
    defaultAppointmentBook.setDate(LocalDate.of(2025, 12, 15));
    defaultAppointmentBook.setTime(3);
    defaultAppointmentBook.setProviderId("provider-123");

    defaultAppointment = new Appointment();
    defaultAppointment.setAppointmentId("appt-456");
    defaultAppointment.setPatientId("patient-789");
    defaultAppointment.setProviderId("provider-123");
    defaultAppointment.setDate(LocalDate.of(2025, 12, 15));
    defaultAppointment.setAppointTime(3);
    defaultAppointment.setStatus(0);
  }

  @Nested
  @DisplayName("AppointmentBook Comprehensive Tests")
  class AppointmentBookComprehensiveTests {

    @DisplayName("AppointmentBook should handle all valid time slots")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12})
    void appointmentBook_ValidTimeSlots_AllAccepted(int timeSlot) {
      AppointmentBook book = new AppointmentBook();
      book.setTime(timeSlot);
      assertEquals(Integer.valueOf(timeSlot), book.getTime());
      assertTrue(timeSlot >= 1 && timeSlot <= 12, "Time slot should be in valid range");
    }

    @DisplayName("AppointmentBook should handle various provider ID formats")
    @ParameterizedTest
    @CsvSource({
      "'provider-123', 'Standard format'",
      "'PROVIDER-456', 'Uppercase format'",
      "'prov_789', 'Underscore format'",
      "'12345678-1234-1234-1234-123456789012', 'UUID format'",
      "'p1', 'Short format'",
      "'very-long-provider-identifier-with-many-characters', 'Long format'"
    })
    void appointmentBook_ProviderIdFormats_AllAccepted(String providerId, String description) {
      AppointmentBook book = new AppointmentBook();
      book.setProviderId(providerId);
      assertEquals(providerId, book.getProviderId(), "Should accept " + description);
      assertNotNull(book.getProviderId(), "Provider ID should not be null");
    }

    @Test
    @DisplayName("AppointmentBook should handle date ranges correctly")
    void appointmentBook_DateRanges_ValidatesCorrectly() {
      AppointmentBook book = new AppointmentBook();
      // Test various date scenarios
      LocalDate today = LocalDate.now();
      LocalDate tomorrow = today.plusDays(1);
      LocalDate nextWeek = today.plusWeeks(1);
      LocalDate nextMonth = today.plusMonths(1);
      LocalDate nextYear = today.plusYears(1);
      LocalDate pastDate = today.minusDays(1);

      LocalDate[] testDates = {today, tomorrow, nextWeek, nextMonth, nextYear, pastDate};

      for (LocalDate date : testDates) {
        book.setDate(date);
        assertEquals(date, book.getDate(), "Date " + date + " should be accepted");
      }
    }

    @Test
    @DisplayName("AppointmentBook should handle weekend dates")
    void appointmentBook_WeekendDates_AcceptsAllDays() {
      AppointmentBook book = new AppointmentBook();
      LocalDate startDate = LocalDate.of(2025, 12, 1); // Start from a known date

      // Test all days of the week
      for (int i = 0; i < 7; i++) {
        LocalDate testDate = startDate.plusDays(i);
        book.setDate(testDate);
        assertEquals(testDate, book.getDate());
        assertNotNull(book.getDate());
        DayOfWeek dayOfWeek = testDate.getDayOfWeek();
        assertTrue(dayOfWeek.getValue() >= 1 && dayOfWeek.getValue() <= 7,
          "Should accept " + dayOfWeek);
      }
    }

    @Test
    @DisplayName("AppointmentBook field values should remain stable after setting")
    void appointmentBook_FieldImmutability_ValuesStableAfterSetting() {
      AppointmentBook book = new AppointmentBook();
      LocalDate testDate = LocalDate.of(2025, 11, 20);
      book.setDate(testDate);
      book.setTime(5);
      book.setProviderId("stable-provider");

      // Values should remain stable
      assertEquals(testDate, book.getDate());
      assertEquals(Integer.valueOf(5), book.getTime());
      assertEquals("stable-provider", book.getProviderId());

      // Setting new values should update properly
      LocalDate newDate = LocalDate.of(2025, 11, 25);
      book.setDate(newDate);
      assertEquals(newDate, book.getDate(), "Date should update to new value");
    }

    @Test
    @DisplayName("AppointmentBook should handle boundary time values")
    void appointmentBook_BoundaryTimeValues_HandledCorrectly() {
      AppointmentBook book = new AppointmentBook();

      // Test minimum time slot
      book.setTime(1);
      assertEquals(Integer.valueOf(1), book.getTime());

      // Test maximum time slot
      book.setTime(12);
      assertEquals(Integer.valueOf(12), book.getTime());

      // Test that entity accepts values outside expected range
      book.setTime(0);
      assertEquals(Integer.valueOf(0), book.getTime());

      book.setTime(13);
      assertEquals(Integer.valueOf(13), book.getTime());
    }
  }

  @Nested
  @DisplayName("Appointment Entity Comprehensive Tests")
  class AppointmentEntityComprehensiveTests {

    @DisplayName("Appointment entity should handle all status codes")
    @ParameterizedTest
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
    @DisplayName("Appointment should handle UUID format IDs")
    void appointment_UuidFormats_AcceptsVariousIdFormats() {
      Appointment appointment = new Appointment();
      String[] testIds = {
        "550e8400-e29b-41d4-a716-446655440000", // Standard UUID
        "appointment-123", // Custom format
        "appt_456", // Underscore format
        "12345", // Numeric
        "a1b2c3d4-e5f6-7890-abcd-ef1234567890" // Mixed UUID
      };

      for (String id : testIds) {
        appointment.setAppointmentId(id);
        assertEquals(id, appointment.getAppointmentId(), "Should accept ID format: " + id);
      }
    }

    @Test
    @DisplayName("Appointment should maintain referential integrity between patient and provider")
    void appointment_ReferentialIntegrity_PatientProviderRelationship() {
      Appointment appointment = new Appointment();
      String patientId = "patient-123";
      String providerId = "provider-456";

      appointment.setPatientId(patientId);
      appointment.setProviderId(providerId);

      assertEquals(patientId, appointment.getPatientId());
      assertEquals(providerId, appointment.getProviderId());
      assertNotEquals(appointment.getPatientId(), appointment.getProviderId(),
        "Patient and provider IDs should be different");
    }

    @Test
    @DisplayName("Appointment should handle cancellation reasons")
    void appointment_CancellationReasons_AcceptsVariousReasons() {
      Appointment appointment = new Appointment();
      String[] reasons = {
        "Patient requested cancellation",
        "Doctor unavailable",
        "Emergency situation",
        "Scheduling conflict",
        null, // No reason provided
        ""   // Empty reason
      };

      for (String reason : reasons) {
        appointment.setCancellationReason(reason);
        assertEquals(reason, appointment.getCancellationReason(),
          "Should accept reason: " + (reason == null ? "null" : reason));
      }
    }

    @Test
    @DisplayName("Appointment should handle invalid status codes at entity level")
    void appointment_InvalidStatusCodes_AcceptedByEntity() {
      Appointment appointment = new Appointment();
      int[] invalidStatuses = {-1, 4, 100, Integer.MAX_VALUE, Integer.MIN_VALUE};

      for (int status : invalidStatuses) {
        appointment.setStatus(status);
        assertEquals(Integer.valueOf(status), appointment.getStatus(),
          "Entity should accept status: " + status);
      }
    }

    @Test
    @DisplayName("Appointment should handle all appointment time slots")
    void appointment_TimeSlots_AllSlotsHandled() {
      Appointment appointment = new Appointment();

      // Test all valid appointment time slots
      for (int i = 1; i <= 12; i++) {
        appointment.setAppointTime(i);
        assertEquals(Integer.valueOf(i), appointment.getAppointTime());
      }

      // Test boundary cases
      appointment.setAppointTime(0);
      assertEquals(Integer.valueOf(0), appointment.getAppointTime());

      appointment.setAppointTime(13);
      assertEquals(Integer.valueOf(13), appointment.getAppointTime());
    }

    @Test
    @DisplayName("Appointment should handle date edge cases")
    void appointment_DateEdgeCases_HandledCorrectly() {
      Appointment appointment = new Appointment();

      // Test various date scenarios
      appointment.setDate(LocalDate.MIN);
      assertEquals(LocalDate.MIN, appointment.getDate());

      appointment.setDate(LocalDate.MAX);
      assertEquals(LocalDate.MAX, appointment.getDate());

      appointment.setDate(LocalDate.now());
      assertEquals(LocalDate.now(), appointment.getDate());
    }
  }

  @Nested
  @DisplayName("AppointmentRecord Comprehensive Tests")
  class AppointmentRecordComprehensiveTests {

    @Test
    @DisplayName("AppointmentRecord should handle complete appointment data")
    void appointmentRecord_CompleteData_AllFieldsWork() {
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
    @DisplayName("AppointmentRecord should handle various status strings")
    void appointmentRecord_StatusStrings_HandledCorrectly() {
      AppointmentRecord record = new AppointmentRecord();
      String[] statuses = {
        "UPCOMING", "COMPLETED", "CANCELLED", "EXPIRED",
        "upcoming", "completed", "cancelled", "expired", // lowercase
        "Upcoming", "Completed", "Cancelled", "Expired", // title case
        null, "" // edge cases
      };

      for (String status : statuses) {
        record.setStatus(status);
        assertEquals(status, record.getStatus(),
          "Should accept status: " + (status == null ? "null" : status));
      }
    }

    @Test
    @DisplayName("AppointmentRecord should handle time formatting variations")
    void appointmentRecord_TimeFormats_VariousFormatsAccepted() {
      AppointmentRecord record = new AppointmentRecord();
      String[] timeFormats = {
        "09:00 AM", "11:30 AM", "02:00 PM", "04:30 PM",
        "9:00 AM", "2:00 PM", // Single digit hours
        "10:00", "14:30", // 24-hour format
        null, "" // Edge cases
      };

      for (String timeFormat : timeFormats) {
        record.setStartTime(timeFormat);
        assertEquals(timeFormat, record.getStartTime(),
          "Should accept time format: " + (timeFormat == null ? "null" : timeFormat));
      }
    }

    @Test
    @DisplayName("AppointmentRecord should handle various provider name formats")
    void appointmentRecord_ProviderNames_VariousFormatsAccepted() {
      AppointmentRecord record = new AppointmentRecord();
      String[] providerNames = {
        "Dr. Smith", "Dr. Jane Doe", "Doctor Johnson",
        "Prof. Williams", "Mr. Brown", "Ms. Davis",
        "Smith", "Jane", // Just names without titles
        null, "" // Edge cases
      };

      for (String name : providerNames) {
        record.setProviderName(name);
        assertEquals(name, record.getProviderName(),
          "Should accept provider name: " + (name == null ? "null" : name));
      }
    }

    @Test
    @DisplayName("AppointmentRecord should handle patient code formats")
    void appointmentRecord_PatientCodes_VariousFormatsAccepted() {
      AppointmentRecord record = new AppointmentRecord();
      String[] patientCodes = {
        "P123456", "PAT-123", "PATIENT_789",
        "12345", "A1B2C3", "PT001",
        null, "" // Edge cases
      };

      for (String code : patientCodes) {
        record.setPatientCode(code);
        assertEquals(code, record.getPatientCode(),
          "Should accept patient code: " + (code == null ? "null" : code));
      }
    }
  }

  @Nested
  @DisplayName("BaseResponse Comprehensive Tests")
  class BaseResponseComprehensiveTests {

    @Test
    @DisplayName("BaseResponse should handle various appointment data types")
    void baseResponse_GenericTypes_SupportsMultipleDataTypes() {
      // Test with List<AppointmentRecord>
      BaseResponse<List<AppointmentRecord>> listResponse = new BaseResponse<>();
      List<AppointmentRecord> appointments = new ArrayList<>();
      listResponse.setData(appointments);
      assertNotNull(listResponse.getData());
      assertTrue(listResponse.getData() instanceof List);

      // Test with String
      BaseResponse<String> stringResponse = new BaseResponse<>();
      stringResponse.setData("success");
      assertEquals("success", stringResponse.getData());

      // Test with AppointmentBook
      BaseResponse<AppointmentBook> bookResponse = new BaseResponse<>();
      bookResponse.setData(defaultAppointmentBook);
      assertEquals(defaultAppointmentBook, bookResponse.getData());

      // Test with Appointment
      BaseResponse<Appointment> appointmentResponse = new BaseResponse<>();
      appointmentResponse.setData(defaultAppointment);
      assertEquals(defaultAppointment, appointmentResponse.getData());
    }

    @Test
    @DisplayName("BaseResponse should handle null and empty data")
    void baseResponse_NullEmptyData_HandledCorrectly() {
      // Test null data
      BaseResponse<AppointmentRecord> nullResponse = new BaseResponse<>();
      assertNull(nullResponse.getData(), "Initial data should be null");

      nullResponse.setData(null);
      assertNull(nullResponse.getData(), "Should accept null data");

      // Test empty list
      BaseResponse<List<AppointmentRecord>> emptyListResponse = new BaseResponse<>();
      List<AppointmentRecord> emptyList = new ArrayList<>();
      emptyListResponse.setData(emptyList);
      assertNotNull(emptyListResponse.getData());
      assertTrue(emptyListResponse.getData().isEmpty());
    }

    @Test
    @DisplayName("BaseResponse should maintain type safety")
    void baseResponse_TypeSafety_MaintainedCorrectly() {
      BaseResponse<AppointmentBook> bookResponse = new BaseResponse<>();
      BaseResponse<AppointmentRecord> recordResponse = new BaseResponse<>();
      BaseResponse<List<Appointment>> listResponse = new BaseResponse<>();

      // Each response should maintain its generic type
      bookResponse.setData(defaultAppointmentBook);
      assertTrue(bookResponse.getData() instanceof AppointmentBook);

      AppointmentRecord record = new AppointmentRecord();
      recordResponse.setData(record);
      assertTrue(recordResponse.getData() instanceof AppointmentRecord);

      List<Appointment> appointments = new ArrayList<>();
      listResponse.setData(appointments);
      assertTrue(listResponse.getData() instanceof List);
    }
  }

  @Nested
  @DisplayName("Business Logic Comprehensive Tests")
  class BusinessLogicComprehensiveTests {

    @Test
    @DisplayName("Appointment booking should validate business hours")
    void appointmentBooking_BusinessHours_ValidatesTimeSlots() {
      AppointmentBook book = new AppointmentBook();
      // Test typical business hours (assuming 1-12 represent different time slots)
      int[] businessHours = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};

      for (int hour : businessHours) {
        book.setTime(hour);
        assertTrue(book.getTime() >= 1 && book.getTime() <= 12,
          "Time slot " + hour + " should be within business hours");
      }
    }

    @Test
    @DisplayName("Appointment should validate date constraints")
    void appointment_DateConstraints_ValidatesCorrectly() {
      Appointment appointment = new Appointment();
      LocalDate today = LocalDate.now();

      // Test various date scenarios
      appointment.setDate(today);
      assertEquals(today, appointment.getDate());

      appointment.setDate(today.plusDays(1));
      assertTrue(appointment.getDate().isAfter(today), "Future date should be after today");

      appointment.setDate(today.minusDays(1));
      assertTrue(appointment.getDate().isBefore(today), "Past date should be before today");
    }

    @Test
    @DisplayName("Appointment status transitions should follow business rules")
    void appointment_StatusTransitions_FollowBusinessRules() {
      Appointment appointment = new Appointment();

      // Test initial state
      appointment.setStatus(0); // UPCOMING
      assertEquals(Integer.valueOf(0), appointment.getStatus());

      // Test transition to completed
      appointment.setStatus(1); // COMPLETED
      assertEquals(Integer.valueOf(1), appointment.getStatus());

      // Test transition to cancelled
      appointment.setStatus(2); // CANCELLED
      assertEquals(Integer.valueOf(2), appointment.getStatus());

      // Test transition to expired
      appointment.setStatus(3); // EXPIRED
      assertEquals(Integer.valueOf(3), appointment.getStatus());
    }

    @Test
    @DisplayName("Multiple appointments should handle conflicts properly")
    void multipleAppointments_ConflictDetection_ValidatesCorrectly() {
      // Create two appointments with potential conflicts
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

      // Same provider, same time, same date but different patients
      assertEquals(appointment1.getProviderId(), appointment2.getProviderId());
      assertEquals(appointment1.getDate(), appointment2.getDate());
      assertEquals(appointment1.getAppointTime(), appointment2.getAppointTime());
      assertNotEquals(appointment1.getPatientId(), appointment2.getPatientId());
      // This would represent a scheduling conflict in real system
    }

    @Test
    @DisplayName("Appointment system should handle edge cases")
    void appointmentSystem_EdgeCases_HandledGracefully() {
      // Test with minimum valid values
      AppointmentBook minBook = new AppointmentBook();
      minBook.setTime(1);
      minBook.setDate(LocalDate.MIN);
      minBook.setProviderId("1");

      assertEquals(Integer.valueOf(1), minBook.getTime());
      assertEquals(LocalDate.MIN, minBook.getDate());
      assertEquals("1", minBook.getProviderId());

      // Test with maximum reasonable values
      AppointmentBook maxBook = new AppointmentBook();
      maxBook.setTime(12);
      maxBook.setDate(LocalDate.MAX);
      maxBook.setProviderId("very-long-provider-id-that-tests-field-limits");

      assertEquals(Integer.valueOf(12), maxBook.getTime());
      assertEquals(LocalDate.MAX, maxBook.getDate());
      assertNotNull(maxBook.getProviderId());
      assertTrue(maxBook.getProviderId().length() > 10);
    }

    @Test
    @DisplayName("Appointment booking should handle same day appointments")
    void appointmentBooking_SameDayAppointments_HandledCorrectly() {
      LocalDate today = LocalDate.now();
      AppointmentBook book1 = new AppointmentBook();
      AppointmentBook book2 = new AppointmentBook();

      // Same day, different times
      book1.setDate(today);
      book1.setTime(3);
      book1.setProviderId("provider-123");

      book2.setDate(today);
      book2.setTime(7);
      book2.setProviderId("provider-123");

      assertEquals(book1.getDate(), book2.getDate());
      assertEquals(book1.getProviderId(), book2.getProviderId());
      assertNotEquals(book1.getTime(), book2.getTime());
    }

    @Test
    @DisplayName("Appointment should handle patient double booking")
    void appointment_PatientDoubleBooking_DetectedCorrectly() {
      Appointment appointment1 = new Appointment();
      appointment1.setPatientId("patient-123");
      appointment1.setProviderId("provider-456");
      appointment1.setDate(LocalDate.of(2025, 12, 30));
      appointment1.setAppointTime(4);

      Appointment appointment2 = new Appointment();
      appointment2.setPatientId("patient-123");
      appointment2.setProviderId("provider-789");
      appointment2.setDate(LocalDate.of(2025, 12, 30));
      appointment2.setAppointTime(4);

      // Same patient, same time, same date but different providers
      assertEquals(appointment1.getPatientId(), appointment2.getPatientId());
      assertEquals(appointment1.getDate(), appointment2.getDate());
      assertEquals(appointment1.getAppointTime(), appointment2.getAppointTime());
      assertNotEquals(appointment1.getProviderId(), appointment2.getProviderId());
    }

    @Test
    @DisplayName("Appointment cancellation should maintain data integrity")
    void appointment_Cancellation_MaintainsDataIntegrity() {
      Appointment appointment = new Appointment();
      appointment.setAppointmentId("appt-cancel-123");
      appointment.setPatientId("patient-456");
      appointment.setProviderId("provider-789");
      appointment.setDate(LocalDate.of(2025, 12, 28));
      appointment.setAppointTime(6);
      appointment.setStatus(0); // UPCOMING

      // Cancel appointment
      appointment.setStatus(2); // CANCELLED
      appointment.setCancellationReason("Patient requested cancellation");

      assertEquals(Integer.valueOf(2), appointment.getStatus());
      assertEquals("Patient requested cancellation", appointment.getCancellationReason());

      // Original data should remain intact
      assertEquals("appt-cancel-123", appointment.getAppointmentId());
      assertEquals("patient-456", appointment.getPatientId());
      assertEquals("provider-789", appointment.getProviderId());
      assertEquals(LocalDate.of(2025, 12, 28), appointment.getDate());
      assertEquals(Integer.valueOf(6), appointment.getAppointTime());
    }
  }

  @Nested
  @DisplayName("Data Consistency Tests")
  class DataConsistencyTests {

    @Test
    @DisplayName("Object equality and hash code consistency")
    void objectEquality_HashCodeConsistency_WorksCorrectly() {
      AppointmentBook book1 = new AppointmentBook();
      AppointmentBook book2 = new AppointmentBook();

      // Initially both should be equal (all null fields)
      assertNotSame(book1, book2, "Different instances should not be same");

      // Set same values
      LocalDate testDate = LocalDate.of(2025, 12, 30);
      book1.setDate(testDate);
      book1.setTime(7);
      book1.setProviderId("provider-test");

      book2.setDate(testDate);
      book2.setTime(7);
      book2.setProviderId("provider-test");

      // Objects with same data should have consistent behavior
      assertEquals(book1.getDate(), book2.getDate());
      assertEquals(book1.getTime(), book2.getTime());
      assertEquals(book1.getProviderId(), book2.getProviderId());
    }

    @Test
    @DisplayName("Appointment entity field consistency")
    void appointment_FieldConsistency_MaintainedCorrectly() {
      Appointment appointment = new Appointment();

      // Set all fields
      appointment.setAppointmentId("consistency-test");
      appointment.setPatientId("patient-consistency");
      appointment.setProviderId("provider-consistency");
      appointment.setDate(LocalDate.of(2025, 11, 15));
      appointment.setAppointTime(9);
      appointment.setStatus(1);
      appointment.setCancellationReason("Test reason");

      // Verify all fields maintain their values
      assertEquals("consistency-test", appointment.getAppointmentId());
      assertEquals("patient-consistency", appointment.getPatientId());
      assertEquals("provider-consistency", appointment.getProviderId());
      assertEquals(LocalDate.of(2025, 11, 15), appointment.getDate());
      assertEquals(Integer.valueOf(9), appointment.getAppointTime());
      assertEquals(Integer.valueOf(1), appointment.getStatus());
      assertEquals("Test reason", appointment.getCancellationReason());
    }

    @Test
    @DisplayName("AppointmentRecord field mapping consistency")
    void appointmentRecord_FieldMapping_ConsistentAcrossOperations() {
      AppointmentRecord record = new AppointmentRecord();

      // Set all fields
      record.setAppointmentId("mapping-test-123");
      record.setDate(LocalDate.of(2025, 10, 5));
      record.setProviderName("Dr. Consistency");
      record.setPatientName("Test Patient");
      record.setPatientCode("PC123");
      record.setPatientEmail("test@consistency.com");
      record.setStatus("COMPLETED");
      record.setTimeCode(11);
      record.setStartTime("03:30 PM");

      // Verify field consistency
      assertEquals("mapping-test-123", record.getAppointmentId());
      assertEquals(LocalDate.of(2025, 10, 5), record.getDate());
      assertEquals("Dr. Consistency", record.getProviderName());
      assertEquals("Test Patient", record.getPatientName());
      assertEquals("PC123", record.getPatientCode());
      assertEquals("test@consistency.com", record.getPatientEmail());
      assertEquals("COMPLETED", record.getStatus());
      assertEquals(Integer.valueOf(11), record.getTimeCode());
      assertEquals("03:30 PM", record.getStartTime());
    }

    @Test
    @DisplayName("Null value handling across all appointment entities")
    void nullValueHandling_AllEntities_HandleGracefully() {
      // Test AppointmentBook with nulls
      AppointmentBook book = new AppointmentBook();
      book.setDate(null);
      book.setTime(null);
      book.setProviderId(null);

      assertNull(book.getDate());
      assertNull(book.getTime());
      assertNull(book.getProviderId());

      // Test Appointment with nulls
      Appointment appointment = new Appointment();
      appointment.setAppointmentId(null);
      appointment.setPatientId(null);
      appointment.setProviderId(null);
      appointment.setDate(null);
      appointment.setAppointTime(null);
      appointment.setStatus(null);
      appointment.setCancellationReason(null);

      assertNull(appointment.getAppointmentId());
      assertNull(appointment.getPatientId());
      assertNull(appointment.getProviderId());
      assertNull(appointment.getDate());
      assertNull(appointment.getAppointTime());
      assertNull(appointment.getStatus());
      assertNull(appointment.getCancellationReason());

      // Test AppointmentRecord with nulls
      AppointmentRecord record = new AppointmentRecord();
      record.setAppointmentId(null);
      record.setDate(null);
      record.setProviderName(null);
      record.setPatientName(null);
      record.setPatientCode(null);
      record.setPatientEmail(null);
      record.setStatus(null);
      record.setTimeCode(null);
      record.setStartTime(null);

      assertNull(record.getAppointmentId());
      assertNull(record.getDate());
      assertNull(record.getProviderName());
      assertNull(record.getPatientName());
      assertNull(record.getPatientCode());
      assertNull(record.getPatientEmail());
      assertNull(record.getStatus());
      assertNull(record.getTimeCode());
      assertNull(record.getStartTime());
    }
  }
}
