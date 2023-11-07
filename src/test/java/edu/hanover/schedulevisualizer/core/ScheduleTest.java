package edu.hanover.schedulevisualizer.core;

import edu.hanover.schedulevisualizer.observable.MyObserver;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ScheduleTest {

    private Section cs220 = new Section("CS", "220", "Fundamentals of Computer Science", new HCTimeSlot(Weekday.MWF(), 1));
    private Section mat121 = new Section("MAT", "121", "Calculus I", new HCTimeSlot(List.of(Weekday.Tuesday), 7));
    private Section fy101 = new Section("FY", "101", "First Year", new UnassignedTimeSlot());

    @Test
    public void canMakeNewEmptySchedule(){
        Schedule schedule = new Schedule();
        assertTrue(schedule.getSections().isEmpty());
    }

    @Test
    public void canMakeScheduleWithListOfSections(){
        List<Section> sections = new ArrayList<>(List.of(cs220, mat121, fy101));
        Schedule schedule = new Schedule(sections);
        assertEquals(Set.of(cs220, mat121, fy101), schedule.getSections());
    }

    @Test
    public void scheduleWillNotTakeDuplicateSections(){
        List<Section> sectionsRepeated = new ArrayList<>(List.of(cs220, mat121, mat121, fy101));
        Schedule schedule = new Schedule(sectionsRepeated);
        assertEquals(Set.of(cs220, mat121, fy101), schedule.getSections());
    }

    @Test
    public void scheduleWillNotAddDuplicateSections(){
        List<Section> sections = new ArrayList<>(List.of(cs220, fy101));
        Schedule schedule = new Schedule(sections);
        assertEquals(Set.of(cs220, fy101), schedule.getSections());
        schedule.addSection(cs220);
        assertEquals(Set.of(cs220, fy101), schedule.getSections());
    }

    @Test
    public void canAddCourses(){
        Schedule schedule = new Schedule();
        assertFalse(schedule.hasSection(cs220));
        assertFalse(schedule.hasSection(mat121));
        schedule.addSection(cs220);
        assertTrue(schedule.hasSection(cs220));
        assertFalse(schedule.hasSection(mat121));
        schedule.addSection(mat121);
        assertTrue(schedule.hasSection(cs220));
        assertTrue(schedule.hasSection(mat121));
    }

    @Test
    public void canRemoveCourses() {
        List<Section> sections = new ArrayList<>(List.of(cs220, mat121));
        Schedule schedule = new Schedule(sections);
        assertTrue(schedule.hasSection(cs220));
        assertTrue(schedule.hasSection(mat121));
        schedule.removeSection(mat121);
        assertTrue(schedule.hasSection(cs220));
        assertFalse(schedule.hasSection(mat121));
        schedule.removeSection(cs220);
        assertFalse(schedule.hasSection(cs220));
        assertFalse(schedule.hasSection(mat121));
    }

    @Test
    public void removeCoursesNotInSections() {
        List<Section> sections = new ArrayList<>(List.of(cs220, mat121));
        Schedule schedule = new Schedule(sections);
        schedule.removeSection(fy101);
        assertEquals(Set.of(cs220, mat121), schedule.getSections());
        schedule.removeSection(cs220);
        schedule.removeSection(cs220);
        assertEquals(Set.of(mat121), schedule.getSections());
    }

    @Test
    public void contextCanCreateNewEmptySchedule(){
        TestableContext context = new TestableContext();
        Schedule originalSchedule = context.getSchedule();
        context.createNewEmptySchedule();
        Schedule newSchedule = context.getSchedule();
        assertFalse(originalSchedule == newSchedule);
    }

    @Test
    public void newCreatedScheduleIsEmpty(){
        TestableContext context = new TestableContext();
        context.createNewEmptySchedule();
        assertTrue(context.getSchedule().getSections().isEmpty());
    }

    @Test
    public void observersAreNotified(){
        MyObserver<List<Section>> observer = mock(MyObserver.class);
        TestableContext context = new TestableContext();
        context.addObserver(observer);
        context.createNewEmptySchedule();
        verify(observer, times(1)).update(any());
    }
}
