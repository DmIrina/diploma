package dipl.uavbackend.controller;

import dipl.uavbackend.dto.EffectiveUavDto;
import dipl.uavbackend.service.EffectiveUavService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin("*")
@CrossOrigin(origins = "*") // Дозволяє запити з будь-якого джерела
@RestController
@RequestMapping("/api/effectiveUavs")
@AllArgsConstructor
public class EffectiveUavController {
    @Autowired
    private EffectiveUavService effectiveUavService;

    @PostMapping
    public ResponseEntity<EffectiveUavDto> createEffectiveUav(@RequestBody EffectiveUavDto effectiveUavDto) {
        EffectiveUavDto savedEffectiveUav = effectiveUavService.createEffectiveUav(effectiveUavDto);
        return new ResponseEntity<>(savedEffectiveUav, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<EffectiveUavDto> getEffectiveUavById(@PathVariable("id") Long effectiveUavId) {
        EffectiveUavDto effectiveUavDto = effectiveUavService.getEffectiveUavById(effectiveUavId);
        return new ResponseEntity<>(effectiveUavDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<EffectiveUavDto>> getAllEffectiveUavs() {
        List<EffectiveUavDto> effectiveUavDtoList = effectiveUavService.getAllEffectiveUavs();
        return new ResponseEntity<>(effectiveUavDtoList, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<EffectiveUavDto> updateEffectiveUav(@PathVariable("id") Long effectiveUavId,
                 @RequestBody EffectiveUavDto effectiveUavDto) {
        EffectiveUavDto newEffectiveUavDto = effectiveUavService.updateEffectiveUav(effectiveUavId, effectiveUavDto);
        return new ResponseEntity<>(newEffectiveUavDto, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteEffectiveUav(@PathVariable("id") Long effectiveUavId) {
        effectiveUavService.deleteEffectiveUav(effectiveUavId);
        return new ResponseEntity<>("EffectiveUav was successfully deleted", HttpStatus.OK);
    }
}
