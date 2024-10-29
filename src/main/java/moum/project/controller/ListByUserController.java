package moum.project.controller;


import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import moum.project.service.AchievementService;
import moum.project.service.ListByUserService;
import moum.project.vo.Achievement;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/achievement")
@RequiredArgsConstructor
public class ListByUserController {

    @NonNull
    private final AchievementService achievementService;

    @GetMapping("list")
    public String list(Model model) throws Exception {
        List<Achievement> list = achievementService.list();
        model.addAttribute("list", list);
        return "achievement/list";
    }

    @GetMapping("view")
    @ResponseBody
    public Object view(String id) throws Exception {
        return  achievementService.get(id);
    }

    @GetMapping("delete")
    @ResponseBody
    public Object delete(String id) throws Exception {
        achievementService.delete(id);
        return "Achievement with id" + id + "삭제되었습니다.";
    }

    @PostMapping("add")
    @ResponseBody
    public Object add(@RequestBody Achievement achievement) throws Exception {
        achievementService.add(achievement);
        return achievementService.get(achievement.getId());
    }

    @PostMapping("update")
    @ResponseBody
    public Object update(@RequestBody Achievement achievement) throws Exception {
        achievementService.update(achievement);
        return achievementService.get(achievement.getId());
    }


}