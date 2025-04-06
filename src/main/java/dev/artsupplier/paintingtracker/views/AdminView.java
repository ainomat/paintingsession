package dev.artsupplier.paintingtracker.views;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import dev.artsupplier.paintingtracker.repository.CanvasRepository;
import dev.artsupplier.paintingtracker.repository.PaintRepository;
import dev.artsupplier.paintingtracker.repository.PaintingSessionRepository;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import com.vaadin.flow.component.button.Button;
import dev.artsupplier.paintingtracker.entity.Canvas;
import dev.artsupplier.paintingtracker.entity.Paint;

import com.vaadin.flow.component.notification.NotificationVariant; //näkyvämpi virheilmoitus

@CssImport("./themes/default/custom-style.css")
@Route(value = "admin", layout = MainLayout.class)
@PageTitle("Admin Page")
@RolesAllowed("ADMIN") //will add admin user
//@PermitAll //FOR TESTING
public class AdminView extends VerticalLayout {
    private Paint selectedPaint = null;
    private Canvas selectedCanvas = null;

    public AdminView(@Autowired PaintRepository paintRepo, @Autowired CanvasRepository canvasRepo, @Autowired PaintingSessionRepository sessionRepo) {

        //admin can add move canvases and paints to database
        H2 paintsHeader = new H2("Add Paints");
        FormLayout paintsForm = new FormLayout();

        var paintName = new com.vaadin.flow.component.textfield.TextField("Name");
        var paintBrand = new com.vaadin.flow.component.textfield.TextField("Brand");
        var paintColor = new com.vaadin.flow.component.textfield.TextField("Color");
        var paintType = new com.vaadin.flow.component.textfield.TextField("Type");
        var paintSave = new Button("Save Paint", event -> {
            //Paint paint = new Paint(); //create new paint object
            Paint paint = (selectedPaint != null) ? selectedPaint : new Paint(); //if selected, update, or else new

            //save paint to database
            paint.setName(paintName.getValue());
            paint.setBrand(paintBrand.getValue());
            paint.setColorCode(paintColor.getValue());
            paint.setType(paintType.getValue());
            paintRepo.save(paint);
            //Notification.show("Paint saved!");
            Notification.show(selectedPaint != null ? "Paint updated!" : "Paint saved!");
            selectedPaint = null;
            paintName.clear();
            paintBrand.clear();
            paintColor.clear();
            paintType.clear();
        });

        paintSave.addClassName("pixel-button");

        //add paint to database
        paintsForm.add(paintName, paintBrand, paintColor, paintType, paintSave);

        //maalin poisto ja muokkaus:
        Grid<Paint> paintGrid = new Grid<>(Paint.class);
        paintGrid.setColumns("name", "brand", "colorCode", "type");
        paintGrid.setItems(paintRepo.findAll());
        paintGrid.addComponentColumn(paint -> {

            Button editButton = new Button("Muokkaa", e -> {
                selectedPaint = paint;
                paintName.setValue(paint.getName());
                paintBrand.setValue(paint.getBrand());
                paintColor.setValue(paint.getColorCode());
                paintType.setValue(paint.getType());
            });

            Button deleteButton = new Button("Delete", e -> {
                boolean inUse = sessionRepo.findAll().stream()
                        .anyMatch(session -> session.getPaints().contains(paint));
                if (inUse) {
                    Notification.show("Tätä maalia ei voi poistaa, koska sitä käytetään maalaussessiossa.");
                } else {
                    /*
                    paintRepo.delete(paint);
                    paintGrid.setItems(paintRepo.findAll());
                    */
                    try {
                        paintRepo.delete(paint);
                        paintGrid.setItems(paintRepo.findAll());
                    } catch (Exception ex) {
                        //Notification.show("Tätä maalia ei voi poistaa, koska sitä käytetään maalaussessiossa.");
                        Notification notification = Notification.show("Tätä maalia ei voi poistaa, koska sitä käytetään maalaussessiossa.");
                        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                    }
                }
            });

            editButton.addClassName("pixel-button");
            deleteButton.addClassName("pixel-button");

            return new HorizontalLayout(editButton, deleteButton);
        }).setHeader("Toiminnot");
        //canvas
        H2 canvasHeader = new H2("Add Canvases");
        FormLayout canvasForm = new FormLayout(); //material, size, primed
        var canvasMaterial = new com.vaadin.flow.component.textfield.TextField("Material");
        var canvasSize = new com.vaadin.flow.component.textfield.TextField("Size");
        var canvasPrimed = new com.vaadin.flow.component.checkbox.Checkbox("Primed");
        var canvasSave = new Button("Save Canvas", event -> {
            //Canvas canvas = new Canvas(); //create new canvas object
            Canvas canvas = (selectedCanvas != null) ? selectedCanvas : new Canvas(); //if selectedcanvas, update, otherwise new

            //save canvas to database
            canvas.setMaterial(canvasMaterial.getValue());
            canvas.setSize(canvasSize.getValue());
            canvas.setPrimed(canvasPrimed.getValue() ? "Yes" : "No");
            canvasRepo.save(canvas);
            //Notification.show("Canvas saved!");
            Notification.show(selectedCanvas != null ? "Canvas updated!" : "Canvas saved!");
            selectedCanvas = null;
            canvasMaterial.clear();
            canvasSize.clear();
            canvasPrimed.clear();
        });
        canvasSave.addClassName("pixel-button");

        //add canvas to database
        canvasForm.add(canvasMaterial, canvasSize, canvasPrimed, canvasSave);

        //canvas muokkaus ja poist:
        Grid<Canvas> canvasGrid = new Grid<>(Canvas.class);
        canvasGrid.setColumns("material", "size", "primed");
        canvasGrid.setItems(canvasRepo.findAll());
        canvasGrid.addComponentColumn(canvas -> {

            Button editButton = new Button("Muokkaa", e -> {
                selectedCanvas = canvas;
                canvasMaterial.setValue(canvas.getMaterial());
                canvasSize.setValue(canvas.getSize());
                canvasPrimed.setValue("Yes".equals(canvas.getPrimed()));
            });

            Button deleteButton = new Button("Delete", e -> {
                boolean inUse = sessionRepo.findAll().stream()
                        .anyMatch(session -> canvas.equals(session.getCanvas()));
                if (inUse) {
                    Notification.show("Tätä kangasta ei voi poistaa, koska sitä käytetään maalaussessiossa.");
                } else {
                    /*
                    canvasRepo.delete(canvas);
                    canvasGrid.setItems(canvasRepo.findAll());

                     */
                    try {
                        canvasRepo.delete(canvas);
                        canvasGrid.setItems(canvasRepo.findAll());
                    } catch (Exception ex) {
                        //Notification.show("Tätä kangasta ei voi poistaa, koska sitä käytetään maalaussessiossa.");
                        //lumo tyyli
                        Notification notification = Notification.show("Tätä pohjaa ei voi poistaa, koska sitä käytetään maalaussessiossa.");
                        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);

                    }

                }
            });

            editButton.addClassName("pixel-button");
            deleteButton.addClassName("pixel-button");

            return new HorizontalLayout(editButton, deleteButton);
        }).setHeader("Toiminnot");

        //layout paint and canvas next to each other
        VerticalLayout paintSection = new VerticalLayout(paintsHeader, paintsForm, paintGrid);
        VerticalLayout canvasSection = new VerticalLayout(canvasHeader, canvasForm, canvasGrid);
        HorizontalLayout formLayout = new HorizontalLayout(paintSection, canvasSection);
        formLayout.setWidthFull();
        formLayout.setFlexGrow(1, paintSection, canvasSection);

        add(formLayout);
        addClassName("admin-view");
    }
}
