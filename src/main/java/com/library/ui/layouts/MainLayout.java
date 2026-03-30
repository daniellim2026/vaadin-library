package com.library.ui.layouts;

import com.library.ui.views.Login;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.router.Layout;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.server.menu.MenuConfiguration;
import com.vaadin.flow.server.menu.MenuEntry;
import com.vaadin.flow.spring.security.AuthenticationContext;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.springframework.security.core.userdetails.UserDetails;

@Layout
@AnonymousAllowed
public final class MainLayout extends AppLayout {

    private final AuthenticationContext authContext;

    MainLayout(AuthenticationContext authContext) {
        this.authContext = authContext;

        setPrimarySection(Section.DRAWER);
        addToDrawer(createHeader(), createLoginLogoutItem(), new Scroller(createSideNav()));
    }

    private Component createHeader() {
        // TODO Replace with real application logo and name
        var appLogo = VaadinIcon.CUBES.create();
        appLogo.setSize("48px");
        appLogo.setColor("green");

        var appName = new Span("My Application");
        appName.getStyle().setFontWeight(Style.FontWeight.BOLD);

        var header = new VerticalLayout(appLogo, appName);
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        return header;
    }

    private SideNav createSideNav() {
        var nav = new SideNav();
        nav.addClassNames(LumoUtility.Margin.Horizontal.MEDIUM);
        MenuConfiguration.getMenuEntries().forEach(entry -> nav.addItem(createSideNavItem(entry)));
        return nav;
    }

    private SideNavItem createSideNavItem(MenuEntry menuEntry) {
        if (menuEntry.icon() != null) {
            return new SideNavItem(menuEntry.title(), menuEntry.path(), new Icon(menuEntry.icon()));
        } else {
            return new SideNavItem(menuEntry.title(), menuEntry.path());
        }
    }

    private Component createLoginLogoutItem() {
        var layout = new HorizontalLayout();
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        authContext.getAuthenticatedUser(UserDetails.class)
            .ifPresentOrElse(user -> {
                Button logout = new Button("Logout", click -> {
                    this.authContext.logout();
                });
                logout.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
                logout.setIcon(VaadinIcon.SIGN_OUT.create());
                Span loggedInUser = new Span("Welcome, " + user.getUsername());
                layout.add(loggedInUser, logout);
            }, () -> {
                Button login = new Button("Login", click -> {
                    getUI().ifPresent(ui -> ui.navigate(Login.class));
                });
                login.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
                login.setIcon(VaadinIcon.SIGN_IN.create());
                layout.add(login);
            });
        return layout;
    }

}
