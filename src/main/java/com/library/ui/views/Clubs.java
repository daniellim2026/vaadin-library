package com.library.ui.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

import java.util.List;

@Route("clubs")
@PageTitle("Clubs – CampusHub")
@Menu(order = 2, title = "Clubs", icon = "vaadin:group")
@PermitAll
public class Clubs extends VerticalLayout {


    record Club(long id, String name, String category, String description, int memberCount, boolean followedByDefault) {}

    private static final List<Club> STUB_CLUBS = List.of(
        new Club(1, "Robotics Club",        "Technology",  "Build and compete with robots at regional and provincial competitions.", 34, false),
        new Club(2, "Drama Club",        "Arts",        "School productions, improv nights, and workshops with industry guests.",  28, true),
        new Club(3, "Math Club",            "Academic",    "Weekly problem-solving sessions, AMC prep, and friendly competitions.",    22, false),
        new Club(5, "Photography Club",     "Arts",        "Shoot, edit, and exhibit — all skill levels welcome.",                   15, false),
        new Club(6, "Debate Club",          "Academic",    "Tournaments, public-speaking workshops, and critical-thinking drills.",  31, true)
    );

    public Clubs() {
        setPadding(true);
        setSpacing(false);

        // ── Header ───────────────────────────────────────────────────────────
        H2 heading = new H2("Clubs");
        heading.getStyle().set("margin-bottom", "0.25rem");

        Paragraph subtitle = new Paragraph("Follow clubs to get notified when they post new events.");
        subtitle.getStyle().set("color", "var(--lumo-secondary-text-color)").set("margin", "0 0 1rem");

        // ── Search / filter bar ───────────────────────────────────────────────
        TextField searchField = new TextField();
        searchField.setPlaceholder("Search clubs…");
        searchField.setWidth("240px");

        HorizontalLayout filterRow = new HorizontalLayout(searchField);
        filterRow.setAlignItems(Alignment.END);
        filterRow.getStyle().set("margin-bottom", "1rem");

        // ── Club grid ────────────────────────────────────────────────────────
        Div grid = new Div();
        grid.getStyle()
            .set("display", "grid")
            .set("grid-template-columns", "repeat(auto-fill, minmax(260px, 1fr))")
            .set("gap", "14px")
            .set("max-width", "960px");

        for (Club club : STUB_CLUBS) {
            grid.add(buildClubCard(club));
        }


        searchField.addValueChangeListener(e -> {
            String query = e.getValue().trim().toLowerCase();
            grid.getChildren().forEach(child -> {
                if (child instanceof Div card) {

                    card.getChildren()
                        .filter(c -> c instanceof H3)
                        .findFirst()
                        .ifPresent(h3 -> {
                            String cardName = ((H3) h3).getText().toLowerCase();
                            card.setVisible(query.isEmpty() || cardName.contains(query));
                        });
                }
            });
        });

        add(heading, subtitle, filterRow, grid);
    }


    private Div buildClubCard(Club club) {
        Div card = new Div();
        card.getStyle()
            .set("background", "var(--lumo-base-color)")
            .set("border", "1px solid var(--lumo-contrast-10pct)")
            .set("border-radius", "var(--lumo-border-radius-l)")
            .set("padding", "1rem 1.25rem")
            .set("display", "flex")
            .set("flex-direction", "column")
            .set("gap", "6px");

        // Category badge
        Span badge = new Span(club.category());
        badge.getStyle()
            .set("display", "inline-block")
            .set("font-size", "0.72rem")
            .set("padding", "2px 8px")
            .set("border-radius", "12px")
            .set("background", "var(--lumo-contrast-5pct)")
            .set("color", "var(--lumo-secondary-text-color)");

        H3 name = new H3(club.name());
        name.getStyle().set("margin", "0.25rem 0 0").set("font-size", "1rem");

        Paragraph desc = new Paragraph(club.description());
        desc.getStyle()
            .set("font-size", "0.85rem")
            .set("color", "var(--lumo-secondary-text-color)")
            .set("margin", "0 0 0.25rem")
            .set("line-height", "1.4");

        Span memberCount = new Span(club.memberCount() + " members");
        memberCount.getStyle().set("font-size", "0.78rem").set("color", "var(--lumo-secondary-text-color)");

        Button followBtn = buildFollowButton(club.followedByDefault(), club.name());


        Button eventsBtn = new Button("See Events");
        eventsBtn.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);
        eventsBtn.addClickListener(e -> UI.getCurrent().navigate("events"));

        HorizontalLayout actions = new HorizontalLayout(followBtn, eventsBtn);
        actions.setSpacing(true);
        actions.getStyle().set("margin-top", "0.5rem").set("flex-wrap", "wrap");

        card.add(badge, name, desc, memberCount, actions);
        return card;
    }

    private Button buildFollowButton(boolean alreadyFollowed, String clubName) {

        boolean[] following = {alreadyFollowed};

        Button btn = new Button(alreadyFollowed ? "✓ Following" : "Follow");
        if (alreadyFollowed) {
            applyFollowingStyle(btn);
        } else {
            applyFollowStyle(btn);
        }

        btn.addClickListener(e -> {
            following[0] = !following[0];
            if (following[0]) {
                btn.setText("✓ Following");
                applyFollowingStyle(btn);
                Notification n = Notification.show("You're now following " + clubName + "!");
                n.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                n.setPosition(Notification.Position.BOTTOM_CENTER);
            } else {
                btn.setText("Follow");
                applyFollowStyle(btn);
                Notification n = Notification.show("Unfollowed " + clubName + ".");
                n.addThemeVariants(NotificationVariant.LUMO_CONTRAST);
                n.setPosition(Notification.Position.BOTTOM_CENTER);
            }

        });

        return btn;
    }

    private static void applyFollowStyle(Button btn) {
        btn.removeThemeVariants(ButtonVariant.LUMO_SUCCESS);
        btn.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_PRIMARY);
    }

    private static void applyFollowingStyle(Button btn) {
        btn.removeThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btn.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_SUCCESS);
    }
}
