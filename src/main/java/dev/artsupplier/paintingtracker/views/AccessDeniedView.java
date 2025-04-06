package dev.artsupplier.paintingtracker.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;

@Route(value = "access-denied", layout = MainLayout.class)

public class AccessDeniedView extends VerticalLayout implements HasErrorParameter<AccessDeniedException> {
    public AccessDeniedView() {
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        add(new H1("Access denied!"));
        add(new Paragraph("You do not have permission to access this page. \n Please contact the admin for more information."));
    }

    @Override
    public int setErrorParameter(BeforeEnterEvent event, ErrorParameter<AccessDeniedException> parameter) {
        return 403;
    }
}
