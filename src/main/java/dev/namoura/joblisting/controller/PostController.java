package dev.namoura.joblisting.controller;

import dev.namoura.joblisting.PostRepository;
import dev.namoura.joblisting.model.Post;
import dev.namoura.joblisting.repository.SearchRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;





@RestController
@CrossOrigin(origins = "*")
public class PostController {

    @Autowired
    PostRepository repo;

    @Autowired
    SearchRespository sRepo;

    @ApiIgnore
    @RequestMapping(value="/")
    public void redirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui.html");
    }
    @PostMapping("/post")
    public Post addPost(@RequestBody Post post){
        return repo.save(post);
    }

    @GetMapping("/posts/{text}")
    public List<Post> search(@PathVariable String text){
        return sRepo.findByText(text);
            }

    @GetMapping("/posts")
    public List<Post> getAllPosts() {
        return repo.findAll();
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<String> removePost(@PathVariable String id) {
        try {
            repo.deleteById(id);
            return ResponseEntity.ok("Post removed successfully");
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
