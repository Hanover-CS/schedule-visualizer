package edu.hanover.schedulevisualizer.core;

import edu.hanover.schedulevisualizer.core.entity.EntityFactory;
import edu.hanover.schedulevisualizer.core.entity.TimeSlot;
import edu.hanover.schedulevisualizer.core.entity.Weekday;

import java.util.List;

class TimeSlotHandler extends TimeSlotCombiner<TimeSlot> {
    EntityFactory ef;
    public TimeSlotHandler(EntityFactory ef) {
        this.ef = ef;
    }

    @Override
    protected TimeSlot combineWithFirstAssignedSecondUnassigned(List<Weekday> wkd1, Integer sn1) {
        return ef.makeUnassignedTimeslot();
    }

    @Override
    protected TimeSlot combineWithBothAssigned(List<Weekday> wkd1, Integer sn1, List<Weekday> wkd2, Integer sn2) {
        if (wkd2.get(0).isTR() && wkd1.get(0).isTR()) {
            return ef.makeHCTimeSlot(wkd1, sn2);
        }
        if (!wkd2.get(0).isTR() && !wkd1.get(0).isTR()) {
            return ef.makeHCTimeSlot(wkd1, sn2);
        }
        return ef.makeHCTimeSlot(wkd2, sn2);
    }

    @Override
    protected TimeSlot combineWithFirstUnassignedSecondAssigned(List<Weekday> weekdays2, Integer slotnum2) {
        if (weekdays2.get(0).isTR()) {
            weekdays2 = Weekday.TR();
        }
        if(!weekdays2.get(0).isTR()) {
            weekdays2 = Weekday.MWF();
        }
        return ef.makeHCTimeSlot(weekdays2, slotnum2);
    }

    @Override
    protected TimeSlot combinedWithBothUnassigned() {
        return ef.makeUnassignedTimeslot();
    }
}