package com.library.ui.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@Route("events/rsvp")
@PageTitle("My Events")
@Menu(order = 3, title = "My Events", icon = "vaadin:star")
@PermitAll
public class MyEvents extends VerticalLayout {

    public MyEvents() {
        setPadding(true);
        setSpacing(true);

        H2 heading = new H2("My Events");
        Paragraph subtitle = new Paragraph("Events you've RSVPed to.");
        subtitle.getStyle().set("color", "var(--lumo-secondary-text-color)");


        Div emptyState = new Div();
        emptyState.getStyle()
            .set("text-align", "center")
            .set("padding", "2rem")
            .set("color", "var(--lumo-secondary-text-color)");

        Anchor browseLink = new Anchor("events", "Browse events");
        Paragraph emptyMsg = new Paragraph("No RSVPs yet — ");
        emptyMsg.add(browseLink);
        emptyState.add(emptyMsg);


        add(heading, subtitle, emptyState);
    }
}
