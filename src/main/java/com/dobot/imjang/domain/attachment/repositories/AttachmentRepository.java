package com.dobot.imjang.domain.attachment.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dobot.imjang.domain.attachment.entities.Attachment;

public interface AttachmentRepository extends JpaRepository<Attachment, UUID> {

}
