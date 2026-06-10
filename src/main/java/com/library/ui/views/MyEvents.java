package com.library.ui.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

import java.util.ArrayList;
import java.util.List;

/**
 * My Events page – shows every event the current user has RSVPed to.
 *
 * Stub data is used here; wire up an EventService / RSVPService to replace
 * the hardcoded list once the backend is ready.
 */
@Route("my-events")
@PageTitle("My Events – CampusHub")
@Menu(order = 3, title = "My Events", icon = "vaadin:star")
@PermitAll
public class MyEvents extends VerticalLayout {

    /** Simple value object for the stub events shown on this page. */
    record RsvpEvent(long id, String title, String category, String meta, String host) {}

    public MyEvents() {
        setPadding(true);
        setSpacing(true);

        // ── Header ───────────────────────────────────────────────────────────
        H2 heading = new H2("My Events");

        Paragraph subtitle = new Paragraph("Events you've RSVPed to.");
        subtitle.getStyle().set("color", "var(--lumo-secondary-text-color)").set("margin-top", "0");

        Hr divider = new Hr();
        divider.getStyle().set("margin", "0.5rem 0 1rem");


        List<RsvpEvent> rsvpEvents = new ArrayList<>();
        rsvpEvents.add(new RsvpEvent(1, "Robotics Club Weekly",  "Club meetup", "Tue May 20, 2025 · 3:30 PM · Room 204",     "Robotics Club"));
        rsvpEvents.add(new RsvpEvent(3, "Resume Writing Workshop", "Workshop",  "Sat May 24, 2025 · 10:00 AM · Library B",   "Career Services"));


        if (rsvpEvents.isEmpty()) {
            add(heading, subtitle, divider, emptyState());
        } else {
            Div cardList = new Div();
            cardList.getStyle()
                .set("display", "flex")
                .set("flex-direction", "column")
                .set("gap", "12px")
                .set("max-width", "680px");

            for (RsvpEvent ev : rsvpEvents) {
                cardList.add(buildRsvpCard(ev));
            }
            add(heading, subtitle, divider, cardList);
        }
    }


    private Div buildRsvpCard(RsvpEvent ev) {
        Div card = new Div();
        card.getStyle()
            .set("background", "var(--lumo-base-color)")
            .set("border", "1px solid var(--lumo-contrast-10pct)")
            .set("border-radius", "var(--lumo-border-radius-l)")
            .set("padding", "1rem 1.25rem")
            .set("display", "flex")
            .set("flex-direction", "column")
            .set("gap", "6px");


        Span badge = new Span(ev.category());
        badge.getStyle()
            .set("display", "inline-block")
            .set("font-size", "0.72rem")
            .set("padding", "2px 8px")
            .set("border-radius", "12px")
            .set("background", "var(--lumo-primary-color-10pct)")
            .set("color", "var(--lumo-primary-color)");


        H3 title = new H3(ev.title());
        title.getStyle()
            .set("margin", "0.25rem 0 0")
            .set("font-size", "1rem")
            .set("cursor", "pointer")
            .set("color", "var(--lumo-primary-color)");
        title.addClickListener(e -> UI.getCurrent().navigate("events/" + ev.id()));


        Paragraph meta = new Paragraph(ev.meta());
        meta.getStyle()
            .set("font-size", "0.85rem")
            .set("color", "var(--lumo-secondary-text-color)")
            .set("margin", "0");


        Span host = new Span("Hosted by: " + ev.host());
        host.getStyle()
            .set("font-size", "0.8rem")
            .set("color", "var(--lumo-secondary-text-color)");


        Button viewBtn = new Button("View Details");
        viewBtn.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_PRIMARY);
        viewBtn.addClickListener(e -> UI.getCurrent().navigate("events/" + ev.id()));

        Button calBtn = new Button("Add to Calendar");
        calBtn.addThemeVariants(ButtonVariant.LUMO_SMALL);
        calBtn.addClickListener(e -> {

            Notification n = Notification.show("Opening Google Calendar…");
            n.setPosition(Notification.Position.BOTTOM_CENTER);

            UI.getCurrent().getPage().open(
                "https://calendar.google.com/calendar/render?action=TEMPLATE&text="
                    + java.net.URLEncoder.encode(ev.title(), java.nio.charset.StandardCharsets.UTF_8),
                "_blank"
            );
        });

        Button cancelRsvpBtn = new Button("Cancel RSVP");
        cancelRsvpBtn.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_TERTIARY);
        cancelRsvpBtn.addClickListener(e -> {
            // Remove card from DOM (real impl would call rsvpService.cancel(ev.id()))
            card.getParent().ifPresent(parent -> {
                if (parent instanceof Div parentDiv) {
                    parentDiv.remove(card);
                    // If no cards remain, show empty state instead
                    if (parentDiv.getChildren().findAny().isEmpty()) {
                        parentDiv.getParent().ifPresent(pp -> {
                            if (pp instanceof VerticalLayout vl) {
                                vl.add(emptyState());
                            }
                        });
                    }
                }
            });
            Notification n = Notification.show("RSVP cancelled.");
            n.addThemeVariants(NotificationVariant.LUMO_CONTRAST);
            n.setPosition(Notification.Position.BOTTOM_CENTER);
        });

        HorizontalLayout actions = new HorizontalLayout(viewBtn, calBtn, cancelRsvpBtn);
        actions.setSpacing(true);
        actions.getStyle().set("margin-top", "0.5rem").set("flex-wrap", "wrap");

        card.add(badge, title, meta, host, actions);
        return card;
    }

    // ── Empty state ───────────────────────────────────────────────────────────

    private Div emptyState() {
        Div empty = new Div();
        empty.getStyle()
            .set("text-align", "center")
            .set("padding", "3rem 1rem")
            .set("color", "var(--lumo-secondary-text-color)");

        Paragraph msg = new Paragraph("No RSVPs yet — ");
        Anchor link = new Anchor("events", "browse events");
        msg.add(link, new com.vaadin.flow.component.html.Span(" to find something to join."));

        empty.add(msg);
        return empty;
    }
}
