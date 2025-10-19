package com.platinum.task_manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskRepository repo;

    @GetMapping
    public List<Task> allTasks() { 
        return repo.findAll(); 
    }

    @GetMapping("/{id}")
    public Task get(@PathVariable String id) { 
        return repo.findById(id).orElse(null); 
    }

    @PostMapping
    public Task create(@RequestBody Task t) { 
        if (t.getTaskExecutions() == null) {
            t.setTaskExecutions(new ArrayList<>());
        }
        return repo.save(t); 
    }

    @PutMapping("/{id}")
    public Task update(@PathVariable String id, @RequestBody Task t) {
        t.setId(id);
        return repo.save(t);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) { 
        repo.deleteById(id); 
    }

    @GetMapping("/search")
    public List<Task> search(@RequestParam String name) {
        List<Task> all = repo.findAll();
        List<Task> result = new ArrayList<>();
        for (Task t : all) {
            if (t.getName() != null && t.getName().contains(name)) {
                result.add(t);
            }
        }
        return result;
    }

    @PutMapping("/{id}/execute")
    public Task execute(@PathVariable String id) {
        Task t = repo.findById(id).orElse(null);
        if (t == null) return null;

        try {
            String cmd = t.getCommand();
            if (cmd != null && cmd.startsWith("echo ")) {
                long start = System.currentTimeMillis();
                
                ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", cmd);
                Process process = builder.start();
                
                String output = "";
                try (Scanner s = new Scanner(process.getInputStream()).useDelimiter("\\A")) {
                    output = s.hasNext() ? s.next() : "";
                }
                
                process.waitFor();
                long end = System.currentTimeMillis();

                TaskExecution exec = new TaskExecution();
                exec.setStartTime(new Date(start));
                exec.setEndTime(new Date(end));
                exec.setOutput(output);

                if (t.getTaskExecutions() == null) {
                    t.setTaskExecutions(new ArrayList<>());
                }
                t.getTaskExecutions().add(exec);
                
                repo.save(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return repo.findById(id).orElse(null);
    }
}
