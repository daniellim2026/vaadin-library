package com.library.ui.views;

import com.library.backend.service.UserService;
import com.library.backend.dto.UserDTO;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("signup")
@PageTitle("Sign Up")
@AnonymousAllowed
public class Signup extends VerticalLayout {

    private final UserService userService;
    private final BeanValidationBinder<UserDTO> binder = new BeanValidationBinder<>(UserDTO.class);

    private final TextField username = new TextField("Username");
    private final PasswordField password = new PasswordField("Password");
    private final PasswordField confirmPassword = new PasswordField("Confirm Password");

    public Signup(UserService userService) {
        this.userService = userService;

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        H2 heading = new H2("Create your account");
        heading.getStyle().set("margin-bottom", "0.5rem");

        username.setWidthFull();
        username.setValueChangeMode(ValueChangeMode.LAZY);

        password.setWidthFull();
        password.setValueChangeMode(ValueChangeMode.LAZY);

        confirmPassword.setWidthFull();
        confirmPassword.setValueChangeMode(ValueChangeMode.LAZY);

        Button submitBtn = new Button("Sign Up");
        submitBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        submitBtn.setWidthFull();

        Button loginLink = new Button("Already have an account? Log in");
        loginLink.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        loginLink.setWidthFull();
        loginLink.addClickListener(e -> UI.getCurrent().navigate("login"));

        binder.bindInstanceFields(this);
        binder.forField(username)
            .withValidator(name -> !this.userService.userExists(name), "Username already exists");
        binder.forField(confirmPassword)
            .withValidator(confirmPass -> confirmPass.equals(password.getValue()), "Passwords do not match");

        submitBtn.addClickListener(e -> {
            UserDTO dto = new UserDTO();
            if (binder.writeBeanIfValid(dto)) {
                this.userService.createUser(dto.getUsername(), dto.getPassword());
                UI.getCurrent().navigate("login");
            } else {
                binder.validate();
            }
        });

        VerticalLayout formCard = new VerticalLayout(username, password, confirmPassword, submitBtn, loginLink);
        formCard.setWidth("340px");
        formCard.getStyle()
            .set("background", "var(--lumo-base-color)")
            .set("border", "1px solid var(--lumo-contrast-10pct)")
            .set("border-radius", "var(--lumo-border-radius-l)")
            .set("padding", "1.5rem");

        add(heading, formCard);
    }
}
