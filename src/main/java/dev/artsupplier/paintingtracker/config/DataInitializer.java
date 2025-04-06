package dev.artsupplier.paintingtracker.config;

import dev.artsupplier.paintingtracker.entity.Canvas;
import dev.artsupplier.paintingtracker.entity.Paint;
import dev.artsupplier.paintingtracker.repository.CanvasRepository;
import dev.artsupplier.paintingtracker.repository.PaintRepository;
import dev.artsupplier.paintingtracker.security.User;
import dev.artsupplier.paintingtracker.security.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component //can add data to the database (paints, canvases)
public class DataInitializer {

    //paints, canvases
    private final PaintRepository paintRepo;
    private final CanvasRepository canvasRepo;

    //users
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(PaintRepository paintRepo, CanvasRepository canvasRepo, UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.paintRepo = paintRepo;
        this.canvasRepo = canvasRepo;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct //for initializing data
    public void initializeData() {
        // Initialize Paints
        if (paintRepo.count() == 0) {
            paintRepo.save(new Paint("Citadel", "#000000", "Abaddon Black", "Acrylic"));
            paintRepo.save(new Paint("Vallejo", "#A52A2A", "Bloody Red", "Acrylic"));
            paintRepo.save(new Paint("Army Painter", "#00FF00", "Goblin Green", "Acrylic"));
        }

        // Initialize Canvases
        if (canvasRepo.count() == 0) {
            canvasRepo.save(new Canvas("Cotton", "30x40cm", "Yes"));
            canvasRepo.save(new Canvas("Linen", "20x30cm", "No"));
            canvasRepo.save(new Canvas("Canvas Board", "10x15cm", "Yes"));
        }

        //some users
        if (userRepo.count() == 0) {
            userRepo.save(new User("user1", passwordEncoder.encode("password1"), "USER"));
            userRepo.save(new User("userToo", passwordEncoder.encode("salasana"), "USER"));
            userRepo.save(new User("admin", passwordEncoder.encode("adminpass"), "ADMIN"));
            userRepo.save(new User("adminToo", passwordEncoder.encode("adminsalasana"), "ADMIN"));
        }
    }
}
