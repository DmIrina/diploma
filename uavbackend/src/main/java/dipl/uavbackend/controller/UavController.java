package dipl.uavbackend.controller;

import dipl.uavbackend.dto.UavDto;
import dipl.uavbackend.service.UavService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*") // Дозволяє запити з будь-якого джерела
@RestController
@RequestMapping("/api/uavs")
@AllArgsConstructor
public class UavController {

    @Autowired
    private UavService uavService;

    @PostMapping
    public ResponseEntity<UavDto> createUav(@RequestBody UavDto uavDto) {
        UavDto newUav = uavService.createUav(uavDto);
        return new ResponseEntity<>(newUav, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<UavDto> getUavById(@PathVariable("id") Long uavId) {
        UavDto uavDto = uavService.getUavById(uavId);
        return new ResponseEntity<>(uavDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UavDto>> getAllUavs() {
        List<UavDto> uavDtoList = uavService.getAllUavs();
        return new ResponseEntity<>(uavDtoList, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<UavDto> updateUav(@PathVariable("id") Long uavId,
                                            @RequestBody UavDto uavDto) {
        UavDto updatedUav = uavService.updateUav(uavId, uavDto);
        return new ResponseEntity<>(updatedUav, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUav(@PathVariable("id") Long uavId) {
        uavService.deleteUav(uavId);
        return new ResponseEntity<>("Delete Uav Successfully", HttpStatus.OK);
    }
}

