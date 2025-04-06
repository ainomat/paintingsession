package dev.artsupplier.paintingtracker.views;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import dev.artsupplier.paintingtracker.entity.Canvas;
import dev.artsupplier.paintingtracker.entity.Paint;
import dev.artsupplier.paintingtracker.entity.PaintingSession;
import dev.artsupplier.paintingtracker.entity.SessionDetails;
import dev.artsupplier.paintingtracker.repository.CanvasRepository;
import dev.artsupplier.paintingtracker.repository.PaintRepository;
import dev.artsupplier.paintingtracker.repository.PaintingSessionRepository;
import dev.artsupplier.paintingtracker.repository.SessionDetailsRepository;
import jakarta.annotation.security.PermitAll;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextArea;
import jakarta.annotation.security.RolesAllowed;

import java.time.LocalDate;
import java.util.List;

@Route(value = "sessions", layout = MainLayout.class) //routing to /sessions
@PageTitle("Painting Sessions") //title shown in browser on this page
//@PermitAll //anyone can access this page, add login later!!!
@RolesAllowed({"USER", "ADMIN"})
public class PaintingsessionView extends VerticalLayout {

    private final PaintingSessionRepository repo;
    private final SessionDetailsRepository detailsRepo;
    private final PaintRepository paintRepo;

    //grid with list of painting sessions (objects from the database)
    //private final Grid<PaintingSession> grid = new Grid<>(PaintingSession.class); //AUTO GENERATE DOESNT WORK FOR THIS
    private final Grid<PaintingSession> grid = new Grid<>(); //empty grid, add columns manually

    //input fields for the painting session
    private final TextField title = new TextField("Title");
    private final DatePicker date = new DatePicker("Date");

    //Buttons
    private final Button create = new Button("Add new session");
    private final Button read = new Button("Open session");
    private final Button update = new Button("Update session");
    private final Button delete = new Button("Delete session");

    //SessionDetails entity, storing the details of the session here
    private final TextArea notes = new TextArea("Painting session notes");
    private final NumberField duration = new NumberField("Duration (minutes)");
    //Canvas entity, storing the canvas used in the session
    private final ComboBox<Canvas> canvasSelect = new ComboBox<>("Canvas");
    //Paint entity
    private final MultiSelectComboBox<Paint> paintSelect = new MultiSelectComboBox<>("Paints used");

    //Canvasrepo
    private final CanvasRepository canvasRepo;

    //filters
    private final TextField titleFilter = new TextField("Filter by title");
    private final DatePicker dateFilter = new DatePicker("Filter by date");
    private final ComboBox<Canvas> canvasFilter = new ComboBox<>("Filter by canvas material");
    private final NumberField durationFilter = new NumberField("Filter by duration (minutes)");
    private final ComboBox<String> paintTypeFilter = new ComboBox<>("Filter by paint type");

    //filtering listeners and load data
    {
        titleFilter.addValueChangeListener(e -> filteringLogic());
        dateFilter.addValueChangeListener(e -> filteringLogic());
        canvasFilter.addValueChangeListener(e -> filteringLogic());
        durationFilter.addValueChangeListener(e -> filteringLogic());
        paintTypeFilter.addValueChangeListener(e -> filteringLogic());
    }

