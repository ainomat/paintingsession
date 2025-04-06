package dev.artsupplier.paintingtracker.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Nav;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility;

import dev.artsupplier.paintingtracker.views.PaintingsessionView;
import dev.artsupplier.paintingtracker.views.AdminView;
import dev.artsupplier.paintingtracker.views.AboutView;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import com.vaadin.flow.component.button.Button;

@AnonymousAllowed
@PermitAll
public class MainLayout extends AppLayout {

    public MainLayout() {
        //initialize layout
        createHeader();
        //createDrawer(); //navigation bar
        createFooter();
    }

    private void createHeader() {
        //create header
        H1 logo = new H1("Painting Tracker");
        logo.addClassNames(LumoUtility.FontSize.XXLARGE, LumoUtility.Margin.MEDIUM);

        //navigation links on right of header
        RouterLink sessionsLink = new RouterLink("Sessions", PaintingsessionView.class);
        RouterLink adminLink = new RouterLink("Admin", AdminView.class);
        RouterLink aboutLink = new RouterLink("About", AboutView.class);
        //login page
        RouterLink loginLink = new RouterLink("Login", LoginView.class);

        //login or logout depending if logged in
        //if logged in, show logout, if not, show login
        Button logoutButton = new Button("Logout", event -> UI.getCurrent().getPage().setLocation("/logout"));

        HorizontalLayout navLinks = new HorizontalLayout(sessionsLink, adminLink, aboutLink, loginLink, logoutButton);

        navLinks.setSpacing(true);

        //add links to header
        HorizontalLayout header = new HorizontalLayout(logo, navLinks);
        header.setWidthFull(); //header takes full width
        header.setAlignItems(FlexComponent.Alignment.CENTER); //align items to center
        header.addClassName("header"); //add class, easier to use
        addToNavbar(header); //add header to navbar
    }
/*
    private void createDrawer() {
        //routes to "sessions", "admin"
        RouterLink sessionsLink = new RouterLink("Sessions", PaintingsessionView.class);
        RouterLink adminLink = new RouterLink("Admin", AdminView.class);

        //horizontal navigation bar
        HorizontalLayout nav = new HorizontalLayout(sessionsLink, adminLink);
        nav.setSpacing(true);
        nav.setAlignItems(FlexComponent.Alignment.CENTER);
        nav.addClassName("nav"); //add class, easier to use

        //Nav nav = new Nav(sessionsLink, adminLink); //create nav
        //addToDrawer(nav);
        addToNavbar(nav);
    }

 */

    private void createFooter() {
        //create footer
        Footer footer = new Footer();
        footer.add(new Span("(c) AinoMa 2025 for JavaWeb course."));
        addToDrawer(footer);
    }
}
