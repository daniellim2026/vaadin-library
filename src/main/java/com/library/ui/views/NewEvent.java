package com.library.ui.views;

import com.library.security.Role;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@Route("events/new")
@RolesAllowed(Role.RoleConstants.ADMIN)
public class NewEvent extends VerticalLayout {

    public NewEvent() {
        setPadding(true);
        setSpacing(true);
        setMaxWidth("500px");

        Button backBtn = new Button("← Back to Events");
        backBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        backBtn.addClickListener(e -> UI.getCurrent().navigate("events"));

        H2 heading = new H2("Create New Event");

        Span adminNote = new Span("Admin / Club leader only");
        adminNote.getStyle()
            .set("display", "inline-block")
            .set("font-size", "0.8rem")
            .set("background", "var(--lumo-warning-color-10pct)")
            .set("color", "var(--lumo-warning-text-color)")
            .set("padding", "4px 10px")
            .set("border-radius", "var(--lumo-border-radius-m)");

        // Form fields
        TextField nameField = new TextField("Event name");
        nameField.setPlaceholder("e.g. Robotics Club Weekly");
        nameField.setWidthFull();

        DatePicker datePicker = new DatePicker("Date");
        datePicker.setWidthFull();

        TimePicker timePicker = new TimePicker("Time");
        timePicker.setWidthFull();

        HorizontalLayout dateTimeRow = new HorizontalLayout(datePicker, timePicker);
        dateTimeRow.setWidthFull();
        dateTimeRow.setSpacing(true);

        TextField locationField = new TextField("Location");
        locationField.setPlaceholder("e.g. Room 204");
        locationField.setWidthFull();

        ComboBox<String> categoryField = new ComboBox<>("Category");
        categoryField.setItems("Club meetup", "Study group", "Sports", "Workshop");
        categoryField.setWidthFull();

        TextArea descField = new TextArea("Description");
        descField.setPlaceholder("What's this event about?");
        descField.setMinHeight("80px");
        descField.setWidthFull();

        Button submitBtn = new Button("Create Event");
        submitBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        submitBtn.addClickListener(e -> {

            UI.getCurrent().navigate("events");
        });

        Button cancelBtn = new Button("Cancel");
        cancelBtn.addClickListener(e -> UI.getCurrent().navigate("events"));

        HorizontalLayout btns = new HorizontalLayout(submitBtn, cancelBtn);
        btns.setSpacing(true);

        add(backBtn, heading, adminNote, nameField, dateTimeRow, locationField, categoryField, descField, btns);
    }
}
