package patientintake;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ClinicCalendar should")
class ClinicCalendarTest {

    private ClinicCalendar calendar;

    @BeforeEach
    public void init() {
        calendar = new ClinicCalendar(LocalDate.now());
    }


    @Test
    @DisplayName("record a new appointment correctly")
    public void allowEntryofAppointment() {


        calendar.addAppointment("Jim", "Weaver", "avery", "09/01/2020 02:00 pm");
        List<PatientAppointment> appointmentList = calendar.getAppointments();

        assertNotNull(appointmentList);
        assertEquals(1, appointmentList.size());

        PatientAppointment enteredAppt = appointmentList.get(0);

        assertAll(
                () -> assertEquals("Jim", enteredAppt.getPatientFirstName()),
                () -> assertEquals("Weaver", enteredAppt.getPatientLastName()),
                () -> assertSame(Doctor.avery, enteredAppt.getDoctor()),
                () -> assertEquals("9/1/2020 02:00 PM",
                        enteredAppt.getAppointmentDateTime().format(DateTimeFormatter.ofPattern("M/d/yyyy hh:mm a", Locale.US)))
        );

        assertEquals("Jim", enteredAppt.getPatientFirstName());
        assertEquals("Weaver", enteredAppt.getPatientLastName());
        assertEquals(Doctor.avery, enteredAppt.getDoctor());
        assertEquals("9/1/2020 02:00 PM",
                enteredAppt.getAppointmentDateTime().format(DateTimeFormatter.ofPattern("M/d/yyyy hh:mm a", Locale.US)));
    }

    @Nested
    @DisplayName("indicate if there are appointments correctly")
    class HasAppointments {

        @Test
        @DisplayName("when there are appointments")
        public void returnTrueForHasAppointmentsIfThereAreAppointments() {

            calendar.addAppointment("Jim", "Weaver", "avery", "09/01/2020 02:00 pm");
            assertTrue(calendar.hasAppointment(LocalDate.of(2020, 9, 1)));
        }

        @Test
        @DisplayName("when there are no appointments")
        void returnFalseForHasAppointmentsIfThereAreNoAppointments() {
            assertFalse(calendar.hasAppointment(LocalDate.of(2018, 9, 1)));
        }
    }

    @Nested
    @DisplayName("return appointments for a given day correctly")
    class AppointmentsForDay {

        @Test
        @DisplayName("for today")
        public void returnCurrentDaysAppointment() {

            calendar.addAppointment("Jim", "Weaver", "avery",
                    "04/15/2020 2:00 pm");
            calendar.addAppointment("Jim", "Weaver", "avery",
                    "08/26/2018 3:00 pm");
            calendar.addAppointment("Jim", "Weaver", "avery",
                    "04/15/2020 3:00 pm");
            assertEquals(2, calendar.getTodayAppointments().size());

        }

        @Test
        @DisplayName("for tomorrow")
        void returnTommorowsAppointments() {
            calendar.addAppointment("Jim", "Weaver", "avery",
                    "04/16/2020 2:00 pm");
            calendar.addAppointment("Jim", "Weaver", "avery",
                    "04/16/2020 3:00 pm");
            calendar.addAppointment("Jim", "Weaver", "avery",
                    "08/26/2018 3:00 pm");
            assertEquals(2, calendar.getTomorrowAppointments().size());
        }

    }

    @Nested
    @DisplayName("return upcoming appointments")
    class UpcomingAppointments {

        @Test
        @DisplayName("as empty list when there are none")
        void whenThereAreNone() {
            List<PatientAppointment> appointments = calendar.getUpcomingAppointments();
            assertEquals(0, appointments.size());
        }

        @Test
        @DisplayName("correctly when there are some in the past as well")
        void whenThereAreSomePastAndFuture() {
            calendar.addAppointment("Jim", "Weaver", "avery",
                    "07/27/2017 2:00 pm");
            calendar.addAppointment("Jim", "Weaver", "avery",
                    "07/27/2018 2:00 pm");
            calendar.addAppointment("Jim", "Weaver", "avery",
                    "08/27/2020 2:00 pm");
            assertEquals(1, calendar.getUpcomingAppointments().size());
        }

    }


}