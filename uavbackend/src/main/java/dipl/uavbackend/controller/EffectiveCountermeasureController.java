package dipl.uavbackend.controller;

import dipl.uavbackend.dto.EffectiveCountermeasureDto;
import dipl.uavbackend.service.EffectiveCountermeasureService;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin("*")
@CrossOrigin(origins = "*") // Дозволяє запити з будь-якого джерела
@RestController
@RequestMapping("/api/effectiveCountermeasures")
@AllArgsConstructor
public class EffectiveCountermeasureController {
    @Autowired
    private EffectiveCountermeasureService effectiveCountermeasureService;

    @PostMapping
    public ResponseEntity<EffectiveCountermeasureDto> createEffectiveCountermeasure(@RequestBody EffectiveCountermeasureDto effectiveCountermeasureDto) {
        EffectiveCountermeasureDto savedEffectiveCountermeasure = effectiveCountermeasureService.createEffectiveCountermeasure(effectiveCountermeasureDto);
        return new ResponseEntity<>(savedEffectiveCountermeasure, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<EffectiveCountermeasureDto> getEffectiveCountermeasureById(@PathVariable("id") Long effectiveCountermeasureId) {
        EffectiveCountermeasureDto effectiveCountermeasureDto = effectiveCountermeasureService.getEffectiveCountermeasureById(effectiveCountermeasureId);
        return new ResponseEntity<>(effectiveCountermeasureDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<EffectiveCountermeasureDto>> getAllEffectiveCountermeasures() {
        List<EffectiveCountermeasureDto> effectiveCountermeasureDtoList = effectiveCountermeasureService.getAllEffectiveCountermeasures();
        return new ResponseEntity<>(effectiveCountermeasureDtoList, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<EffectiveCountermeasureDto> updateEffectiveCountermeasure(@PathVariable("id") Long effectiveCountermeasureId,
                 @RequestBody EffectiveCountermeasureDto effectiveCountermeasureDto) {
        EffectiveCountermeasureDto newEffectiveCountermeasureDto = effectiveCountermeasureService.updateEffectiveCountermeasure(effectiveCountermeasureId, effectiveCountermeasureDto);
        return new ResponseEntity<>(newEffectiveCountermeasureDto, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteEffectiveCountermeasure(@PathVariable("id") Long effectiveCountermeasureId) {
        effectiveCountermeasureService.deleteEffectiveCountermeasure(effectiveCountermeasureId);
        return new ResponseEntity<>("EffectiveCountermeasure was successfully deleted", HttpStatus.OK);
    }
}
