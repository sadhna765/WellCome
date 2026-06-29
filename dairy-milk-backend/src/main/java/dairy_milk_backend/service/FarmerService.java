package dairy_milk_backend.service;

import dairy_milk_backend.entity.Farmer;
import dairy_milk_backend.repository.FarmerRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FarmerService {

  private final FarmerRepository farmerRepository;

  public FarmerService(FarmerRepository farmerRepository) {
    this.farmerRepository = farmerRepository;
  }

  public Farmer saveFarmer(Farmer farmer) {
    return farmerRepository.save(farmer);
  }

  public List<Farmer> getAllFarmers() {
    return farmerRepository.findAll();
  }

  public Farmer getFarmerById(Long id) {
    return farmerRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Farmer not found with id " + id));
  }

  public Farmer updateFarmer(Long id, Farmer farmerData) {
      Farmer farmer = farmerRepository.findById(id)
              .orElseThrow(() -> new RuntimeException("Farmer not found with id " + id));
      farmer.setName(farmerData.getName());
      farmer.setMobile(farmerData.getMobile());
      farmer.setVillage(farmerData.getVillage());
      return farmerRepository.save(farmer);
    }
    
}


