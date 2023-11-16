package dev.namoura.joblisting.repository;

import dev.namoura.joblisting.model.Post;

import java.util.List;

public interface SearchRespository {

    List<Post> findByText(String text);
}
