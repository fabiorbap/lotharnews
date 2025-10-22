package br.fabiorbap.lotharnews.article.model

import br.fabiorbap.lotharnews.common.util.getNewId

fun ArticleResponse.toEntity(): ArticleEntity =
    ArticleEntity(
        source = source?.toSource(),
        author = author,
        title = title,
        description = description,
        url = url ?: getNewId(),
        urlToImage = urlToImage,
        publishedAt = publishedAt,
        content = content,
        isFavorite = false,
    )

fun List<ArticleResponse>.toEntities(): List<ArticleEntity> = map { it.toEntity() }

fun SourceResponse.toSource(): Source =
    Source(
        id = id,
        name = name,
    )

fun ArticleEntity.toModel(): Article =
    Article(
        url = url,
        source = source,
        author = author,
        title = title,
        description = description,
        urlToImage = urlToImage,
        publishedAt = publishedAt,
        content = content,
        isFavorite = isFavorite,
    )

fun List<ArticleEntity>.toModels(): List<Article> = map { it.toModel() }
