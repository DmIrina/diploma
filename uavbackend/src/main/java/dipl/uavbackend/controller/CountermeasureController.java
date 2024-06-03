package dipl.uavbackend.controller;
import dipl.uavbackend.dto.CountermeasureDto;
import dipl.uavbackend.service.CountermeasureService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*") // Дозволяє запити з будь-якого джерела
@RestController
@RequestMapping("/api/countermeasures")
@AllArgsConstructor
public class CountermeasureController {

    @Autowired
    private CountermeasureService countermeasureService;
    @PostMapping
    public ResponseEntity<CountermeasureDto> createCountermeasure(@RequestBody CountermeasureDto countermeasureDto) {
       CountermeasureDto newCountermeasure =  countermeasureService.createCountermeasure(countermeasureDto);
       return new ResponseEntity<>(newCountermeasure, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<CountermeasureDto> getCountermeasureById(@PathVariable("id") Long countermeasureId) {
       CountermeasureDto countermeasureDto = countermeasureService.getCountermeasureById(countermeasureId);
       return new ResponseEntity<>(countermeasureDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CountermeasureDto>> getAllCountermeasures() {
        List<CountermeasureDto> countermeasureDtoList = countermeasureService.getAllCountermeasures();
        return new ResponseEntity<>(countermeasureDtoList, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<CountermeasureDto> updateCountermeasure(@PathVariable("id") Long countermeasureId,
                                                          @RequestBody CountermeasureDto countermeasureDto) {
       CountermeasureDto updatedCountermeasure = countermeasureService.updateCountermeasure(countermeasureId, countermeasureDto);
       return new ResponseEntity<>(updatedCountermeasure, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCountermeasure(@PathVariable("id") Long countermeasureId) {
        countermeasureService.deleteCountermeasure(countermeasureId);
        return new ResponseEntity<>("Delete Countermeasure Successfully", HttpStatus.OK);
    }
}

