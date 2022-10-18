package edu.hanover.schedulevisualizer.ui.elements;

import edu.hanover.schedulevisualizer.core.Weekday;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;


public class DayColumn extends VBox {
    @FXML
    private Label label;
    private Weekday day;

    public DayColumn() {
        super();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "dayColumn-view.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    static DayColumn forWeekday(Weekday weekday) {
        DayColumn dayColumn = new DayColumn();
        dayColumn.setDay(weekday);
        dayColumn.setId("column" + weekday);
        return dayColumn;
    }

    public Weekday getDay() {
        return day;
    }

    public void setDay(Weekday day) {
        this.day = day;
        label.setText(day.name());
        addSlots(DayPattern.forDay(day));
    }

    private void addSlots(DayPattern dayPattern) {
        for (int slotNum = 1; slotNum <= dayPattern.numSlots; slotNum++) {
            getChildren().add(TimeSlot.forDayPattern(dayPattern, makeSlotID(slotNum)));
        }
    }

    private String makeSlotID(int slotNum) {
        return "slot" + slotNum + getId();
    }

}
