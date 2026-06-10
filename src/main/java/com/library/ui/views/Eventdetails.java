package com.library.ui.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.PermitAll;

/**
 * Event detail page.
 * Route: /events/{eventId}  (e.g. /events/1)
 *
 * FIX: was incorrectly @Route("events") — same as Events.java, causing a
 * startup conflict.  Changed to @Route("events/:eventId([0-9]+)") so Vaadin
 * routes /events/1, /events/42, etc. here and leaves /events to Events.java.
 */
@Route("events/:eventId([0-9]+)")
@PageTitle("Event Details – CampusHub")
@PermitAll
public class Eventdetails extends VerticalLayout implements BeforeEnterObserver {

    private final H2 eventTitle    = new H2("Event Title");
    private final Paragraph eventMeta = new Paragraph();
    private final Paragraph eventDesc = new Paragraph();
    private final Span hostedBy    = new Span();
    private final Span categoryBadge = new Span();

    // Kept for future admin-role check
    private final Button editBtn   = new Button("Edit Event");
    private final Button deleteBtn = new Button("Delete Event");

    // Store resolved event id for the calendar link
    private long resolvedId = 0;

    public Eventdetails() {
        setPadding(true);
        setSpacing(true);

        // ── Back button ──────────────────────────────────────────────────────
        Button backBtn = new Button("← Back to All Events");
        backBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        backBtn.addClickListener(e -> UI.getCurrent().navigate("events"));

        // ── Category badge ───────────────────────────────────────────────────
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

        hostedBy.getStyle()
            .set("font-size", "0.85rem")
            .set("color", "var(--lumo-secondary-text-color)");

        // ── RSVP button ──────────────────────────────────────────────────────
        Button rsvpBtn = new Button("RSVP to this event");
        rsvpBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        rsvpBtn.addClickListener(e -> {
            rsvpBtn.setText("✓ You're going!");
            rsvpBtn.removeThemeVariants(ButtonVariant.LUMO_PRIMARY);
            rsvpBtn.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
            Notification n = Notification.show("You're RSVPed! Added to My Events.");
            n.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            n.setPosition(Notification.Position.BOTTOM_CENTER);
        });

        // ── Add to Calendar button ────────────────────────────────────────────
        Button calendarBtn = new Button("Add to Calendar");
        calendarBtn.addClickListener(e -> {

            // Date/time are encoded as YYYYMMDDTHHmmSSZ.
            String gcalUrl = buildGoogleCalendarUrl(
                eventTitle.getText(),
                "20260520T153000",   // start  – YYYYMMDDTHHMMSS (local)
                "20260520T163000",   // end    – 1 hour later
                eventMeta.getText(), //loc
                eventDesc.getText()
            );

            UI.getCurrent().getPage().open(gcalUrl, "_blank");
        });


        editBtn.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_TERTIARY);
        editBtn.addClickListener(e -> UI.getCurrent().navigate("events/new"));
        editBtn.setVisible(false);

        deleteBtn.addThemeVariants(ButtonVariant.LUMO_ERROR);
        deleteBtn.setVisible(false);

        HorizontalLayout actions = new HorizontalLayout(rsvpBtn, calendarBtn, editBtn, deleteBtn);
        actions.setSpacing(true);
        actions.getStyle().set("flex-wrap", "wrap");

        add(backBtn, categoryBadge, eventTitle, eventMeta, divider, eventDesc, hostedBy, actions);
    }



    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        String rawId = event.getRouteParameters().get("eventId").orElse("0");
        try {
            resolvedId = Long.parseLong(rawId);
        } catch (NumberFormatException ex) {
            resolvedId = 0;
        }
        loadEvent(resolvedId);
    }

    private void loadEvent(long id) {
        // Stub data matching the cards in Events.java
        switch ((int) id) {
            case 1 -> {
                categoryBadge.setText("Club meetup");
                eventTitle.setText("Robotics Club Weekly");
                eventMeta.setText("Tuesday May 20, 2025 · 3:30 PM · Room 204");
                eventDesc.setText(
                    "Come join the weekly Robotics Club meeting. We'll be working on our " +
                        "competition robots and probably playing speed with mr caulderwood. ");
                hostedBy.setText("Hosted by: Mr. Caulderwood");
            }
            case 2 -> {
                categoryBadge.setText("Sports");
                eventTitle.setText("Saints vs VC");
                eventMeta.setText("Thursday May 22, 2025 · 4:00 PM · Main Field");
                eventDesc.setText(
                    "Cheer on the Saints in their home match against VC . " +
                        "Come out and show your school spirit — snacks available at the stands!");
                hostedBy.setText("Hosted by: Athletics Department");
            }
            case 3 -> {
                categoryBadge.setText("Workshop");
                eventTitle.setText("Resume Writing Workshop");
                eventMeta.setText("Saturday May 24, 2025 · 10:00 AM · Library B");
                eventDesc.setText(
                    "Learn how to craft a standout resume with guidance from our career " +
                        "advisor. Bring a draft if you have one — peer review time included.");
                hostedBy.setText("Hosted by: Career Services");
            }
            case 4 -> {
                categoryBadge.setText("Study group");
                eventTitle.setText("Calculus Study Hall");
                eventMeta.setText("Sunday May 25, 2025 · 2:00 PM · Room 101");
                eventDesc.setText(
                    "Open study session for MATH 120 / 121. TAs will be present to help " +
                        "with limits, derivatives, and integration. Bring your textbook and questions.");
                hostedBy.setText("Hosted by: Math Department");
            }
            default -> {
                categoryBadge.setText("Event");
                eventTitle.setText("Event #" + id);
                eventMeta.setText("Date & location TBD");
                eventDesc.setText("Details coming soon.");
                hostedBy.setText("");
            }
        }
    }


    /**
     * Builds a Google Calendar "create event" URL.
    CLAUDE
     */
    private static String buildGoogleCalendarUrl(
        String title, String start, String end, String location, String details
    ) {
        try {
            String base = "https://calendar.google.com/calendar/render?action=TEMPLATE";
            base += "&text="     + java.net.URLEncoder.encode(title,    java.nio.charset.StandardCharsets.UTF_8);
            base += "&dates="    + start + "/" + end;
            base += "&location=" + java.net.URLEncoder.encode(location, java.nio.charset.StandardCharsets.UTF_8);
            base += "&details="  + java.net.URLEncoder.encode(details,  java.nio.charset.StandardCharsets.UTF_8);
            return base;
        } catch (Exception ex) {
            return "https://calendar.google.com/";
        }
    }
}
