package dairy_milk_backend.controller;

import dairy_milk_backend.entity.Farmer;
import dairy_milk_backend.service.FarmerService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/farmers")
@CrossOrigin("*")
public class FarmerController {

  private final FarmerService farmerService;
  public FarmerController(FarmerService farmerService) {
    this.farmerService = farmerService;
  }

  @PostMapping
  public Farmer createFarmer(@RequestBody Farmer farmer) {
    return farmerService.saveFarmer(farmer);
  }

  @GetMapping
  public List<Farmer> getAllFarmers() {
    return farmerService.getAllFarmers();
  }

  @GetMapping("/{id}")
  public Farmer getFarmerById(@PathVariable Long id) {
    return farmerService.getFarmerById(id);
  }

  @PutMapping("/{id}")
  public Farmer updateFarmer(
          @PathVariable Long id,
          @RequestBody Farmer farmer) {

      return farmerService.updateFarmer(id, farmer);
  }
  
}