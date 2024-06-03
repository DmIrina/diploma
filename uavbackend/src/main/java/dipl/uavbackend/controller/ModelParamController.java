package dipl.uavbackend.controller;

import dipl.uavbackend.dto.ModelParamDto;
import dipl.uavbackend.service.ModelParamService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/modelparams")
@AllArgsConstructor
public class ModelParamController {

    @Autowired
    private ModelParamService modelParamService;

    @PostMapping
    public ResponseEntity<ModelParamDto> createModelParam(@RequestBody ModelParamDto modelParamDto) {
        ModelParamDto newModelParam = modelParamService.createModelParam(modelParamDto);
        return new ResponseEntity<>(newModelParam, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<ModelParamDto> getModelParamById(@PathVariable("id") Long modelParamId) {
        ModelParamDto modelParamDto = modelParamService.getModelParamById(modelParamId);
        return new ResponseEntity<>(modelParamDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ModelParamDto>> getAllModelParams() {
        List<ModelParamDto> modelParamDtoList = modelParamService.getAllModelParams();
        return new ResponseEntity<>(modelParamDtoList, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<ModelParamDto> updateModelParam(@PathVariable("id") Long uavId,
                                                          @RequestBody ModelParamDto modelParamDto) {
        ModelParamDto updatedModelParam = modelParamService.updateModelParam(uavId, modelParamDto);
        return new ResponseEntity<>(updatedModelParam, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteModelParam(@PathVariable("id") Long modelParamId) {
        modelParamService.deleteModelParam(modelParamId);
        return new ResponseEntity<>("Delete ModelParam Successfully", HttpStatus.OK);
    }
}
