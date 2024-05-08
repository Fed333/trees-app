package com.it.revolution.trees.app.controllers;

import com.it.revolution.trees.app.mapper.TreeMapper;
import com.it.revolution.trees.app.model.dto.AddTreeRequestDto;
import com.it.revolution.trees.app.model.dto.AddTreeTaskRequestDto;
import com.it.revolution.trees.app.model.dto.TreeDto;
import com.it.revolution.trees.app.model.dto.TreeShortDto;
import com.it.revolution.trees.app.model.entity.AssignedTreeTask;
import com.it.revolution.trees.app.model.entity.Tree;
import com.it.revolution.trees.app.model.entity.TreeTaskStatus;
import com.it.revolution.trees.app.model.entity.TreeTaskType;
import com.it.revolution.trees.app.service.TreeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/trees")
public class TreeController {

    private final TreeService treeService;

    private final TreeMapper treeMapper;

    @GetMapping
    public ResponseEntity<List<TreeShortDto>> getListTreeShort(@RequestParam(value = "startX", required = false) Double startX,
                                                               @RequestParam(value = "endX", required = false) Double endX,
                                                               @RequestParam(value = "startY", required = false) Double startY,
                                                               @RequestParam(value = "endY", required = false) Double endY,
                                                               @RequestParam(value = "registration_number", required = false) String registrationNumber) {
        List<TreeShortDto> trees = treeService.getTreeShortList(startX, endX, startY, endY, registrationNumber);
        return ResponseEntity.ok(trees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TreeDto> getTree(@PathVariable("id") Long id) {
        return ResponseEntity.of(treeService.findTree(id));
    }

    @PostMapping
    public ResponseEntity<TreeDto> create(AddTreeRequestDto treeDto,
                                       @RequestParam(name = "photo", required = false) MultipartFile photo) {
        return ResponseEntity.ok(treeMapper.mapToDto(treeService.createTree(treeDto, photo)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {


        Optional<Tree> optionalTree = treeService.deleteTree(id);
        if (optionalTree.isPresent()) {
            Tree deleted = optionalTree.get();
            return ResponseEntity.ok(Map.of(
                    "message", "Successfully deleted tree with id " + id,
                    "tree", treeMapper.mapToDto(deleted))
            );
        } else {
            return ResponseEntity.ok(Map.of(
                    "message", "No tree with id " + id + " was found.")
            );
        }
    }

}
