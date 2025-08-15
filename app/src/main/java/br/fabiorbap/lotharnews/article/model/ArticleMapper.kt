package br.fabiorbap.lotharnews.article.model

import java.util.UUID

fun ArticleResponse.toEntity(): ArticleEntity {
    return ArticleEntity(
        source = source?.toSource(),
        author = author,
        title = title,
        description = description,
        url = url ?: UUID.randomUUID().toString(),
        urlToImage = urlToImage,
        publishedAt = publishedAt,
        content = content,
        isFavorite = false
    )
}

fun List<ArticleResponse>.toEntities(): List<ArticleEntity> = map { it.toEntity() }

fun SourceResponse.toSource(): Source {
    return Source(
        id = id,
        name = name
    )
}

fun ArticleEntity.toModel(): Article {
    return Article(
        url = url,
        source = source,
        author = author,
        title = title,
        description = description,
        urlToImage = urlToImage,
        publishedAt = publishedAt,
        content = content
    )
}

fun List<ArticleEntity>.toModels(): List<Article> = map { it.toModel() }