package ru.itmo.alkarized.lab4.controllers;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.itmo.alkarized.lab4.entities.Point;
import ru.itmo.alkarized.lab4.entities.User;
import ru.itmo.alkarized.lab4.repositories.PointRepository;

@RestController
@RequestMapping("point")
@CrossOrigin(origins = "http://localhost:4200")
public class PointController {
    @Autowired
    private PointRepository pointRepository;

    @PostMapping
    public ResponseEntity<?> addPoint(@AuthenticationPrincipal User user, @RequestBody Point point){
        point.setResult();
        point.setUserOwner(user);
        point.setId(pointRepository.save(point).getId());
        System.out.print(new JSONObject(point));
        return new ResponseEntity<>(point, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllPoints(@AuthenticationPrincipal User user){
        return new ResponseEntity<>(user.getPoints(), HttpStatus.OK);
    }
}
