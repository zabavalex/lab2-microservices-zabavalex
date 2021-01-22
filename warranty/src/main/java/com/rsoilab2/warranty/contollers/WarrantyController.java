package com.rsoilab2.warranty.contollers;

import com.rsoilab2.warranty.entities.Warranty;
import com.rsoilab2.warranty.enumerations.WarrantyDecisions;
import com.rsoilab2.warranty.enumerations.WarrantyStatus;
import com.rsoilab2.warranty.models.WarrantyInformationResponse;
import com.rsoilab2.warranty.models.WarrantyRequest;
import com.rsoilab2.warranty.models.WarrantyResponse;
import com.rsoilab2.warranty.services.WarrantyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/warranty")
public class WarrantyController {
    @Autowired
    private WarrantyService warrantyService;

    @GetMapping("/{itemUid}")
    public ResponseEntity<?> getInformationAboutWarrantyByItemUid(@PathVariable String itemUid){
        return ResponseEntity.ok(new WarrantyInformationResponse(warrantyService.getWarrantyByItemUid(UUID.fromString(itemUid))));
    }

    @PostMapping("/{itemUid}/warranty")
    public ResponseEntity<?> performWarrantyRequest(@PathVariable String itemUid, @RequestBody WarrantyRequest warrantyRequest){
        Warranty warrantyFromDB = warrantyService.getWarrantyByItemUid(UUID.fromString(itemUid));
        WarrantyDecisions decisions;
        warrantyFromDB.setComment(warrantyRequest.getReason());
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MONTH, -1);
        if (warrantyFromDB.getWarrantyDate().after(now) && warrantyFromDB.getStatus() ==
                WarrantyStatus.ON_WARRANTY) {
            decisions = warrantyRequest.getAvailableCount() > 0 ? WarrantyDecisions.RETURN : WarrantyDecisions.FIXING;
            warrantyFromDB.setStatus(WarrantyStatus.USE_WARRANTY);
        } else {
            decisions = WarrantyDecisions.REFUSE;
            warrantyFromDB.setStatus(WarrantyStatus.REMOVED_FROM_WARRANTY);
        }

        warrantyService.save(warrantyFromDB);

        return ResponseEntity.ok(new WarrantyResponse(decisions.name(), warrantyFromDB.getWarrantyDate().getTime().toString()));
    }

    @PostMapping("/{itemUid}")
    public ResponseEntity<?> performWarrantyPeriodBeginningRequest(@PathVariable String itemUid){
        Warranty warranty = new Warranty();
        warranty.setItemUid(UUID.fromString(itemUid));
        warranty.setStatus(WarrantyStatus.ON_WARRANTY);
        warranty.setWarrantyDate(Calendar.getInstance());
        warrantyService.save(warranty);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{itemUid}")
    public ResponseEntity<?> performCloseWarrantyRequest(@PathVariable String itemUid){
        warrantyService.deleteAll(UUID.fromString(itemUid));
        return ResponseEntity.noContent().build();
    }
}
