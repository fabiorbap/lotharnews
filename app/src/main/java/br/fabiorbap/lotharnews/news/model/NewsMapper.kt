package br.fabiorbap.lotharnews.news.model

fun NewsResponse.toNews(): News {
    return News(
        totalResults = this.totalResults,
        articles = this.articles?.map { article ->
            article.toArticle()
        }
    )
}

fun ArticleResponse.toArticle(): Article {
    return Article(
        source = source?.toSource(),
        author = author,
        title = title,
        description = description,
        url = url,
        urlToImage = urlToImage,
        publishedAt = publishedAt,
        content = content
    )
}

fun SourceResponse.toSource(): Source {
    return Source(
        id = id,
        name = name
    )
}