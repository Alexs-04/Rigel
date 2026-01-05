package korebit.controllers.rest;

import korebit.logic.services.DashboardService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping("/api/dashboard")
@RestController
public class DashBoardRestController {
    private final DashboardService dashboardService;

    public DashBoardRestController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/data")
    public Map<?, ?> getDataDashboard() {
        return dashboardService.getDashboard();
    }
}
