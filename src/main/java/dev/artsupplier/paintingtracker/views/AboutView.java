package dev.artsupplier.paintingtracker.views;


import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.annotation.security.PermitAll;

@AnonymousAllowed
//@Route(value = "about", layout = MainLayout.class)
@Route(value = "", layout = MainLayout.class)
@PageTitle("About Painting Tracker") //title of the page
@PermitAll //page visible to all
public class AboutView extends VerticalLayout {

    public AboutView() {
        setSpacing(true);
        setPadding(true);

        H2 header = new H2("About Painting Tracker");
        Paragraph description = new Paragraph("Painting Tracker is a web application designed to help artists and hobbyists keep track of their painting sessions, materials used, and progress over time. It provides a user-friendly interface for managing painting sessions, including details about the paints, canvases, and brushes used.");
        Paragraph features = new Paragraph("Features:\n" +
                "- Track painting sessions with details such as date, title, and materials used.\n" +
                "- Manage paints, canvases, and brushes in a user-friendly interface.\n" +
                "- View and edit past sessions for better organization and planning.\n");
        Paragraph version = new Paragraph("Version: 1.0.0");

        add(header, description, features, version);
    }
}
