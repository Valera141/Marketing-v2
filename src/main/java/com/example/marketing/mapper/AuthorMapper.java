package com.example.marketing.mapper;

import com.example.marketing.dto.AuthorRequestDTO;
import com.example.marketing.dto.AuthorResponseDTO;
import com.example.marketing.model.Author;

public class AuthorMapper {

	public static AuthorResponseDTO toResponse(Author entity) {
		if (entity == null) return null;

		return AuthorResponseDTO.builder()
				.authorApiId(entity.getAuthorApiId())
				.username(entity.getUsername())
				.verified(entity.getIsVerified())
				.follower(entity.getFollowerCount())
				.priority(entity.getIsPriorityInfluencer())
				.firstRegistrationDate(entity.getFirstRegistrationDate())
				.build();
	}

	public static Author toEntity(AuthorRequestDTO request) {
		if (request == null) return null;

		return Author.builder()
				.username(request.username())
				.isVerified(request.verified())
				.followerCount(request.follower() != null ? request.follower() : 0)
				.isPriorityInfluencer(request.priority())
				.build();
	}

	public static void copyToEntity(AuthorRequestDTO request, Author existing) {
		if (request == null || existing == null) return;

		if (request.username() != null)
			existing.setUsername(request.username());

		if (request.verified() != null)
			existing.setIsVerified(request.verified());

		if (request.follower() != null)
			existing.setFollowerCount(request.follower());

		if (request.priority() != null)
			existing.setIsPriorityInfluencer(request.priority());
	}
}
