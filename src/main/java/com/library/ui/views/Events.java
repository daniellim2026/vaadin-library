package com.library.ui.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.PermitAll;

@Route("events")
@PageTitle("Events")
@Menu(order = 1, icon = "vaadin:calendar", title = "Events")
@PermitAll
public class Events extends VerticalLayout implements BeforeEnterObserver {

    public Events() {
        setPadding(true);
        setSpacing(false);


        H2 heading = new H2("All Events");
        heading.getStyle().set("margin-bottom", "0.5rem");


        ComboBox<String> categoryFilter = new ComboBox<>("Category");
        categoryFilter.setItems("All", "Club meetup", "Study group", "Sports", "Workshop");
        categoryFilter.setValue("All");
        categoryFilter.setWidth("160px");

        ComboBox<String> dateFilter = new ComboBox<>("Date");
        dateFilter.setItems("Any date", "This week", "This month");
        dateFilter.setValue("Any date");
        dateFilter.setWidth("140px");

        TextField searchField = new TextField("Search");
        searchField.setPlaceholder("Search events...");
        searchField.setWidth("200px");

        HorizontalLayout filters = new HorizontalLayout(categoryFilter, dateFilter, searchField);
        filters.setAlignItems(Alignment.END);
        filters.getStyle().set("margin-bottom", "1rem");


        Div grid = new Div();
        grid.getStyle()
            .set("display", "grid")
            .set("grid-template-columns", "repeat(auto-fill, minmax(220px, 1fr))")
            .set("gap", "12px");

        grid.add(eventCard("Robotics Club Weekly", "Club meetup", "May 20 · Room 204", 1L));
        grid.add(eventCard("Saints vs VC", "Sports", "May 22 · Main Field", 2L));
        grid.add(eventCard("Resume Writing Workshop", "Workshop", "May 24 · Library B", 3L));
        grid.add(eventCard("Calculus Study Hall", "Study group", "May 25 · Room 101", 4L));

        add(heading, filters, grid);
    }

    private Div eventCard(String title, String category, String meta, Long id) {
        Div card = new Div();
        card.getStyle()
            .set("background", "var(--lumo-base-color)")
            .set("border", "1px solid var(--lumo-contrast-10pct)")
            .set("border-radius", "var(--lumo-border-radius-l)")
            .set("padding", "1rem")
            .set("cursor", "pointer");

        Span badge = new Span(category);
        badge.getStyle()
            .set("display", "inline-block")
            .set("font-size", "0.75rem")
            .set("padding", "2px 8px")
            .set("border-radius", "12px")
            .set("background", "var(--lumo-primary-color-10pct)")
            .set("color", "var(--lumo-primary-color)");

        H3 name = new H3(title);
        name.getStyle().set("margin", "0.5rem 0 0.25rem").set("font-size", "1rem");

        Paragraph metaP = new Paragraph(meta);
        metaP.getStyle().set("font-size", "0.85rem").set("color", "var(--lumo-secondary-text-color)").set("margin", "0 0 0.75rem");

        Button rsvpBtn = new Button("RSVP");
        rsvpBtn.addClickListener(e -> {
            rsvpBtn.setText("✓ Going");
            rsvpBtn.getStyle().set("background", "var(--lumo-success-color-10pct)").set("color", "var(--lumo-success-text-color)");
        });

        card.addClickListener(e -> UI.getCurrent().navigate("events/" + id));
        card.add(badge, name, metaP, rsvpBtn);
        return card;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

    }
}
