package com.egs.shopping.service.mapper;


import com.egs.shopping.domain.*;
import com.egs.shopping.service.dto.CommentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Comment} and its DTO {@link CommentDTO}.
 */
@Mapper(componentModel = "spring", uses = {CategoryMapper.class, CustomerMapper.class})
public interface CommentMapper extends EntityMapper<CommentDTO, Comment> {

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "customer.id", target = "customerId")
    CommentDTO toDto(Comment comment);

    @Mapping(source = "categoryId", target = "category")
    @Mapping(source = "customerId", target = "customer")
    Comment toEntity(CommentDTO commentDTO);

    default Comment fromId(Long id) {
        if (id == null) {
            return null;
        }
        Comment comment = new Comment();
        comment.setId(id);
        return comment;
    }
}
