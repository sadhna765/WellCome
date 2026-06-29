package dairy_milk_backend.controller;

import dairy_milk_backend.entity.Setting;
import dairy_milk_backend.service.SettingService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/settings")
@CrossOrigin(origins = "http://localhost:4200")
public class SettingController {

    private final SettingService settingService;

    public SettingController(SettingService settingService) {
        this.settingService = settingService;
    }

    // Settings load karo
    @GetMapping
    public Setting getSettings() {
        return settingService.getSettings();
    }

    // Settings save/update karo
    @PutMapping
    public Setting updateSettings(@RequestBody Setting setting) {
        return settingService.updateSettings(setting);
    }
}