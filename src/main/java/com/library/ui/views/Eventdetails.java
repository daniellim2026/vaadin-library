package com.library.ui.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@Route("events")
@PermitAll
public class Eventdetails extends VerticalLayout implements HasUrlParameter<Long> {

    private final H2 eventTitle = new H2("Event Title");
    private final Paragraph eventMeta = new Paragraph();
    private final Paragraph eventDesc = new Paragraph();
    private final Span hostedBy = new Span();
    private final Span categoryBadge = new Span();

    private Button editBtn = new Button("Edit Event");
    private Button deleteBtn = new Button("Delete Event");

    public Eventdetails() {
        setPadding(true);
        setSpacing(true);

        // Back button
        Button backBtn = new Button("← Back to All Events");
        backBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        backBtn.addClickListener(e -> UI.getCurrent().navigate("events"));

        // Category badge
        categoryBadge.getStyle()
            .set("display", "inline-block")
            .set("font-size", "0.75rem")
            .set("padding", "2px 8px")
            .set("border-radius", "12px")
            .set("background", "var(--lumo-primary-color-10pct)")
            .set("color", "var(--lumo-primary-color)");

        eventTitle.getStyle().set("margin", "0.5rem 0 0.25rem");

        eventMeta.getStyle()
            .set("color", "var(--lumo-secondary-text-color)")
            .set("font-size", "0.9rem");

        Hr divider = new Hr();

        eventDesc.getStyle().set("line-height", "1.6");

        hostedBy.getStyle().set("font-size", "0.85rem").set("color", "var(--lumo-secondary-text-color)");

        // Action buttons
        Button rsvpBtn = new Button("RSVP to this event");
        rsvpBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        rsvpBtn.addClickListener(e -> {
            rsvpBtn.setText("✓ You're going!");
            rsvpBtn.removeThemeVariants(ButtonVariant.LUMO_PRIMARY);
            rsvpBtn.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        });

        Button calendarBtn = new Button("Add to calendar");

        editBtn.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_TERTIARY);
        editBtn.addClickListener(e -> UI.getCurrent().navigate("events/new"));
        editBtn.setVisible(false); // shown only for admins

        deleteBtn.addThemeVariants(ButtonVariant.LUMO_ERROR);
        deleteBtn.setVisible(false); // shown only for admins

        HorizontalLayout actions = new HorizontalLayout(rsvpBtn, calendarBtn, editBtn, deleteBtn);
        actions.setSpacing(true);
        actions.getStyle().set("flex-wrap", "wrap");

        add(backBtn, categoryBadge, eventTitle, eventMeta, divider, eventDesc, hostedBy, actions);
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, Long eventId) {
        categoryBadge.setText("Club meetup");
        eventTitle.setText("Robotics Club Weekly");
        eventMeta.setText("Tuesday May 20, 2025 · 3:30 PM · Room 204");
        eventDesc.setText("Come join the weekly Robotics Club meeting. We'll be working on our competition robot and discussing next steps for the regional qualifier.");
        hostedBy.setText("Hosted by: Robotics Club");


    }
}
