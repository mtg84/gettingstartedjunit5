package patientintake;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;


class ClinicCalendarTest {

    private ClinicCalendar calendar;

    @BeforeEach
    public void init() {
        calendar = new ClinicCalendar(LocalDate.now());
    }


    @Test
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

    @Test
    public void returnTrueForHasAppointmentsIfThereAreAppointments(){

        calendar.addAppointment("Jim","Weaver","avery","09/01/2020 02:00 pm");
        assertTrue(calendar.hasAppointment(LocalDate.of(2020, 9,1)));
    }

    @Test
    public void returnFalseForHasAppointmentsIfThereAreNotAppointments(){

        assertFalse(calendar.hasAppointment(LocalDate.of(2020, 9,1)));
    }

    @Test
    public void returnCurrentDaysAppointment(){

        calendar.addAppointment("Jim", "Weaver", "avery",
                "04/15/2020 2:00 pm");
        calendar.addAppointment("Jim", "Weaver", "avery",
                "08/26/2018 3:00 pm");
        calendar.addAppointment("Jim", "Weaver", "avery",
                "04/15/2020 3:00 pm");
        assertEquals(2, calendar.getTodayAppointments().size());


    }
}