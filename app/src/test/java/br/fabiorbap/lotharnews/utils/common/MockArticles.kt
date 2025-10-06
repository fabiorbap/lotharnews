package br.fabiorbap.lotharnews.utils.common

import br.fabiorbap.lotharnews.article.model.Article
import br.fabiorbap.lotharnews.article.model.ArticleEntity
import br.fabiorbap.lotharnews.article.model.ArticleResponse
import br.fabiorbap.lotharnews.article.model.Source
import br.fabiorbap.lotharnews.article.model.SourceResponse

val mockArticlesResponse = listOf(
    ArticleResponse(
        source = SourceResponse(null, "BBC News"),
        author = null,
        title = "Chris Mason: How Polanski's Green leadership could impact UK politics",
        description = "Zack Polanski's landslide victory is the latest example of a shake up in the country's politics.",
        url = "https://www.bbc.com/news/articles/c9d0d32q0eno",
        urlToImage = "https://ichef.bbci.co.uk/news/1024/branded_news/9ea5/live/3d188cc0-8847-11f0-b391-6936825093bd.jpg",
        publishedAt = "2025-09-02T22:49:49Z",
        content = "Chris MasonPolitical editor\r\nZack Polanski's sweary, brash and blunt victory video on social media said everything about how the Green Party of England and Wales is under new leadership.\r\nHis landsli… [+2577 chars]"
    ),
    ArticleResponse(
        source = SourceResponse(null, "BBC News"),
        author = null,
        title = "Chris Mason: How Polanski's Green leadership could impact UK politics",
        description = "Zack Polanski's landslide victory is the latest example of a shake up in the country's politics.",
        url = "https://www.bbc.com/news/articles/c9d0d32q0eno",
        urlToImage = "https://ichef.bbci.co.uk/news/1024/branded_news/9ea5/live/3d188cc0-8847-11f0-b391-6936825093bd.jpg",
        publishedAt = "2025-09-02T22:49:49Z",
        content = "Chris MasonPolitical editor\r\nZack Polanski's sweary, brash and blunt victory video on social media said everything about how the Green Party of England and Wales is under new leadership.\r\nHis landsli… [+2577 chars]"
    )
)

val mockArticles = listOf(
    Article(
        source = Source(null, "BBC News"),
        null,
        "Chris Mason: How Polanski's Green leadership could impact UK politics",
        "Zack Polanski's landslide victory is the latest example of a shake up in the country's politics.",
        "https://www.bbc.com/news/articles/c9d0d32q0eno",
        "https://ichef.bbci.co.uk/news/1024/branded_news/9ea5/live/3d188cc0-8847-11f0-b391-6936825093bd.jpg",
        "2025-09-02T22:49:49Z",
        "Chris MasonPolitical editor\r\nZack Polanski's sweary, brash and blunt victory video on social media said everything about how the Green Party of England and Wales is under new leadership.\r\nHis landsli… [+2577 chars]",
        false
    ),
    Article(
        source = Source(null, "BBC News"),
        null,
        "Chris Mason: How Polanski's Green leadership could impact UK politics",
        "Zack Polanski's landslide victory is the latest example of a shake up in the country's politics.",
        "https://www.bbc.com/news/articles/c9d0d32q0eno",
        "https://ichef.bbci.co.uk/news/1024/branded_news/9ea5/live/3d188cc0-8847-11f0-b391-6936825093bd.jpg",
        "2025-09-02T22:49:49Z",
        "Chris MasonPolitical editor\r\nZack Polanski's sweary, brash and blunt victory video on social media said everything about how the Green Party of England and Wales is under new leadership.\r\nHis landsli… [+2577 chars]",
        false
    )
)

val mockArticleEntities = listOf(
    ArticleEntity(
        source = Source(null, "BBC News"),
        author = null,
        title = "Chris Mason: How Polanski's Green leadership could impact UK politics",
        description = "Zack Polanski's landslide victory is the latest example of a shake up in the country's politics.",
        url = "https://www.bbc.com/news/articles/c9d0d32q0eno",
        urlToImage = "https://ichef.bbci.co.uk/news/1024/branded_news/9ea5/live/3d188cc0-8847-11f0-b391-6936825093bd.jpg",
        publishedAt = "2025-09-02T22:49:49Z",
        content = "Chris MasonPolitical editor\r\nZack Polanski's sweary, brash and blunt victory video on social media said everything about how the Green Party of England and Wales is under new leadership.\r\nHis landsli… [+2577 chars]",
        isFavorite = false
    ),
    ArticleEntity(
        source = Source(null, "BBC News"),
        author = null,
        title = "Chris Mason: How Polanski's Green leadership could impact UK politics",
        description = "Zack Polanski's landslide victory is the latest example of a shake up in the country's politics.",
        url = "https://www.bbc.com/news/articles/c9d0d32q0eno",
        urlToImage = "https://ichef.bbci.co.uk/news/1024/branded_news/9ea5/live/3d188cc0-8847-11f0-b391-6936825093bd.jpg",
        publishedAt = "2025-09-02T22:49:49Z",
        content = "Chris MasonPolitical editor\r\nZack Polanski's sweary, brash and blunt victory video on social media said everything about how the Green Party of England and Wales is under new leadership.\r\nHis landsli… [+2577 chars]",
        isFavorite = false
    )
)