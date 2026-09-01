package com.braingrow.controller;
import org.springframework.web.bind.annotation.*;
@RestController public class HealthController {@GetMapping("/") public String home(){return "BrainGrow V5 API is running";}@GetMapping("/api/health") public java.util.Map<String,String> health(){return java.util.Map.of("status","ok");}}
