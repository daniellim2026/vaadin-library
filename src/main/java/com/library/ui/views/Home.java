package com.library.ui.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("")
@PageTitle("CampusHub")
@AnonymousAllowed
public class Home extends VerticalLayout {

    public Home() {
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setPadding(true);
        setSpacing(true);

        // Hero
        H1 title = new H1("Welcome to CampusHub");
        Paragraph subtitle = new Paragraph(
            "Find every club meetup, study session, sports game, and workshop at St. Georges — all in one place."
        );
        subtitle.getStyle().set("text-align", "center").set("max-width", "480px").set("color", "var(--lumo-secondary-text-color)");

        Button browseBtn = new Button("Browse Events", e -> UI.getCurrent().navigate("events"));
        browseBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_LARGE);

        Button signupBtn = new Button("Sign Up Free", e -> UI.getCurrent().navigate("signup"));
        signupBtn.addThemeVariants(ButtonVariant.LUMO_LARGE);

        HorizontalLayout heroBtns = new HorizontalLayout(browseBtn, signupBtn);
        heroBtns.setSpacing(true);


        Div statsRow = new Div();
        statsRow.getStyle()
            .set("display", "grid")
            .set("grid-template-columns", "repeat(3, 1fr)")
            .set("gap", "12px")
            .set("width", "100%")
            .set("max-width", "500px")
            .set("margin-top", "1.5rem");

        statsRow.add(statCard("24", "Upcoming events"));
        statsRow.add(statCard("12", "Active clubs"));
        statsRow.add(statCard("340", "Students signed up"));

        add(title, subtitle, heroBtns, statsRow);
    }

    private Div statCard(String number, String label) {
        Div card = new Div();
        card.getStyle()
            .set("background", "var(--lumo-base-color)")
            .set("border", "1px solid var(--lumo-contrast-10pct)")
            .set("border-radius", "var(--lumo-border-radius-l)")
            .set("padding", "1rem")
            .set("text-align", "center");

        Span num = new Span(number);
        num.getStyle().set("display", "block").set("font-size", "1.5rem").set("font-weight", "500");

        Span lbl = new Span(label);
        lbl.getStyle().set("display", "block").set("font-size", "0.8rem").set("color", "var(--lumo-secondary-text-color)");

        card.add(num, lbl);
        return card;
    }
}
