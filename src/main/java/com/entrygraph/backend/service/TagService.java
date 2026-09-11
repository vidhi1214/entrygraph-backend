package com.entrygraph.backend.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.entrygraph.backend.dto.request.CreateTagRequest;
import com.entrygraph.backend.dto.response.TagResponse;
import com.entrygraph.backend.entity.Tag;
import com.entrygraph.backend.exception.DuplicateTagException;
import com.entrygraph.backend.exception.TagNotFoundException;
import com.entrygraph.backend.repository.TagRepository;

@Service
public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<TagResponse> getAllTags() {
        return tagRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public TagResponse getTagById(UUID id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() ->
                        new TagNotFoundException(
                                "Tag not found with id: " + id));

        return toResponse(tag);
    }

    public TagResponse createTag(CreateTagRequest request) {
        String name = request.getName().trim();

        if (tagRepository.existsByNameIgnoreCase(name)) {
            throw new DuplicateTagException(
                    "Tag already exists with name: " + name);
        }

        Tag tag = new Tag();
        tag.setName(name);

        Tag saved = tagRepository.save(tag);

        return toResponse(saved);
    }

    public void deleteTag(UUID id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() ->
                        new TagNotFoundException(
                                "Tag not found with id: " + id));

        tagRepository.delete(tag);
    }

    private TagResponse toResponse(Tag tag) {
        TagResponse response = new TagResponse();

        response.setId(tag.getId());
        response.setName(tag.getName());
        response.setCreatedAt(tag.getCreatedAt());

        return response;
    }
}
