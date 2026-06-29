package dairy_milk_backend.service;

import dairy_milk_backend.entity.Setting;
import dairy_milk_backend.repository.SettingRepository;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SettingService {

    private final SettingRepository settingRepository;

    public SettingService(SettingRepository settingRepository) {
        this.settingRepository = settingRepository;
    }

    // Settings load karo — agar koi nahi hai to ek default bana ke return karo
    public Setting getSettings() {
        return settingRepository.findById(1L)
            .orElseGet(this::createDefault);
    }

    // Settings update karo (hamesha id=1 waali row pe)
    public Setting updateSettings(Setting updated) {
        Setting existing = settingRepository.findById(1L)
            .orElseGet(this::createDefault);

        existing.setDairyName(updated.getDairyName());
        existing.setOwnerName(updated.getOwnerName());
        existing.setContact(updated.getContact());
        existing.setAddress(updated.getAddress());
        existing.setDefaultRate(updated.getDefaultRate());
        existing.setCurrency(updated.getCurrency());

        return settingRepository.save(existing);
    }

    // Pehli baar — khaali default row banao
    private Setting createDefault() {
        Setting setting = new Setting();
        setting.setDairyName("My Dairy");
        setting.setOwnerName("");
        setting.setContact("");
        setting.setAddress("");
        setting.setDefaultRate(0.0);
        setting.setCurrency("Rs");
        return settingRepository.save(setting);
    }
}