    //constructor
    public PaintingsessionView(PaintingSessionRepository repo, SessionDetailsRepository detailsRepo,
                               CanvasRepository canvasRepo, PaintRepository paintRepo) {
        setPadding(true);
        setSpacing(true);
        setMargin(true);

        this.repo = repo;
        this.detailsRepo = detailsRepo;
        this.canvasRepo = canvasRepo;
        this.paintRepo = paintRepo;

        //mofidied style
        create.getStyle()
                .set("background-color", "#83c5be")
                .set("color", "#000000")
                .set("border-radius", "10px");
        update.getStyle()
                .set("background-color", "#ff006e")
                .set("color", "#000000")
                .set("border-radius", "0px");
        delete.getStyle()
                .set("background-color", "#800e13")
                .set("color", "#e09f3e")
                .set("border-radius", "50%")
                .set("width", "100px")
                .set("height", "100px");

        //delete.addClassName("glow-button");




        canvasFilter.setItems(canvasRepo.findAll());
        canvasFilter.setItemLabelGenerator(Canvas::getMaterial);

        paintTypeFilter.setItems(paintRepo.findAll().stream()
                .map(Paint::getType)
                .filter(type -> type != null && !type.isEmpty()) //filter out null and empty types
                .distinct()  //dont show duplicates
                .toList()
        );

        //clear input fields button
        Button clearFiltersButton = new Button("Clear input fields", e -> {
            titleFilter.clear();
            dateFilter.clear();
            canvasFilter.clear();
            durationFilter.clear();
            paintTypeFilter.clear();
            filteringLogic(); //refresh the grid
        });

        /*
        //filtering area
        TextField titleFilter = new TextField("Filter by title");
        DatePicker dateFilter = new DatePicker("Filter by date");

        ComboBox<Canvas> canvasFilter = new ComboBox<>("Filter by canvas material");
        canvasFilter.setItems(canvasRepo.findAll());
        canvasFilter.setItemLabelGenerator(Canvas::getMaterial);

        NumberField durationFilter = new NumberField("Filter by duration (minuter)");
        durationFilter.setStep(5.0); //step size for the number field: 5, 10, 15, etc.

        ComboBox<String> paintTypeFilter = new ComboBox<>("Filter by paint type");
        paintTypeFilter.setItems(paintRepo.findAll().stream()
                .map(Paint::getType)
                .filter(type -> type != null && !type.isEmpty()) //filter out null and empty types
                .distinct()  //dont show duplicates
                .toList()
        );
        */

        //grid
        grid.addColumn(PaintingSession::getId).setHeader("ID"); //id column
        grid.addColumn(PaintingSession::getTitle).setHeader("Title"); //title column
        grid.addColumn(PaintingSession::getDate).setHeader("Date"); //date column

        //notes column
        grid.addColumn(session -> {
            SessionDetails details = session.getSessionDetails();
            return details != null ? details.getNotes() : "";
        }).setHeader("Notes");

        //canvas column
        grid.addColumn(session -> {
            Canvas canvas = session.getCanvas();
            return canvas != null ? canvas.getMaterial() + " | " + canvas.getSize() + " | Primed: " + canvas.getPrimed() : "";
        }).setHeader("Canvas");

        //paints column
        grid.addColumn(session -> {
            if (session.getPaints() != null && !session.getPaints().isEmpty()) {
                return session.getPaints().stream()
                        .map(Paint::getName)
                        .reduce((a, b) -> a + ", " + b)
                        .orElse("");
            }
            return "";
        }).setHeader("Paints");

        grid.addColumn(session -> {
            SessionDetails details = session.getSessionDetails();
            return details != null ? details.getDuration() + " minutes" : "";
        }).setHeader("Duration");

        //load canvases from the database
        canvasSelect.setItems(canvasRepo.findAll());//load canvases from the database
        canvasSelect.setItemLabelGenerator(canvas ->
                //show material, size and primed in the combobox
                canvas.getMaterial() + " | " + canvas.getSize() + " | Primed: " + canvas.getPrimed()
        );
        //custom css
        canvasSelect.addClassName("canvas-picker");

        //load paints from the database
        paintSelect.setItems(paintRepo.findAll());//load paints from the database
        paintSelect.setItemLabelGenerator(paint ->
                //show name and brand in the combobox
                "Name: " + paint.getName() + " | Brand: " + paint.getBrand()
        );
        //custom css
        paintSelect.addClassName("paint-multi-picker");

        //fill grid with data after "saving"
        //grid.setItems(repo.findAll());

        //fill form with data from the selected row
        grid.asSingleSelect().addValueChangeListener(event -> {
            PaintingSession session = event.getValue();
            if (session != null) {
                title.setValue(session.getTitle());
                date.setValue(session.getDate());
                canvasSelect.setValue(session.getCanvas()); //link selected canvas
                //select paints
                paintSelect.setItems(paintRepo.findAll());//refresh the list of paints
                paintSelect.setValue(session.getPaints());//select the paints used in the session

                var details = session.getSessionDetails();
                if (details != null) {
                    notes.setValue(details.getNotes() != null ? details.getNotes() : ""); //if null, detai empty string
                    duration.setValue(details.getDuration() != null ? details.getDuration().doubleValue() : null); //if null, set to null
                } else {
                    notes.clear();
                    duration.clear();
                }
            }
        });

        HorizontalLayout filterBar = new HorizontalLayout
                (titleFilter, dateFilter, canvasFilter, durationFilter, paintTypeFilter, clearFiltersButton
                );
        //horizontal bar lumo utility style
        filterBar.addClassNames(
                "bg-primary",
                "text-contrast",
                "p-m",
                "rounded-l",
                "gap-m"
        );

        //grid style lumo utility style
        grid.addClassNames(
                "border", "p-s", "text-l", "shadow-m");


        //add input and buttons
        add(title, date, notes, duration, canvasSelect, paintSelect, create,
                filterBar,
                grid, delete, update);
        filteringLogic(); //LOAD WHEN THE PAGE LOADS

        //button to add new session
        create.addClickListener(e -> {
            //create new session
            PaintingSession session = new PaintingSession();
            session.setTitle(title.getValue()); //set title to session
            session.setDate(date.getValue());//set date to session
            session.setCanvas(canvasSelect.getValue()); //link selected canvas
            session.setPaints(paintSelect.getSelectedItems()); //link selected paints
            repo.save(session);//save session to the database

            //create sessiondetails
            var details = new dev.artsupplier.paintingtracker.entity.SessionDetails();
            details.setSession(session); //linking sessiondetails to session
            details.setNotes(notes.getValue()); //set notes to sessiondetails
            if (duration.getValue() != null) {
                details.setDuration(duration.getValue().intValue()); //set duration to sessiondetails
            }
            detailsRepo.save(details); //save sessiondetails to the database

            grid.setItems(repo.findAll()); //refresh the grid ith getall again

            //clear input fields
            title.clear();
            date.clear();
            notes.clear();
            duration.clear();
            canvasSelect.clear();
            paintSelect.clear();
            paintSelect.deselectAll();
        });

        //update, basically the same as create
        update.addClickListener(e -> {
            //get selected session
            PaintingSession session = grid.asSingleSelect().getValue();
            if (session != null) {
                //update session
                session.setTitle(title.getValue());
                session.setDate(date.getValue());
                session.setCanvas(canvasSelect.getValue()); //link selected canvas
                session.setPaints(paintSelect.getSelectedItems()); //link selected paints

                repo.save(session);//save session to the database

                //update sessiondetails
                SessionDetails details = session.getSessionDetails();
                if (details == null) {
                    details = new SessionDetails();
                    details.setSession(session); //linking sessiondetails to session
                }
                details.setNotes(notes.getValue());
                if (duration.getValue() != null) {
                    details.setDuration(duration.getValue().intValue()); //set duration to sessiondetails
                }
                detailsRepo.save(details); //save sessiondetails to the database

                grid.setItems(repo.findAll()); //refresh the grid ith getall again

            }
        });

        //delete button
        delete.addClickListener(e -> {
            //get selected session
            PaintingSession session = grid.asSingleSelect().getValue();
            if (session != null) {
                //first delete related sessiondetails
                SessionDetails details = session.getSessionDetails();
                if (details != null) {
                    detailsRepo.delete(details);
                }
                //delete session
                repo.delete(session);
                grid.setItems(repo.findAll()); //refresh the grid ith getall again
                //clear input fields
                title.clear();
                date.clear();
                notes.clear();
                duration.clear();
                canvasSelect.clear();
                paintSelect.clear();

                grid.getDataProvider().refreshAll(); //force refresh of the grid
            }
        });
    }

//filtering logic______________________________________
    private void filteringLogic() {
        List<PaintingSession> filteredSessions = repo.findAll();

        List<PaintingSession> filtered = filteredSessions.stream()
                .filter(session -> {
                    //TITLE FILTER: null or empty or contains lowercase
                    String titleValue = titleFilter.getValue();
                    return titleValue == null || titleValue.isEmpty() ||
                            session.getTitle().toLowerCase().contains(titleValue.toLowerCase());
                })
                .filter(session -> {
                    //DATE FILTER:empty or equals
                    LocalDate selectedDate = dateFilter.getValue();
                    return selectedDate == null || session.getDate().equals(selectedDate);
                })
                .filter(session -> {
                    //CANVAS FILTER: by material from canvas table
                    Canvas selectedCanvas = canvasFilter.getValue();
                    return selectedCanvas == null ||
                            session.getCanvas().getMaterial().equals(selectedCanvas.getMaterial());
                })
                .filter(session -> {
                    //DURATION FILTER:
                    SessionDetails details = session.getSessionDetails();
                    Double value = durationFilter.getValue();
                    return value == null ||
                            (details != null && details.getDuration() != null &&
                                    details.getDuration().doubleValue() == value);
                })
                .filter(session -> {
                    //PAINT TYPE FILTER:
                    String selectedType = paintTypeFilter.getValue();
                    return selectedType == null ||
                            session.getPaints().stream()
                                    .anyMatch(paint -> paint.getType().equals(selectedType));
                })
                .toList();
        grid.setItems(filtered); //set filtered items to the grid
    }
}
