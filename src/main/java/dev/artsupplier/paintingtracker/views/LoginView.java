package dev.artsupplier.paintingtracker.views;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import com.vaadin.flow.component.login.LoginForm;

import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.annotation.security.PermitAll;

@AnonymousAllowed
@Route(value = "login", layout = MainLayout.class)
@PermitAll
public class LoginView extends VerticalLayout {

    //declare login form
    private final LoginForm loginForm = new LoginForm();

    public LoginView() {

        //default login by Spring Security
        loginForm.setAction("login");

        add(loginForm);
    }
}
