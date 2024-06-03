package dipl.uavbackend.controller;
import dipl.uavbackend.entity.Zone;
import dipl.uavbackend.service.ZoneService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*") // Дозволяє запити з будь-якого джерела
@RestController
@RequestMapping("/api/zones")
@AllArgsConstructor
public class ZoneController {

    @Autowired
    private ZoneService zoneService;

    @GetMapping("/{x}/{y}/{z}")
    public ResponseEntity<Zone> getZone(@PathVariable("x") int x,
                                        @PathVariable("y") int y,
                                        @PathVariable("z") int z) {
       // Zone zone = zoneService.getZone(1, x, y, z);
        Zone zone = new Zone(1,1,1,1);
        if (zone != null) {
            return new ResponseEntity<>(zone, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

